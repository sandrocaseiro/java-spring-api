package dev.sandrocaseiro.apitemplate.controllers;

import dev.sandrocaseiro.apitemplate.exceptions.BindValidationException;
import dev.sandrocaseiro.apitemplate.mappers.UserMapper;
import dev.sandrocaseiro.apitemplate.models.DPage;
import dev.sandrocaseiro.apitemplate.models.DPageable;
import dev.sandrocaseiro.apitemplate.models.DResponse;
import dev.sandrocaseiro.apitemplate.models.DSearchReq;
import dev.sandrocaseiro.apitemplate.models.domain.EUser;
import dev.sandrocaseiro.apitemplate.models.dto.user.*;
import dev.sandrocaseiro.apitemplate.models.jpa.JUserGroup;
import dev.sandrocaseiro.apitemplate.models.service.SUser;
import dev.sandrocaseiro.apitemplate.security.UserPrincipal;
import dev.sandrocaseiro.apitemplate.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "User Operations")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/v1/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create User", description = "Create a new user", responses = {
        @ApiResponse(responseCode = "201", description = "Created", content = {@Content(schema = @Schema(implementation = DUserCreateResp.DResponseDUserCreateResp.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
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
    @Operation(summary = "Update User", description = "Update an user", responses = {
        @ApiResponse(responseCode = "204", description = "Updated"),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public void updateUser(@Parameter(description = "User's id", in = ParameterIn.PATH, required = true, example = "1")
                           @PathVariable int id,
                           @RequestBody @Valid DUserUpdateReq dto,
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
    @Operation(summary = "Update User's balance", description = "Update an user's balance", responses = {
        @ApiResponse(responseCode = "204", description = "Updated"),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public void updateUserBalance(@Parameter(description = "User's id", in = ParameterIn.PATH, required = true, example = "1")
                                  @PathVariable int id,
                                  @RequestBody @Valid DUserBalanceUpdateReq dto,
                                  Errors bindingErrors) {
        if (bindingErrors.hasErrors())
            throw new BindValidationException(bindingErrors);

        userService.updateBalance(id, dto.getBalance());
    }

    @PatchMapping("/v2/users/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isGroup(1)")
    @Operation(summary = "Update User's balance", description = "Update an user's balance", responses = {
        @ApiResponse(responseCode = "204", description = "Updated"),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public void updateUserBalanceApi(@Parameter(description = "User's id", in = ParameterIn.PATH, required = true, example = "1")
                                     @PathVariable int id,
                                     @RequestBody @Valid DUserBalanceUpdateReq dto,
                                     Errors bindingErrors) {
        if (bindingErrors.hasErrors())
            throw new BindValidationException(bindingErrors);

        userService.updateBalanceApi(id, dto.getBalance());
    }

    @DeleteMapping("/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user", description = "Delete an user", responses = {
        @ApiResponse(responseCode = "204", description = "Deleted"),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public void deleteUser(@Parameter(description = "User's id", in = ParameterIn.PATH, required = true, example = "1")
                           @PathVariable int id) {
        userService.delete(id);
    }

    @GetMapping("/v1/users")
    @Operation(summary = "Search users", description = "Search users by name", responses = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public List<DUserGroupResp> searchUsers(@Parameter(description = "Search string", in = ParameterIn.QUERY, example = "user1")
                                            @RequestParam(value = "$search", required = false) String search) {
        List<EUser> users = userService.searchByName(search);
        return userMapper.toDUserGroupRespList(users);
    }

    //Sensitive search
    @PostMapping("/v1/users/search")
    @Operation(summary = "Search users", description = "Search users using sensitive information", responses = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public List<DUserGroupResp> searchUsersSensitive(@RequestBody @Valid DSearchReq search,
                                                     Errors bindingErrors) {
        if (bindingErrors.hasErrors())
            throw new BindValidationException(bindingErrors);

        List<EUser> users = userService.searchByCpf(search.getSearchContent());
        return userMapper.toDUserGroupRespList(users);
    }

    //Get token user
    @GetMapping("/v1/users/current")
    @Operation(summary = "Get token's user", description = "Get current token's user information", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = DUserGroupResp.DResponseDUserGroupResp.class))}),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public DUserGroupResp findUser(@AuthenticationPrincipal UserPrincipal principal) {
        JUserGroup user = userService.findById(principal.getId());

        return userMapper.toDUserGroupResp(user);
    }

    @GetMapping("/v1/users/{id}")
    @PreAuthorize("canAccessUser(#id)")
    @Operation(summary = "Get user", description = "Get user by Id", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = DUserGroupResp.DResponseDUserGroupResp.class))}),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public DUserGroupResp findUser(@Parameter(description = "User's id", in = ParameterIn.PATH, required = true, example = "1")
                                   @PathVariable int id) {
        JUserGroup user = userService.findById(id);

        return userMapper.toDUserGroupResp(user);
    }

    @GetMapping("/v2/users/{id}")
    @PreAuthorize("canAccessUser(#id)")
    @Operation(summary = "Get user", description = "Get user by Id", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = DUserGroupResp.DResponseDUserGroupResp.class))}),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public DUserResp findUserApi(@Parameter(description = "User's id", in = ParameterIn.PATH, required = true, example = "1")
                                 @PathVariable int id) {
        SUser user = userService.findByIdApi(id);

        return userMapper.toDUserResp(user);
    }

    @GetMapping("/v1/users/group/{id}")
    @Operation(summary = "Get users by group", description = "Get all users by group id", responses = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public List<DUserResp> findAllByGroup(@Parameter(description = "Group Id", in = ParameterIn.PATH, required = true, example = "1")
                                          @PathVariable int id) {
        List<EUser> users = userService.findByGroup(id);

        return userMapper.toDUserRespList(users);
    }

    @GetMapping("/v1/users/active")
    @Operation(summary = "Get all active users", description = "Get all active users with paging", responses = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public DPage<DUserGroupResp> findAll(DPageable pageable) {
        Page<EUser> users = userService.findAllActive(pageable.asPageable());
        return userMapper.toDUserGroupRespPaged(users);
    }

    @GetMapping("/v1/users/report")
    @Operation(summary = "Get all users", description = "Get a report for all users", responses = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public List<DUserReportResp> usersReport() {
        List<EUser> users = userService.findAll();

        return userMapper.toDUserReportRespList(users, "BRL");
    }
}
