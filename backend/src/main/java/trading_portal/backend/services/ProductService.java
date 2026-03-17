package trading_portal.backend.services;

import trading_portal.backend.entity.Product;

import java.util.List;

public interface ProductService {
    Product create(Product p);
    Product update(int id, Product p);
    void delete(int id);
    List<Product> getAll();
}
