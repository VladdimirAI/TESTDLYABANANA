package com.binance.connector.myyyyyFUTURE;

import com.binance.connector.myyyyyFUTURE.MYTEST.ProcessorBirghiTEST;
import com.binance.connector.myyyyyFUTURE.mp3.MP3Player;
import com.binance.connector.myyyyyFUTURE.parsery.Parser;
import com.binance.connector.myyyyyFUTURE.processory.Processor;
import com.binance.connector.myyyyyFUTURE.suchnostyotservera.acaunt.AccountUpdate;
import com.binance.connector.myyyyyFUTURE.sushnosty.Order;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;
import com.binance.connector.myyyyyFUTURE.sushnosty.ValutnayaPara;
import com.binance.connector.myyyyyFUTURE.ustanovkaorderov.OrderManager;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GURU {

    public static  AccountUpdate accountUpdate;

    static Map<String, Order> runTimeOrders;
    static Map<String, Order> stopLossOrders;
    static Map<String, Order> takeProfitOrders;

    static Set<Svecha> poslednyeSvechiisConsoli;
    static Map<String, List<Svecha>> historySvechey; //todo подчистка
    static List<String> MONEY;
    static Map<String, ValutnayaPara> mapPosleZapytoy;
    static double buyBalace;
    static double shellBalace;
    static double realBalace;


    static ExecutorService executor; //todo возможно убрать

    public static Processor processor;
    public static OrderManager orderManager;


    public static void activator(List<String> MONEY) {
        runTimeOrders = new HashMap<>();
        stopLossOrders = new HashMap<>();
        takeProfitOrders = new HashMap<>();

        poslednyeSvechiisConsoli = new HashSet<>();
        historySvechey = new HashMap<>();

        mapPosleZapytoy = Parser.parsInfoPosleZapytoy(PrivateConfig.INFOOCYFRAHPOSLEZAPYTOY);
        realBalace = PrivateConfig.NACHALNUYBALANS;
        buyBalace = realBalace * 2;//todo изучить
        shellBalace = realBalace * 2;//todo изучить

        GURU.MONEY = MONEY;

        int numberOfThreads = 10; // Примерное количество потоков //todo обратить внимание

        executor = Executors.newFixedThreadPool(numberOfThreads);

        processor = new Processor();
        orderManager = new OrderManager(PrivateConfig.API_KEY,PrivateConfig.SECRET_KEY);
        accountUpdate = new AccountUpdate();
    }

    public static synchronized void setBuyBalance(double buyBalance) {
        GURU.buyBalace = buyBalance;
    }

    public double getBuyBalance() {
        return buyBalace;
    }

    public static synchronized void updateSellBalance(double change) {
        shellBalace += change; // change может быть как положительным, так и отрицательным
    }

    public static synchronized double getSellBalance() {
        return shellBalace;
    }
    public static synchronized void setRealBalance(double realBalance) {
        realBalace = realBalance;
    }

    public double getRealBalance() {
        return realBalace;
    }


    public static void updateSvechi(String simbol, Svecha svecha) {
    historySvechey.get(simbol).add(svecha);
    }

    public static List<Svecha> getHistorySvecheyOnSybol(String simbol){
       return historySvechey.get(simbol);
    }

    public static void addOneMoneyAndHistorySvechey(String money, List<Svecha> listOneSvechi22) {
        historySvechey.put(money,listOneSvechi22);
    }

    public static void addOrder(Order order){ ///todo почему не используеться ???
        String symbol = order.getSymbol();
        runTimeOrders.put(symbol,order);
    }

    public static void addStopLossOrder(Order order){
        String symbol = order.getSymbol();
        stopLossOrders.put(symbol,order);
    }

    public static void addTakeProfitOrder(Order order){
        String symbol = order.getSymbol();
        takeProfitOrders.put(symbol,order);
    }

    public static Map<String, ValutnayaPara> getMapPosleZapytoy() {
        return mapPosleZapytoy;
    }



    public static double ocruglitel(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Map<String,Order> getRunTimeOrders() {
        return runTimeOrders;
    }

    public static Map<String, Order> getStopLossOrders() {return stopLossOrders;}

    public static Map<String, Order> getTakeProfitOrders() {return takeProfitOrders;}


    public static void playSIGNAL(){
        MP3Player.play("musicSignal.mp3");
    }

}


//todo statik and zapis sinhronaized