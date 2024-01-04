package com.binance.connector.myyyyyFUTURE.suchnostyotservera.acaunt;

public class AccountBalance {
    private String asset; // Валюта
    private double walletBalance; // Общий баланс
    private double crossWalletBalance; // Доступный для вывода баланс
    private double balanceChange; // Изменение баланса

    // Геттеры и сеттеры

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public double getCrossWalletBalance() {
        return crossWalletBalance;
    }

    public void setCrossWalletBalance(double crossWalletBalance) {
        this.crossWalletBalance = crossWalletBalance;
    }

    public double getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(double balanceChange) {
        this.balanceChange = balanceChange;
    }
}