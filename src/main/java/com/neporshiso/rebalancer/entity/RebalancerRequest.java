package com.neporshiso.rebalancer.entity;

import lombok.Data;

@Data
public class RebalancerRequest {
    private Portfolio initial;
    private Portfolio desired;

    public RebalancerRequest(Portfolio initial, Portfolio desired) {
        this.initial = initial;
        this.desired = desired;
    }
}
