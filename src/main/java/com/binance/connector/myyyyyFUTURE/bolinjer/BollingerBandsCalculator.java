package com.binance.connector.myyyyyFUTURE.bolinjer;





import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import java.util.ArrayList;
import java.util.List;

public class BollingerBandsCalculator {
    public static List<Double> calculateSMA(List<Svecha> candlesticks, int period) {
        List<Double> sma = new ArrayList<>();
        for (int i = 0; i < candlesticks.size(); i++) {
            if (i < period - 1) {
                sma.add(null);
                continue;
            }
            double sum = 0;
            for (int j = i; j > i - period; j--) {
                sum += candlesticks.get(j).getClose();
            }
            sma.add(sum / period);
        }
        return sma;
    }

    public static List<Double[]> calculateBollingerBands(List<Svecha> candlesticks, int period) {
        List<Double> sma = calculateSMA(candlesticks, period);
        List<Double[]> bands = new ArrayList<>();

        for (int i = 0; i < candlesticks.size(); i++) {
            if (i < period - 1) {
                bands.add(new Double[]{null, null});
                continue;
            }
            double sumOfSquares = 0;
            for (int j = i; j > i - period; j--) {
                sumOfSquares += Math.pow(candlesticks.get(j).getClose() - sma.get(i), 2);
            }
            double stddev = Math.sqrt(sumOfSquares / period);
            bands.add(new Double[]{sma.get(i) + 2 * stddev, sma.get(i) - 2 * stddev});
        }
        return bands;
    }


    //todo объеденить в один
}
