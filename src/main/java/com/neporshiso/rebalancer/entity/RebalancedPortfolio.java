package com.neporshiso.rebalancer.entity;

import lombok.Data;

import java.util.List;

@Data
public class RebalancedPortfolio {

    private List<RebalancedSecurity> portfolio;

    public RebalancedPortfolio(List<RebalancedSecurity> portfolio) {
        this.portfolio = portfolio;
    }
}
