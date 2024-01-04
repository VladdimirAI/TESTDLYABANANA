package com.binance.connector.myyyyyFUTURE.processory;

import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.sushnosty.Order;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import java.awt.*;
import java.util.List;

//todo не сработает на нижней половине!!!!!!!!!!!!!!!!!Доделать
public class ProcessorUsloviiVihoda { //todo что бы не было условй выхода все должны быть тру

    public static boolean neTopchimsyaNamesteVerhnayPolovina(String symbol, List<Svecha> tekuhuyList, int kolichestvoSvechey, int procentDlyaVihoda) {
        Order orderRunTime = GURU.getRunTimeOrders().get(symbol);
        double cenaZakrutyaSvechi = tekuhuyList.get(tekuhuyList.size() - 1).getClose();
        double cenaVhoda = orderRunTime.getCenaVhoda();

        if (++orderRunTime.kakayaPoshetuSvecha >= kolichestvoSvechey && cenaVhoda - cenaVhoda * procentDlyaVihoda <= cenaZakrutyaSvechi) { //todo счетчик свечей в ордере Рантайм
            return false;
        }
        return true;
    }

    public static boolean proverkaNaMolotRV(Svecha svecha) {

        double telo = Math.abs(svecha.getClose() - svecha.getOpen()); // Размер тела свечи
        double nizhniyHvost = Math.min(svecha.getOpen(), svecha.getClose()) - svecha.getLow(); // Нижний хвост
        double verhniyHvost = svecha.getHigh() - Math.max(svecha.getOpen(), svecha.getClose()); // Верхний хвост

        // Проверяем, соответствует ли свеча критериям молота
        if (nizhniyHvost >= 1.5 * telo && verhniyHvost <= telo) {
            return false; // Свеча является молотом
        }
        return true; // Свеча не является молотом
    }

    public static boolean neskolkoPodryadZelenuh(List<Svecha> tekuhuyList, String symbol, int podrydZelenyh) {
        Order orderRunTime = GURU.getRunTimeOrders().get(symbol);
        int tecushayaSvechaNomber = orderRunTime.kakayaPoshetuSvecha;

        if (tecushayaSvechaNomber < podrydZelenyh) {
            return true;
        }

        int index = tekuhuyList.size() - 1;
        int schetchikSovpadenyiZEL = 0;

        for (int i = tecushayaSvechaNomber; i > 0; i--) {
            if (tekuhuyList.get(index--).getColor().equals(Color.RED)) {
                schetchikSovpadenyiZEL = 0;
            } else {
                if (podrydZelenyh == ++schetchikSovpadenyiZEL) {//todo проверить присоение;
                    return false;
                }
            }
        }
        return true;
    }


    public static int procentDoSL(Svecha poslednyaSvecha, String symbol) {
        double stopLossCena = 0;
        try {
            stopLossCena = GURU.getStopLossOrders().get(symbol).getCenaVhoda();
        } catch (Exception e) {
            return 0;  //todo если мы в жней части там уже нет ОСО - возвращаем 0 - что бы не рсаболи условия перестановки
        }

        double tekushayaCena = poslednyaSvecha.getClose();
        return (int) (((stopLossCena - tekushayaCena) / tekushayaCena) * 100);//todo кастим
    }


    public static double procentDoTP(Svecha poslednyaSvecha, String symbol) {
        Order order = GURU.getTakeProfitOrders().get(symbol);
        double cenaTakeProfit = order.getCenaVhoda();
        double sma = poslednyaSvecha.getSma();

        double raznicaVProcentah = ((cenaTakeProfit - sma) / sma) * 100;
        return Math.abs(raznicaVProcentah);
    }
}