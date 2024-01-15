package com.binance.connector.myyyyyFUTURE.MYTEST.parsery;

import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SvechaParser {
    public static List<Svecha> parsimZaGod(String symbol) {
//        String pathToFile = "C:\\ИсторическиеДАнныеБИНАНС\\2\\" + symbol + ".txt";
        String pathToFile = "C:\\ИсторическиеДАнныеБИНАНС\\2\\" + symbol + ".txt";

        List<Svecha> svechi = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(pathToFile));
            for (String line : lines) {
                String[] parts = line.split(",");
                long openTime = Long.parseLong(parts[0]);
                double open = Double.parseDouble(parts[1]);
                double high = Double.parseDouble(parts[2]);
                double low = Double.parseDouble(parts[3]);
                double close = Double.parseDouble(parts[4]);

                Svecha svecha = new Svecha(symbol, openTime, open, high, low, close);
                svechi.add(svecha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Размер листа " + svechi.size());

        return svechi;
        // После парсинга, в списке svechi будут все свечи в порядке файла
        // Здесь вы можете выполнить с этим списком что-либо
    }
}