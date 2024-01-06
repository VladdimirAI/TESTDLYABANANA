package com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator;




import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import java.awt.*;

public class AnalizerPatternOne {

  private   double tailToBodyRatio = 2.0;

  private   double procentSmaLine = 10.0;


// todo добавить проверку на доджик - сильно малекре тело свечи
    public void analizer (Svecha candlestick, double sma){

 double differenceBody = candlestick.getColor() == Color.GREEN ? candlestick.getClose() - candlestick.getOpen() : candlestick.getOpen() - candlestick.getClose(); // числовое значение тела свечи (модуль без знака)
 double differenceTail = candlestick.getColor() == Color.GREEN ?  candlestick.getHigh() - candlestick.getClose() : candlestick.getHigh() - candlestick.getOpen();  // числовое значение верхнего хвоста  свечи (модуль без знака)

      if( differenceTail/differenceBody > tailToBodyRatio && sma > procentSmaLine){
          System.out.println("Ставим ");
      }
      else{
          System.out.println("Не ставим "); //Todo убрать потом
      }

    }




}
