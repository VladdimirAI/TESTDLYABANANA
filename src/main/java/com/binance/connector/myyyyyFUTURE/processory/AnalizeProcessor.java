package com.binance.connector.myyyyyFUTURE.processory;


import com.binance.connector.myyyyyFUTURE.PrivateConfig;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

public class AnalizeProcessor { //todo данные подкоректировать





    public static boolean doBolinjera(Svecha svecha){

      double sma = svecha.getSma();
      double closePrice = svecha.getClose();

      // Рассчитываем процентное отклонение от SMA
      double deviation = Math.abs(closePrice - sma);
      double percentDeviation = (deviation / closePrice) * 100;

      // Возвращаем true, если отклонение составляет 15% или более
      return percentDeviation >= PrivateConfig.PRIEMLEMUYPROCENTDOBOLINJERA;

    }


  public static boolean verhnuyiHvostChtonado(Svecha svecha) {
    double dlinaVerhnegoHvosta = svecha.getHigh() - Math.max(svecha.getOpen(), svecha.getClose());
    double smaValue = svecha.getSma();

    // Проверяем, соответствует ли длина верхнего хвоста заданным пропорциям относительно SMA
    return dlinaVerhnegoHvosta >= PrivateConfig.OTSKOLKIRAZHVOSTDOLGHENBITBOLSHETRENDA * smaValue && dlinaVerhnegoHvosta <= PrivateConfig.DOSKOLKIRAZHVOSTDOLGHENBITBOLSHETRENDA * smaValue;
  }


}
