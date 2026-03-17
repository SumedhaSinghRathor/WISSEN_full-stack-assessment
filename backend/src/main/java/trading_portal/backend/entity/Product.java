package trading_portal.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int asset_id;
    private String ticker;
    private String name;
    private Double current_price;
    private String type;
    @Column
    private Double opening_price;
    
    public int getAsset_id() {
        return asset_id;
    }
    public void setAsset_id(int asset_id) {
        this.asset_id = asset_id;
    }
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setOpening_price(Double opening_price){
        this.opening_price=opening_price;
    }
    public Double getOpening_price(){
        return this.opening_price;
    }
    public Double getCurrent_price() {
        return current_price;
    }
    public void setCurrent_price(Double current_price) {
        this.current_price = current_price;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
