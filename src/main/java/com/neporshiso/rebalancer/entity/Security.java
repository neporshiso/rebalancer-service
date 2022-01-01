package com.neporshiso.rebalancer.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Security {
    private BigDecimal units;
    private BigDecimal price;
    private BigDecimal weight;
    private String ticker;
}
