package trading_portal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trading_portal.backend.entity.PortfolioPosition;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioPositionRepository extends JpaRepository<PortfolioPosition, Long> {
    List<PortfolioPosition> findByUserId(int userId);
    Optional<PortfolioPosition> findByUserIdAndProductId(int userId, int productId);
}
