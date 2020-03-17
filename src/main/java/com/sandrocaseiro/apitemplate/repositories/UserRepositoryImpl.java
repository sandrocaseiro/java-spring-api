package com.sandrocaseiro.apitemplate.repositories;

import com.sandrocaseiro.apitemplate.models.domain.EUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public EUser findByUsernameActive(String username) {
        Query query = entityManager.createNativeQuery("select * from \"USER\" where email = :username and active = 1", EUser.class)
            .setParameter("username", username);

        try {
            return (EUser) query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<EUser> findByGroup(int groupId) {
        //The destiny object must have all of the columns returned by the procedure
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_findByGroup", EUser.class)
            .registerStoredProcedureParameter("p_group_id", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("p_result", void.class, ParameterMode.REF_CURSOR)
            .setParameter("p_group_id", groupId);

        return (List<EUser>)query.getResultList();
    }
}
