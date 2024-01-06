package com.binance.connector.myyyyyFUTURE.mp3;

import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.InputStream;

public class MP3Player {

    public static void play(String resourcePath) {
        new Thread(() -> {
            try {
                InputStream resourceStream = MP3Player.class.getClassLoader().getResourceAsStream(resourcePath);
                if (resourceStream == null) {
                    throw new IllegalArgumentException("Не удалось найти файл: звука " + resourcePath);
                }
                Player player = new Player(resourceStream);
                player.play(); // Блокирует этот поток до окончания воспроизведения
                player.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
