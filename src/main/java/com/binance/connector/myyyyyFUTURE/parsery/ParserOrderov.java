package com.binance.connector.myyyyyFUTURE.parsery;

import com.binance.connector.myyyyyFUTURE.sushnosty.Order;
import org.json.JSONObject;

public class ParserOrderov {

    public static Order parseOrder(String json) { //предпологаем что подходит на все три типа ордеров
        JSONObject jsonObj = new JSONObject(json);

        long orderId = jsonObj.getLong("orderId");
        String symbol = jsonObj.getString("symbol");
        double cummulativeQuoteQty = jsonObj.getDouble("cumQuote");
        double executedQty = jsonObj.getDouble("executedQty");
        String status = jsonObj.getString("status");
        String side = jsonObj.getString("side");
        String type = jsonObj.getString("type");

        // Цена входа не указана в ответе, так что мы ставим ее в VistavlyatelStopITakeProffit

        return new Order(symbol, orderId, cummulativeQuoteQty, executedQty, status, side, type);
    }




}
