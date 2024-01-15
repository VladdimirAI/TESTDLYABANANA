package com.binance.connector.myyyyyFUTURE.MYTEST;


import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.MYTEST.parsery.SvechaParser;
import com.binance.connector.myyyyyFUTURE.MYTEST.syshnostytest.TestSvechaDDTO;
import com.binance.connector.myyyyyFUTURE.MYTEST.tolkodlyanachala.ParserNazvanyi;
import com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator.AnalizerPatternOne;
import com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator.VidoditelVOkno;
import com.binance.connector.myyyyyFUTURE.PrivateConfig;
import com.binance.connector.myyyyyFUTURE.bolinjer.BollingerBandsCalculator;
import com.binance.connector.myyyyyFUTURE.parsery.ReaderMoney;
import com.binance.connector.myyyyyFUTURE.sushnosty.Order;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.awt.*;
import java.util.List;


public class PrognatOdnuMonety {

    static double balance = 0;


    static VidoditelVOkno vidoditelVOkno;


    static List<String> teParyChtoZakonchilisRuntimom = new ArrayList<>();


    static String symbol = "CYBERUSDT";

    public static void main(String[] args) {


        vidoditelVOkno = new VidoditelVOkno();



        List<Double> vseBalacy = new ArrayList<>();


        processSymbol(symbol);


        if (GURUTEST.runTimeOrdersRClientom.isEmpty()) { //todo если свечей далее нет для закрытия
            balance += GURUTEST.realBalaceClienta - 100;
        }


        vseBalacy.add(balance);


//        System.out.println("Баланс от изначального 100 долларо спустя годовой прогон по всем парам = " + balance);
        for (double bal : vseBalacy) {
            System.out.println(bal);
        }
        System.out.println("cvcvcvcvcvcvcv");
        for (String paraRunTimeOrder : teParyChtoZakonchilisRuntimom) {
            System.out.println(paraRunTimeOrder);
        }


    }


    public static void processSymbol(String symbol) {

        GURUTEST GURUTEST = new GURUTEST(symbol);


        List<Svecha> listVsehSvecheyOdnoyMonety = SvechaParser.parsimZaGod(symbol); //todo поправить внутри

        List<Svecha> listVsehSvecheyNugnoyDaty = SvechaVidovatelPoDate.filterSvechi(listVsehSvecheyOdnoyMonety, "2023-01-01", "2023-12-30");

        prostavitSvechamSma(listVsehSvecheyNugnoyDaty);

        for (Svecha svecha : listVsehSvecheyNugnoyDaty)
            AnalizerPatternOne.analizer(svecha, svecha.getSma());

        System.out.println(GURUTEST.testovyiList.size());

        for (Svecha svecha : GURUTEST.testovyiList) {
            System.out.println(svecha.toString());
        }
        for (TestSvechaDDTO testSvechaDDTO : GURUTEST.testovyiListDubley) {
            System.out.println(testSvechaDDTO.toString());
        }

//
        for (Svecha svecha : listVsehSvecheyNugnoyDaty)
            AnalizerPatternOne.analizer10(svecha);

        List<Svecha> listProtivPOvtoryusheshySvechiSybola = new ArrayList<>();
        for (Svecha svecha : GURUTEST.testovyiList) {
            if (listProtivPOvtoryusheshySvechiSybola.contains(svecha)) {
                continue;  // исправляет - когда нужные свечи в одном симке - он гонит оди и тот же график два раза
            }

            LocalDate date = convertTimestampToLocalDate(svecha.getOpenTime());
            LocalDate dayBefore = date.minusDays(1);

            List<Svecha> filteredCandles = SvechaVidovatelPoDate.filterSvechi(
                    listVsehSvecheyOdnoyMonety,
                    dayBefore.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            ); //представить что скобки нету
            for (Svecha sv : filteredCandles) {
                listProtivPOvtoryusheshySvechiSybola.add(sv);
            }

            vidoditelVOkno.vivodVOkno(filteredCandles, svecha.getOpenTime(), symbol);

//
//                try {
//                    Thread.sleep(2000000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }


            System.out.println("SymbolDlyaTEsta " + symbol);


            ZAPUSKREALNOGOTESTA(filteredCandles, symbol);//todo тесть только кусочки где свечи по 10+ процентов


            /////TODO ниже логика для случая когда много снимков одной монеты заходит
            if (!GURUTEST.runTimeOrdersRClientom.isEmpty()) {
                Order ostavShysyaOrder = GURUTEST.runTimeOrdersRClientom.get(symbol);

                GURUTEST.realBalaceClienta += ostavShysyaOrder.getCenaVhoda() * ostavShysyaOrder.colichestvoCuplennuhMonet; // возвращаем не исполнившийся ордер или часть неисплнеившуюся

                teParyChtoZakonchilisRuntimom.add(symbol + "на случай много карточек");
            }

            GURUTEST.runTimeOrdersRClientom = new HashMap<>();
            GURUTEST.takeProfitOrdersRClientom = new HashMap<>();
            GURUTEST.stopLossOrdersRClientom = new HashMap<>();


        }

    }


    public static void prostavitSvechamSma(List<Svecha> listVsehSvecheyNugnoyDaty) {

        List<Double> smaValues = BollingerBandsCalculator.calculateSMA(listVsehSvecheyNugnoyDaty, 22); // period - это период для SMA

        for (int i = 0; i < listVsehSvecheyNugnoyDaty.size(); i++) {
            Svecha svecha = listVsehSvecheyNugnoyDaty.get(i);
            Double smaValue = smaValues.get(i);
            if (smaValue != null) { // перввые 22 свечи null sma
                svecha.setSma(smaValue); // установка значения SMA для каждой свечи
            }
        }
    }


    public static void ZAPUSKREALNOGOTESTA(List<Svecha> listVsehSvecheyNugnoyDaty, String symbol) {

////////////////////////////WWWWWWWWWWWWWWWWWWWWWWW из запуска майн
        ReaderMoney readerMoney = new ReaderMoney();
        List<String> oldMoneyZnak = readerMoney.readCurrencyPairsFromFile(PrivateConfig.MONEY_FILE);//todo поменять файл на фьючерсный
        for (String pair : oldMoneyZnak) {
            System.out.println(pair);
        }

        GURU.activator(oldMoneyZnak);

        List<Svecha> pervyList22 = new ArrayList<>(listVsehSvecheyNugnoyDaty.subList(0, 22));
        List<Svecha> vtoroyListposle22 = new ArrayList<>(listVsehSvecheyNugnoyDaty.subList(22, listVsehSvecheyNugnoyDaty.size()));


        for (Svecha svecha : pervyList22) {
            // Здесь ваша логика для определения цвета на основе цен свечи
            // Например, если цена закрытия выше цены открытия, устанавливаем цвет "зеленый", иначе "красный"
            Color color = (svecha.getClose() > svecha.getOpen()) ? Color.GREEN : Color.RED;
            svecha.setColor(color);
        }


        GURU.addOneMoneyAndHistorySvechey(symbol, pervyList22);

///////////////////////MMMMMMMMMMMMMMMMMMM из запуска майн

        GURUTEST.processorBirghiTEST.nachaloTesta(vtoroyListposle22);


    }





    private static LocalDate convertTimestampToLocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}