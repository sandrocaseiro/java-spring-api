package com.sandrocaseiro.apitemplate.models.io.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum IPosicaoSaldo {
    F("Final"),
    P("Parcial"),
    I("Intra-Dia");

    @Getter
    private String nome;

    public String getValue() {
        return this.name();
    }
}
