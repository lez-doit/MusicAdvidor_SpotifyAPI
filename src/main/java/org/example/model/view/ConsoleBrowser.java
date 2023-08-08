package org.example.model.view;

import java.util.Scanner;

import org.example.model.data.BrowserConfig;
import org.example.model.view.menu.*;

public class ConsoleBrowser {
  private static Scanner scanner;

  private static ConsoleBrowser instance;

  public ConsoleBrowser() {
    scanner = new Scanner(System.in);
    Menu mainMenu =
        new Menu.Builder()
            .addOption(new AuthenticateOption())
            .addOption(new NewOption())
            .addOption(new FeaturedOption())
            .addOption(new CategoriesOption())
            .addOption(new ExitOption())
            .build();

    System.out.println("\n---------------------------------------------");
    System.out.println("Welcome to the Music Advisor v1.0 by lez-doit");
    System.out.println("---------------------------------------------");

    run(mainMenu);
  }

  public static void run(Menu menu) {
    while (true) {
      menu.showAllOptions();

      int input = handleInput();

      if (input == 0) {
        break;
      } else if (menu.hasOption(input)) {
        menu.executeOption(input);
      } else {
        System.out.println("\nInvalid input, please enter correct number.");
      }
    }
  }

  private static int handleInput() {
    if (scanner.hasNextInt()) {
      return scanner.nextInt();
    } else {
      scanner.next();
      return -1;
    }
  }

  public static void showPagesMenu(MenuOption option) {
    Menu pagesMenu =
        new Menu.Builder()
            .addOption(new PreviousPageOption())
            .addOption(new NextPageOption())
            .addOption(new ReturnOption())
            .build();

    pagesMenu.showAllOptions();

    int input = handleInput();

    if (pagesMenu.hasOption(input)) {
      pagesMenu.executeOption(input);
      option.execute();
    } else {
      BrowserConfig.PAGE.set(0);
    }
  }
}
