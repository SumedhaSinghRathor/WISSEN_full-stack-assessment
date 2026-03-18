package trading_portal.backend.services;

import trading_portal.backend.dto.PortfolioResponse;
import trading_portal.backend.entity.TradeTransaction;
import trading_portal.backend.dto.TradeRequest;

import java.util.List;

public interface TradingService {
    TradeTransaction buy(TradeRequest request);
    TradeTransaction sell(TradeRequest request);
    List<TradeTransaction> getTransactionsForUser(int userId);
    List<PortfolioResponse> getPortfolioForUser(int userId);
}
