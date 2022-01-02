package com.neporshiso.rebalancer.service;

import com.neporshiso.rebalancer.entity.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RebalancerService {

    public RebalancedPortfolio calculateDelta(Portfolio initial, Portfolio desired) {
        Map<String, Security> initialPortfolioMap = initial.getSecurities()
                .stream()
                .collect(Collectors.toMap(Security::getTicker, item -> item));

        List<RebalancedSecurity> rebalancedSecurities = new ArrayList<>();
        RebalancedPortfolio rebalPortfolio = new RebalancedPortfolio(rebalancedSecurities);

        BigDecimal totalPortfolioValue = initial.getSecurities()
                .stream()
                .map(security -> security.getPrice().multiply(security.getUnits()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        for (Security sec2 : desired.getSecurities()) {

            Security sec1 = initialPortfolioMap.get(sec2.getTicker());

            BigDecimal units2 = sec2.getWeight()
                    .multiply(totalPortfolioValue)
                    .divide(sec2.getPrice(), 0, RoundingMode.FLOOR);

            RebalancedSecurity rs = new RebalancedSecurity();

            BigDecimal delta = units2.subtract(sec1.getUnits());
            rs.setDesiredWeight(sec2.getWeight());
            rs.setActualWeight(units2.multiply(sec2.getPrice()).divide(totalPortfolioValue, 4, RoundingMode.HALF_DOWN));
            rs.setPrice(sec2.getPrice());
            rs.setTicker(sec2.getTicker());
            rs.setDeltaUnits(delta);

            if (units2.compareTo(sec1.getUnits()) > 0 ) {
                rs.setAction(Transaction.BUY);
            }

            if (units2.compareTo(sec1.getUnits()) < 0 ) {
                rs.setAction(Transaction.SELL);
            }

            if (units2.compareTo(sec1.getUnits()) == 0 ) {
                rs.setAction(Transaction.HOLD);
            }

            rebalancedSecurities.add(rs);
        }

        BigDecimal portfolioValueAfterRebalancing = rebalPortfolio.getPortfolio()
                .stream()
                .map(security -> security.getPrice()
                        .multiply(initialPortfolioMap.get(security.getTicker()).getUnits().add(security.getDeltaUnits())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal portfolioValueDelta = totalPortfolioValue.subtract(portfolioValueAfterRebalancing);
        rebalPortfolio.setUnallocatedValue(portfolioValueDelta);

        return rebalPortfolio;
    }

}
