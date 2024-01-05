package com.binance.connector.myyyyyFUTURE.processory;


import com.binance.connector.myyyyyFUTURE.GURU;
import com.binance.connector.myyyyyFUTURE.PrivateConfig;
import com.binance.connector.myyyyyFUTURE.sushnosty.Order;
import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

public class VistavlyatelStopITakeProffit {

    public static boolean createForLineTakeAndStopAndADDSet(Order orderRunTime, Svecha svecha) {


        String symbol = orderRunTime.getSymbol();

        double quantity50 = (orderRunTime.getCummulativeQuoteQty() / 2);
        double ocruglenuyQuantitySuchetomMonety50 = GURU.ocruglitel(quantity50, GURU.getMapPosleZapytoy().get(symbol).cifrPosleZapytoyDlyaLotaVoVTOROYMONETE);

        double nizHvosta = Math.max(svecha.getOpen(), svecha.getClose());
        double pribavkaKNizuHvosta = (svecha.getHigh() - nizHvosta) * PrivateConfig.STOPLOSSVPROCENTAH;

        double stopLoss = nizHvosta + pribavkaKNizuHvosta;
        double doSkolkiGotovyBratUbytok = stopLoss + (stopLoss * (PrivateConfig.STOPLIMIDOSKOLKIUBYTOK - PrivateConfig.STOPLOSSVPROCENTAH));

        double takeProfit = svecha.getSma(); // todo нужен только в верхней половине
//        double takeProfitTwo = svecha.getDownBolinjer();

        Order orderStop = (GURU.orderManager.creatMARKETOrderStopLoss(symbol, orderRunTime.getCummulativeQuoteQty(), stopLoss));
        orderStop.setCenaVhoda(stopLoss);
        GURU.addStopLossOrder(orderStop); //todo переделать потом в менеджере на человечиский со стопами
//       GURU.addOCOOrder(OcoOrderExecutor.createOCOOrder(symbol, ocruglenuyQuantitySuchetomMonety50, takeProfitTwo, stopLoss, doSkolkiGotovyBratUbytok));
        Order orderProfit = (GURU.orderManager.creatMARKETrderTakeProfit(symbol, ocruglenuyQuantitySuchetomMonety50, takeProfit));
        orderProfit.setCenaVhoda(takeProfit);
        GURU.addTakeProfitOrder(orderProfit);


        return true;


    }
}