package org.example.model.view.menu;

public class ExitOption implements MenuOption {

  @Override
  public void print(int position) {
    System.out.println(0 + ". Exit.");
  }

  @Override
  public void execute() {

  }
}
