package com.binance.connector.myyyyyFUTURE.util.parsmoneyznak;

import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class PairsExtractor {

    public static void main(String[] args) {
        String inputFile = "C:\\parsedPairsInfoMONEYFUTURE.txt"; // Укажите путь к вашему файлу
        String outputFile = "C:\\USDT пары FULL.txt"; // Укажите путь к файлу вывода

        try {
            String content = new String(Files.readAllBytes(Paths.get(inputFile)));
            Matcher m = Pattern.compile("Symbol: (\\w+)").matcher(content);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            while (m.find()) {
                writer.write(m.group(1) + System.lineSeparator());
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
