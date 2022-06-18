package com.neporshiso.rebalancer.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RebalancedPortfolio {

    private List<RebalancedSecurity> portfolio;
    // Due to portfolio weights a user can choose, there is no guarantee that all value will be evenly reallocated
    private BigDecimal unallocatedValue;
    private BigDecimal totalValue;

    public RebalancedPortfolio(List<RebalancedSecurity> portfolio) {
        this.portfolio = portfolio;
    }
}
