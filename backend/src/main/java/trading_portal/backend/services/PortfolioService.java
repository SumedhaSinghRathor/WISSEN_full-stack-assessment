package trading_portal.backend.services;

import trading_portal.backend.dto.PortfolioSummaryDTO;
import trading_portal.backend.entity.Portfolio;

public interface PortfolioService {
    PortfolioSummaryDTO getPortfolioDetails(Long portfolioId);

    Portfolio createPortfolio(Integer userId, String name);

}
