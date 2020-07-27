package dev.sandrocaseiro.apitemplate.models.dto.user;

import dev.sandrocaseiro.apitemplate.models.DResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class DUserGroupResp {
    @Schema(description = "User's id", required = true, example = "1")
    private int id;
    @Schema(description = "User's name", required = true, example = "user1")
    private String name;
    @Schema(description = "User's e-mail", required = true, example = "user1@mail.com")
    private String email;
    @Schema(description = "User's group name", required = true, example = "Group 1")
    private String group;

    @Schema(name = "DResponse<DUserGroupResp>", description = "Model to return user with group name")
    public static class DResponseDUserGroupResp extends DResponse<DUserGroupResp> {
        public DResponseDUserGroupResp(List<Error> errors, DUserGroupResp data) {
            super(errors, data);
        }
    }
}
