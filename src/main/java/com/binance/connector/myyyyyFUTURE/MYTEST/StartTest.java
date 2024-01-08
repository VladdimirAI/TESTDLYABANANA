package com.binance.connector.myyyyyFUTURE.MYTEST;

import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.MYTEST.parsery.SvechaParser;
import com.binance.connector.myyyyyFUTURE.MYTEST.syshnostytest.TestSvechaDDTO;
import com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator.AnalizerPatternOne;
import com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator.VidoditelVOkno;
import com.binance.connector.myyyyyFUTURE.PrivateConfig;
import com.binance.connector.myyyyyFUTURE.bolinjer.BollingerBandsCalculator;
import com.binance.connector.myyyyyFUTURE.parsery.ReaderMoney;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;


public class StartTest {
   static VidoditelVOkno vidoditelVOkno;
//            "2023-01-01", // начальная дата в формате "год-месяц-день"
//            "2023-12-31"  // конечная дата в формате "год-месяц-день"

    public static void main(String[] args) {

        String symbol = "ZRXUSDT";

        vidoditelVOkno = new VidoditelVOkno();

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


        for (Svecha svecha : listVsehSvecheyNugnoyDaty)
            AnalizerPatternOne.analizer10(svecha);


        for (Svecha svecha : GURUTEST.testovyiList) {
            LocalDate date = convertTimestampToLocalDate(svecha.getOpenTime());
            LocalDate dayBefore = date.minusDays(1);

            List<Svecha> filteredCandles = SvechaVidovatelPoDate.filterSvechi(
                    listVsehSvecheyOdnoyMonety,
                    dayBefore.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            );

            vidoditelVOkno.vivodVOkno(filteredCandles,svecha.getOpenTime());
        }
    }





//        vidoditelVOkno.vivodVOkno(listVsehSvecheyNugnoyDaty);

//        for (Svecha svecha : GURUTEST.testovyiList) {
//            vivestyVOdelnoeOcnoSveshuDOiPose(listVsehSvecheyOdnoyMonety,svecha ); //подумь как ускорить например выбрать другую дату
//        }

//        ZAPUSKREALNOGOTESTA(listVsehSvecheyNugnoyDaty, symbol);


/////////////////////////////////////////////
//        vidoditelVOkno.vivodVOkno(listVsehSvecheyNugnoyDaty);
////////////////////////////////////////////////////


        //добавляем одну свечу в рантаймРежиме


//      Svecha posled =  listVsehSvecheyNugnoyDaty.get(listVsehSvecheyNugnoyDaty.size()-1);
//      Svecha predPosl =  listVsehSvecheyNugnoyDaty.get(listVsehSvecheyNugnoyDaty.size()-2);
//      Svecha predpred =  listVsehSvecheyNugnoyDaty.get(listVsehSvecheyNugnoyDaty.size()-3);
//
//      try {
//        Thread.sleep(10000);
//        vidoditelVOkno.chart.dobavlenyeSvechiRunTime(predpred);
//        Thread.sleep(2000);
//
//        vidoditelVOkno.chart.dobavlenyeSvechiRunTime(predPosl);
//        Thread.sleep(2000);
//
//        vidoditelVOkno.chart.dobavlenyeSvechiRunTime(posled);
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }





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




//    public static   void vivestyVOdelnoeOcnoSveshuDOiPose(List<Svecha> allCandles, Svecha targetSvecha) {
//        int targetIndex = allCandles.indexOf(targetSvecha);
//        if (targetIndex == -1) return; // Если свеча не найдена
//
//        int startIndex = Math.max(targetIndex - 50, 0);
//        int endIndex = Math.min(targetIndex + 50, allCandles.size() - 1);
//
//        List<Svecha> surroundingCandles = allCandles.subList(startIndex, endIndex + 1);
//
//        vidoditelVOkno.vivodVOkno(surroundingCandles);
//    }


    private static LocalDate convertTimestampToLocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}