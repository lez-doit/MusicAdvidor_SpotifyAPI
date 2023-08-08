package org.example.model.view.menu;

import org.example.model.data.BrowserConfig;

public class NextPageOption implements MenuOption {

  @Override
  public void print(int position) {
    System.out.println(position + ". Next page.");
  }

  @Override
  public void execute() {
    if(BrowserConfig.PAGE.get() + 1 <= BrowserConfig.MAX_PAGE.get()) {
      BrowserConfig.PAGE.set(BrowserConfig.PAGE.get() + 1);
    }
  }
}
