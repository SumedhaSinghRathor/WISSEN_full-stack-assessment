package trading_portal.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading_portal.backend.dto.PortfolioResponse;
import trading_portal.backend.dto.TradeRequest;
import trading_portal.backend.entity.PortfolioPosition;
import trading_portal.backend.entity.Product;
import trading_portal.backend.entity.TradeTransaction;
import trading_portal.backend.entity.TransactionType;
import trading_portal.backend.repository.PortfolioPositionRepository;
import trading_portal.backend.repository.ProductRepository;
import trading_portal.backend.repository.TradeTransactionRepository;
import trading_portal.backend.services.TradingService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradingServiceImpl implements TradingService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private TradeTransactionRepository transactionRepo;

    @Autowired
    private PortfolioPositionRepository portfolioRepo;

    @Override
    public TradeTransaction buy(TradeRequest request) {
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        if (request.getQuantity() <= 0) { throw new RuntimeException("Quantity must be > 0"); }

        double price = product.getCurrent_price();
        TradeTransaction tx = new TradeTransaction(request.getUserId(), product.getAsset_id(), product.getName(), product.getTicker(), request.getQuantity(), price, TransactionType.BUY);
        transactionRepo.save(tx);

        PortfolioPosition position = portfolioRepo.findByUserIdAndProductId(request.getUserId(), product.getAsset_id()).orElse(null);
        if (position == null) {
            position = new PortfolioPosition();
            position.setUserId(request.getUserId());
            position.setProductId(product.getAsset_id());
            position.setProductName(product.getName());
            position.setTicker(product.getTicker());
            position.setQuantity(request.getQuantity());
            position.setAverageBuyPrice(price);
        } else {
            int oldQty = position.getQuantity();
            int buyQty = request.getQuantity();
            double oldTotal = oldQty * position.getAverageBuyPrice();
            double newTotal = buyQty * price;
            int newQty = oldQty + buyQty;
            position.setQuantity(newQty);
            position.setAverageBuyPrice((oldTotal + newTotal) / newQty);
        }
        portfolioRepo.save(position);
        return tx;
    }

    @Override
    public TradeTransaction sell(TradeRequest request) {
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        if (request.getQuantity() <= 0) { throw new RuntimeException("Quantity must be > 0"); }

        PortfolioPosition position = portfolioRepo.findByUserIdAndProductId(request.getUserId(), product.getAsset_id()).orElseThrow(() -> new RuntimeException("No position found for this stock"));
        if (position.getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient quantity to sell");
        }

        double price = product.getCurrent_price();
        TradeTransaction tx = new TradeTransaction(request.getUserId(), product.getAsset_id(), product.getName(), product.getTicker(), request.getQuantity(), price, TransactionType.SELL);
        transactionRepo.save(tx);

        position.setQuantity(position.getQuantity() - request.getQuantity());
        if (position.getQuantity() == 0) {
            portfolioRepo.delete(position);
        } else {
            portfolioRepo.save(position);
        }
        return tx;
    }

    @Override
    public List<TradeTransaction> getTransactionsForUser(int userId) {
        return transactionRepo.findByUserIdOrderByTimestampDesc(userId);
    }

    @Override
    public List<PortfolioResponse> getPortfolioForUser(int userId) {
        List<PortfolioPosition> positions = portfolioRepo.findByUserId(userId);
        List<PortfolioResponse> responses = new ArrayList<>();
        for (PortfolioPosition p : positions) {
            Product product = productRepo.findById(p.getProductId()).orElse(null);
            if (product == null) continue;
            PortfolioResponse r = new PortfolioResponse();
            r.setProductId(p.getProductId());
            r.setProductName(p.getProductName());
            r.setTicker(p.getTicker());
            r.setQuantity(p.getQuantity());
            r.setAverageBuyPrice(p.getAverageBuyPrice());
            r.setCurrentPrice(product.getCurrent_price());
            r.setValue(product.getCurrent_price() * p.getQuantity());
            r.setProfitLoss((product.getCurrent_price() - p.getAverageBuyPrice()) * p.getQuantity());
            responses.add(r);
        }
        return responses;
    }
}
