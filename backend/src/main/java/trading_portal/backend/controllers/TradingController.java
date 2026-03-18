package trading_portal.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trading_portal.backend.dto.PortfolioResponse;
import trading_portal.backend.dto.TradeRequest;
import trading_portal.backend.entity.TradeTransaction;
import trading_portal.backend.services.TradingService;

import java.util.List;

@RestController
@RequestMapping("/api/trade")
@CrossOrigin(origins = "http://localhost:4200")
public class TradingController {

    @Autowired
    private TradingService tradingService;

    @PostMapping("/buy")
    public TradeTransaction buy(@RequestBody TradeRequest request) {
        return tradingService.buy(request);
    }

    @PostMapping("/sell")
    public TradeTransaction sell(@RequestBody TradeRequest request) {
        return tradingService.sell(request);
    }

    @GetMapping("/transactions/{userId}")
    public List<TradeTransaction> getTransactions(@PathVariable int userId) {
        return tradingService.getTransactionsForUser(userId);
    }

    @GetMapping("/portfolio/{userId}")
    public List<PortfolioResponse> getPortfolio(@PathVariable int userId) {
        return tradingService.getPortfolioForUser(userId);
    }
}
