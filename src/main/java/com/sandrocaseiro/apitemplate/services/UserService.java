package com.sandrocaseiro.apitemplate.services;

import com.sandrocaseiro.apitemplate.clients.UserClient;
import com.sandrocaseiro.apitemplate.exceptions.ForbiddenException;
import com.sandrocaseiro.apitemplate.exceptions.ItemNotFoundException;
import com.sandrocaseiro.apitemplate.exceptions.UsernameAlreadyExistsException;
import com.sandrocaseiro.apitemplate.models.api.AUserByIdResp;
import com.sandrocaseiro.apitemplate.models.api.AUserUpdateReq;
import com.sandrocaseiro.apitemplate.models.domain.EUser;
import com.sandrocaseiro.apitemplate.models.jpa.JUserGroup;
import com.sandrocaseiro.apitemplate.models.service.SUser;
import com.sandrocaseiro.apitemplate.repositories.UserRepository;
import com.sandrocaseiro.apitemplate.security.IAuthenticationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IAuthenticationInfo authInfo;
    private final UserRepository userRepository;
    private final UserClient userApiClient;

    public EUser create(EUser user) {
        if (userRepository.findByUsername(user.getEmail()) != null)
            throw new UsernameAlreadyExistsException();

        user.setActive(true);
        return userRepository.save(user);
    }

    @Transactional
    public void update(EUser user) {
        EUser dbUser = userRepository.findById(user.getId()).orElseThrow(ItemNotFoundException::new);

        dbUser.setName(user.getName());
        dbUser.setCpf(user.getCpf());
        dbUser.setPassword(user.getPassword());
        dbUser.setGroupId(user.getGroupId());
        dbUser.setRoles(user.getRoles());

        userRepository.save(dbUser);
    }

    @Transactional
    public void updateBalance(int id, BigDecimal balance) {
        int updatedUsers = userRepository.updateBalance(id, balance);
        if (updatedUsers == 0)
            throw new ItemNotFoundException();
    }

    public void updateBalanceApi(int id, BigDecimal balance) {
        userApiClient.updateBalance(id, new AUserUpdateReq(balance));
    }

    @Transactional
    public void delete(int id) {
        if (authInfo.getId() == id)
            throw new ForbiddenException();

        EUser user = userRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        user.setActive(false);

        userRepository.save(user);
    }

    public JUserGroup findById(int id) {
        JUserGroup user = userRepository.findOneById(id);
        if (user == null)
            throw new ItemNotFoundException();

        return user;
    }

    public SUser findByIdApi(int id) {
        AUserByIdResp user = userApiClient.getById(id).getData();

        return new SUser(user.getId(), user.getName(), user.getEmail());
    }

    public List<EUser> searchByName(String name) {
        return userRepository.searchByName(name);
    }

    public List<EUser> searchByCpf(String cpf) {
        return userRepository.searchByCpf(cpf);
    }

    public List<EUser> findByGroup(int groupId) {
        return userRepository.findByGroup(groupId);
    }

    public Page<EUser> findAllActive(Pageable pageable) {
        return userRepository.findAllActive(pageable);
    }

    public List<EUser> findAll() {
        return userRepository.findAll();
    }
}
