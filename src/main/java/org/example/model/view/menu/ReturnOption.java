package org.example.model.view.menu;

public class ReturnOption implements MenuOption {

    @Override
    public void print(int position) {
        System.out.println(0 + ". Back.");
    }

    @Override
    public void execute() {

    }
}
