package org.example.views;
import java.util.Scanner;

import org.example.models.App;
import org.example.models.enums.Menu;

public class AppView {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().checkCommand(scanner);
        } while (!App.getCurrentMenu().equals(Menu.ExitMenu));
    }
}