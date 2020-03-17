package com.sandrocaseiro.apitemplate.models.dto.user;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class DUserBalanceUpdateReq {
    @NotNull
    @Min(0)
    private BigDecimal balance;
}
