package com.neporshiso.rebalancer.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RebalancedSecurity {
    private String ticker;
    private Transaction action;
    private BigDecimal deltaUnits;
    private BigDecimal price;
    private BigDecimal desiredWeight;
    private BigDecimal actualWeight;
}
