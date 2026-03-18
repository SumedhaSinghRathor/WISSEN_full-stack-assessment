package trading_portal.backend.services;

import trading_portal.backend.dto.PortfolioResponse;
import trading_portal.backend.dto.TradeRequest;
import trading_portal.backend.entity.Portfolio;
import trading_portal.backend.entity.TradeTransaction;

import java.util.List;

public interface TradingService {
    TradeTransaction buy(TradeRequest request);
    TradeTransaction sell(TradeRequest request);
    List<TradeTransaction> getTransactionsForUser(int userId);
    List<PortfolioResponse> getPortfolioForUser(int userId);
    Portfolio createPortfolio(int userId, String name);
    List<Portfolio> getPortfoliosForUser(int userId);
}
