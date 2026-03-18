package trading_portal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import trading_portal.backend.entity.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByPortfolioId(Long portfolioId);

    List<Transaction> findByPortfolioIdAndProductId(Long portfolioId, int productId);
}
