package com.binance.connector.myyyyyFUTURE.MYTEST;

import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SvechaVidovatelPoDate {

    public static List<Svecha> filterSvechi(List<Svecha> svechi, String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long startMillis = LocalDate.parse(startDateStr, formatter).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        // Добавляем 1 день к конечной дате и вычитаем 1 миллисекунду, чтобы получить конец дня
        long endMillis = LocalDate.parse(endDateStr, formatter).plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;

        List<Svecha> filteredSvechi = new ArrayList<>();
        for (Svecha svecha : svechi) {
            if (svecha.getOpenTime() >= startMillis && svecha.getOpenTime() <= endMillis) {
                filteredSvechi.add(svecha);
            }
        }
        return filteredSvechi;
    }
}
