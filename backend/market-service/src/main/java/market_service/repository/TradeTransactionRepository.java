package market_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import market_service.entity.TradeTransaction;

import java.util.List;

public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {
    List<TradeTransaction> findByUserIdOrderByTimestampDesc(int userId);
}
