package dev.sandrocaseiro.apitemplate.services;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.sandrocaseiro.apitemplate.clients.UserClient;
import dev.sandrocaseiro.apitemplate.exceptions.AppErrors;
import dev.sandrocaseiro.apitemplate.exceptions.AppException;
import dev.sandrocaseiro.apitemplate.models.AResponse;
import dev.sandrocaseiro.apitemplate.models.api.AUserByIdResp;
import dev.sandrocaseiro.apitemplate.models.api.AUserUpdateReq;
import dev.sandrocaseiro.apitemplate.models.domain.EUser;
import dev.sandrocaseiro.apitemplate.models.jpa.JUserGroup;
import dev.sandrocaseiro.apitemplate.models.service.SUser;
import dev.sandrocaseiro.apitemplate.repositories.UserRepository;
import dev.sandrocaseiro.apitemplate.security.IAuthenticationInfo;
import dev.sandrocaseiro.apitemplate.utils.JsonUtil;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
            throw AppException.of(AppErrors.USERNAME_ALREADY_EXISTS);

        user.setActive(true);
        return userRepository.save(user);
    }

    @Transactional
    public void update(EUser user) {
        EUser dbUser = userRepository.findById(user.getId()).orElseThrow(() -> AppException.of(AppErrors.ITEM_NOT_FOUND_ERROR));

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
            throw AppException.of(AppErrors.ITEM_NOT_FOUND_ERROR);
    }

    public void updateBalanceApi(int id, BigDecimal balance) {
        Response resp = userApiClient.updateBalance(id, new AUserUpdateReq(balance));
        if (resp.status() == HttpStatus.NOT_FOUND.value())
            throw AppException.of(AppErrors.NOT_FOUND_ERROR);
        else if (HttpStatus.valueOf(resp.status()).isError())
            throw AppException.of(AppErrors.API_ERROR);
    }

    @Transactional
    public void delete(int id) {
        if (authInfo.getId() == id)
            throw AppException.of(AppErrors.FORBIDDEN_ERROR);

        EUser user = userRepository.findById(id).orElseThrow(() -> AppException.of(AppErrors.ITEM_NOT_FOUND_ERROR));
        user.setActive(false);

        userRepository.save(user);
    }

    public JUserGroup findById(int id) {
        JUserGroup user = userRepository.findOneById(id);
        if (user == null)
            throw AppException.of(AppErrors.ITEM_NOT_FOUND_ERROR);

        return user;
    }

    public SUser findByIdApi(int id) {
        Response resp = userApiClient.getById(id);
        if (resp.status() == HttpStatus.NOT_FOUND.value())
            throw AppException.of(AppErrors.NOT_FOUND_ERROR);
        else if (HttpStatus.valueOf(resp.status()).isError())
            throw AppException.of(AppErrors.API_ERROR);

        AResponse<AUserByIdResp> userResp = JsonUtil.deserialize(resp.body(), new TypeReference<AResponse<AUserByIdResp>>(){});

        return new SUser(userResp.getData().getId(), userResp.getData().getName(), userResp.getData().getEmail());
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
