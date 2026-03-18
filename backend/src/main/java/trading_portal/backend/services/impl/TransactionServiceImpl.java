package trading_portal.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trading_portal.backend.entity.*;
import trading_portal.backend.repository.*;
import trading_portal.backend.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private PortfolioRepository portfolioRepo;

    @Autowired
    private ProductRepository productRepo;

    @Override
    public Transaction buy(Long portfolioId, int productId, int qty) {

        Portfolio portfolio = portfolioRepo.findById(portfolioId).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();

        Transaction t = new Transaction();
        t.setPortfolio(portfolio);
        t.setProduct(product);
        t.setQuantity(qty);
        t.setType("BUY");
        t.setPriceAtExecution(product.getCurrent_price());

        return transactionRepo.save(t);
    }

    @Override
    public Transaction sell(Long portfolioId, int productId, int qty) {

        int owned = calculateQuantity(portfolioId, productId);

        if (owned < qty) {
            throw new RuntimeException("Not enough shares");
        }

        Portfolio portfolio = portfolioRepo.findById(portfolioId).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();

        Transaction t = new Transaction();
        t.setPortfolio(portfolio);
        t.setProduct(product);
        t.setQuantity(qty);
        t.setType("SELL");
        t.setPriceAtExecution(product.getCurrent_price());

        return transactionRepo.save(t);
    }

    private int calculateQuantity(Long portfolioId, int productId) {

        var transactions = transactionRepo
                .findByPortfolioIdAndProductId(portfolioId, productId);

        int total = 0;

        for (Transaction t : transactions) {
            if (t.getType().equals("BUY")) total += t.getQuantity();
            else total -= t.getQuantity();
        }

        return total;
    }
}