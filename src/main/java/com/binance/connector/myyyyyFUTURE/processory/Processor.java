package com.binance.connector.myyyyyFUTURE.processory;


import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.PrivateConfig;
import com.binance.connector.myyyyyFUTURE.bolinjer.BollingerBandsCalculator;
import com.binance.connector.myyyyyFUTURE.parsery.ParserUserSoceta;
import com.binance.connector.myyyyyFUTURE.parsery.SvecnoyParser;
import com.binance.connector.myyyyyFUTURE.sushnosty.Order;
import com.binance.connector.myyyyyFUTURE.sushnosty.OrderDTO;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Processor {


    public void priemJsonZakrytyhSvechey(JSONObject jsonSvechaZakrytaya) {

        Svecha svecha = SvecnoyParser.parseSvecha(jsonSvechaZakrytaya);
        String symbol = svecha.money;

        // метод добавления свечи
        List<Svecha> tekushuyList = addSvecha(symbol, svecha);

        //Обдейтим свечу боленджерам
        addLineBolinjer(tekushuyList); // todo тут можно чистить листы - ДОБАВИТЬ
        //Текущая свеча - полна - вместе с боленджарами

        if (GURU.getRunTimeOrders().containsKey(symbol)) {
            boolean neMolot = ProcessorUsloviiVihoda.proverkaNaMolotRV(svecha);
            boolean neTopchimsyaNaMeste = ProcessorUsloviiVihoda.neTopchimsyaNamesteVerhnayPolovina(symbol, tekushuyList, PrivateConfig.USLOVIEVIHODACOLSVECHEYINAMESTE, PrivateConfig.USLOVIYAVIHODAPROCENTSCITAEMUYNAMESTE);
            boolean neNeskolkoPodryadZelenuh = ProcessorUsloviiVihoda.neskolkoPodryadZelenuh(tekushuyList, symbol, PrivateConfig.USLOVIYAVIHODAPODRYDZELENYH); //
            if (neMolot && neTopchimsyaNaMeste && neNeskolkoPodryadZelenuh) { // todo перерорверить условия
                double procentDoTP = ProcessorUsloviiVihoda.procentDoTP(svecha, symbol);
                if (procentDoTP > PrivateConfig.TPMINIMUMCHOBEGONETROGAT) { //если больше в процентах - не близко к ТП то подтягиваем ТП
                    ProcessorPerestonovkiSLiTP.perestanovkaTkeProfita(symbol, svecha);
                }
            } else {

                ProcessorZakruvator.zakrytVSEOrderaMonety(symbol);    //todo закрываем все что связанос позицией

            }
        }


        if (AnalizeProcessor.verhnuyiHvostChtonado(svecha)) { // todo везде проверить финасы - что убавлються - потом проверить что прибовляються при обновлении информации
            if (AnalizeProcessor.doBolinjera(svecha)) {
                Order orderRunTime = zahodVShellPoziciyu(symbol, svecha); //todo выставили рантайм ордер
                VistavlyatelStopITakeProffit.createForLineTakeAndStopAndADDSet(orderRunTime, svecha); //todo  ОСО и стоп

            }
        }


    }


    public void priemDannyhPolzovatelya(String dannyeOtSoceta) { //todo многопоток ее могут начат читать  пока она записываься - ИСПРАВИТЬ В БУДУЩЕМ
        JSONObject jsonObj = new JSONObject(dannyeOtSoceta);
        String eventType = jsonObj.getString("e");

        switch (eventType) {

            case "ORDER_TRADE_UPDATE":
                System.out.println("Тип события ORDER_TRADE_UPDATE " + eventType);// Парсинг обновления ордера
                OrderDTO orderDTO = ParserUserSoceta.handleOrderTradeUpdate(dannyeOtSoceta);
                procesPriInfoObOrdere(orderDTO);
                break;
            case "ACCOUNT_UPDATE":
                System.out.println("Тип события ACCOUNT_UPDATE " + eventType);// Парсинг обновления аккаунта
                ParserUserSoceta.parseAccountUpdate(dannyeOtSoceta);
                break;
            case "OUTBOUND_ACCOUNT_POSITION":
                System.out.println("Тип события OUTBOUND_ACCOUNT_POSITION ---------------------------------------------------==================== " + eventType);// Парсинг обновления баланса
                break;
            // Дополнительные типы сообщений
            default:
                System.out.println("Неизвестный тип события: " + eventType);
                break;
        }
    }

//после открытия рантайм  {"e":"ACCOUNT_UPDATE","T":1704388422171,"E":1704388422173,"a":{"B":[{"a":"USDT","wb":"19.27388055","cw":"19.27388055","bc":"0"}],"P":[{"s":"ACHUSDT","pa":"300","ep":"0.02","cr":"-0.02783998","up":"-0.00300000","mt":"cross","iw":"0","ps":"BOTH","ma":"USDT","bep":"0.02001"}],"m":"ORDER"}}
//после закрытия вторым рантайм в другую сторону {"e":"ACCOUNT_UPDATE","T":1704388431285,"E":1704388431287,"a":{"B":[{"a":"USDT","wb":"19.26788205","cw":"19.26788205","bc":"0"}],"P":[{"s":"ACHUSDT","pa":"0","ep":"0","cr":"-0.03083998","up":"0","mt":"cross","iw":"0","ps":"BOTH","ma":"USDT","bep":"0"}],"m":"ORDER"}}
    //s: Символ торговой пары (например, "ACHUSDT"). //закрыл в рантайме -рантаймом
//        c: Клиентский ID ордера.
//        S: Сторона ордера ("SELL" в данном случае).
//        o: Тип ордера ("MARKET").
//        q: Количество (например, "300").
//        x и X: Текущее состояние ордера (в данном случае "NEW" и затем "FILLED", что означает, что ордер был исполнен).
//        i: ID ордера на бирже.
//        ap: Средняя цена исполнения ордера.
//        n: Комиссия за сделку.
//        N: Валюта комиссии ("USDT").
//        t: ID торговой сделки на бирже.
//        rp: Реализованная прибыль или убыток по ордеру.

    //и там же
//    B (Балансы):

//    Этот раздел содержит информацию о вашем балансе в различных валютах.
//    Каждый элемент в списке B содержит следующие поля:
//    a: Валюта (например, "USDT").
//    wb: Общий баланс в этой валюте на вашем счете.
//    cw: Доступный для вывода баланс в этой валюте.
//            bc: Баланс, используемый в текущих открытых позициях.
//            P (Позиции):
//
//    Этот раздел содержит информацию о ваших текущих позициях в различных торговых парах.
//    Каждый элемент в списке P содержит следующие поля:
//    s: Символ торговой пары (например, "ACHUSDT").
//    pa: Текущее количество (или объем) в позиции.
//    ep: Средняя цена входа в позицию.
//            cr: Ставка финансирования (если применимо).
//    up: Нереализованная прибыль или убыток по данной позиции.
//            mt: Тип маржи (например, "cross" для кросс-маржи).
//    iw: Замороженная сумма на марже.
//    ps: Тип позиции (например, "BOTH").
//    ma: Валюта маржи (например, "USDT").

    //        System.out.println("Результат арифметической нагрузки: " + result);

    public void procesPriInfoObOrdere(OrderDTO orderDTO) {

        if (orderDTO.orderStatus.equals("FILLED") && orderDTO.side.equals("BUY")) { // реагируем только на завершенне и в покупку - тоесть против нашего рантайма - ордера

            String symbol = orderDTO.symbol;
            long orderId = orderDTO.orderId;


            Order takeProfitOrder = GURU.getTakeProfitOrders().get(symbol);
            Order runTimeOrder = GURU.getRunTimeOrders().get(symbol);
            Order stopLossOrder = GURU.getStopLossOrders().get(symbol);

            if (takeProfitOrder != null && takeProfitOrder.getOrderId() == orderId) {
                orderDTO.setTip("TAKEPROFIT");
            } else if (runTimeOrder != null && runTimeOrder.getOrderId() == orderId) {
                orderDTO.setTip("RUNTIME");
            } else if (stopLossOrder != null && stopLossOrder.getOrderId() == orderId) {
                orderDTO.setTip("STOPLOSS");
            }else {
                orderDTO.setTip("LEVUYTIP");
            }


            //todo провверить что везде ложаться при создний в ордер менеджере
            if (orderDTO.getTip().equals("TAKEPROFIT")) { //todo бывает только в верхней половине
                double cenaSrabotavshegoTP =  GURU.getTakeProfitOrders().get(symbol).getCenaVhoda();
                GURU.getTakeProfitOrders().remove(symbol); //todo удаляем тейк профит из листа - его заменит стоплосс ниже - чуть выше сма

                double vOrdereColichestvo =  GURU.getRunTimeOrders().get(symbol).colichestvoCuplennuhMonet; //todo сетим число монет в рантайме
                GURU.getRunTimeOrders().get(symbol).colichestvoCuplennuhMonet = vOrdereColichestvo - orderDTO.originalQuantity; // перепроверить правильное ли значение приходит в тейкпрояит при егсиполнении 50% должно быть


                double dobavka = cenaSrabotavshegoTP * (PrivateConfig.PERESTANOVKATPPROCENTDOBAVKIKSMA / 100.0); // Рассчитываем 2% от цены
                double novayaCena = cenaSrabotavshegoTP + dobavka; // Прибавляем 2% к начальной цене

                GURU.orderManager.creatMARKETOrderStopLoss(symbol,vOrdereColichestvo,novayaCena); //todo создаем новый стоп за место тейка тоже на 50%
                GURU.orderManager.cancelOrder(symbol,GURU.getStopLossOrders().get(symbol).getOrderId());//todo удаляем старый стоп имет ли смыслы строка сверху или снизу этой ??

                //todo при создании нового ордера стоп старый в ГУРУ сам долджен затерется.





            } else if (orderDTO.getTip().equals("RUNTIME")) { //todo просматриваем и кенселим все стопы и тейки
                GURU.getRunTimeOrders().remove(symbol);

                    if(GURU.getStopLossOrders().containsKey(symbol)){ //
                        Order tecuhiySTOrder = GURU.getStopLossOrders().get(symbol);//
                        GURU.orderManager.cancelOrder(symbol,tecuhiySTOrder.getOrderId());//todo отменяем текущий СТОП ордер

                        GURU.getStopLossOrders().remove(symbol);
                        }
                    if (GURU.getTakeProfitOrders().containsKey(symbol)){
                        Order tecuhiyTPOrder = GURU.getTakeProfitOrders().get(symbol);
                        GURU.orderManager.cancelOrder(symbol,tecuhiyTPOrder.getOrderId()); //todo отменяем текущий ПРОФИТ ордер

                        GURU.getTakeProfitOrders().remove(symbol);
                        }




            } else if (orderDTO.getTip().equals("STOPLOSS")) { //todo  - рантайм будет сам леквидирован независимо от половины низ или вверх               // todo в будещем поставить просто елсе
                GURU.getStopLossOrders().remove(symbol);

                if(GURU.getTakeProfitOrders().containsKey(symbol)){

                   long idTekushegoTPOrdera = GURU.getTakeProfitOrders().get(symbol).getOrderId();
                   GURU.orderManager.cancelOrder(symbol,idTekushegoTPOrdera);
                   GURU.getTakeProfitOrders().remove(symbol);

                }



            }


        }

    }


    ///todo/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void processAccountUpdate(JSONObject jsonEvent) {
        System.out.println("Внутри processAccountUpdate");
        System.out.println(jsonEvent.toString(2));
        JSONArray balances = jsonEvent.getJSONArray("B");
        for (int i = 0; i < balances.length(); i++) {
            JSONObject balance = balances.getJSONObject(i);
            String asset = balance.getString("a"); // Актив
            String free = balance.getString("f"); // Свободный баланс
            String locked = balance.getString("l"); // Заблокированный баланс
            System.out.println("processAccountUpdate--v");
            System.out.println("Asset: " + asset + ", Free: " + free + ", Locked: " + locked);
        }
    }

    public static synchronized void processOrderUpdate(JSONObject jsonEvent) {  //todo сюда прилетает инфа при закрытии осо ордера
        System.out.println("Внутри processOrderUpdate");
        System.out.println(jsonEvent.toString(2));
        try {
            System.out.println("В методе processOrderUpdate 1");
            String symbol = jsonEvent.getString("s"); // Символ
            System.out.println("В методе processOrderUpdate 2");

            // Изменение здесь: читаем orderId как число, а затем преобразуем в строку
            int orderIdInt = jsonEvent.getInt("i"); // ID ордера как число
            String orderId = String.valueOf(orderIdInt); // Преобразование числа в строку

            System.out.println("В методе processOrderUpdate 3");
            String orderStatus = jsonEvent.getString("X"); // Статус ордера
            System.out.println("В методе processOrderUpdate 4");
            String orderType = jsonEvent.getString("o"); // Тип ордера
            System.out.println("processOrderUpdate--v");
            System.out.println("Order Update - Symbol: " + symbol + ", OrderId: " + orderId + ", Status: " + orderStatus + ", Type: " + orderType);
            //todo логика обработки срабатывание осо ордера(оно же и для стоп лимит) ниже "STOP_LOSS_LIMIT" - стоп лосс, "LIMIT_MAKER" - тейк профит ... STOP_LOSS_LIMIT - простой стоплосс такой же тип - значит при закрыти стоп лоса сюда прилитит два ордера
            //todo обновить балас

//            if(orderStatus.equals("FILLED")){ // если статус исполнен
//                if(orderType.equals("STOP_LOSS_LIMIT")){ //todo хорошая точка для захода повторно в   позицию так как закрылось по стопу - СРАБОТАЛ СТОП
//                    GURU.getTekushueOcoOrdera().remove(symbol);
//                    GURU.getStopLImitMargOrders().remove(symbol);
//                    GURU.getRunTimeOrders().remove(symbol);
//
//
//
//                }else if(orderType.equals("LIMIT_MAKER")){ //todo это тейк профит
//                    //todo кенсел отложенный ордер стоплос
//                    GURU.getStopLImitMargOrders().remove(symbol);
//                    //todo новый отложенный ордер стоп на сма
//                    //todo добавляем его в гуру
//                }
////               //todo где выходим из рантайм ордера  - тами дергаем лдогику - кансел ордер для всех отложенных -(обработь ошибки так как снизу осо уже не будет)
//
////todo рантайм мы тоже же закрываем и получаем сигнал - но он тут не нужен
//
//
//            }


////            Order Update - Symbol: ACHUSDT, OrderId: 318560102, Status: FILLED, Type: LIMIT_MAKER  тайк профит сработал
////            Order Update - Symbol: ACHUSDT, OrderId: 318560101, Status: EXPIRED, Type: STOP_LOSS_LIMIT стоп лос удален
            //todo логика обработки срабатывание осо ордера(оно же и для стоп лимит) выше
        } catch (JSONException e) {
            System.out.println("Ошибка при работе с JSON: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }


    ////    "e": "executionReport",
////            "X": "NEW",
////            "s": "ACHUSDT",
////            "i": 318083565,
////            "o": "LIMIT",
////
////
////            "e": "executionReport",
////            "X": "NEW",
////            "s": "ACHUSDT",
////            "i": 318083565,
////            "o": "LIMIT",
//
//
//
    public static void processOcoOrderUpdate(JSONObject jsonEvent) {
        try {
            System.out.println("processOcoOrderUpdate");
            System.out.println(jsonEvent.toString(2));
            String listStatusType = jsonEvent.getString("l"); // Статус списка
            String listOrderStatus = jsonEvent.getString("L"); // Статус ордеров в списке
            JSONArray orders = jsonEvent.getJSONArray("O"); // Массив ордеров

            System.out.println("processOcoOrderUpdate1--V");
            System.out.println("OCO Order Update - List Status: " + listStatusType + ", Order Status: " + listOrderStatus);

            for (int i = 0; i < orders.length(); i++) {
                JSONObject order = orders.getJSONObject(i);
                String symbol = order.getString("s"); // Символ

                // Измените эту строку
                int orderIdInt = order.getInt("i"); // ID ордера как число
                String orderId = String.valueOf(orderIdInt); // Преобразование числа в строку

                System.out.println("processOcoOrderUpdate2--V");
                System.out.println("Order - Symbol: " + symbol + ", OrderId: " + orderId);
            }

        } catch (JSONException e) {
            System.out.println("Ошибка при работе с JSON: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void processBalanceUpdate(JSONObject jsonEvent) {
        System.out.println("processBalanceUpdate");
        System.out.println("Обработка события обновления баланса");

        try {
            // Получение данных из JSON-объекта
            String asset = jsonEvent.getString("a"); // Актив
            String delta = jsonEvent.getString("d"); // Изменение баланса
            long timestamp = jsonEvent.getLong("T"); // Временная метка события

            // Вывод информации в консоль
            System.out.println("Актив: " + asset + ", Изменение баланса: " + delta + ", Время события: " + timestamp);

            // Здесь может быть дополнительная логика обработки события

        } catch (JSONException e) {
            System.out.println("Ошибка при работе с JSON: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }




    private static void handleOtherEvents(JSONObject jsonEvent) {
        System.out.println("Внутри handleOtherEvents");
        System.out.println(jsonEvent.toString(2));
        // Обработка других типов событий...
        // Пример: вывод информации о событии
        String eventType = jsonEvent.getString("e");
        System.out.println("Обработка события типа: " + eventType);
        // Дополнительная логика обработки событий...
    }


//    public static void lisenBalanceAndOrders(String event) { //todo проверить используеться ли.
//        System.out.println("В методе lisenBalanceAndOrders");
//        JSONObject jsonEvent = new JSONObject(event);
//        String eventType = jsonEvent.getString("e");
//
//        if ("outboundAccountPosition".equals(eventType)) {
//            JSONArray balances = jsonEvent.getJSONArray("B");
//            for (int i = 0; i < balances.length(); i++) {
//                JSONObject balance = balances.getJSONObject(i);
//                String asset = balance.getString("a");
//                String free = balance.getString("f");
//                String locked = balance.getString("l");
//                // Дальнейшая обработка данных баланса
//
//                System.out.println(asset + " ++++++ "+ free + " +++++++++ "+locked);
//            }
//        }
//        // Обработка других типов событий...
//    }
//=============----------------------==============================----------------------------------=====================

    public Order zahodVShellPoziciyu(String symbol, Svecha svecha) {
//        return OrderManager.marketMarginOrderShell(simbol, svecha.getClose(), PrivateConfig.NA_ODIN_ORDER_V_USDT);
        double quantity = (PrivateConfig.NA_ODIN_ORDER_V_USDT / svecha.getClose());
        double ocruglenuyQuantitySuchetomMonety = GURU.ocruglitel(quantity, GURU.getMapPosleZapytoy().get(symbol).cifrPosleZapytoyDlyaLotaVoVTOROYMONETE);
        Order order = GURU.orderManager.createMarketOrder(symbol, "SELL", ocruglenuyQuantitySuchetomMonety,false);
        order.setCenaVhoda(svecha.getClose());
        return order;
    }


    public List<Svecha> addSvecha(String symbol, Svecha svecha) {
        List<Svecha> tekushuyList = GURU.getHistorySvecheyOnSybol(symbol);
        tekushuyList.add(svecha); //todo решить пробему с переполнением - в промежутке между свечами
        return tekushuyList;
    }


    public Svecha addLineBolinjer(List<Svecha> listSvechey) {

        Svecha poslednayaSvecha = listSvechey.get(listSvechey.size() - 1);

        // Вычисляем SMA и линии Боллинджера
        List<Double> smaValues = BollingerBandsCalculator.calculateSMA(listSvechey, PrivateConfig.BOLIDJERPERIOD);
        List<Double[]> bollingerBands = BollingerBandsCalculator.calculateBollingerBands(listSvechey, PrivateConfig.BOLIDJERPERIOD);


        // Получаем значения для последней свечи
        //сетим в свечу сма
        poslednayaSvecha.setSma(smaValues.get(smaValues.size() - 1));

        // Верхняя и нижняя линии
        Double[] lastBollingerBand = bollingerBands.get(bollingerBands.size() - 1);
        poslednayaSvecha.setUpBolinjer(lastBollingerBand[0]);
        poslednayaSvecha.setDownBolinjer(lastBollingerBand[1]);

        return poslednayaSvecha;
    }

}
