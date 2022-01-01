package com.neporshiso.rebalancer.controller;

import com.neporshiso.rebalancer.entity.Portfolio;
import com.neporshiso.rebalancer.entity.RebalancedPortfolio;
import com.neporshiso.rebalancer.entity.RebalancerRequest;
import com.neporshiso.rebalancer.service.RebalancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RebalancerController {

    @Autowired
    RebalancerService rebalancerService;

    @PostMapping("/portfolio")
    public RebalancedPortfolio rebalancePortfolio(@RequestBody RebalancerRequest req) {
        Portfolio intialPortfolio = req.getInitial();
        Portfolio desiredPortfolio = req.getDesired();
        return rebalancerService.calculateDelta(intialPortfolio, desiredPortfolio);
    }
}
