package com.neporshiso.rebalancer.service;

import com.neporshiso.rebalancer.entity.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class RebalancerService {

    public RebalancedPortfolio calculateDelta(Portfolio p) {

        List<RebalancedSecurity> rebalancedSecurities = new ArrayList<>();
        RebalancedPortfolio rebalPortfolio = new RebalancedPortfolio(rebalancedSecurities);

        BigDecimal totalPortfolioValue = p.getTotalValue();

        for (Security sec : p.getSecurities()) {
            RebalancedSecurity rs = new RebalancedSecurity();

            BigDecimal unitsAfterRebalance = sec.getDesiredWeight()
                    .multiply(totalPortfolioValue)
                    .divide(sec.getPrice(), 0, RoundingMode.FLOOR);

            BigDecimal delta = unitsAfterRebalance.subtract(sec.getCurrentUnits());
            rs.setDesiredWeight(sec.getDesiredWeight());
            rs.setTicker(sec.getTicker());
            rs.setPrice(sec.getPrice());
            rs.setCurrentWeight(unitsAfterRebalance.multiply(sec.getPrice()).divide(totalPortfolioValue, 4, RoundingMode.HALF_DOWN));
            rs.setDeltaUnits(delta);
            rs.setCurrentUnits(unitsAfterRebalance);
            // TODO: is long the right type here?
            long diff = unitsAfterRebalance.compareTo(sec.getCurrentUnits());

            if (diff > 0 ) {
                rs.setAction(Transaction.BUY);
            }

            if (diff < 0 ) {
                rs.setAction(Transaction.SELL);
            }

            if (diff == 0 ) {
                rs.setAction(Transaction.HOLD);
            }

            rebalancedSecurities.add(rs);
        }

        BigDecimal portfolioValueAfterRebalancing = rebalPortfolio.getPortfolio()
                .stream()
                .map(rs -> rs.getPrice().multiply(rs.getCurrentUnits()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal portfolioValueDelta = totalPortfolioValue.subtract(portfolioValueAfterRebalancing);
        rebalPortfolio.setUnallocatedValue(portfolioValueDelta);

        return rebalPortfolio;
    }

}
