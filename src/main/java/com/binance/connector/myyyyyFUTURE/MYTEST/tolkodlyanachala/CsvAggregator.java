package com.binance.connector.myyyyyFUTURE.MYTEST.tolkodlyanachala;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CsvAggregator {
    public static void main(String[] args) throws IOException {
//        String directoryPath = "C:\\ИсторическиеДАнныеБИНАНС\\1"; // Замените на ваш путь к файлам
        String directoryPath = "C:\\Исторические данные в виде много файлов"; // Замените на ваш путь к файлам
        File dir = new File(directoryPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv"));

        // Фильтрация и сортировка файлов по дате в названии
        Arrays.sort(files, Comparator.comparing(File::getName));

        HashMap<String, List<File>> filesByPair = new HashMap<>();

        // Группировка файлов по валютным парам
        for (File file : files) {
            String pair = file.getName().split("-")[0];
            filesByPair.computeIfAbsent(pair, k -> new ArrayList<>()).add(file);
        }

        // Объединение файлов для каждой пары в правильном порядке
        for (String pair : filesByPair.keySet()) {
            List<File> pairFiles = filesByPair.get(pair);
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(directoryPath, pair + ".txt"));

            for (File pairFile : pairFiles) {
                List<String> lines = Files.readAllLines(pairFile.toPath());
                lines.remove(0); // Предполагая, что первая строка - заголовок
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            writer.close();
        }
    }
}
