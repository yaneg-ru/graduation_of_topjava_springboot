package ru.yaneg.graduation_of_topjava_springboot.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Messages {

    private final MessageSourceAccessor messageSource;

    @Autowired
    public Messages(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    public String getMessage(String msg, Locale locale) {
        return messageSource.getMessage(msg, msg, locale);
    }

}
