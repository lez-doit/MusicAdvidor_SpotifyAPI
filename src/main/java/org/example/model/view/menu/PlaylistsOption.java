package org.example.model.view.menu;

public class PlaylistsOption implements MenuOption {
  @Override
  public void print(int position) {
    System.out.println(position + ". Find playlists.");
  }

  @Override
  public void execute() {}
}
