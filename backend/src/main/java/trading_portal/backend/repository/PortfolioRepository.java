package trading_portal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trading_portal.backend.entity.Portfolio;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByUserId(Integer userId);
}