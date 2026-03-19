package market_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import market_service.entity.Portfolio;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUserId(int userId);
    boolean existsByUserIdAndName(int userId, String name);
}
