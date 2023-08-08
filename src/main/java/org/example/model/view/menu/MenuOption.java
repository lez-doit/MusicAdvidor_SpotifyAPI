package org.example.model.view.menu;

import org.example.model.data.UserData;
import org.example.model.service.OptionProcessor;

public interface MenuOption {
    OptionProcessor processor = OptionProcessor.getInstance();
    UserData userData = UserData.getInstance();

    void print(int position);
    void execute();
}
