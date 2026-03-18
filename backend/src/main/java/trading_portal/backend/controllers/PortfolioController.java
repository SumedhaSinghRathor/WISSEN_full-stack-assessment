package trading_portal.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import trading_portal.backend.dto.PortfolioSummaryDTO;
import trading_portal.backend.entity.Portfolio;
import trading_portal.backend.services.PortfolioService;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService service;

    @GetMapping("/{portfolioId}")
    public PortfolioSummaryDTO getPortfolio(@PathVariable Long portfolioId) {
        return service.getPortfolioDetails(portfolioId);
    }
    @PostMapping("/create")
    public Portfolio createPortfolio(@RequestParam("userId") Integer userId,
                                     @RequestParam("name") String name) {
        return service.createPortfolio(userId, name);
    }
}