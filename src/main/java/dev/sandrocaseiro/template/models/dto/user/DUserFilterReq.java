package dev.sandrocaseiro.template.models.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Model for user filter")
public class DUserFilterReq {
	@Schema(description = "User's name filter", example = "user1")
	private String name;
}
