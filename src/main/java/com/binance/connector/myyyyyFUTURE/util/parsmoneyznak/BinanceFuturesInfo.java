//package com.binance.connector.myyyyyFUTURE.util;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//public class BinanceFuturesInfo {
//    public static void main(String[] args) {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://fapi.binance.com/fapi/v1/exchangeInfo"))
//                .build();
//
//        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenAccept(json -> saveToFile(json, "C:\\binance_exchange_info.json"))
//                .join();
//    }
//
//    private static void saveToFile(String content, String path) {
//        try {
//            Files.writeString(Paths.get(path), content);
//        } catch (IOException e) {
//            System.err.println("Ошибка при записи файла: " + e.getMessage());
//        }
//    }
//}
// работает нно на 11 версии