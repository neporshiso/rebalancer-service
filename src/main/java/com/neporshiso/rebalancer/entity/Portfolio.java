package com.neporshiso.rebalancer.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class Portfolio {
    private List<Security> securities;

    public Portfolio(List<Security> securities) {
        this.securities = securities;
    }
}
