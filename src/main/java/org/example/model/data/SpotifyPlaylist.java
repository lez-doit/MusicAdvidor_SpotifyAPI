package org.example.model.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class SpotifyPlaylist implements Parseable {

  private static SpotifyAlbum.Parser parser;
  private final String name;
  private final String owner;
  private final String description;
  private final String url;

  public SpotifyPlaylist(String name, String owner, String description, String url) {
    this.name = name;
    this.owner = owner;
    this.description = description;
    this.url = url;
  }

  @Override
  public void print() {
    System.out.println("\n" + name + " by " + owner);
    System.out.println("\"" + description + "\"");
    System.out.println("Link: " + url);
  }

  public static class Parser implements SpotifyParser {

    @Override
    public List<? extends Parseable> parse(String body) {
      List<SpotifyPlaylist> playlists = new ArrayList<>();

      JsonObject playlistsObject =
          new Gson().fromJson(body, JsonObject.class).getAsJsonObject("playlists");

      JsonArray items = playlistsObject.getAsJsonArray("items");

      for (JsonElement element : items) {
        JsonObject item = element.getAsJsonObject();

        JsonObject ownerObject = item.getAsJsonObject("owner");

        String owner = ownerObject.get("display_name").getAsString();

        String name = item.get("name").getAsString();

        String description = item.get("description").getAsString();

        String url = item.getAsJsonObject("external_urls").get("spotify").getAsString();

        playlists.add(new SpotifyPlaylist(name, owner, description, url));
      }

      return playlists;
    }
  }
}
