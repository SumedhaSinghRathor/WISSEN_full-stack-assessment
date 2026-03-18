package trading_portal.backend.dto;

public class PortfolioItemDTO {

    private String productName;
    private int quantity;
    private double investedValue;
    private double currentValue;
    private double returns;
    private int productId;

    public PortfolioItemDTO(String productName, int quantity,
                            double investedValue, double currentValue,
                            double returns, int productId) {
        this.productName = productName;
        this.quantity = quantity;
        this.investedValue = investedValue;
        this.currentValue = currentValue;
        this.returns = returns;
        this.productId = productId;
    }

    // getters

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getInvestedValue() {
        return investedValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public double getReturns() {
        return returns;
    }

    public int getProductId() {
        return productId;
    }
}