package com.binance.connector.myyyyyFUTURE.suchnostyotservera.acaunt;

public class OrderDTONEISPOLZUYU {
    private String eventType;
    private long eventTime;
    private long transactionTime;
    // Данные ордера
    private String symbol;
    private String clientOrderId;
    private String side;
    // ... другие поля



    public OrderDTONEISPOLZUYU() {
        // Конструктор по умолчанию
    }

    public OrderDTONEISPOLZUYU(String eventType, long eventTime, long transactionTime, String symbol, String clientOrderId, String side) {
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.transactionTime = transactionTime;
        this.symbol = symbol;
        this.clientOrderId = clientOrderId;
        this.side = side;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public long getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(long transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
