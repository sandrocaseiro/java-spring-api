package dev.sandrocaseiro.template.models.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class AUserUpdateReq {
    private BigDecimal balance;
}
