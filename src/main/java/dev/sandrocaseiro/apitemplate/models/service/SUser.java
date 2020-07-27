package dev.sandrocaseiro.apitemplate.models.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SUser {
    private Integer id;
    private String name;
    private String email;
}
