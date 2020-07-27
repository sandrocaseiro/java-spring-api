package dev.sandrocaseiro.apitemplate.repositories;

import dev.sandrocaseiro.apitemplate.models.domain.EUser;

import java.util.List;

public interface UserRepositoryCustom {
    EUser findByUsernameActive(String username);

    List<EUser> findByGroup(int groupId);
}
