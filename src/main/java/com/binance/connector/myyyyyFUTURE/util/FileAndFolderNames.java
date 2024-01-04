package com.binance.connector.myyyyyFUTURE.util;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileAndFolderNames {
    public static void main(String[] args) {
        String startFolder = "C:\\Users\\svn13\\Downloads\\binance-futures-connector-java-main\\binance-futures-connector-java-main"; // Укажите путь к начальной папке

        try {
            StringBuilder contentBuilder = new StringBuilder();
            Files.walkFileTree(Paths.get(startFolder), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileName = file.getFileName().toString();
                    String filePath = file.toString().replace(startFolder, "").replace(File.separator, ".");
                    contentBuilder.append(filePath).append(".").append(fileName).append("\n");
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    String folderName = dir.getFileName().toString();
                    String folderPath = dir.toString().replace(startFolder, "").replace(File.separator, ".");
                    contentBuilder.append(folderPath).append(".").append(folderName).append("\n");
                    return FileVisitResult.CONTINUE;
                }
            });

            String fileAndFolderNames = contentBuilder.toString();

            // Теперь можно записать содержимое в текстовый документ
            try (PrintWriter writer = new PrintWriter("имена_файлов_и_папок.txt")) {
                writer.write(fileAndFolderNames);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("Имена файлов и папок записаны в текстовый документ.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

