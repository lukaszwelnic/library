package org.library;

import java.util.Locale;
import org.library.config.AppConfig;
import org.library.service.MessageService;
import org.library.ui.LibraryUI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LibraryApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        LibraryUI ui = context.getBean(LibraryUI.class);

        Locale locale = ui.chooseLocale(); // UI handles prompting
        MessageService messageService = context.getBean(MessageService.class);
        messageService.setLocale(locale);

        System.out.println("Locale set to: " + locale.getDisplayLanguage());
        ui.start();
    }
}
