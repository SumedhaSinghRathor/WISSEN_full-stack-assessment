package trading_portal.backend.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int asset_id;
    private String ticker;
    private String name;
    private float current_price;
    private String type;
    
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
    public float getCurrent_price() {
        return current_price;
    }
    public void setCurrent_price(float current_price) {
        this.current_price = current_price;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
