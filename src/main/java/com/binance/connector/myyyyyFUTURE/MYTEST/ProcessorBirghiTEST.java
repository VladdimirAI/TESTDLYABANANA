package com.binance.connector.myyyyyFUTURE.MYTEST;

import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.PrivateConfig;
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

    public Svecha predpologhitelnoParonormalnayaSvecha;
    public double bilBalansHodNazad = 100;


    public ProcessorBirghiTEST(String symbol) { //todo больше не создавать!!!!!!!!!
        this.symbol = symbol;
    }

    public void nachaloTesta(List<Svecha> listVsehSvecheyZaGodOdnoyMonety) {


        for (Svecha nextSvecha : listVsehSvecheyZaGodOdnoyMonety) {
            System.out.println( GURUTEST.runTimeOrdersRClientom.size() +" " + GURUTEST.takeProfitOrdersRClientom.size() + " " + GURUTEST.stopLossOrdersRClientom.size());
            System.out.println(GURUTEST.realBalaceClienta);

//            otlovParonormalnoySvechi(nextSvecha);

            proverkaSrabotkiOrderovNaSvechu(nextSvecha);

            tecushayaSvecha = nextSvecha;



//
//                if(nextSvecha.getOpenTime() == 1679253300000L) {
//                    System.out.println( GURUTEST.runTimeOrdersRClientom.size() +" " + GURUTEST.takeProfitOrdersRClientom.size() + " " + GURUTEST.stopLossOrdersRClientom.size());
//                    System.out.println(GURUTEST.realBalaceClienta);
//
//                }
//            if(nextSvecha.getOpenTime() == 1677024900000L) {
//                System.out.println( GURUTEST.runTimeOrdersRClientom.size() +" " + GURUTEST.takeProfitOrdersRClientom.size() + " " + GURUTEST.stopLossOrdersRClientom.size());
//                System.out.println(GURUTEST.realBalaceClienta);
//
//            }


                otpravkaClientuSvechi(nextSvecha); //только когда выполняеться метод приемка ордеров хоть и спустым икончаетьсятолько потом продолжнаеться кдтут тоесть вызываетьс новя итерация






//            priemkaObrabotkaOrderov(nextSvecha);


        }
        //todo код после прогонки всех свечей по окончанию теста
        System.out.println(GURUTEST.realBalaceClienta);

    }


    public void proverkaSrabotkiOrderovNaSvechu(Svecha svechaDlyaOtpravkiClientu) { //stLoss TAkeProfi

//        if(svechaDlyaOtpravkiClientu.getOpenTime() == 1683937800000L){
//            System.out.println(GURUTEST.realBalaceClienta);
//        }


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
//        if(symbol.equals("1000PEPEUSDT")){
//            System.out.println();
//        }

        if (GURUTEST.stopLossOrdersRClientom.containsKey(symbol)) {
            Order order = GURUTEST.stopLossOrdersRClientom.get(symbol);
            double cenorderaSL = order.getCenaVhoda();
            double qentySL = order.getColichestvoCuplennuhMonet();

            if (verhCenySvechi >= cenorderaSL) {
//            if (verhCenySvechi >= cenorderaSL && nizhCenySvichi <= cenorderaSL) {

                // Тут код для обработки stop loss orders
                // ...
                if(GURU.sybloyRazreshenyPovrtoryZahoda.contains(symbol)) { // для повторного захода
                    GURUTEST.dogonOrder.put(symbol, GURUTEST.stopLossOrdersRClientom.get(symbol));
                    GURU.sybloyRazreshenyPovrtoryZahoda.remove(symbol);  // для повторного захода
                }
                GURUTEST.stopLossOrdersRClientom.remove(symbol);

                Order runTimeOrder = GURUTEST.runTimeOrdersRClientom.get(symbol);

                GURUTEST.runTimeOrdersRClientom.remove(symbol);


                double scolkoNadoNaPokupkuMonet = cenorderaSL * qentySL;

                double scolkoZatratiliNaPokupkuVRunTime = runTimeOrder.getCenaVhoda() * runTimeOrder.colichestvoCuplennuhMonet; //todo првавельнее было бы qentySL из раннтайма взять

                double raznycaObshayaBezTela = scolkoZatratiliNaPokupkuVRunTime - scolkoNadoNaPokupkuMonet;

                GURUTEST.realBalaceClienta += scolkoZatratiliNaPokupkuVRunTime + raznycaObshayaBezTela;

                if (GURUTEST.takeProfitOrdersRClientom.containsKey(symbol)) { //todo потму что если верх то тп надо удалить
                    GURUTEST.takeProfitOrdersRClientom.remove(symbol);




                }
            }

        }


    }


    private void checkTakeProfitOrders(Svecha svecha) {
        double verhCenySvechi = svecha.getHigh();
        double nizhCenySvichi = svecha.getLow();

//        if(symbol.equals("1000PEPEUSDT")){
//           System.out.println();
//      }

        if (GURUTEST.takeProfitOrdersRClientom.containsKey(symbol)) {
            Order orderTP = GURUTEST.takeProfitOrdersRClientom.get(symbol);
            double cenorderaTP = orderTP.getCenaVhoda();
            double qentyTP = orderTP.getColichestvoCuplennuhMonet();

            if (nizhCenySvichi <= cenorderaTP ) {
                // Тут код для обработки take profit orders
                // ...
                System.out.println("PTMetod balas "+GURUTEST.realBalaceClienta);

                GURUTEST.stopLossOrdersRClientom.remove(symbol);

                Order orderRuTime = GURUTEST.runTimeOrdersRClientom.get(symbol);

                orderRuTime.colichestvoCuplennuhMonet = orderRuTime.colichestvoCuplennuhMonet / 2; // произвел изменения уменьшение монет в ратайме
//                orderRuTime.setCenaVhoda(orderTP.getCenaVhoda());// произвел изменения в ратайме ненадо!!!!!!!!!


                double scolkoNadoNaPokupkuMonet = cenorderaTP * orderRuTime.colichestvoCuplennuhMonet;

                double scolkoZatratiliNaPokupkuVRunTime = orderRuTime.getCenaVhoda() * orderRuTime.colichestvoCuplennuhMonet; //todo првавельнее было бы qentySL из раннтайма взять

                double raznycaObshayaBezTela = scolkoZatratiliNaPokupkuVRunTime  - scolkoNadoNaPokupkuMonet ;

                GURUTEST.realBalaceClienta += scolkoZatratiliNaPokupkuVRunTime + raznycaObshayaBezTela;


                GURUTEST.takeProfitOrdersRClientom.remove(symbol); //TODO В ПРОЦЕССОРЕ ПРОД. СДЕЛАТЬ ПРИ ТАКОМ РАСКЛАДЕ ПОСТАНОВКУ СТОП ЛОССА в ЭТО МЕСТО +1% ВВЕРХ  уж есть в     public void procesPriInfoObOrdere(OrderDTO orderDTO) { перепроверить


                Order  slOrder = new Order(symbol,GURUTEST.generateOrderID(),0,0, "NEW","BUY","STOP_MARKET");
                slOrder.setCenaVhoda(cenorderaTP + cenorderaTP/100 * PrivateConfig.USTANOVKASLPRISRABOTKETPNASKOLCOPROCENTOVVISHE);
                slOrder.setColichestvoCuplennuhMonet(orderRuTime.colichestvoCuplennuhMonet);

                GURUTEST.stopLossOrdersRClientom.put(symbol,slOrder);  //TODO В ПРОЦЕССОРЕ ПРОД. СДЕЛАТЬ ПРИ ТАКОМ РАСКЛАДЕ ПОСТАНОВКУ СТОП ЛОССА в ЭТО МЕСТО +1% ВВЕРХ  уж есть в     public void procesPriInfoObOrdere(OrderDTO orderDTO) { перепроверить
//                GURU.orderManager.creatMARKETOrderStopLoss(symbol,orderRuTime.colichestvoCuplennuhMonet,)


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

        if (orderListotClienta.isEmpty()){
            System.out.println("Готов приемка ордеров было пусто");
            return;
        }else{
            Order  rrrrr = GURUTEST.runTimeOrdersRClientom.get(symbol);
            System.out.println(tecushayaSvecha);
           for(Order oredr : orderListotClienta){
               System.out.println(oredr);
           }
        }


        for (Order zaprosOrder : orderListotClienta) {
            String zaprosOrderNapravlenye = zaprosOrder.getSide();
            String zaporOrderType = zaprosOrder.getType();

//            if(zaprosOrder.getSymbol().equals("1000PEPEUSDT")){
//                System.out.println();
//            }

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

                        double tecuhayaCena = tecushayaSvecha.getClose();

                        double raznicaSeychas = zatratilPriVhodeVrun - tecuhayaCena * vhodilRaneevRunMonetSHTUK;

                        GURUTEST.realBalaceClienta += zatratilPriVhodeVrun + raznicaSeychas;

                        GURUTEST.runTimeOrdersRClientom.remove(symbol);

                        if( GURUTEST.stopLossOrdersRClientom.containsKey(symbol)){ GURUTEST.stopLossOrdersRClientom.remove(symbol);}

                        if(GURUTEST.takeProfitOrdersRClientom.containsKey(symbol)){GURUTEST.takeProfitOrdersRClientom.remove(symbol);} //todo проверить может они на стороне клтиента удаля.ться

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



    public void otlovParonormalnoySvechi(Svecha svecha){

        double raznicaVprocentah = (((GURUTEST.realBalaceClienta - bilBalansHodNazad) / bilBalansHodNazad) * 100);

        if(raznicaVprocentah > 2){ //todo тут 2 как 20 - помнить что закрываються иногда и половинки ордера


            System.out.println("Свеча которая подняла баланс  аж на " +raznicaVprocentah + " процентов(прибаить 0) "+ predpologhitelnoParonormalnayaSvecha.toString());

        }



        bilBalansHodNazad = GURUTEST.realBalaceClienta;
        predpologhitelnoParonormalnayaSvecha = svecha;
    }

}