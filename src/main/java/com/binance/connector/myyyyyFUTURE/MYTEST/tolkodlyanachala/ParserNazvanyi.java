package com.binance.connector.myyyyyFUTURE.MYTEST.tolkodlyanachala;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ParserNazvanyi {
    public static List<String> parsNazvanya() {
        // Замените это на путь к вашей директории
        File folder = new File("C:\\ИсторическиеДАнныеБИНАНС\\2\\");
        File[] listOfFiles = folder.listFiles();

        List<String> filenames = new ArrayList<>();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    // Удаление расширения .txt из названия файла
                    String filenameWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");
                    filenames.add(filenameWithoutExtension);
                }
            }
        }
        return filenames;
    }
}