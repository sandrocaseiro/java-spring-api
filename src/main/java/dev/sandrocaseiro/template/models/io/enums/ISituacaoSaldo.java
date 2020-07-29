package dev.sandrocaseiro.template.models.io.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ISituacaoSaldo {
    C("Credor"),
    D("Devedor");

    @Getter
    private String nome;

    public String getValue() {
        return this.name();
    }
}
