package trading_portal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trading_portal.backend.entity.TradeTransaction;

import java.util.List;

@Repository
public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {
    List<TradeTransaction> findByUserIdOrderByTimestampDesc(int userId);
}
