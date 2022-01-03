package com.neporshiso.rebalancer.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RebalancedSecurity extends Security {
    private Transaction action;
    private BigDecimal deltaUnits; // What needs to change to get to desired state
}
