package com.binance.connector.myyyyyFUTURE.sushnosty;

import java.util.Objects;

public class Order {
    private double cenaVhoda;
    private String symbol;
    private long orderId;
    private double cummulativeQuoteQty;
    private double executedQty;
    private String status;
    private String side;//направление сделки
    private String type;

    public int kakayaPoshetuSvecha;

    public boolean verhnyayPolovina; //todo спользуеться только для рантайм оредров

    public Order() {
    }

    public Order(String symbol, long orderId, double cummulativeQuoteQty, double executedQty, String status, String side, String type) {
        this.cenaVhoda = cenaVhoda;
        this.symbol = symbol;
        this.orderId = orderId;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.executedQty = executedQty;
        this.status = status;
        this.side = side;
        this.type = type;
        this.kakayaPoshetuSvecha = 0;
        this.verhnyayPolovina = true;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Геттеры и сеттеры для каждого поля
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public double getCummulativeQuoteQty() {
        return cummulativeQuoteQty;
    }

    public void setCummulativeQuoteQty(double cummulativeQuoteQty) {
        this.cummulativeQuoteQty = cummulativeQuoteQty;
    }

    public double getExecutedQty() {
        return executedQty;
    }

    public void setExecutedQty(double executedQty) {
        this.executedQty = executedQty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public double getCenaVhoda() {
        return cenaVhoda;
    }

    public void setCenaVhoda(double cenaVhoda) {
        this.cenaVhoda = cenaVhoda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order that = (Order) o;
        return getOrderId() == that.getOrderId() && Objects.equals(getSymbol(), that.getSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymbol(), getOrderId());
    }
}

//import java.math.BigDecimal;
//
//public class Order {
//    private long orderId;
//    private String symbol;
//    private String status;
//    private String clientOrderId;
//    private BigDecimal price;
//    private BigDecimal avgPrice;
//    private BigDecimal origQty;
//    private BigDecimal executedQty;
//    private BigDecimal cumQty;
//    private BigDecimal cumQuote;
//    private String timeInForce;
//    private String type;
//    private boolean reduceOnly;
//    private boolean closePosition;
//    private String side;
//    private String positionSide;
//    private BigDecimal stopPrice;
//    private String workingType;
//    private boolean priceProtect;
//    private String origType;
//    private String priceMatch;
//    private String selfTradePreventionMode;
//    private long goodTillDate;
//    private long updateTime;
//
//    // Getters and Setters for each field
//    // ...
//}


