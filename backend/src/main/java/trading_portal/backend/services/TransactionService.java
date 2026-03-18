package trading_portal.backend.services;

import trading_portal.backend.entity.Transaction;

public interface TransactionService {
    Transaction buy(Long portfolioId, int productId, int qty);

    Transaction sell(Long portfolioId, int productId, int qty);
}
