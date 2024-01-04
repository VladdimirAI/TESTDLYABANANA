package com.binance.connector.myyyyyFUTURE.streampotoki;

import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import com.binance.connector.futures.client.utils.WebSocketCallback;
import com.binance.connector.myyyyyFUTURE.processory.Processor;

public class UserDataStream {

    private UMWebsocketClientImpl userWsClient;
    private  Processor processor;

    public UserDataStream() {
        userWsClient = new UMWebsocketClientImpl();
       processor = new Processor(); //todo проерить как лучше синглтон или так
    }

    public void subscribeToUserDataStream(String listenKey) {
        userWsClient.listenUserStream(listenKey, new WebSocketCallback() {
            @Override
            public void onReceive(String message) {
                // Обработка уведомлений пользователя
//                System.out.println("Данные пользователя: " + message);
                processor.priemDannyhPolzovatelya(message);
            }
        });
    }
}
