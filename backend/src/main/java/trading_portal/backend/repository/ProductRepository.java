package trading_portal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trading_portal.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
       
}