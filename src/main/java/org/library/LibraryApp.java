package org.library;

import org.library.config.AppConfig;
import org.library.ui.LibraryUI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LibraryApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        LibraryUI libraryUI = context.getBean(LibraryUI.class);

        libraryUI.start();
    }
}
