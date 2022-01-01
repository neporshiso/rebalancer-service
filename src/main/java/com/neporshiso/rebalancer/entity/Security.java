package com.neporshiso.rebalancer.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class Security {
    private BigDecimal units;
    private BigDecimal price;
    private BigDecimal weight;
    private String ticker;
}
