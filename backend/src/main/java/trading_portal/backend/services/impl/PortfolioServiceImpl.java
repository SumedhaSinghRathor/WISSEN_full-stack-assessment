package trading_portal.backend.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trading_portal.backend.dto.*;
import trading_portal.backend.entity.*;
import trading_portal.backend.repository.*;
import trading_portal.backend.services.PortfolioService;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Override
    public PortfolioSummaryDTO getPortfolioDetails(Long portfolioId) {

        List<Transaction> transactions =
                transactionRepo.findByPortfolioId(portfolioId);

        Map<Integer, List<Transaction>> grouped =
                transactions.stream().collect(Collectors.groupingBy(
                        t -> t.getProduct().getAsset_id()
                ));

        List<PortfolioItemDTO> items = new ArrayList<>();

        double totalInvested = 0;
        double totalCurrent = 0;

        for (var entry : grouped.entrySet()) {

            List<Transaction> txns = entry.getValue();
            Product product = txns.get(0).getProduct();

            int qty = 0;
            double invested = 0;

            for (Transaction t : txns) {
                if (t.getType().equals("BUY")) {
                    qty += t.getQuantity();
                    invested += t.getQuantity() * t.getPriceAtExecution();
                } else {
                    qty -= t.getQuantity();
                    invested -= t.getQuantity() * t.getPriceAtExecution();
                }
            }

            if (qty <= 0) continue;

            double currentValue = qty * product.getCurrent_price();
            double returns = currentValue - invested;

            totalInvested += invested;
            totalCurrent += currentValue;

            items.add(new PortfolioItemDTO(
                    product.getName(),
                    qty,
                    invested,
                    currentValue,
                    returns,
                    product.getAsset_id()
            ));
        }

        return new PortfolioSummaryDTO(
                items,
                totalInvested,
                totalCurrent - totalInvested
        );
    }

    @Autowired
    private PortfolioRepository portfolioRepo;

    @Autowired
    private UserRepository userRepo;
    @Override
    public Portfolio createPortfolio(Integer userId, String name) {

        User user = userRepo.findById(userId).orElseThrow();

        Portfolio p = new Portfolio();
        p.setPortfolio_name(name);
        p.setUser(user);

        return portfolioRepo.save(p);
    }
}