package trading_portal.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import trading_portal.backend.entity.Product;
import trading_portal.backend.repository.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl {
    @Autowired
    private ProductRepository repo;

    @Scheduled(fixedRate = 1000) // every 1 second
    public void runPriceUpdate() {
        updatePrices();
    }

    public Product create(Product p) {
        p.setOpening_price(p.getCurrent_price());
        if (p.getAvailable_shares() == null || p.getAvailable_shares() <= 0) {
            p.setAvailable_shares(1000);
        }
        return repo.save(p);
    }

    public Product update(int id, Product p) {
        Product existing = repo.findById(id).orElseThrow();
        existing.setName(p.getName());
        existing.setTicker(p.getTicker());
        existing.setType(p.getType());
        existing.setCurrent_price(p.getCurrent_price());
        if (p.getAvailable_shares() != null && p.getAvailable_shares() >= 0) {
            existing.setAvailable_shares(p.getAvailable_shares());
        }
        return repo.save(existing);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }
    public Product getById(int id) {
        return repo.findById(id).orElse(null);
    }
    public void updatePrices() {

        List<Product> products = repo.findAll();

        for (Product p : products) {

            double currentPrice = p.getCurrent_price();
            double openingPrice = p.getOpening_price();
            double changePercent = (Math.random() * 10) - 5; // -5 to +5
            double changeAmount = currentPrice * (changePercent / 100);
            double newPrice = currentPrice + changeAmount;
            double minLimit = openingPrice * 0.8; // -20%
            double maxLimit = openingPrice * 1.2; // +20%

            if (newPrice < minLimit) {
                newPrice = minLimit;
            }

            if (newPrice > maxLimit) {
                newPrice = maxLimit;
            }
            newPrice = Math.round(newPrice * 100.0) / 100.0;
            p.setCurrent_price(newPrice);
        }

        repo.saveAll(products);
    }
}
