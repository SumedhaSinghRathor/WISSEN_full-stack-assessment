package trading_portal.backend.dto;

import java.util.List;

public class PortfolioSummaryDTO {

    private List<PortfolioItemDTO> items;
    private double totalInvested;
    private double totalReturns;

    public PortfolioSummaryDTO(List<PortfolioItemDTO> items,
                               double totalInvested,
                               double totalReturns) {
        this.items = items;
        this.totalInvested = totalInvested;
        this.totalReturns = totalReturns;
    }

    // getters

    public List<PortfolioItemDTO> getItems() {
        return items;
    }

    public double getTotalInvested() {
        return totalInvested;
    }

    public double getTotalReturns() {
        return totalReturns;
    }
}