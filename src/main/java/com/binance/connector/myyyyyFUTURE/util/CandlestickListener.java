package com.binance.connector.myyyyyFUTURE.util;

import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;

import java.util.ArrayList;

public class CandlestickListener {
    public static void main(String[] args) {
        UMWebsocketClientImpl client = new UMWebsocketClientImpl(); // используйте URL продакшн-версии WebSocket

        // Создаем поток для прослушивания свечей на паре BTC/USDT
        // Параметр "1m" означает 1-минутные свечи. Можно изменить на другой интервал, например "5m" для 5-минутных свечей
        int streamID = client.klineStream("btcusdt", "1m", (event) -> {
            System.out.println(event); // вывод информации о свече
        });

        // Для закрытия потока используйте client.closeConnection(streamID);
    }
}
