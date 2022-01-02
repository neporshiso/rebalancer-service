package com.neporshiso.rebalancer.service;

import com.neporshiso.rebalancer.entity.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

            Security sec1 = Optional.ofNullable(initialPortfolioMap.get(sec2.getTicker())).orElseGet(() -> new Security(BigDecimal.valueOf(0)));

            BigDecimal units2 = sec2.getWeight()
                    .multiply(totalPortfolioValue)
                    .divide(sec2.getPrice(), 0, RoundingMode.FLOOR);

            RebalancedSecurity rs = new RebalancedSecurity();

            BigDecimal delta = units2.subtract(sec1.getUnits());
//            Could use @Builder here...
            rs.setDesiredWeight(sec2.getWeight());
            rs.setWeight(units2.multiply(sec2.getPrice()).divide(totalPortfolioValue, 4, RoundingMode.HALF_DOWN));
            rs.setPrice(sec2.getPrice());
            rs.setTicker(sec2.getTicker());
            rs.setDeltaUnits(delta);
            rs.setUnits(units2);

            long diff = units2.compareTo(sec1.getUnits());
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
                .map(rs -> rs.getPrice().multiply(rs.getUnits()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal portfolioValueDelta = totalPortfolioValue.subtract(portfolioValueAfterRebalancing);
        rebalPortfolio.setUnallocatedValue(portfolioValueDelta);

        return rebalPortfolio;
    }

}
