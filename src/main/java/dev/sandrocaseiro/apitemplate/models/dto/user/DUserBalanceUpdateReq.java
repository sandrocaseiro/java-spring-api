package dev.sandrocaseiro.apitemplate.models.dto.user;

import dev.sandrocaseiro.apitemplate.validations.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Schema(description = "Model for updating user's balance")
public class DUserBalanceUpdateReq {
    @NotEmpty
    @Min(0)
    @Schema(description = "User's new balance", required = true, example = "55.79")
    private BigDecimal balance;
}
