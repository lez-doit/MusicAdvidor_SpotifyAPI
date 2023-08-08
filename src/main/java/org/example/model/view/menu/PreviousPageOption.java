package org.example.model.view.menu;

import org.example.model.data.BrowserConfig;

public class PreviousPageOption implements MenuOption {

  @Override
  public void print(int position) {
    System.out.println(position + ". Previous page.");
  }

  @Override
  public void execute() {
    if(BrowserConfig.PAGE.get() + 1 >= 2) {
      BrowserConfig.PAGE.set(BrowserConfig.PAGE.get() - 1);
    }
  }
}
