package dev.sandrocaseiro.template.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;

@AllArgsConstructor
@Getter
public class UserPrincipal {
    private final int id;
    private final String name;
    private final String email;
    private final int groupId;
    private final HashSet<Integer> roles;
}
