package org.example.model.view.menu;

import org.example.model.data.BrowserConfig;
import org.example.model.data.ServerConfig;
import org.example.model.view.ConsoleBrowser;

public class FeaturedOption implements MenuOption {

  @Override
  public void print(int position) {
    System.out.println(position + ". Show featured playlists.");
  }

  @Override
  public void execute() {
    if (userData.isAuthorized()) {
      BrowserConfig.MAX_PAGE.set(100 / BrowserConfig.DATA_PER_PAGE.get() - 1);

      processor.showFeatured();

      ConsoleBrowser.showPagesMenu(this);
    } else {
      System.out.println("\nYou have to authorize before using the app.");
      System.out.println("Please follow the link below to log in your Spotify account");
      System.out.println(ServerConfig.buildAuthenticateLink());

      processor.authenticateUser();
    }
  }
}
