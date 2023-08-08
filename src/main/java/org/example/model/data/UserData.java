package org.example.model.data;

public class UserData {
  private static UserData instance;
  private boolean authorized;

  private UserData() {
    this.authorized = false;
  }

  public static UserData getInstance() {
    if (instance == null) {
      instance = new UserData();
    }
    return instance;
  }

  public boolean isAuthorized() {
      return authorized;
  }

  public void setAuthorized(boolean state) {
    this.authorized = state;
  }

  public void nextPage() {

  }
}
