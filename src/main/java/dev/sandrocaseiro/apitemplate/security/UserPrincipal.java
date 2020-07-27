package dev.sandrocaseiro.apitemplate.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;

@AllArgsConstructor
@Getter
public class UserPrincipal {
    private int id;
    private String name;
    private String email;
    private int groupId;
    private HashSet<Integer> roles;
}
