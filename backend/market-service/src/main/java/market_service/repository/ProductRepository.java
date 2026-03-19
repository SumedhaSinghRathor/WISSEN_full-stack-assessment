package market_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import market_service.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
       
}
