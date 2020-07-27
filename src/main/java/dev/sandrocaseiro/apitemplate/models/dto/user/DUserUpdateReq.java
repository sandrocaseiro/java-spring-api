package dev.sandrocaseiro.apitemplate.models.dto.user;

import dev.sandrocaseiro.apitemplate.validations.Cpf;
import dev.sandrocaseiro.apitemplate.validations.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Schema(description = "Model for user update")
public class DUserUpdateReq {
    @NotEmpty
    @Size(max = 50)
    @Schema(description = "User's name", required = true, example = "user1")
    private String name;

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
