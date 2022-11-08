package com.neporshiso.rebalancer.controller;

import com.neporshiso.rebalancer.entity.Portfolio;
import com.neporshiso.rebalancer.entity.RebalancedPortfolio;
import com.neporshiso.rebalancer.service.RebalancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
public class RebalancerController {

    @Autowired
    RebalancerService rebalancerService;

    @GetMapping("/")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/portfolio")
    public RebalancedPortfolio rebalancePortfolio(@RequestBody Portfolio p) {
        return rebalancerService.calculateDelta(p);
    }
}
