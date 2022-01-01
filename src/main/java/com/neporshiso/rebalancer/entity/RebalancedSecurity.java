package com.neporshiso.rebalancer.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RebalancedSecurity {
    String ticker;
    Transaction action;
    BigDecimal deltaUnits;
}
