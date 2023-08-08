package org.example.model.service;

import com.google.gson.*;

import java.util.List;

import org.example.model.data.*;
import org.example.model.validator.ResponseStatus;

public class OptionProcessor {
  private static OptionProcessor instance;

  private final JavaServer server;

  private OptionProcessor() {
    server = JavaServer.getInstance();
  }

  public static OptionProcessor getInstance() {
    if (instance == null) {
      instance = new OptionProcessor();
    }
    return instance;
  }

  public void authenticateUser() {
    UserData.getInstance().setAuthorized(false);

    server.runCodeReceiver();

    String responseBody = server.sendAuthorizationRequest();

    ResponseStatus status = ResponseStatus.get(responseBody);

    switch (status) {
      case ERROR -> {
        ServerConfig.AUTH_CODE.set("");
        System.out.println("\nError while authorizing:\n" + getError(responseBody));
      }

      case EXCEPTION -> {
        ServerConfig.AUTH_CODE.set("");
        System.out.println("\nFailed to authenticate!");
      }

      case CORRECT -> {
        JsonObject json = new Gson().fromJson(responseBody, JsonObject.class);
        ServerConfig.ACCESS_TOKEN.set(json.get("access_token").getAsString());

        responseBody = server.sendGetRequest(ServerConfig.API + "/v1/me");

        status = ResponseStatus.get(responseBody);
        if (status == ResponseStatus.ERROR) {
          System.out.println("\nError while working with Spotify API:\n" + getError(responseBody));
        } else if (status == ResponseStatus.EXCEPTION) {
          System.out.println("\nFailed to get API access!");
        } else {
          json = new Gson().fromJson(responseBody, JsonObject.class);
          System.out.println("\nWelcome, " + json.get("display_name").getAsString());
          UserData.getInstance().setAuthorized(true);
        }
      }
    }
  }

  private String getError(String responseBody) {
    JsonObject json = new Gson().fromJson(responseBody, JsonObject.class);
    return json.get("error").getAsJsonObject().get("message").getAsString();
  }

  public void showNewAlbums() {
    String responseBody =
        server.sendGetRequest(
            ServerConfig.API
                + "/v1/browse/new-releases?limit="
                + BrowserConfig.DATA_PER_PAGE
                + "&offset="
                + (BrowserConfig.DATA_PER_PAGE.get() * BrowserConfig.PAGE.get()));

    SpotifyParser parser = new SpotifyAlbum.Parser();

    List<? extends Parseable> albums = parser.parse(responseBody);

    printPage("\nNew albums on Spotify:", albums);
  }

  public void showFeatured() {
    String responseBody =
        server.sendGetRequest(
            ServerConfig.API
                + "/v1/browse/featured-playlists?limit="
                + BrowserConfig.DATA_PER_PAGE
                + "&offset="
                + (BrowserConfig.DATA_PER_PAGE.get() * BrowserConfig.PAGE.get()));

    SpotifyParser parser = new SpotifyPlaylist.Parser();

    List<? extends Parseable> playlists = parser.parse(responseBody);

    printPage("\nFeatured playlists on Spotify:", playlists);
  }

  public void showCategories() {
    String responseBody = server.sendGetRequest(
            ServerConfig.API
                    + "/v1/browse/categories?limit="
                    + BrowserConfig.DATA_PER_PAGE
                    + "&offset="
                    + (BrowserConfig.DATA_PER_PAGE.get() * BrowserConfig.PAGE.get())
    );

    JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
    JsonObject categories = jsonResponse.getAsJsonObject("categories");
    JsonArray items = categories.getAsJsonArray("items");

    System.out.println("\nMusic categories:");
    int position = 1;
    for (JsonElement item : items) {
      JsonObject itemObject = item.getAsJsonObject();
      String categoryName = itemObject.get("name").getAsString();
      System.out.println(position + ". " + categoryName);
      position++;
    }

    System.out.println("\n- Page " + (BrowserConfig.PAGE.get() + 1) + " -");
  }

  private void printPage(String pageName, List<? extends Parseable> data) {
    System.out.println(pageName);
    for (Parseable item : data) {
      item.print();
    }
    System.out.println("\n- Page " + (BrowserConfig.PAGE.get() + 1 + " -"));
  }
}
