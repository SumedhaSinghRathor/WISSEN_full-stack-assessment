package market_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import market_service.entity.PortfolioPosition;

import java.util.List;
import java.util.Optional;

public interface PortfolioPositionRepository extends JpaRepository<PortfolioPosition, Long> {
    List<PortfolioPosition> findByUserId(int userId);
    Optional<PortfolioPosition> findByUserIdAndProductIdAndPortfolioId(int userId, int productId, Long portfolioId);
    List<PortfolioPosition> findByUserIdAndPortfolioId(int userId, Long portfolioId);
}
