package trading_portal.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import trading_portal.backend.entity.Transaction;
import trading_portal.backend.services.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/buy")
    public Transaction buy(@RequestParam Long portfolioId,
                           @RequestParam int productId,
                           @RequestParam int qty) {

        return service.buy(portfolioId, productId, qty);
    }

    @PostMapping("/sell")
    public Transaction sell(@RequestParam Long portfolioId,
                            @RequestParam int productId,
                            @RequestParam int qty) {

        return service.sell(portfolioId, productId, qty);
    }
}