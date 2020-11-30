package com.graphql.xymatic.model;

import java.time.LocalDateTime;

public class StockPrice {
    private String symbol;
    private double price;
    private LocalDateTime timestamp;

    public StockPrice(String symbol, double price, LocalDateTime timestamp) {
        this.price = price;
        this.symbol = symbol;
        this.timestamp = timestamp;
    }
}
