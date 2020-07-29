package dev.sandrocaseiro.template.models.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ATokenReq {
    private String username;
    private String password;
}
