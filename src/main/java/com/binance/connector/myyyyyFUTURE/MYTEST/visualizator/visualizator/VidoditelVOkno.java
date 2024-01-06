package com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator;

import com.binance.connector.myyyyyFUTURE.bolinjer.BollingerBandsCalculator;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;


import javax.swing.*;
import java.util.List;

public class VidoditelVOkno {

   public CandlestickPropocianatorRisvalshik chart;

//    private void runApplication(String coin) {
    public void vivodVOkno(List<Svecha> candlesticks) {
        // Считываем данные свечей из файла
//        List<Svecha> candlesticks = CandlestickDataParser.readCandlesticksFromFile(coin);
//
//        // Проверяем успешность загрузки данных
//        if (candlesticks == null || candlesticks.isEmpty()) {
//            System.out.println("Не удалось загрузить данные свечей.");
//            return;
//        }

        // Вычисляем Bollinger Bands и SMA
        int period = 21; // Используем рекомендуемый период Bollinger Bands
        List<Double[]> bollingerBands = BollingerBandsCalculator.calculateBollingerBands(candlesticks, period);
        List<Double> smaValues = BollingerBandsCalculator.calculateSMA(candlesticks, period);

        // Создаем и настраиваем окно приложения
        JFrame frame = createApplicationFrame(candlesticks, bollingerBands, smaValues);
        frame.setVisible(true);
    }

    private JFrame createApplicationFrame(List<Svecha> candlesticks, List<Double[]> bollingerBands, List<Double> smaValues) {
        JFrame frame = new JFrame("Candlestick Chart with Bollinger Bands and SMA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chart = new CandlestickPropocianatorRisvalshik(candlesticks, bollingerBands, smaValues);

        // Создаем JScrollPane и добавляем в него CandlestickChart
        JScrollPane scrollPane = new JScrollPane(chart);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane); // Добавляем JScrollPane в JFrame

        frame.pack();
        frame.setLocationRelativeTo(null);
        return frame;
    }
}
