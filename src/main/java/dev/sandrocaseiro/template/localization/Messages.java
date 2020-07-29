package dev.sandrocaseiro.template.localization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Messages {
    TEMP("message.temp")
    ;

    @Getter
    private final String value;
}
