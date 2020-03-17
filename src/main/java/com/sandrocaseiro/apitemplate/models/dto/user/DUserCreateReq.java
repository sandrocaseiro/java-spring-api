package com.sandrocaseiro.apitemplate.models.dto.user;

import com.sandrocaseiro.apitemplate.validations.Cpf;
import com.sandrocaseiro.apitemplate.validations.Email;
import com.sandrocaseiro.apitemplate.validations.NotEmpty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class DUserCreateReq {
    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 150)
    @Email
    private String email;

    @Cpf
    @NotNull
    @Size(max = 11)
    private String cpf;

    @NotNull
    @Size(max = 20)
    private String password;

    @NotNull
    @Min(1)
    private Integer groupId;

    @NotEmpty
    private List<Integer> roles;
}
