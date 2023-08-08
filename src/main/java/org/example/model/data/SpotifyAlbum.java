package org.example.model.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class SpotifyAlbum implements Parseable {
  private final String name;
  private final List<String> artists;
  private final String url;

  public SpotifyAlbum(String name, List<String> artists, String url) {
    this.name = name;
    this.artists = artists;
    this.url = url;
  }

  @Override
  public void print() {
    System.out.println("\n" + name);
    System.out.print("Artists: ");
    for (int i = 0; i < artists.size(); i++) {
      System.out.print(artists.get(i));

      if (i < artists.size() - 1) System.out.print(", ");
    }
    System.out.println("\nLink: " + url);
  }

  public static class Parser implements SpotifyParser {

    @Override
    public List<? extends Parseable> parse(String body) {
      List<SpotifyAlbum> albums = new ArrayList<>();

      JsonArray items =
          new Gson()
              .fromJson(body, JsonObject.class)
              .getAsJsonObject("albums")
              .getAsJsonArray("items");

      for (JsonElement element : items) {
        JsonObject item = element.getAsJsonObject();

        JsonArray artistsJson = item.getAsJsonArray("artists");
        List<String> artists = new ArrayList<>();
        for (JsonElement artist : artistsJson) {
          artists.add(artist.getAsJsonObject().get("name").getAsString());
        }

        albums.add(
            new SpotifyAlbum(
                item.get("name").getAsString(),
                artists,
                item.get("external_urls").getAsJsonObject().get("spotify").getAsString()));
      }

      return albums;
    }
  }
}
