package com.sandrocaseiro.apitemplate.repositories;

import com.sandrocaseiro.apitemplate.models.domain.EUser;

import java.util.List;

public interface UserRepositoryCustom {
    EUser findByUsernameActive(String username);

    List<EUser> findByGroup(int groupId);
}
