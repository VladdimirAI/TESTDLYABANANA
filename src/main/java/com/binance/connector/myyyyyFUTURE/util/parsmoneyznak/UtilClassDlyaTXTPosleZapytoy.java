package com.binance.connector.myyyyyFUTURE.util.parsmoneyznak;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UtilClassDlyaTXTPosleZapytoy {

    public static void main(String[] args) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("C:\\infoMoneyFuture.json")));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray symbols = jsonObject.getJSONArray("symbols");
            List<String> tradingPairsInfo = parseTradingPairs(symbols);
            Files.write(Paths.get("C:\\parsedPairsInfoMONEYFUTURE.txt"), tradingPairsInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> parseTradingPairs(JSONArray symbols) {
        List<String> tradingPairsInfo = new ArrayList<>();
        for (int i = 0; i < symbols.length(); i++) {
            JSONObject symbol = symbols.getJSONObject(i);
            String symbolName = symbol.getString("symbol");
            if (symbolName.endsWith("USDT")) {
                StringBuilder info = new StringBuilder();
                info.append("Symbol: ").append(symbolName).append("\n");

                JSONArray filters = symbol.getJSONArray("filters");
                for (int j = 0; j < filters.length(); j++) {
                    JSONObject filter = filters.getJSONObject(j);
                    String filterType = filter.getString("filterType");
                    if ("PRICE_FILTER".equals(filterType)) {
                        info.append("Price Filter: \n");
                        info.append("  Tick Size: ").append(filter.getString("tickSize")).append("\n");
                    }
                    if ("LOT_SIZE".equals(filterType)) {
                        info.append("Lot Size: \n");
                        info.append("  Step Size: ").append(filter.getString("stepSize")).append("\n");
                    }
                }
                info.append("\n");
                tradingPairsInfo.add(info.toString());
            }
        }
        return tradingPairsInfo;
    }
}
//    Для ACHUSDT:
//
//        Tick Size: 0.00001000 — это минимальное изменение цены, которое может быть для этой пары. Но для вашего примера это не столь важно.
//        Step Size: 1.00000000 — это означает, что вы можете торговать этой монетой только целыми числами (например, 1, 2, 3 и так далее), дробные количества не допускаются.
//        Для JOEUSDT:
//
//        Tick Size: 0.00010000 — это минимальное изменение цены для этой пары.
//        Step Size: 0.01000000 — это означает, что минимальное количество, которое вы можете купить или продать, составляет 0.01. Вы можете торговать этой монетой в количествах, кратных 0.01 (например, 0.01, 0.02, 0.03 и так далее, вплоть до двух знаков после запятой).

