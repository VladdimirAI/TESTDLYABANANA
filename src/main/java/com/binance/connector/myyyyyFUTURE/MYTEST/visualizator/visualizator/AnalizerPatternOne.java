package com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator;




import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.MYTEST.GURUTEST;
import com.binance.connector.myyyyyFUTURE.MYTEST.syshnostytest.TestSvechaDDTO;
import com.binance.connector.myyyyyFUTURE.PrivateConfig;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AnalizerPatternOne {

  private static   double voskolkoRazHvostBolsheTrenda = PrivateConfig.OTSKOLKIRAZHVOSTDOLGHENBITBOLSHETRENDA ;

  private  static    double priemlemoDoSma = (double) PrivateConfig.PRIEMLEMUYPROCENTDOBOLINJERA;


// todo добавить проверку на доджик - сильно малекре тело свечи
    public static void analizer (Svecha svecha, double sma){

// double differenceBody = candlestick.getColor() == Color.GREEN ? candlestick.getClose() - candlestick.getOpen() : candlestick.getOpen() - candlestick.getClose(); // числовое значение тела свечи (модуль без знака)
// double differenceTail = candlestick.getColor() == Color.GREEN ?  candlestick.getHigh() - candlestick.getClose() : candlestick.getHigh() - candlestick.getOpen();  // числовое значение верхнего хвоста  свечи (модуль без знака)
//
//      if( differenceTail/differenceBody > tailToBodyRatio && sma > procentSmaLine){ //todo он должен быть не в два раза больше свечи а не меньше тренда
//          System.out.println("Ставим ");
//      }
//      else{
//          System.out.println("Не ставим "); //Todo убрать потом
//      }
       double cenaVerhaTela = Math.max(svecha.getOpen(),svecha.getClose());
       double cenzVerhaHvosta = svecha.getHigh();

       double raznicaVceneTrend = cenaVerhaTela - sma;

       double raznicaVprocentah = (raznicaVceneTrend/sma) * 100; // 10 ?

       double raznicaHvostaVCene = cenzVerhaHvosta - cenaVerhaTela;


       double hvostBolsheVRaz = raznicaHvostaVCene / raznicaVceneTrend; // 1.5

//        System.out.println("-----------");

        if(raznicaVprocentah >= priemlemoDoSma) {
//            System.out.println("111111111111111");
        }
        if( hvostBolsheVRaz >= voskolkoRazHvostBolsheTrenda) {
//            System.out.println("222222222222222");
        }

        if(raznicaVprocentah >= priemlemoDoSma && hvostBolsheVRaz >= voskolkoRazHvostBolsheTrenda){
//            System.out.println("СУПЕР - НАША СВЕЧА ==========================================================================================================");
//            System.out.println("Она открылась в ----------  "+ svecha.getOpenTime());
//            GURU.playSIGNAL();
            GURUTEST.testovyiList.add(svecha);
            GURUTEST.testovyiListDubley.add(new TestSvechaDDTO(cenaVerhaTela,cenzVerhaHvosta,raznicaVceneTrend,raznicaVprocentah,raznicaHvostaVCene,hvostBolsheVRaz));

        }

    }

    public static void analizer10(Svecha svecha) {
        double openPrice = svecha.getOpen();
        double closePrice = svecha.getClose();

        // Расчет процентного изменения относительно цены открытия
        double changePercentage = ((closePrice - openPrice) / openPrice) * 100;

        // Проверка, увеличилась ли цена на 10% или более
        if (Math.abs(changePercentage) >= 10) {
//            System.out.println("Цена изменилась на 10% или более относительно цены открытия.");
//            System.out.println(convertTimestampToDate(svecha.getOpenTime()));
            // Дополнительная логика, если нужно
        }
    }

    public static String convertTimestampToDate(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }



}
