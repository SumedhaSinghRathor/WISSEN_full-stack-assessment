package trading_portal.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import trading_portal.backend.entity.Product;
import trading_portal.backend.repository.ProductRepository;

import java.util.List;

public class ProductServiceImpl {
    @Autowired
    private ProductRepository repo;

    public Product create(Product p) {
        return repo.save(p);
    }

    public Product update(int id, Product p) {
        Product existing = repo.findById(id).orElseThrow();
        existing.setName(p.getName());
        existing.setTicker(p.getTicker());
        existing.setType(p.getType());
        existing.setCurrent_price(p.getCurrent_price());
        return repo.save(existing);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }
}
