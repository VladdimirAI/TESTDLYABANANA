package com.binance.connector.myyyyyFUTURE.ustanovkaorderov;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.PrivateConfig;
import com.binance.connector.myyyyyFUTURE.parsery.ParserOrderov;
import com.binance.connector.myyyyyFUTURE.sushnosty.Order;

import java.util.LinkedHashMap;

public class OrderManager {

    private static UMFuturesClientImpl client;

    public OrderManager(String apiKey, String secretKey) {
        client = new UMFuturesClientImpl(apiKey, secretKey);
    }

    public Order createMarketOrder(String symbol, String side, double quantity,boolean socrashat) { //"origType":"MARKET"//todo квенти был стриннгом - если будут неполадки сомтреть сюда

        double ocruglenuyQuantitySuchetomMonety = GURU.ocruglitel(quantity, GURU.getMapPosleZapytoy().get(symbol).cifrPosleZapytoyDlyaLotaVoVTOROYMONETE);


        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", side); // "SELL" для шорта, "BUY" для лонга
        parameters.put("type", "MARKET");
        parameters.put("quantity", ocruglenuyQuantitySuchetomMonety);
        parameters.put("reduceOnly", socrashat); // Установите этот параметр, если вы хотите, чтобы ордер выполнялся только как сокращение позиции


        // Вызов метода для создания ордера
        String response = client.account().newOrder(parameters);
        System.out.println("Market Order Response: " + response);

        Order order = ParserOrderov.parseOrder(response); //цену входа ставим в процессоре  в zahodVShellPoziciyu
        order.colichestvoCuplennuhMonet = ocruglenuyQuantitySuchetomMonety;

        return order;

    }
//    Market Order Response: {"orderId":945383596,"symbol":"ACHUSDT","status":"NEW","clientOrderId":"XWE2lEN9zwGvMtrWlBMD1F","price":"0.0000000","avgPrice":"0.00","origQty":"307","executedQty":"0","cumQty":"0","cumQuote":"0.0000000","timeInForce":"GTC","type":"MARKET","reduceOnly":false,"closePosition":false,"side":"SELL","positionSide":"BOTH","stopPrice":"0.0000000","workingType":"CONTRACT_PRICE","priceProtect":false,"origType":"MARKET","priceMatch":"NONE","selfTradePreventionMode":"NONE","goodTillDate":0,"updateTime":1704332660235}


    public  void setMARKETStopLossForShort(String symbol, String quantity, String stopPrice) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", "BUY"); // Маркетный Стоп-лосс для шорта - ордер на покупку
        parameters.put("type", "STOP_MARKET");
        parameters.put("quantity", quantity);
        parameters.put("stopPrice", stopPrice);

        String response = client.account().newOrder(parameters);
        System.out.println("Stop Loss Order Response: " + response);
    }

    public void setMARKETTakeProfitForShort(String symbol, String quantity, String stopPrice) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", "BUY"); // Тейк-профит для шорта - ордер на покупку
        parameters.put("type", "TAKE_PROFIT_MARKET");
        parameters.put("quantity", quantity);
        parameters.put("stopPrice", stopPrice);

        String response = client.account().newOrder(parameters);
        System.out.println("Take Profit Order Response: " + response);
    }



        // Существующие методы...

        public Order creatMARKETrderTakeProfit(String symbol, double quantity, double price) {//"origType":"LIMIT"  .. название поменять МАркет

        double ocruglenuyQuantitySuchetomMonety = GURU.ocruglitel(quantity, GURU.getMapPosleZapytoy().get(symbol).cifrPosleZapytoyDlyaLotaVoVTOROYMONETE);

            LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("symbol", symbol);
            parameters.put("side", "BUY");
//            parameters.put("type", "LIMIT");
            parameters.put("type", "TAKE_PROFIT_MARKET");
            parameters.put("timeInForce", "GTC"); // Good Till Canceled
            parameters.put("quantity", ocruglenuyQuantitySuchetomMonety); //todo test quantity ocruglenuyQuantitySuchetomMonety
//            parameters.put("price", String.valueOf(price));//todo возможно можноактивировать для ограничения цены  - типа до скольки готов тепрпть убыток
            parameters.put("stopPrice", String.valueOf(price));
            parameters.put("reduceOnly", true); // Установите этот параметр, если вы хотите, чтобы ордер выполнялся только как сокращение позиции


            String response = client.account().newOrder(parameters);
            System.out.println("Delayed Buy Order Response: " + response);
            return ParserOrderov.parseOrder(response);
        }
 //   Delayed Buy Order Response: {"orderId":948872562,"symbol":"ACHUSDT","status":"NEW","clientOrderId":"Amioe3kKbu1vpq9xFvuoKI","price":"0.0000000","avgPrice":"0.00","origQty":"158","executedQty":"0","cumQty":"0","cumQuote":"0.0000000","timeInForce":"GTC","type":"TAKE_PROFIT_MARKET","reduceOnly":true,"closePosition":false,"side":"BUY","positionSide":"BOTH","stopPrice":"0.0197100","workingType":"CONTRACT_PRICE","priceProtect":false,"origType":"TAKE_PROFIT_MARKET","priceMatch":"NONE","selfTradePreventionMode":"NONE","goodTillDate":0,"updateTime":1704448357952}


//0.01950


    public Order creatMARKETOrderStopLoss(String symbol, double quantity, double price) {//"origType":"STOP_MARKET"

        double ocruglenuyQuantitySuchetomMonety = GURU.ocruglitel(quantity, GURU.getMapPosleZapytoy().get(symbol).cifrPosleZapytoyDlyaLotaVoVTOROYMONETE);


        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", "BUY");
        parameters.put("type", "STOP_MARKET");
        parameters.put("timeInForce", "GTC"); // Good Till Canceled
        parameters.put("quantity", ocruglenuyQuantitySuchetomMonety); //todo test quantity ocruglenuyQuantitySuchetomMonety
        parameters.put("stopPrice", String.valueOf(price)); // Цена активации стоп-ордера
        parameters.put("reduceOnly", true); // Установите этот параметр, если вы хотите, чтобы ордер выполнялся только как сокращение позиции


        String response = client.account().newOrder(parameters);
        System.out.println("Delayed Buy Order Response: " + response);
        return ParserOrderov.parseOrder(response);
    }
  //      Delayed Buy Order Response: {"orderId":945483426,"symbol":"ACHUSDT","status":"NEW","clientOrderId":"yZy0YEixZA5ZnjoDXFkD79","price":"0.0000000","avgPrice":"0.00","origQty":"308","executedQty":"0","cumQty":"0","cumQuote":"0.0000000","timeInForce":"GTC","type":"STOP_MARKET","reduceOnly":false,"closePosition":false,"side":"BUY","positionSide":"BOTH","stopPrice":"0.0200000","workingType":"CONTRACT_PRICE","priceProtect":false,"origType":"STOP_MARKET","priceMatch":"NONE","selfTradePreventionMode":"NONE","goodTillDate":0,"updateTime":1704336377661}


    public void cancelOrder(String symbol, long orderId) { //для отложенных лимит и стоп лос "status":"CANCELED",
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("orderId", orderId);

        String response = client.account().cancelOrder(parameters);
        System.out.println("Cancel Order Response: " + response);
    }
//    Cancel Order Response: {"orderId":946916635,"symbol":"ACHUSDT","status":"CANCELED","clientOrderId":"VrFjrKhVtPgfcxgoRh5jsF","price":"0.0190000","avgPrice":"0.00","origQty":"309","executedQty":"0","cumQty":"0","cumQuote":"0.0000000","timeInForce":"GTC","type":"LIMIT","reduceOnly":false,"closePosition":false,"side":"BUY","positionSide":"BOTH","stopPrice":"0.0000000","workingType":"CONTRACT_PRICE","priceProtect":false,"origType":"LIMIT","priceMatch":"NONE","selfTradePreventionMode":"NONE","goodTillDate":0,"updateTime":1704382866522}



//    public static void main(String[] args) {
    public static void main() {
        OrderManager orderManager = new OrderManager(PrivateConfig.API_KEY,PrivateConfig.SECRET_KEY);
//        orderManager.creatLIMITOrderTakeProfit("ACHUSDT",308,0.22000);
//        orderManager.creatLIMITOrderTakeProfit("ACHUSDT",309,0.01900);
//        orderManager.cancelOrder("ACHUSDT",946916635);



//        orderManager.creatMARKETOrderStopLoss("ACHUSDT",120,0.01975);
//        orderManager.creatMARKETrderTakeProfit("ACHUSDT",158,0.01970);
        System.out.println("Оправляю нужный ордер ------------------VVVVVVVVVVVVVVVVVVVVVVVVVVVV");

        orderManager.createMarketOrder("ACHUSDT","SELL",307,false);

        System.out.println("После ордера ордера ------------------MMMMMMMMMMMMMMM");

//        orderManager.createMarketOrder("ACHUSDT","BUY",307,true);
    }


    }

//            orderManager.createMarketOrder("ACHUSDT","SELL",307);
//            orderManager.createMarketOrder("ACHUSDT","BUY",307);
//    Market Order Response: {"orderId":946901926,"symbol":"ACHUSDT","status":"NEW","clientOrderId":"Pj5QexorB7VXMaXcl8Dxzk","price":"0.0000000","avgPrice":"0.00","origQty":"307","executedQty":"0","cumQty":"0","cumQuote":"0.0000000","timeInForce":"GTC","type":"MARKET","reduceOnly":false,"closePosition":false,"side":"SELL","positionSide":"BOTH","stopPrice":"0.0000000","workingType":"CONTRACT_PRICE","priceProtect":false,"origType":"MARKET","priceMatch":"NONE","selfTradePreventionMode":"NONE","goodTillDate":0,"updateTime":1704382431227} //открыл маркет
//Market Order Response: {"orderId":946906067,"symbol":"ACHUSDT","status":"NEW","clientOrderId":"zozWVmtJTaOUhDUpqSGR8C","price":"0.0000000","avgPrice":"0.00","origQty":"307","executedQty":"0","cumQty":"0","cumQuote":"0.0000000","timeInForce":"GTC","type":"MARKET","reduceOnly":false,"closePosition":false,"side":"BUY","positionSide":"BOTH","stopPrice":"0.0000000","workingType":"CONTRACT_PRICE","priceProtect":false,"origType":"MARKET","priceMatch":"NONE","selfTradePreventionMode":"NONE","goodTillDate":0,"updateTime":1704382497958}//закрыл маркет

//////////////

//        orderManager.creatLIMITOrderTakeProfit("ACHUSDT",309,0.01900);
//    Delayed Buy Order Response: {"orderId":946916635,"symbol":"ACHUSDT","status":"NEW","clientOrderId":"VrFjrKhVtPgfcxgoRh5jsF","price":"0.0190000","avgPrice":"0.00","origQty":"309","executedQty":"0","cumQty":"0","cumQuote":"0.0000000","timeInForce":"GTC","type":"LIMIT","reduceOnly":false,"closePosition":false,"side":"BUY","positionSide":"BOTH","stopPrice":"0.0000000","workingType":"CONTRACT_PRICE","priceProtect":false,"origType":"LIMIT","priceMatch":"NONE","selfTradePreventionMode":"NONE","goodTillDate":0,"updateTime":1704382775476}
//////////////////
//        orderManager.cancelOrder("ACHUSDT",946916635); удалил тейк профит
//                Cancel Order Response: {"orderId":946916635,"symbol":"ACHUSDT","status":"CANCELED","clientOrderId":"VrFjrKhVtPgfcxgoRh5jsF","price":"0.0190000","avgPrice":"0.00","origQty":"309","executedQty":"0","cumQty":"0","cumQuote":"0.0000000","timeInForce":"GTC","type":"LIMIT","reduceOnly":false,"closePosition":false,"side":"BUY","positionSide":"BOTH","stopPrice":"0.0000000","workingType":"CONTRACT_PRICE","priceProtect":false,"origType":"LIMIT","priceMatch":"NONE","selfTradePreventionMode":"NONE","goodTillDate":0,"updateTime":1704382866522}
//////////////





