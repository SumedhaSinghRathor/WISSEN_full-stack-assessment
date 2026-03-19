package market_service.service;

import market_service.dto.PortfolioResponse;
import market_service.dto.TradeRequest;
import market_service.entity.Portfolio;
import market_service.entity.TradeTransaction;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface TradingService {
    TradeTransaction buy(TradeRequest request);
    TradeTransaction sell(TradeRequest request);
    List<TradeTransaction> getTransactionsForUser(int userId);
    List<PortfolioResponse> getPortfolioForUser(int userId);
    List<PortfolioResponse> getPortfolioDetails(int userId, Long portfolioId);
    Portfolio createPortfolio(int userId, String name);
    List<Portfolio> getPortfoliosForUser(int userId);
}
