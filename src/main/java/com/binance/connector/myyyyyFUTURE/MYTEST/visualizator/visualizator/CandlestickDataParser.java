package com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator;

import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;
import org.json.JSONArray;
//import org.json.JSONException;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CandlestickDataParser {
//
//    public static List<Svecha> readCandlesticksFromFile(String fileName) {
//        List<Svecha> candlesticks = new ArrayList<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//            String line = reader.readLine();
//            JSONArray jsonArray = new JSONArray(line);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONArray candlestickData = jsonArray.getJSONArray(i);
////                long openTime = candlestickData.getLong(0);
//                double open = candlestickData.getDouble(1);
//                double high = candlestickData.getDouble(2);
//                double low = candlestickData.getDouble(3);
//                double close = candlestickData.getDouble(4);
//                // Пропускаем остальные данные, так как они нам не нужны для отображения
//
//                Svecha candlestick = new Svecha( open, high, low, close);
//                candlesticks.add(candlestick);  //todo  в ориге ложит еще два параметра
//            }
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//
//        return candlesticks;
//    }
//}