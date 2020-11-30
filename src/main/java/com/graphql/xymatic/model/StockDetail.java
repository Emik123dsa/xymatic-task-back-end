package com.graphql.xymatic.model;

public class StockDetail {
    private String symbol;
    private String name;
    private long marketCap;

    public StockDetail(String symbol, String name, long marketCap) {
        this.name = name;
        this.symbol = symbol;
        this.marketCap = marketCap;
    }

    // getter and setters
}
