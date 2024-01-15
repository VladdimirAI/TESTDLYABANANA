package com.binance.connector.myyyyyFUTURE.processory;


import com.binance.connector.myyyyyFUTURE.PrivateConfig;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

public class AnalizeProcessor { //todo данные подкоректировать





    public static boolean doBolinjera(Svecha svecha){

      double sma = svecha.getSma();
      double closePrice = svecha.getClose();

      // Рассчитываем процентное отклонение от SMA
//      double deviation = Math.abs(closePrice - sma);
      double deviation = (closePrice - sma);
      double percentDeviation = (deviation / closePrice) * 100;

      // Возвращаем true, если отклонение составляет 15% или более
      return percentDeviation >= PrivateConfig.PRIEMLEMUYPROCENTDOBOLINJERA;

    }


  public static boolean verhnuyiHvostChtonado(Svecha svecha) {

    double verTelahSvechi = Math.max(svecha.getOpen(), svecha.getClose());

    double dlinaVerhnegoHvosta = svecha.getHigh() - verTelahSvechi;
    double smaValue = svecha.getSma();

    double doBolinjera = verTelahSvechi - smaValue;

    // Проверяем, соответствует ли длина верхнего хвоста заданным пропорциям относительно SMA
    return dlinaVerhnegoHvosta >= PrivateConfig.OTSKOLKIRAZHVOSTDOLGHENBITBOLSHETRENDA * doBolinjera && dlinaVerhnegoHvosta <= PrivateConfig.DOSKOLKIRAZHVOSTDOLGHENBITBOLSHETRENDA * doBolinjera;
  }


}
