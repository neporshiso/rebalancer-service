package com.neporshiso.rebalancer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Security {
    private String ticker;
    private BigDecimal currentUnits;
    private BigDecimal price;
    private BigDecimal currentWeight;
    private BigDecimal desiredWeight;

    public Security(BigDecimal units) {
        this.currentUnits = units;
    }
}
