package com.sandrocaseiro.apitemplate.controllers;

import com.sandrocaseiro.apitemplate.exceptions.BindValidationException;
import com.sandrocaseiro.apitemplate.mappers.UserMapper;
import com.sandrocaseiro.apitemplate.models.DPage;
import com.sandrocaseiro.apitemplate.models.DPageable;
import com.sandrocaseiro.apitemplate.models.DSearchReq;
import com.sandrocaseiro.apitemplate.models.domain.EUser;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserBalanceUpdateReq;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserCreateReq;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserCreateResp;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserGroupResp;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserReportResp;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserResp;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserUpdateReq;
import com.sandrocaseiro.apitemplate.models.jpa.JUserGroup;
import com.sandrocaseiro.apitemplate.models.service.SUser;
import com.sandrocaseiro.apitemplate.security.UserPrincipal;
import com.sandrocaseiro.apitemplate.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestScope
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/v1/users")
    @ResponseStatus(HttpStatus.CREATED)
    public DUserCreateResp createUser(@RequestBody @Valid DUserCreateReq dto,
                                      Errors bindingErrors) {
        if (bindingErrors.hasErrors())
            throw new BindValidationException(bindingErrors);

        EUser user = userMapper.toEUser(dto);
        user = userService.create(user);

        return userMapper.toDUserCreateResp(user);
    }

    @PutMapping("/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody @Valid DUserUpdateReq dto,
                           @PathVariable int id,
                           Errors bindingErrors) {
        if (bindingErrors.hasErrors())
            throw new BindValidationException(bindingErrors);

        EUser user = userMapper.toEUser(dto);
        user.setId(id);

        userService.update(user);
    }

    @PatchMapping("/v1/users/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isGroup(1)")
    public void updateUserBalance(@PathVariable int id,
                                  @RequestBody @Valid DUserBalanceUpdateReq dto,
                                  Errors bindingErrors) {
        if (bindingErrors.hasErrors())
            throw new BindValidationException(bindingErrors);

        userService.updateBalance(id, dto.getBalance());
    }

    @PatchMapping("/v2/users/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isGroup(1)")
    public void updateUserBalanceApi(@PathVariable int id,
                                     @RequestBody @Valid DUserBalanceUpdateReq dto,
                                     Errors bindingErrors) {
        if (bindingErrors.hasErrors())
            throw new BindValidationException(bindingErrors);

        userService.updateBalanceApi(id, dto.getBalance());
    }

    @DeleteMapping("/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.delete(id);
    }

    @GetMapping("/v1/users")
    public List<DUserGroupResp> searchUsers(@RequestParam("$search") String search) {
        List<EUser> users = userService.searchByName(search);
        return userMapper.toDUserGroupRespList(users);
    }

    //Sensitive search
    @PostMapping("/v1/users/search")
    public List<DUserGroupResp> searchUsersSensitive(@RequestBody @Valid DSearchReq search,
                                                     Errors bindingErrors) {
        if (bindingErrors.hasErrors())
            throw new BindValidationException(bindingErrors);

        List<EUser> users = userService.searchByCpf(search.getSearchContent());
        return userMapper.toDUserGroupRespList(users);
    }

    //Get token user
    @GetMapping("/v1/users/current")
    public DUserGroupResp findUser(@AuthenticationPrincipal UserPrincipal principal) {
        JUserGroup user = userService.findById(principal.getId());

        return userMapper.toDUserGroupResp(user);
    }

    @GetMapping("/v1/users/{id}")
    @PreAuthorize("canAccessUser(#id)")
    public DUserGroupResp findUser(@PathVariable int id) {
        JUserGroup user = userService.findById(id);

        return userMapper.toDUserGroupResp(user);
    }

    @GetMapping("/v2/users/{id}")
    @PreAuthorize("canAccessUser(#id)")
    public DUserResp findUserApi(@PathVariable int id) {
        SUser user = userService.findByIdApi(id);

        return userMapper.toDUserResp(user);
    }

    @GetMapping("/v1/users/group/{id}")
    public List<DUserResp> findAllByGroup(@PathVariable int id) {
        List<EUser> users = userService.findByGroup(id);

        return userMapper.toDUserRespList(users);
    }

    @GetMapping("/v1/users/active")
    public DPage<DUserGroupResp> findAll(DPageable pageable) {
        Page<EUser> users = userService.findAllActive(pageable.asPageable());
        return userMapper.toDUserGroupRespPaged(users);
    }

    @GetMapping("/v1/users/report")
    public List<DUserReportResp> usersReport() {
        List<EUser> users = userService.findAll();

        return userMapper.toDUserReportRespList(users, "BRL");
    }
}
