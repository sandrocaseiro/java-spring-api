package dev.sandrocaseiro.template.models;

import dev.sandrocaseiro.template.validations.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Model to search using sensitive information")
public class DSearchReq {
    @NotEmpty
    @Schema(description = "Search string", required = true, example = "29035196090")
    private String searchContent;
}
