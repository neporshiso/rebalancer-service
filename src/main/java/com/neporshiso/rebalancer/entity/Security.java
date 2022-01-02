package com.neporshiso.rebalancer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Security {
    private BigDecimal units;
    private BigDecimal price;
    private BigDecimal weight;
    private String ticker;

    public Security(BigDecimal units) {
        this.units = units;
    }
}
