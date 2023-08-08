package org.example.model.view.menu;

import java.util.HashMap;
import java.util.Map;

public class Menu {
  private final Map<Integer, MenuOption> options;
  private int totalOptions;

  public Menu() {
    this.options = new HashMap<>();
    this.totalOptions = 0;
  }



  public void addOption(MenuOption option) {
    totalOptions++;
    options.put(totalOptions, option);
  }

  public void showAllOptions() {
    System.out.println();
    for(int position = 1; position <= totalOptions; position++) {
      options.get(position).print(position);
    }
  }

  public boolean hasOption(int input) {
    return options.containsKey(input);
  }

  public void executeOption(int position) {
    options.get(position).execute();
  }

  public static class Builder {
    private final Menu menu;

    public Builder() {
      menu = new Menu();
    }

    public Builder addOption(MenuOption option) {
      menu.addOption(option);
      return this;
    }

    public Menu build() {
      return menu;
    }
  }
}
