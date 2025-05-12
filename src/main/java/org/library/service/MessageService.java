package org.library.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;

@Service
public class MessageService {
    private final MessageSource messageSource;
    private Locale locale = Locale.getDefault();

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String code, Object... args) {
        String message = messageSource.getMessage(code, null, locale);
        return MessageFormat.format(message, args);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}

