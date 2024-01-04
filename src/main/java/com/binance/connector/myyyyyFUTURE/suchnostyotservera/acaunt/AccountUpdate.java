package com.binance.connector.myyyyyFUTURE.suchnostyotservera.acaunt;

import java.util.ArrayList;
import java.util.List;

public class AccountUpdate {
    private List<AccountBalance> balances;
    private List<Position> positions;

    // Геттеры и сеттеры


    public AccountUpdate() {
        balances = new ArrayList<>();
        positions = new ArrayList<>();
    }

    public List<AccountBalance> getBalances() {
        return balances;
    }

    public void setBalances(List<AccountBalance> balances) {
        this.balances = balances;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
