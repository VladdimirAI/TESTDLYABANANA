package com.binance.connector.myyyyyFUTURE.MYTEST;

import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.processory.Processor;
import com.binance.connector.myyyyyFUTURE.sushnosty.Order;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ProcessorBirghiTEST {

    public String symbol;

    public GURUTEST GURUTEST;

    public Svecha tecushayaSvecha;

    public boolean ochered;


    public ProcessorBirghiTEST(String symbol) { //todo больше не создавать!!!!!!!!!
        this.symbol = symbol;
    }

    public void nachaloTesta(List<Svecha> listVsehSvecheyZaGodOdnoyMonety) {
        System.out.println("nachaloTesta thread id: " + Thread.currentThread().getId());


        for (Svecha nextSvecha : listVsehSvecheyZaGodOdnoyMonety) {

            proverkaSrabotkiOrderovNaSvechu(nextSvecha);

            tecushayaSvecha = nextSvecha;





                otpravkaClientuSvechi(nextSvecha); //только когда выполняеться метод приемка ордеров хоть и спустым икончаетьсятолько потом продолжнаеться кдтут тоесть вызываетьс новя итерация






//            priemkaObrabotkaOrderov(nextSvecha);


        }
        //todo код после прогонки всех свечей по окончанию теста
        System.out.println(GURUTEST.realBalaceClienta);

    }


    public void proverkaSrabotkiOrderovNaSvechu(Svecha svechaDlyaOtpravkiClientu) { //stLoss TAkeProfi

        Random rand = new Random();
        boolean firstConditionFirst = rand.nextBoolean();


        if (firstConditionFirst) {
            // Выполнить первое условие
            checkStopLossOrders(svechaDlyaOtpravkiClientu); //todo условвие 50 на 50 что сработает первее

            // Выполнить второе условие
            checkTakeProfitOrders(svechaDlyaOtpravkiClientu);

        } else {
            // Выполнить второе условие
            checkTakeProfitOrders(svechaDlyaOtpravkiClientu);

            // Выполнить первое условие
            checkStopLossOrders(svechaDlyaOtpravkiClientu);
        }
    }


    private void checkStopLossOrders(Svecha svecha) {
        double verhCenySvechi = svecha.getHigh();
        double nizhCenySvichi = svecha.getLow();

        if (GURUTEST.stopLossOrdersRClientom.containsKey(symbol)) {
            Order order = GURUTEST.stopLossOrdersRClientom.get(symbol);
            double cenorderaSL = order.getCenaVhoda();
            double qentySL = order.getColichestvoCuplennuhMonet();

            if (verhCenySvechi >= cenorderaSL && nizhCenySvichi <= cenorderaSL) {
                // Тут код для обработки stop loss orders
                // ...
                GURUTEST.stopLossOrdersRClientom.remove(symbol);

                Order runTimeOrder = GURUTEST.runTimeOrdersRClientom.get(symbol);
                GURUTEST.runTimeOrdersRClientom.remove(symbol);


                double scolkoNadoNaPokupkuMonet = cenorderaSL * qentySL;

                double scolkoZatratiliNaPokupkuVRunTime = runTimeOrder.getCenaVhoda() * runTimeOrder.colichestvoCuplennuhMonet; //todo првавельнее было бы qentySL из раннтайма взять

                double raznycaObshayaBezTela = scolkoZatratiliNaPokupkuVRunTime - scolkoNadoNaPokupkuMonet;

                GURUTEST.realBalaceClienta += scolkoZatratiliNaPokupkuVRunTime + raznycaObshayaBezTela;

                if (GURUTEST.takeProfitOrdersRClientom.containsKey(symbol)) { //todo потму что верх и  низ бывает СЛ
                    GURUTEST.takeProfitOrdersRClientom.remove(symbol);
                }
            }

        }


    }


    private void checkTakeProfitOrders(Svecha svecha) {
        double verhCenySvechi = svecha.getHigh();
        double nizhCenySvichi = svecha.getLow();

        if (GURUTEST.takeProfitOrdersRClientom.containsKey(symbol)) {
            Order orderTP = GURUTEST.takeProfitOrdersRClientom.get(symbol);
            double cenorderaTP = orderTP.getCenaVhoda();
            double qentyTP = orderTP.getColichestvoCuplennuhMonet();

            if (verhCenySvechi >= cenorderaTP && nizhCenySvichi <= cenorderaTP) {
                // Тут код для обработки take profit orders
                // ...

                GURUTEST.stopLossOrdersRClientom.remove(symbol);

                Order orderRuTime = GURUTEST.runTimeOrdersRClientom.get(symbol);

                orderRuTime.colichestvoCuplennuhMonet = orderRuTime.colichestvoCuplennuhMonet / 2; // произвел изменения уменьшение монет в ратайме
//                orderRuTime.setCenaVhoda(orderTP.getCenaVhoda());// произвел изменения в ратайме ненадо!!!!!!!!!


                double scolkoNadoNaPokupkuMonet = cenorderaTP * orderRuTime.colichestvoCuplennuhMonet / 2;

                double scolkoZatratiliNaPokupkuVRunTime = orderRuTime.getCenaVhoda() * orderRuTime.colichestvoCuplennuhMonet / 2; //todo првавельнее было бы qentySL из раннтайма взять

                double raznycaObshayaBezTela = scolkoZatratiliNaPokupkuVRunTime - scolkoNadoNaPokupkuMonet;

                GURUTEST.realBalaceClienta += scolkoZatratiliNaPokupkuVRunTime + raznycaObshayaBezTela;


                GURUTEST.takeProfitOrdersRClientom.remove(symbol);


            }
        }
    }
///////////////////////////////////////////////////////////////////////////OTPRAVKA PRIEMKA

    public void otpravkaClientuSvechi(Svecha svecha) {
        System.out.println("otpravkaClientuSvechi--");

//        Svecha copy = new Svecha(svecha.money,svecha.getOpenTime(), svecha.getOpen(), svecha.getHigh(),svecha.getLow(),svecha.getClose());


        GURU.processor.TestmetodPriemkaCvechi(svecha);

    }

    public void priemkaObrabotkaOrderov(List<Order> orderListotClienta) { //сюдаже и рантайм приходит
        System.out.println("priemkaObrabotkaOrderov thread id: " + Thread.currentThread().getId());

        if (orderListotClienta.isEmpty()){
            System.out.println("Готов приемка ордеров было пусто");
            return;
        }


        for (Order zaprosOrder : orderListotClienta) {
            String zaprosOrderNapravlenye = zaprosOrder.getSide();
            String zaporOrderType = zaprosOrder.getType();

            if (zaprosOrderNapravlenye.equals("SELL")) {

                if (GURUTEST.runTimeOrdersRClientom.containsKey(symbol) || GURUTEST.stopLossOrdersRClientom.containsKey(symbol) || GURUTEST.takeProfitOrdersRClientom.containsKey(symbol)) {
                    System.out.println("Так быть не должно ставка при имеющихся ордерах - БАГА");
                    System.out.println("Ордера которые лежа в ГУРУТЕСТ");

                    Optional.ofNullable(GURUTEST.runTimeOrdersRClientom.get(symbol))
                            .ifPresent(order -> System.out.println("RunTime Order: " + order.toString()));
                    Optional.ofNullable(GURUTEST.stopLossOrdersRClientom.get(symbol))
                            .ifPresent(order -> System.out.println("StopLoss Order: " + order.toString()));
                    Optional.ofNullable(GURUTEST.takeProfitOrdersRClientom.get(symbol))
                            .ifPresent(order -> System.out.println("TakeProfit Order: " + order.toString()));

//                 В этом коде:
//
//                 Optional.ofNullable используется для создания Optional объекта, который может содержать null.
//                         ifPresent выполняет переданный лямбда-выражение, если значение в Optional существует (то есть, не null).
//                 Этот подход позволяет избежать явной проверки на null и делает код более читабельным и функциональным. Если соответствующие значения существуют в мапах, они будут выведены в консоль. Если значения отсутствуют, ifPresent ничего не делает.
                }

                GURUTEST.runTimeOrdersRClientom.put(symbol, zaprosOrder);
                GURUTEST.realBalaceClienta -= zaprosOrder.colichestvoCuplennuhMonet * zaprosOrder.getCenaVhoda();


            } else { //todo тут все ВЫХОДЫ - бай рантайм(выход) стоп и профит

                switch (zaporOrderType) {
                    case "MARKET": // Код для обработки типа MARKET бай - выход из текущей позиции

                        double vhodilRaneevRunCena = GURUTEST.runTimeOrdersRClientom.get(symbol).getCenaVhoda();
                        //todo СРАВНИТЬ Вбудущем qenty
                        double vhodilRaneevRunMonetSHTUK = GURUTEST.runTimeOrdersRClientom.get(symbol).getColichestvoCuplennuhMonet();

                        double zatratilPriVhodeVrun = vhodilRaneevRunCena * vhodilRaneevRunMonetSHTUK;

                        double tecuhayaCena = tecushayaSvecha.getOpen();

                        double raznicaSeychas = zatratilPriVhodeVrun - tecuhayaCena * vhodilRaneevRunMonetSHTUK;

                        GURUTEST.realBalaceClienta += zatratilPriVhodeVrun + raznicaSeychas;

                        GURUTEST.runTimeOrdersRClientom.remove(symbol);

                        break;

                    case "STOP_MARKET": // Код для обработки типа STOP_MARKET

                        GURUTEST.stopLossOrdersRClientom.put(symbol, zaprosOrder);


                        break;

                    case "TAKE_PROFIT_MARKET":   // Код для обработки типа TAKE_PROFIT_MARKET

                        GURUTEST.takeProfitOrdersRClientom.put(symbol, zaprosOrder);


                        break;

                    default:
                        System.out.println("Что за левый тип ??"); // Код для обработки неизвестного типа
                        break;
                }


            }


//        parameters.put("side", side); // "SELL" для шорта, "BUY" для лонга
//        parameters.put("type", "MARKET");

//        parameters.put("side", "BUY");
//        parameters.put("type", "STOP_MARKET");


//        parameters.put("side", "BUY");
//        parameters.put("type", "TAKE_PROFIT_MARKET");


        }
        System.out.println("Готов приемка ордеров");

    }
}