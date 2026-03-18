package trading_portal.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trading_portal.backend.dto.PortfolioResponse;
import trading_portal.backend.dto.TradeRequest;
import trading_portal.backend.entity.Portfolio;
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

    @PostMapping("/portfolios")
    public Portfolio createPortfolio(@RequestBody java.util.Map<String, Object> request) {
       Integer userId = request.get("userId") != null 
    ? Integer.parseInt(request.get("userId").toString()) 
    : null;
        String name = request.get("name") == null ? null : request.get("name").toString();
        if (userId == null || name == null || name.trim().isEmpty()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "userId and name are required");
        }
        return tradingService.createPortfolio(userId, name.trim());
    }

    @GetMapping("/portfolios/{userId}")
    public List<Portfolio> getPortfolios(@PathVariable int userId) {
        return tradingService.getPortfoliosForUser(userId);
    }

    @GetMapping("/transactions/{userId}")
    public List<TradeTransaction> getTransactions(@PathVariable int userId) {
        return tradingService.getTransactionsForUser(userId);
    }

    @GetMapping("/portfolio/{userId}")
    public List<PortfolioResponse> getPortfolio(@PathVariable int userId) {
        return tradingService.getPortfolioForUser(userId);
    }

    @GetMapping("/portfolio/{userId}/{portfolioId}")
    public List<PortfolioResponse> getPortfolioDetails(@PathVariable int userId, @PathVariable Long portfolioId) {
        return tradingService.getPortfolioDetails(userId, portfolioId);
    }
}

