package com.binance.connector.myyyyyFUTURE.processory;


import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.MYTEST.GURUTEST;

public class ProcessorZakruvator { //todo проверить все методы

    public static void zakrytVSEOrderaMonety(String symbol) {

//        if (GURU.getRunTimeOrders().containsKey(symbol)) {
        if (GURUTEST.runTimeOrdersRClientom.containsKey(symbol)) {

            double skolichestvoMonet = GURU.getRunTimeOrders().get(symbol).getCummulativeQuoteQty(); //todo поменял на возврат колечества кплоеннх провалиться посомтреть повниательнее

           GURUTEST.orderaNaServer.add( GURU.orderManager.createMarketOrder(symbol,"BUY",skolichestvoMonet,true));

//            GURU.getRunTimeOrders().remove(symbol);
        }

//        if (GURU.getTakeProfitOrders().containsKey(symbol)) {
        if (GURUTEST.takeProfitOrdersRClientom.containsKey(symbol)) {

            long id = GURUTEST.takeProfitOrdersRClientom.get(symbol).getOrderId();

            GURU.orderManager.cancelOrder(symbol,id);

//            GURU.getTakeProfitOrders().remove(symbol);
        }

//        if (GURU.getStopLossOrders().containsKey(symbol)) {
        if (GURUTEST.stopLossOrdersRClientom.containsKey(symbol)) {

            long id = GURUTEST.stopLossOrdersRClientom.get(symbol).getOrderId();
            GURU.orderManager.cancelOrder(symbol,id);
//            GURU.getStopLossOrders().remove(symbol);

//            GURUTEST.stopLossOrdersRClientom.remove(symbol);

        }



        }



    }


