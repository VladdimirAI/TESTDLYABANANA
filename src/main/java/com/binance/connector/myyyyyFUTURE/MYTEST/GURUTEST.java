package com.binance.connector.myyyyyFUTURE.MYTEST;

import com.binance.connector.myyyyyFUTURE.MYTEST.syshnostytest.TestSvechaDDTO;
import com.binance.connector.myyyyyFUTURE.PrivateConfig;
import com.binance.connector.myyyyyFUTURE.parsery.Parser;
import com.binance.connector.myyyyyFUTURE.sushnosty.Order;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;
import com.binance.connector.myyyyyFUTURE.sushnosty.ValutnayaPara;

import java.util.*;

public class GURUTEST {

  public   static Map<String, Order> runTimeOrdersRClientom;
  public   static Map<String, Order> stopLossOrdersRClientom;
  public   static Map<String, Order> takeProfitOrdersRClientom;

  public static Map<String, ValutnayaPara> mapPosleZapytoy;


    static double buyBalaceClienta;
    static double shellBalaceClienta;
    static double realBalaceClienta;

    public static ProcessorBirghiTEST processorBirghiTEST;

    public static List<Order> orderaNaServer;

    static long  poslednyiOrderID = 0;

    public  static List<Svecha> testovyiList ;
    public  static List<TestSvechaDDTO> testovyiListDubley ;


    public GURUTEST(String symbol) {

        runTimeOrdersRClientom = new HashMap<>();
        stopLossOrdersRClientom = new HashMap<>();
        takeProfitOrdersRClientom = new HashMap<>();

        mapPosleZapytoy = Parser.parsInfoPosleZapytoy(PrivateConfig.INFOOCYFRAHPOSLEZAPYTOY);

        realBalaceClienta = PrivateConfig.NACHALNUYBALANS;
        buyBalaceClienta = realBalaceClienta * 2;//todo изучить
        shellBalaceClienta = realBalaceClienta * 2;//todo изучить

        processorBirghiTEST = new ProcessorBirghiTEST(symbol);


        testovyiList = new ArrayList<>();
        testovyiListDubley = new ArrayList<>();
    }

    public static long generateOrderID() {
        return poslednyiOrderID++;
    }
}
