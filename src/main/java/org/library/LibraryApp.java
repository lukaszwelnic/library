package org.library;

import java.util.Locale;
import java.util.Scanner;
import org.library.config.AppConfig;
import org.library.service.MessageService;
import org.library.ui.LibraryUI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LibraryApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose language / Wybierz język:");
        System.out.println("1. English");
        System.out.println("2. Polski");
        System.out.print("> ");

        int choice = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();

        Locale locale = switch (choice) {
            case 1 -> new Locale("en");
            case 2 -> new Locale("pl");
            default -> Locale.ENGLISH;
        };

        LibraryUI ui = context.getBean(LibraryUI.class);

        MessageService messageService = context.getBean(MessageService.class);
        messageService.setLocale(locale);

        System.out.println("Locale set to: " + locale.getDisplayLanguage());

        ui.start();

    }
}
