package trading_portal.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Holding {
    @Id
    @GeneratedValue
    private Long id;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Portfolio getPortfolio() {
        return portfolio;
    }
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
