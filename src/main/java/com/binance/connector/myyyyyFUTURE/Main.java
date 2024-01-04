package com.binance.connector.myyyyyFUTURE;

import com.binance.connector.myyyyyFUTURE.parsery.GetterAndParserSvechey;
import com.binance.connector.myyyyyFUTURE.parsery.ReaderMoney;
import com.binance.connector.myyyyyFUTURE.streampotoki.AllPairsCandlestickStream;
import com.binance.connector.myyyyyFUTURE.streampotoki.UserDataStream;
import com.binance.connector.myyyyyFUTURE.ustanovkaorderov.OrderManager;

import java.util.List;

public class Main {
    //    DefaultUrls
    public static void main(String[] args) {

        ReaderMoney readerMoney = new ReaderMoney();
        List<String> oldMoneyZnak = readerMoney.readCurrencyPairsFromFile(PrivateConfig.MONEY_FILE);//todo поменять файл на фьючерсный
        for (String pair : oldMoneyZnak) {
            System.out.println(pair);
        }

        GURU.activator(oldMoneyZnak);

        GetterAndParserSvechey.getVseSvechiizSpiska(oldMoneyZnak);


//        AllPairsCandlestickStream allPairsCandlestickStream = new AllPairsCandlestickStream();
//        allPairsCandlestickStream.subscribeToAllPairsCandlesticks(GURU.MONEY,PrivateConfig.TIMENG);
//
//        UserDataStream userDataStream = new UserDataStream();
//        userDataStream.subscribeToUserDataStream(PrivateConfig.LISTEN_KEY);

//        OrderManager orderManager = new OrderManager(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
//
//        // Создание маркет ордера на продажу (SHORT)
//        orderManager.createMarketOrder("ACHUSDT", "SELL", "309");  //Market Order Response: {"orderId":944500711,"symbol":"ACHUSDT","status":"NEW","clientOrderId":"JFtLsyHfIQQ6z9EOoccywB","price":"0.0000000","avgPrice":"0.00","origQty":"309","executedQty":"0","cumQty":"0","cumQuote":"0.0000000","timeInForce":"GTC","type":"MARKET","reduceOnly":false,"closePosition":false,"side":"SELL","positionSide":"BOTH","stopPrice":"0.0000000","workingType":"CONTRACT_PRICE","priceProtect":false,"origType":"MARKET","priceMatch":"NONE","selfTradePreventionMode":"NONE","goodTillDate":0,"updateTime":1704311239766}


//        // Установка стоп-лосса для шорта
//        orderManager.setStopLossForShort("ACHUSDT", "0.01", "18000");
//
//        // Установка тейк-профита для шорта
//        orderManager.setTakeProfitForShort("ACHUSDT", "0.01", "22000");
    }
}
