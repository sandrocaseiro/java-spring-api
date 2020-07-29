package dev.sandrocaseiro.template.repositories;

import dev.sandrocaseiro.template.models.domain.EUser;

import java.util.List;

public interface UserRepositoryCustom {
    EUser findByUsernameActive(String username);

    List<EUser> findByGroup(int groupId);
}
