package com.binance.connector.myyyyyFUTURE.processory;


import com.binance.connector.myyyyyFUTURE.GURU;

public class ProcessorZakruvator { //todo проверить все методы

    public static void zakrytVSEOrderaMonety(String symbol) {

        if (GURU.getRunTimeOrders().containsKey(symbol)) {

            double skolichestvoMonet = GURU.getRunTimeOrders().get(symbol).getCummulativeQuoteQty();

            GURU.orderManager.createMarketOrder(symbol,"BUY",skolichestvoMonet,true);

            GURU.getRunTimeOrders().remove(symbol);
        }

        if (GURU.getTakeProfitOrders().containsKey(symbol)) {

            long id = GURU.getTakeProfitOrders().get(symbol).getOrderId();
            GURU.orderManager.cancelOrder(symbol,id);


            GURU.getTakeProfitOrders().remove(symbol);
        }

        if (GURU.getStopLossOrders().containsKey(symbol)) {

            long id = GURU.getStopLossOrders().get(symbol).getOrderId();
            GURU.orderManager.cancelOrder(symbol,id);


            GURU.getStopLossOrders().remove(symbol);
        }
        }

    }


