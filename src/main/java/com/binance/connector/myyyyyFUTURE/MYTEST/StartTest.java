package com.binance.connector.myyyyyFUTURE.MYTEST;

import com.binance.connector.myyyyyFUTURE.MYTEST.parsery.SvechaParser;
import com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator.CandlestickPropocianatorRisvalshik;
import com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator.VidoditelVOkno;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import java.util.List;

public class StartTest {

//            "2023-01-01", // начальная дата в формате "год-месяц-день"
//            "2023-12-31"  // конечная дата в формате "год-месяц-день"

    public static void main(String[] args) {


      List<Svecha> listVsehSvecheyOdnoyMonety = SvechaParser.main();

      List<Svecha> listVsehSvecheyNugnoyDaty = SvechaVidovatelPoDate.filterSvechi(listVsehSvecheyOdnoyMonety,"2023-12-24","2023-12-25");

        VidoditelVOkno vidoditelVOkno = new VidoditelVOkno();

        vidoditelVOkno.vivodVOkno(listVsehSvecheyNugnoyDaty);
/////////////////////////////////////////////////////выше вывели часть свечей в окно

      Svecha posled =  listVsehSvecheyNugnoyDaty.get(listVsehSvecheyNugnoyDaty.size()-1);
      Svecha predPosl =  listVsehSvecheyNugnoyDaty.get(listVsehSvecheyNugnoyDaty.size()-2);
      Svecha predpred =  listVsehSvecheyNugnoyDaty.get(listVsehSvecheyNugnoyDaty.size()-3);



        //добавляем одну свечу в рантаймРежиме
        vidoditelVOkno.chart.dobavlenyeSvechiRunTime(predpred);
        vidoditelVOkno.chart.dobavlenyeSvechiRunTime(predPosl);
        vidoditelVOkno.chart.dobavlenyeSvechiRunTime(posled);





    }
}
