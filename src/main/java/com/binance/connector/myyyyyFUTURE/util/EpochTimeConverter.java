package com.binance.connector.myyyyyFUTURE.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class EpochTimeConverter {

    public static long convertToEpochMillis(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        return dateTime.toInstant(ZoneOffset.ofHours(+5)).toEpochMilli(); //todo разница  часовом поесе 5 часов
    }

    public static void main(String[] args) {
        String dateTimeStr = "2023-05-13 05:30:00"; // Пример даты и времени
        long epochMillis = convertToEpochMillis(dateTimeStr);
        System.out.println("Миллисекунд с начала эпохи: " + epochMillis);
    }
}
//1683939600000 2023-05-13 06:00:00
//1683942300000 2023-05-13 06:45:00

