package com.sandrocaseiro.apitemplate.security;

import com.google.common.base.Objects;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class TokenUser extends User {
    private static final long serialVersionUID = 600L;

    private int id;
    private String name;
    private int groupId;
    private List<Integer> roles;

    public TokenUser(String username, String password, int id, String name, int groupId, List<Integer> roles) {
        super(username, password, Collections.emptyList());
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.roles = new ArrayList<>(roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        TokenUser tokenUser = (TokenUser) o;
        return Objects.equal(getUsername(), tokenUser.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getUsername());
    }
}
