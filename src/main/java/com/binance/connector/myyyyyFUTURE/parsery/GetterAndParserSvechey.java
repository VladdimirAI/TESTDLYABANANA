package com.binance.connector.myyyyyFUTURE.parsery;



import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.PrivateConfig;

import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;
import org.json.JSONArray;
import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetterAndParserSvechey {

    public static void getVseSvechiizSpiska(List<String> oldMoneyZnak) {
        for(String moneyPara : oldMoneyZnak){
            getDlyaOndnoyPary22(moneyPara);
        }
    }

    public static void getDlyaOndnoyPary22(String symbol){

        String interval = PrivateConfig.TIMENG; // Интервал свечи: 1m, 3m, 5m, 15m, 30m, 1h, 2h, 4h, 6h, 8h, 12h, 1d, 3d, 1w, 1M
        int limit = 22; // Количество свечей: 1-1500

        // Выполняем запрос и получаем данные
        String responseData = fetchCandlestickData(symbol, interval, limit);
        // Если данные получены, записываем их в файл
        System.out.println("11111111111");
        if (responseData != null) {
            writeDataToFile(symbol + ".txt", responseData); // тут формируеться же и имя докумета тхт //todo в будущем убрать - и куда ваще сохраняеться ?
            System.out.println("22222222222222");
            List<Svecha> svechi = parseSvechiFromResponse(responseData,symbol);

            GURU.addOneMoneyAndHistorySvechey(symbol,svechi);
            System.out.println("33333333333333333333");
        }
    }
    /**
     * Запрашивает данные свечей с Binance API.
     *
     * @param symbol Валютная пара для получения данных.
     * @param interval Интервал времени для каждой свечи.
     * @param limit Количество запрашиваемых свечей.
     * @return Строка с данными свечей в формате JSON или null при ошибке.
     */
    private static String fetchCandlestickData(String symbol, String interval, int limit) {
//        String urlString = "https://api.binance.com/api/v3/klines?symbol=" + symbol + "&interval=" + interval + "&limit=" + limit;
        String urlString = "https://fapi.binance.com/fapi/v1/markPriceKlines?symbol=" + symbol + "&interval=" + interval + "&limit=" + limit;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream responseStream = connection.getInputStream();
            Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8.name());
            String responseData = scanner.useDelimiter("\\A").next(); // Используем сканер для чтения входящего потока
            scanner.close();

            return responseData;
        } catch (IOException e) {
            System.err.println("Ошибка при получении данных свечей в классе GetterAndParserSvechey - начало программы: " + e.getMessage());
            return null;
        }
    }

    /**
     * Записывает строку данных в файл.
     *
     * @param fileName Имя файла, в который будет произведена запись.
     * @param data Строка данных для записи.
     */
    private static void writeDataToFile(String fileName, String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(data); // Записываем данные в файл
            writer.close();
            System.out.println("Данные успешно записаны в файл " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи данных в файл: " + e.getMessage());
        }
    }



    public static List<Svecha> parseSvechiFromResponse(String responseData, String symbol) {
        List<Svecha> svechi = new ArrayList<>();
        JSONArray svechiJsonArray = new JSONArray(responseData);

        for (int i = 0; i < svechiJsonArray.length(); i++) {
            JSONArray svechaJson = svechiJsonArray.getJSONArray(i);
            long openTime = svechaJson.getLong(0);
            double open = svechaJson.getDouble(1);
            double high = svechaJson.getDouble(2);
            double low = svechaJson.getDouble(3);
            double close = svechaJson.getDouble(4);

            Color color = (close >= open) ? Color.GREEN : Color.RED;
            svechi.add(new Svecha(color, symbol, openTime, open, high, low, close));
        }

        return svechi;
    }

}




//    Ответ API Binance на запрос данных свечей (klines) включает следующую информацию для каждой свечи​​:
//
//        Open time: Время открытия свечи в миллисекундах с начала эпохи Unix.
//        Open: Цена открытия.
//        High: Максимальная цена за время свечи.
//        Low: Минимальная цена за время свечи.
//        Close (or latest price): Цена закрытия или последняя цена.
//        Volume: Объем торгов за время свечи.
//        Close time: Время закрытия свечи в миллисекундах с начала эпохи Unix.
//        Base asset volume: Объем торгов основной валюты.
//        Number of trades: Количество торгов.
//        Taker buy volume: Объем покупок, совершенных по рыночной цене.
//        Taker buy base asset volume: Объем покупок основной валюты, совершенных по рыночной цене.
//        Ignore: Поле, которое можно игнорировать (обычно содержит значение "0").

//
//symbol=BTCUSDT: Это означает, что запрашиваются данные для валютной пары Bitcoin (BTC) и Tether (USDT).
//
//        interval=15m: Здесь указывается таймфрейм каждой свечи. 15m означает, что каждая свеча представляет собой данные за 15-минутный интервал.
//
//        limit=192: Этот параметр ограничивает количество возвращаемых свечей. В данном случае запрашивается 192 свечи. Если учитывать, что каждая свеча представляет 15 минут, то 192 * 15м дает вам данные за последние 48 часов (или 2 дня).