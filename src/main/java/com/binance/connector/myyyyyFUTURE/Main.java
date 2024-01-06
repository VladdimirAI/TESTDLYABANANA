package com.binance.connector.myyyyyFUTURE;

import com.binance.connector.myyyyyFUTURE.parsery.GetterAndParserSvechey;
import com.binance.connector.myyyyyFUTURE.parsery.ReaderMoney;
import com.binance.connector.myyyyyFUTURE.streampotoki.AllPairsCandlestickStream;
import com.binance.connector.myyyyyFUTURE.streampotoki.UserDataStream;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static   AllPairsCandlestickStream allPairsCandlestickStream;
    public static UserDataStream userDataStream;
    public static String listenKey;

    public static void main(String[] args) {

        ReaderMoney readerMoney = new ReaderMoney();
        List<String> oldMoneyZnak = readerMoney.readCurrencyPairsFromFile(PrivateConfig.MONEY_FILE);//todo поменять файл на фьючерсный
        for (String pair : oldMoneyZnak) {
            System.out.println(pair);
        }

        GURU.activator(oldMoneyZnak);

        GetterAndParserSvechey.getVseSvechiizSpiska(oldMoneyZnak);


        listenKey = GURU.orderManager.createListenKey();
        System.out.println(listenKey);


        allPairsCandlestickStream = new AllPairsCandlestickStream();
        allPairsCandlestickStream.subscribeToAllPairsCandlesticks(GURU.MONEY, PrivateConfig.TIMENG);  // todo раз в 24 часа перепдключать
//
        userDataStream = new UserDataStream();
        userDataStream.subscribeToUserDataStream(listenKey);
//        userDataStream.subscribeToUserDataStream(PrivateConfig.LISTEN_KEY);  // для тестов










        ScheduledExecutorService executorServiceReconnect = Executors.newSingleThreadScheduledExecutor();
        long periodReconnect = 24 * 60; // Период в минутах для переподключения (24 часа)
        executorServiceReconnect.scheduleAtFixedRate(() -> reconnectStreams(), 24 * 60, periodReconnect, TimeUnit.MINUTES);


        ///ниже продление листен кей

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(); // todo раз в 55 минут делает PUT запрос

        // Запуск задачи с начальной задержкой 55 и повтором каждые 55 минут
        long period = 53; // Период в минутах
        executorService.scheduleAtFixedRate(() -> callMyMethod(), 53, period, TimeUnit.MINUTES);
    }

    public static void callMyMethod() {
        GURU.orderManager.prodlenyeListenKey();
//        GURU.playSIGNAL();
    }



    public static void reconnectStreams() {
        try {
            // Закрыть текущие WebSocket соединения
            closeCurrentWebsocketConnections();

            // Подождать некоторое время перед повторным подключением
            Thread.sleep(3000); // Ожидание 3 секундs

            // Повторно инициировать WebSocket соединения
            initializeWebsocketConnections();
        } catch (InterruptedException e) {
            System.out.println("Неполадки в методе reconnectStreams()");
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Неполадки 2222 в методе reconnectStreams()");
            e.printStackTrace();
        }
    }

    private static void closeCurrentWebsocketConnections() {
       userDataStream.closeAllStream();
       allPairsCandlestickStream.closeAllStream();
    }

    private static void initializeWebsocketConnections() {
        // Здесь ваш код для создания и подключения новых WebSocket соединений
        // Например: создание новых экземпляров AllPairsCandlestickStream и UserDataStream
        allPairsCandlestickStream = new AllPairsCandlestickStream();
        allPairsCandlestickStream.subscribeToAllPairsCandlesticks(GURU.MONEY, PrivateConfig.TIMENG);

        UserDataStream userDataStream = new UserDataStream();
        userDataStream.subscribeToUserDataStream(listenKey);
    }

}


//        OrderManager.main();
//        OrderManager orderManager = new OrderManager(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
//
//        // Создание маркет ордера на продажу (SHORT)
//        orderManager.createMarketOrder("ACHUSDT", "SELL", "309");  //Market Order Response: {"orderId":944500711,"symbol":"ACHUSDT","status":"NEW","clientOrderId":"JFtLsyHfIQQ6z9EOoccywB","price":"0.0000000","avgPrice":"0.00","origQty":"309","executedQty":"0","cumQty":"0","cumQuote":"0.0000000","timeInForce":"GTC","type":"MARKET","reduceOnly":false,"closePosition":false,"side":"SELL","positionSide":"BOTH","stopPrice":"0.0000000","workingType":"CONTRACT_PRICE","priceProtect":false,"origType":"MARKET","priceMatch":"NONE","selfTradePreventionMode":"NONE","goodTillDate":0,"updateTime":1704311239766}


//        // Установка стоп-лосса для шорта
//        orderManager.setStopLossForShort("ACHUSDT", "0.01", "18000");
//
//        // Установка тейк-профита для шорта
//        orderManager.setTakeProfitForShort("ACHUSDT", "0.01", "22000");