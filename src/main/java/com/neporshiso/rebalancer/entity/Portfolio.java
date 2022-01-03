package com.neporshiso.rebalancer.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class Portfolio {
    private BigDecimal totalValue;
    private List<Security> securities;

    public Portfolio(BigDecimal tv, List<Security> securities) {
        this.totalValue = tv;
        this.securities = securities;
    }
}
