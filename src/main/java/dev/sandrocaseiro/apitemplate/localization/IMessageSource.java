package dev.sandrocaseiro.apitemplate.localization;

import dev.sandrocaseiro.apitemplate.exceptions.AppErrors;

import java.util.Locale;

public interface IMessageSource {
    String getMessage(String resource);

    String getMessage(String resource, Object[] params);

    String getMessage(AppErrors error);

    String getMessage(AppErrors error, Object[] params);

    String getMessage(Messages message);

    Locale getCurrentLocale();

    String getCurrentLocaleTag();
}
