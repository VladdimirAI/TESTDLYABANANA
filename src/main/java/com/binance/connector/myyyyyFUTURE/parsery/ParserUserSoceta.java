package com.binance.connector.myyyyyFUTURE.parsery;

import com.binance.connector.myyyyyFUTURE.sushnosty.OrderDTO;
import org.json.JSONArray;
import org.json.JSONObject;

public class ParserUserSoceta {

    public static void parseAccountUpdate(String jsonData) {
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW parseAccountUpdate");

        JSONObject jsonObj = new JSONObject(jsonData);
        String eventType = jsonObj.getString("e");              // Тип события
        long eventTime = jsonObj.getLong("E");                 // Время события
        long transactionTime = jsonObj.getLong("T");           // Время транзакции
        System.out.println("Event Type: " + eventType + ", Event Time: " + eventTime + ", Transaction Time: " + transactionTime);



        JSONObject updateData = jsonObj.getJSONObject("a");
        String eventReasonType = updateData.getString("m");    // Причина события
        System.out.println("Event Reason Type: " + eventReasonType);


        JSONArray balances = updateData.getJSONArray("B");
        for (int i = 0; i < balances.length(); i++) {
            JSONObject balance = balances.getJSONObject(i);
            String asset = balance.getString("a");             // Актив
            String walletBalance = balance.getString("wb");    // Баланс кошелька
            String crossWalletBalance = balance.getString("cw");// Перекрестный баланс кошелька
            String balanceChange = balance.getString("bc");    // Изменение баланса, за исключением PnL и комиссии
            // Дополнительная обработка здесь
            System.out.println("Asset: " + asset + ", Wallet Balance: " + walletBalance + ", Cross Wallet Balance: " + crossWalletBalance + ", Balance Change: " + balanceChange);

        }


        JSONArray positions = updateData.getJSONArray("P");
        for (int i = 0; i < positions.length(); i++) {
            JSONObject position = positions.getJSONObject(i);
            String symbol = position.getString("s");           // Символ
            String positionAmount = position.getString("pa");  // Количество в позиции
            String entryPrice = position.getString("ep");      // Цена входа
            String unrealizedPnL = position.getString("up");   // Нереализованный PnL
            // Дополнительная обработка здесь
            System.out.println("Symbol: " + symbol + ", Position Amount: " + positionAmount + ", Entry Price: " + entryPrice + ", Unrealized PnL: " + unrealizedPnL);

        }

        // Дополнительные действия после парсинга

        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
    }

    public static OrderDTO handleOrderTradeUpdate(String jsonData) {
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW handleOrderTradeUpdate");
        JSONObject jsonObj = new JSONObject(jsonData);
//        String eventType = jsonObj.getString("e");              // Тип события
//        long eventTime = jsonObj.getLong("E");                 // Время события
//        long transactionTime = jsonObj.getLong("T");           // Время транзакции
//        System.out.println("Event Type: " + eventType + ", Event Time: " + eventTime + ", Transaction Time: " + transactionTime);


        JSONObject orderData = jsonObj.getJSONObject("o");
        String symbol = orderData.getString("s");              // Символ  ////////////////////////////////////////////////////

        String side = orderData.getString("S");                // Сторона (покупка/продажа)   /////////////////////////////////

        String originalQuantity = orderData.getString("q");   // Исходное количество   ///////////////////////////////////////////

        String orderStatus = orderData.getString("X");        // Статус ордера  ////////////////////////////////////////////////
        long orderId = orderData.getLong("i");                // Идентификатор ордера  ///////////////////////////////////////////

        // Дополнительная обработка здесь
        System.out.println("Symbol: " + symbol + ", Side: " + side + " Original Quantity: " + originalQuantity +
                ", Order Status: " + orderStatus + ", Order ID: " + orderId );
        // Дополнительные действия после парсинга
        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");


       return new OrderDTO(symbol,side,Double.parseDouble(originalQuantity),orderStatus,orderId);
    }


}


////todo ниже предудущие полностью рабочии версии полные
//System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW parseAccountUpdate");
//
//        JSONObject jsonObj = new JSONObject(jsonData);
//        String eventType = jsonObj.getString("e");              // Тип события
//        long eventTime = jsonObj.getLong("E");                 // Время события
//        long transactionTime = jsonObj.getLong("T");           // Время транзакции
//        System.out.println("Event Type: " + eventType + ", Event Time: " + eventTime + ", Transaction Time: " + transactionTime);
//
//
//
//        JSONObject updateData = jsonObj.getJSONObject("a");
//        String eventReasonType = updateData.getString("m");    // Причина события
//        System.out.println("Event Reason Type: " + eventReasonType);
//
//
//        JSONArray balances = updateData.getJSONArray("B");
//        for (int i = 0; i < balances.length(); i++) {
//        JSONObject balance = balances.getJSONObject(i);
//        String asset = balance.getString("a");             // Актив
//        String walletBalance = balance.getString("wb");    // Баланс кошелька
//        String crossWalletBalance = balance.getString("cw");// Перекрестный баланс кошелька
//        String balanceChange = balance.getString("bc");    // Изменение баланса, за исключением PnL и комиссии
//        // Дополнительная обработка здесь
//        System.out.println("Asset: " + asset + ", Wallet Balance: " + walletBalance + ", Cross Wallet Balance: " + crossWalletBalance + ", Balance Change: " + balanceChange);
//
//        }
//
//
//        JSONArray positions = updateData.getJSONArray("P");
//        for (int i = 0; i < positions.length(); i++) {
//        JSONObject position = positions.getJSONObject(i);
//        String symbol = position.getString("s");           // Символ
//        String positionAmount = position.getString("pa");  // Количество в позиции
//        String entryPrice = position.getString("ep");      // Цена входа
//        String unrealizedPnL = position.getString("up");   // Нереализованный PnL
//        // Дополнительная обработка здесь
//        System.out.println("Symbol: " + symbol + ", Position Amount: " + positionAmount + ", Entry Price: " + entryPrice + ", Unrealized PnL: " + unrealizedPnL);
//
//        }
//
//        // Дополнительные действия после парсинга
//
//        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
//        }
//
//public static void handleOrderTradeUpdate(String jsonData) {
//        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW handleOrderTradeUpdate");
//        JSONObject jsonObj = new JSONObject(jsonData);
//        String eventType = jsonObj.getString("e");              // Тип события
//        long eventTime = jsonObj.getLong("E");                 // Время события
//        long transactionTime = jsonObj.getLong("T");           // Время транзакции
//        System.out.println("Event Type: " + eventType + ", Event Time: " + eventTime + ", Transaction Time: " + transactionTime);
//
//
//        JSONObject orderData = jsonObj.getJSONObject("o");
//        String symbol = orderData.getString("s");              // Символ
//        String clientOrderId = orderData.getString("c");       // Идентификатор клиентского ордера
//        String side = orderData.getString("S");                // Сторона (покупка/продажа)
//        String orderType = orderData.getString("o");           // Тип ордера
//        String timeInForce = orderData.getString("f");        // Время действия ордера
//        String originalQuantity = orderData.getString("q");   // Исходное количество
//        String originalPrice = orderData.getString("p");      // Исходная цена
//        String averagePrice = orderData.getString("ap");      // Средняя цена
//        String stopPrice = orderData.getString("sp");         // Цена стоп-заявки
//        String executionType = orderData.getString("x");      // Тип исполнения
//        String orderStatus = orderData.getString("X");        // Статус ордера
//        long orderId = orderData.getLong("i");                // Идентификатор ордера
//        String lastFilledQuantity = orderData.getString("l"); // Последнее заполненное количество
//        String filledAccumulatedQuantity = orderData.getString("z"); // Накопленное количество заполнения
//        String lastFilledPrice = orderData.getString("L");    // Цена последнего заполнения
//        // Дополнительная обработка здесь
//        System.out.println("Symbol: " + symbol + ", Client Order ID: " + clientOrderId + ", Side: " + side + ", Order Type: " + orderType +
//        ", Time in Force: " + timeInForce + ", Original Quantity: " + originalQuantity + ", Original Price: " + originalPrice +
//        ", Average Price: " + averagePrice + ", Stop Price: " + stopPrice + ", Execution Type: " + executionType +
//        ", Order Status: " + orderStatus + ", Order ID: " + orderId + ", Last Filled Quantity: " + lastFilledQuantity +
//        ", Filled Accumulated Quantity: " + filledAccumulatedQuantity + ", Last Filled Price: " + lastFilledPrice);
//        // Дополнительные действия после парсинга
//        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
//        }