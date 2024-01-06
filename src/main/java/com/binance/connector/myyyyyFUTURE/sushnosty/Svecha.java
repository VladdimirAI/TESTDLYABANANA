package com.binance.connector.myyyyyFUTURE.sushnosty;


import java.awt.*;

public class Svecha {

//    public final String money;
    public  String money; //todo для теста не файнал

//    private final long openTime;
    private long openTime; //todo для теста не файнал
    private final double open;
    private final double high;
    private final double low;
    private final double close;

    private  double sma;
    private  double UpBolinjer;
    private  double DownBolinjer;

    public Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Svecha(Color color ,String money ,long openTime, double open, double high, double low, double close) {
        this.openTime = openTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.color = color;
        this.money = money;
    }


    // todo конструкторр для тестовой части
    public Svecha(String money, long openTime, double open, double high, double low, double close) {
        this.money = money;
        this.openTime = openTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }
    // todo конструкторр для тестовой части
    public Svecha(double open, double high, double low, double close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }


    // Геттеры для open, high, low, close

    public long getOpenTime() {
        return openTime;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }


    public double getSma() {
        return sma;
    }

    public void setSma(double sma) {
        this.sma = sma;
    }

    public double getUpBolinjer() {
        return UpBolinjer;
    }

    public void setUpBolinjer(double upBolinjer) {
        UpBolinjer = upBolinjer;
    }

    public double getDownBolinjer() {
        return DownBolinjer;
    }

    public void setDownBolinjer(double downBolinjer) {
        DownBolinjer = downBolinjer;
    }
}


//openTime: Это поле хранит временную метку начала периода свечи. Обычно это время выражается в миллисекундах с начала эпохи (1 января 1970 года). Эта информация используется для определения момента времени, когда начался интервал, который представляет свеча.
//
//        open: Цена открытия. Это цена актива (например, акции, валюты или товара) в начале временного интервала, который покрывает свеча. Это первая цена, которая была зафиксирована в начале торгового периода.
//
//        high: Максимальная цена. Это самая высокая цена, до которой достиг актив в течение временного интервала свечи.
//
//        low: Минимальная цена. Это самая низкая цена, до которой актив упал в течение временного интервала свечи.
//
//        close: Цена закрытия. Это цена актива в конце временного интервала, который представляет свеча. Это последняя цена, которая была зафиксирована в конце торгового периода.