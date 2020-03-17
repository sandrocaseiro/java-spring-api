package com.sandrocaseiro.apitemplate.repositories;

import com.sandrocaseiro.apitemplate.models.domain.ERole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends BaseRepository<ERole, Integer> {
    @Query("select u.roles from User u where u.id = :id ")
    List<ERole> findAllByUserId(int id);
}
