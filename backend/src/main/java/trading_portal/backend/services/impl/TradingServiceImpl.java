package trading_portal.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading_portal.backend.dto.PortfolioResponse;
import trading_portal.backend.dto.TradeRequest;
import trading_portal.backend.entity.Portfolio;
import trading_portal.backend.entity.PortfolioPosition;
import trading_portal.backend.entity.Product;
import trading_portal.backend.entity.TradeTransaction;
import trading_portal.backend.entity.TransactionType;
import trading_portal.backend.repository.PortfolioPositionRepository;
import trading_portal.backend.repository.PortfolioRepository;
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

    @Autowired
    private PortfolioRepository portfolioMetaRepo;

    @Override
    public TradeTransaction buy(TradeRequest request) {
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Product not found"));
        if (request.getQuantity() <= 0) { throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Quantity must be > 0"); }
        if (request.getPortfolioId() == null) { throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "portfolioId is required"); }
        if (product.getAvailable_shares() == null || product.getAvailable_shares() < request.getQuantity()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Insufficient shares available to buy");
        }

        Portfolio portfolio = portfolioMetaRepo.findById(request.getPortfolioId()).orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Portfolio not found"));
        if (portfolio.getUserId() != request.getUserId()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Portfolio does not belong to user");
        }

        double price = product.getCurrent_price();
        TradeTransaction tx = new TradeTransaction(request.getUserId(), portfolio.getId(), portfolio.getName(), product.getAsset_id(), product.getName(), product.getTicker(), request.getQuantity(), price, TransactionType.BUY);
        transactionRepo.save(tx);
        product.setAvailable_shares(product.getAvailable_shares() - request.getQuantity());
        productRepo.save(product);

        PortfolioPosition position = portfolioRepo.findByUserIdAndProductIdAndPortfolioId(request.getUserId(), product.getAsset_id(), portfolio.getId()).orElse(null);
        if (position == null) {
            position = new PortfolioPosition();
            position.setUserId(request.getUserId());
            position.setPortfolioId(portfolio.getId());
            position.setPortfolioName(portfolio.getName());
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
        if (request.getPortfolioId() == null) { throw new RuntimeException("portfolioId is required"); }

        Portfolio portfolio = portfolioMetaRepo.findById(request.getPortfolioId()).orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Portfolio not found"));
        if (portfolio.getUserId() != request.getUserId()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Portfolio does not belong to user");
        }

        PortfolioPosition position = portfolioRepo.findByUserIdAndProductIdAndPortfolioId(request.getUserId(), product.getAsset_id(), portfolio.getId())
                .orElseThrow(() -> new RuntimeException("No position found for this stock in selected portfolio"));
        if (position.getQuantity() < request.getQuantity()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Insufficient quantity to sell");
        }

        double price = product.getCurrent_price();
        TradeTransaction tx = new TradeTransaction(request.getUserId(), portfolio.getId(), portfolio.getName(), product.getAsset_id(), product.getName(), product.getTicker(), request.getQuantity(), price, TransactionType.SELL);
        transactionRepo.save(tx);
        product.setAvailable_shares(product.getAvailable_shares() + request.getQuantity());
        productRepo.save(product);

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
        List<TradeTransaction> list = transactionRepo.findByUserIdOrderByTimestampDesc(userId);
        return list;
    }

    @Override
    public List<PortfolioResponse> getPortfolioForUser(int userId) {
        List<PortfolioPosition> positions = portfolioRepo.findByUserId(userId);
        List<PortfolioResponse> responses = new ArrayList<>();
        for (PortfolioPosition p : positions) {
            Product product = productRepo.findById(p.getProductId()).orElse(null);
            if (product == null) continue;
            PortfolioResponse r = new PortfolioResponse();
            r.setPortfolioId(p.getPortfolioId());
            r.setPortfolioName(p.getPortfolioName());
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

    @Override
    public List<PortfolioResponse> getPortfolioDetails(int userId, Long portfolioId) {
        Portfolio portfolio = portfolioMetaRepo.findById(portfolioId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Portfolio not found"));
        if (portfolio.getUserId() != userId) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Portfolio does not belong to user");
        }

        List<PortfolioPosition> positions = portfolioRepo.findByUserIdAndPortfolioId(userId, portfolioId);
        List<PortfolioResponse> responses = new ArrayList<>();
        for (PortfolioPosition p : positions) {
            Product product = productRepo.findById(p.getProductId()).orElse(null);
            if (product == null) continue;
            PortfolioResponse r = new PortfolioResponse();
            r.setPortfolioId(p.getPortfolioId());
            r.setPortfolioName(p.getPortfolioName());
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

    @Override
    public Portfolio createPortfolio(int userId, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Portfolio name cannot be empty");
        }
        if (portfolioMetaRepo.existsByUserIdAndName(userId, name.trim())) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Portfolio with this name already exists");
        }
        Portfolio portfolio = new Portfolio(userId, name.trim());
        return portfolioMetaRepo.save(portfolio);
    }

    @Override
    public List<Portfolio> getPortfoliosForUser(int userId) {
        return portfolioMetaRepo.findByUserId(userId);
    }
}
