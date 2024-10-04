package hnt.com.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageHelper {

    private static MessageSource messageSource;

    @Autowired
    public MessageHelper(MessageSource messageSource) {
        MessageHelper.messageSource = messageSource;
    }

    public static String getMessage(String name, Object... parameters) {
        return messageSource.getMessage(name, parameters, Locale.ENGLISH);
    }

}
