package dev.sandrocaseiro.template.models.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Model to return user")
public class DUserResp {
    @Schema(description = "User's id", required = true, example = "1")
    private int id;
    @Schema(description = "User's name", required = true, example = "user1")
    private String name;
    @Schema(description = "User's e-mail", required = true, example = "user1@mail.com")
    private String email;
}
