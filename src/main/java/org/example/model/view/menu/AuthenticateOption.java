package org.example.model.view.menu;

import org.example.model.data.ServerConfig;
import org.example.model.data.UserData;

public class AuthenticateOption implements MenuOption {
  @Override
  public void print(int position) {
    System.out.println(position + ". Authenticate.");
  }

  @Override
  public void execute() {
    if (userData.isAuthorized()) {
      System.out.println("\nYou have already logged in.");
    } else {
      System.out.println("\nPlease follow the link below to log in your Spotify account");
      System.out.println(ServerConfig.buildAuthenticateLink());

      processor.authenticateUser();
    }
  }
}
