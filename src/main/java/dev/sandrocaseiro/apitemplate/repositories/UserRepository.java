package dev.sandrocaseiro.apitemplate.repositories;

import dev.sandrocaseiro.apitemplate.models.domain.EUser;
import dev.sandrocaseiro.apitemplate.models.jpa.JUserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<EUser, Integer>, UserRepositoryCustom {
    @Modifying
    @Query("update User set balance = :balance, updateDate = SYSDATE where id = :id")
    int updateBalance(int id, BigDecimal balance);

    @Query("select u from User u where u.email = :username")
    EUser findByUsername(String username);

    @Query("select u.id as id, u.name as name, u.email as email, u.group.name as group from User u where u.id = :id")
    JUserGroup findOneById(int id);

    @Query("select u from User u inner join fetch u.group g where lower(u.name) like lower(concat('%', :name, '%'))")
    List<EUser> searchByName(String name);

    @Query("select u from User u inner join fetch u.group g where lower(u.cpf) like lower(concat('%', :cpf, '%'))")
    List<EUser> searchByCpf(String cpf);

    @Query(
        value = "select u from User u inner join fetch u.group where u.active = true",
        countQuery = "select count(1) from User u where u.active = true"
    )
    Page<EUser> findAllActive(Pageable pageable);

    @Query("select u from User u inner join fetch u.group")
    List<EUser> findAll();
}
