package dev.sandrocaseiro.template.models.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "Model for file upload")
public class DSaveFileReq {
    @Schema(description = "File id", required = true, example = "1")
    private int id;
    @Schema(description = "File name", required = true, example = "file")
    private String name;
    @Schema(description = "File", required = true)
    private MultipartFile attachment;
}
