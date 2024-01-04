package com.binance.connector.myyyyyFUTURE.parsery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderMoney {
    public List<String> readCurrencyPairsFromFile(String filePath) {
        List<String> currencyPairs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                currencyPairs.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencyPairs;
    }
}
