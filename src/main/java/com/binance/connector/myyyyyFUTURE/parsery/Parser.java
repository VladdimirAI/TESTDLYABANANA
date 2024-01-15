package com.binance.connector.myyyyyFUTURE.parsery;

import com.binance.connector.myyyyyFUTURE.sushnosty.Order;
import com.binance.connector.myyyyyFUTURE.sushnosty.ValutnayaPara;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

  static   int i = 0;
  static   int y = 0;
    public static Order parseLinkedHashMapToOrder(LinkedHashMap<String, Object> map) {
        Order order = new Order();
        order.setOrderId((Long) map.getOrDefault("orderId", 0L));
        order.setSymbol((String) map.getOrDefault("symbol", ""));
        order.setCenaVhoda(Long.parseLong(map.getOrDefault("price", "0").toString()));
        order.setCummulativeQuoteQty(Double.parseDouble(map.getOrDefault("cumQuote", "0.0").toString()));
        order.setExecutedQty(Double.parseDouble(map.getOrDefault("executedQty", "0.0").toString()));
        order.setStatus((String) map.getOrDefault("status", ""));
        order.setSide((String) map.getOrDefault("side", ""));
        order.setType((String) map.getOrDefault("type", ""));
        return order;
    }

    public static void parseJsonResponseAndUpdateOrder(String jsonResponse, Order order) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        order.setOrderId(jsonObject.optLong("orderId", order.getOrderId()));
        order.setSymbol(jsonObject.optString("symbol", order.getSymbol()));
        order.setCenaVhoda(jsonObject.optDouble("price", order.getCenaVhoda()));
        order.setCummulativeQuoteQty(jsonObject.optDouble("cumQuote", order.getCummulativeQuoteQty()));
        order.setExecutedQty(jsonObject.optDouble("executedQty", order.getExecutedQty()));
        order.setStatus(jsonObject.optString("status", order.getStatus()));
        order.setSide(jsonObject.optString("side", order.getSide()));
        order.setType(jsonObject.optString("type", order.getType()));
    }







    public static Map<String, ValutnayaPara> parsInfoPosleZapytoy(String fileName) {
        String filePath = fileName; // Укажите правильный путь к файлу
        Map<String, ValutnayaPara> map = parseFile(filePath);
//        map.forEach((key, value) -> System.out.println(key + " => " + value));
        return map;
    }

    public static Map<String, ValutnayaPara> parseFile(String filePath) {
        Map<String, ValutnayaPara> resultMap = new HashMap<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] pairs = content.split("\\r?\\n\\r?\\n\\r?\\n"); // Разделить на пары по двум пустым строкам

//            Pattern pattern = Pattern.compile("Symbol: (\\S+).*?Tick Size: (\\d+\\.\\d+).*?Step Size: (\\d+\\.\\d+)", Pattern.DOTALL);
              Pattern pattern = Pattern.compile("Symbol: (\\S+).*?Tick Size: (\\d+(?:\\.\\d+)?).*?Step Size: (\\d+(?:\\.\\d+)?)", Pattern.DOTALL);



            for (String pair : pairs) {

                Matcher matcher = pattern.matcher(pair);
                if (matcher.find()) {

                    String symbol = matcher.group(1);

                        String tickSize = matcher.group(2);
                        String stepSize = matcher.group(3);
                        ValutnayaPara valutnayaPara = new ValutnayaPara(symbol, tickSize, stepSize);
                        resultMap.put(symbol, valutnayaPara);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }


}
