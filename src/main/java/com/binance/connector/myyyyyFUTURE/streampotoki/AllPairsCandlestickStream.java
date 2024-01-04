package com.binance.connector.myyyyyFUTURE.streampotoki;

import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import com.binance.connector.futures.client.utils.WebSocketCallback;
import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.processory.Processor;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllPairsCandlestickStream {

    private UMWebsocketClientImpl wsClient;
    private Processor processor;

    public AllPairsCandlestickStream() {
        wsClient = new UMWebsocketClientImpl();
        processor = GURU.processor;
    }

    public void subscribeToAllPairsCandlesticks(List<String> symbols, String interval) {
        ArrayList<String> streams = new ArrayList<>();

        for (String symbol : symbols) {
            streams.add(symbol.toLowerCase() + "@kline_" + interval);
        }

        wsClient.combineStreams(streams, new WebSocketCallback() {
            @Override
            public void onReceive(String message) {
                // Здесь вы можете анализировать сообщения о свечах
                System.out.println("Данные о свечах: " + message);

                // Пример обработки JSON-ответа
                JSONObject jsonEvent = new JSONObject(message);
                JSONObject data = jsonEvent.getJSONObject("data");
                JSONObject kline = data.getJSONObject("k");
                boolean isKlineClosed = kline.getBoolean("x");
                if (isKlineClosed) {
                    System.out.println("Свеча закрыта");// Действия в случае, если свеча закрыта
            processor.priemJsonZakrytyhSvechey(jsonEvent);
                }
            }
        });
    }
}

