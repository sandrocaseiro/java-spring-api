package dev.sandrocaseiro.apitemplate.models.dto.user;

import dev.sandrocaseiro.apitemplate.models.DResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class DUserCreateResp {
    @Schema(description = "User's id", example = "1")
    private int id;
    @Schema(description = "User's name", example = "user1")
    private String name;
    @Schema(description = "User's e-mail", example = "user1@mail.com")
    private String email;
    @Schema(description = "User's Group Id", example = "1")
    private int groupId;
    @Schema(description = "User's Role id's", example = "[1,2]")
    private List<Integer> roles;

    @Schema(name = "DResponse<DUserCreateResp>", description = "Response data for user created successfully")
    public static class DResponseDUserCreateResp extends DResponse<DUserCreateResp> {
        public DResponseDUserCreateResp(List<Error> errors, DUserCreateResp data) {
            super(errors, data);
        }
    }
}
