package trading_portal.backend.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Portfolio {
    @Id
    @GeneratedValue
    private Long portfolio_id;

    private String portfolio_name;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Holding> holdings;

    public Long getPortfolio_id() {
        return portfolio_id;
    }

    public void setPortfolio_id(Long portfolio_id) {
        this.portfolio_id = portfolio_id;
    }

    public String getPortfolio_name() {
        return portfolio_name;
    }

    public void setPortfolio_name(String portfolio_name) {
        this.portfolio_name = portfolio_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Holding> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<Holding> holdings) {
        this.holdings = holdings;
    }
}
