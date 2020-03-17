package com.sandrocaseiro.apitemplate.models.dto.user;

import com.sandrocaseiro.apitemplate.validations.Cpf;
import com.sandrocaseiro.apitemplate.validations.NotEmpty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class DUserUpdateReq {
    @NotNull
    @Size(max = 50)
    private String name;

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
