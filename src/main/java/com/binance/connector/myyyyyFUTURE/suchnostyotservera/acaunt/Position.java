package com.binance.connector.myyyyyFUTURE.suchnostyotservera.acaunt;

public class Position {
    private String symbol; // Символ торговой пары
    private double positionAmount; // Количество в позиции
    private double entryPrice; // Средняя цена входа
    private double fundingRate; // Ставка финансирования
    private double unrealizedProfit; // Нереализованная прибыль/убыток
    private String marginType; // Тип маржи
    private double isolatedWallet; // Замороженная сумма на марже
    private String positionSide; // Тип позиции
    private String marginAsset; // Валюта маржи

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPositionAmount() {
        return positionAmount;
    }

    public void setPositionAmount(double positionAmount) {
        this.positionAmount = positionAmount;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public double getFundingRate() {
        return fundingRate;
    }

    public void setFundingRate(double fundingRate) {
        this.fundingRate = fundingRate;
    }

    public double getUnrealizedProfit() {
        return unrealizedProfit;
    }

    public void setUnrealizedProfit(double unrealizedProfit) {
        this.unrealizedProfit = unrealizedProfit;
    }

    public String getMarginType() {
        return marginType;
    }

    public void setMarginType(String marginType) {
        this.marginType = marginType;
    }

    public double getIsolatedWallet() {
        return isolatedWallet;
    }

    public void setIsolatedWallet(double isolatedWallet) {
        this.isolatedWallet = isolatedWallet;
    }

    public String getPositionSide() {
        return positionSide;
    }

    public void setPositionSide(String positionSide) {
        this.positionSide = positionSide;
    }

    public String getMarginAsset() {
        return marginAsset;
    }

    public void setMarginAsset(String marginAsset) {
        this.marginAsset = marginAsset;
    }
// Геттеры и сеттеры
}
