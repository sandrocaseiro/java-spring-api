package dev.sandrocaseiro.template.models.dto.user;


import dev.sandrocaseiro.template.validations.Cpf;
import dev.sandrocaseiro.template.validations.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Schema(description = "Model for user creation")
public class DUserCreateReq {
    @NotEmpty
    @Size(max = 50)
    @Schema(description = "User's name", required = true, example = "user1")
    private String name;

    @NotEmpty
    @Email
    @Size(max = 150)
    @Schema(description = "User's e-mail", required = true, example = "user1@mail.com")
    private String email;

    @Cpf
    @NotEmpty
    @Size(max = 11)
    @Schema(description = "User's CPF", required = true, example = "29035196090")
    private String cpf;

    @NotEmpty
    @Size(max = 20)
    @Schema(description = "User's password", required = true, example = "1234")
    private String password;

    @NotEmpty
    @Min(1)
    @Schema(description = "User's Group Id", required = true, example = "1")
    private Integer groupId;

    @NotEmpty
    @Schema(description = "User's Role id's", required = true, example = "[1,2]")
    private List<Integer> roles;
}
