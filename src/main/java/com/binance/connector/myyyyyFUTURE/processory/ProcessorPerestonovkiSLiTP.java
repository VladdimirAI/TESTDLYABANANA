package com.binance.connector.myyyyyFUTURE.processory;


import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.PrivateConfig;
import com.binance.connector.myyyyyFUTURE.sushnosty.Order;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

public class ProcessorPerestonovkiSLiTP {
//todo АТОМАРНОСТЬ - спросить потдержки

    public static void perestanovkaTkeProfita(String symbol, Svecha svecha) {//todo на второй половине подтягивать стоп лосс ?

        Order orderNaUdalenye = GURU.getTakeProfitOrders().get(symbol);

        GURU.orderManager.cancelOrder(symbol, orderNaUdalenye.getOrderId());

        Order newTPOrder = GURU.orderManager.creatLIMITOrderTakeProfit(symbol, orderNaUdalenye.getCummulativeQuoteQty(), svecha.getSma()); //getCummulativeQuoteQty все верно ?
        newTPOrder.setCenaVhoda(svecha.getSma());

        GURU.getTakeProfitOrders().put(symbol, newTPOrder);


    }

    public static void perestonovkaStopLossa() { //когла сработал профит - удаляем два старых(сверху) стопа - и ставим новый ЧУТЬ выше сма

    }


}

