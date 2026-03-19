package market_service.service;

import market_service.entity.Product;

import java.util.List;

public interface ProductService {
    Product create(Product p);
    Product update(int id, Product p);
    void delete(int id);
    List<Product> getAll();
    Product getById(int id);
}
