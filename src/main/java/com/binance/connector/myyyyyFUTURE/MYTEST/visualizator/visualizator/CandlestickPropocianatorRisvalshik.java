package com.binance.connector.myyyyyFUTURE.MYTEST.visualizator.visualizator;

import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CandlestickPropocianatorRisvalshik extends JPanel {

    AnalizerPatternOne analizerPatternOne;

    private final List<Svecha> candlesticks;
    private final List<Double[]> bollingerBands; // Данные линий Боллинджера
    private final List<Double> smaValues; // Данные SMA
    private double minPrice = Double.MAX_VALUE;
    private double maxPrice = Double.MIN_VALUE;


    private double scale = 1.0;
    private final int minCandleWidth = 1;
    private final int maxCandleWidth = 20;

    private long highlightedOpenTime;

    public CandlestickPropocianatorRisvalshik(List<Svecha> candlesticks, List<Double[]> bollingerBands, List<Double> smaValues,long openTime) {
        AnalizerPatternOne analizerPatternOne1 = new AnalizerPatternOne();
        this.analizerPatternOne = analizerPatternOne1;
        this.candlesticks = candlesticks;
        this.bollingerBands = bollingerBands;
        this.smaValues = smaValues;
        calculatePriceRange();
        setPreferredSize(new Dimension(800, 600));



        addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                scale = Math.min(scale + 0.1, maxCandleWidth / (double) minCandleWidth);
            } else {
                scale = Math.max(scale - 0.1, 1.0);
            }
            updatePreferredSize(); // Обновляем размер компонента
            repaint(); // Перерисовываем компонент

        });


        this.highlightedOpenTime = openTime;
    }

    private void calculatePriceRange() {
        for (Svecha candle : candlesticks) {
            minPrice = Math.min(minPrice, candle.getLow());
            maxPrice = Math.max(maxPrice, candle.getHigh());
        }
    }

    private double calculatePercentageChange(double previousClose, double currentClose) {
        if (previousClose == 0) {
            return 0; // Избегаем деления на ноль
        }
        return  ((currentClose - previousClose) / previousClose * 100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
//        int candleWidth = Math.max(width / candlesticks.size() - 1, 1);
        int candleWidth = Math.max((int) (minCandleWidth * scale), minCandleWidth);
        // Отрисовка свечей
        for (int i = 0; i < candlesticks.size(); i++) {
            Svecha candle = candlesticks.get(i);

            int x = i * (candleWidth + 1);
            int yHigh = (int) ((maxPrice - candle.getHigh()) / (maxPrice - minPrice) * height);
            int yLow = (int) ((maxPrice - candle.getLow()) / (maxPrice - minPrice) * height);
            int yOpen = (int) ((maxPrice - candle.getOpen()) / (maxPrice - minPrice) * height);
            int yClose = (int) ((maxPrice - candle.getClose()) / (maxPrice - minPrice) * height);

            Color color = candle.getClose() > candle.getOpen() ? Color.GREEN : Color.RED;
            candle.setColor(color);
            g2d.setColor(color);

            g2d.fillRect(x, Math.min(yOpen, yClose), candleWidth, Math.abs(yClose - yOpen));
            g2d.drawLine(x + candleWidth / 2, yHigh, x + candleWidth / 2, yLow);
        }

//        // Отрисовка полос Боллинджера
//        g2d.setColor(Color.BLUE);
//        for (int i = 0; i < candlesticks.size(); i++) {
//            Double[] bands = bollingerBands.get(i);
//            if (bands != null && bands[0] != null && bands[1] != null) {
//                int x = i * (candleWidth + 1);
//                int yUpperBand = (int) ((maxPrice - bands[0]) / (maxPrice - minPrice) * height);
//                int yLowerBand = (int) ((maxPrice - bands[1]) / (maxPrice - minPrice) * height);
//
//                g2d.drawLine(x, yUpperBand, x + candleWidth, yUpperBand);
//                g2d.drawLine(x, yLowerBand, x + candleWidth, yLowerBand);
//            }
//        }
        // Отрисовка полос Боллинджера
        g2d.setColor(Color.BLUE);
        for (int i = 1; i < candlesticks.size(); i++) {

                Double[] previousBands = bollingerBands.get(i - 1);
                Double[] currentBands = bollingerBands.get(i);

                if (previousBands != null && currentBands != null && previousBands[0] != null && currentBands[0] != null) {
                    int x1 = (i - 1) * (candleWidth + 1);
                    int y1Upper = (int) ((maxPrice - previousBands[0]) / (maxPrice - minPrice) * height);
                    int y1Lower = (int) ((maxPrice - previousBands[1]) / (maxPrice - minPrice) * height);

                    int x2 = i * (candleWidth + 1);
                    int y2Upper = (int) ((maxPrice - currentBands[0]) / (maxPrice - minPrice) * height);
                    int y2Lower = (int) ((maxPrice - currentBands[1]) / (maxPrice - minPrice) * height);

                    // Рисуем непрерывные линии для верхней и нижней полос Боллинджера
                    g2d.drawLine(x1, y1Upper, x2, y2Upper);
                    g2d.drawLine(x1, y1Lower, x2, y2Lower);
                }
            }

        // Отрисовка линии SMA
        g2d.setColor(Color.BLACK);
        for (int i = 1; i < candlesticks.size(); i++) {
//            if (i < smaValues.size() && smaValues.get(i) != null && smaValues.get(i - 1) != null) {
                if (smaValues.get(i) != null && smaValues.get(i - 1) != null) { // ориг

                int x1 = (i - 1) * (candleWidth + 1);
                int y1 = (int) ((maxPrice - smaValues.get(i - 1)) / (maxPrice - minPrice) * height);
                int x2 = i * (candleWidth + 1);
                int y2 = (int) ((maxPrice - smaValues.get(i)) / (maxPrice - minPrice) * height);
                g2d.drawLine(x1, y1, x2, y2);
            }

        }

        // Отрисовка процентных изменений
        for (int i = 1; i < candlesticks.size(); i++) {
            Svecha currentCandle = candlesticks.get(i);
            double percentageChange = calculatePercentageChange(candlesticks.get(i - 1).getClose(), currentCandle.getClose());
            percentageChange  = Math.abs(percentageChange);
            int x = i * (candleWidth + 1);
            int y = (int) ((maxPrice - currentCandle.getClose()) / (maxPrice - minPrice) * getHeight());

            // Выбираем контрастный цвет для процентов, исходя из цвета свечи
            Color textColor = currentCandle.getClose() > currentCandle.getOpen() ? new Color(0, 100, 0) : Color.BLUE;
//            Color textColor = currentCandle.getClose() > currentCandle.getOpen() ? Color.GREEN : Color.BLUE;
            g2d.setColor(textColor);

            // Создаем текст с процентами
            String percentageText = String.format("%.1f%%", percentageChange);
            int textWidth = g2d.getFontMetrics().stringWidth(percentageText);
            int textHeight = g2d.getFontMetrics().getHeight();

            // Располагаем текст так, чтобы он не перекрывал тело свечи
            int yOffset = currentCandle.getClose() > currentCandle.getOpen() ? -textHeight - 2 : textHeight + 2;
            g2d.drawString(percentageText, x, y + yOffset);


            // Расчет и отображение дополнительного процентного изменения относительно средней линии Боллинджера
//            if (Math.abs(percentageChange) > 10 && smaValues.size() > i) {
            if (i == smaValues.size()-2) { // работаем с предпоследней свечей(последняя сформировавщиеся)


                testConsole(smaValues.size()-1); //вывод в консоль - последней сформировавщейся свечи, и последней несвормировавщейся


                Double smaValue = smaValues.get(i-1); // Значение SMA для текущей(предпоследней- посленей сформировавщейся) свечи

                if (smaValue != null) {
                    double bollingerPercentageChange = calculatePercentageChange(smaValue, currentCandle.getClose());

                    // Выбираем оранжевый цвет для текста
                    g2d.setColor(Color.ORANGE);

                    // Располагаем дополнительный текст на смещение в другую сторону
                    int extraOffset = currentCandle.getClose() > currentCandle.getOpen() ? yOffset - 15 : yOffset + 15;
                    g2d.drawString(String.format("%.1f%%", bollingerPercentageChange), x, y + extraOffset);

//                    analizerPatternOne.analizer(currentCandle,smaValue);

                }
            }
        }





        // Находим центральную свечу и её значение
        int centerIndex = candlesticks.size() / 2;
        Svecha centralCandle = candlesticks.get(centerIndex);
        double centralValue = centralCandle.getClose(); // Можно выбрать другое значение

        // Координаты для горизонтальной линии
        int yLine = (int) ((maxPrice - centralValue) / (maxPrice - minPrice) * getHeight());

        // Отрисовка горизонтальной линии
        g2d.setColor(Color.GRAY);
        g2d.drawLine(0, yLine, getWidth(), yLine);

        // Добавление подписи к линии
        String label = String.format("%.2f", centralValue);
        g2d.drawString(label, 5, yLine - 2); // Расположение подписи рядом с линией

        ///////

        // Отрисовка метки для выделенной свечи
        for (int i = 0; i < candlesticks.size(); i++) {
            Svecha candle = candlesticks.get(i);
            if (candle.getOpenTime() == highlightedOpenTime) {
                drawHighlightedMark(g2d, i);
            }
        }
    }
//
//    private void drawHighlightedMark(Graphics2D g2d, int index) {
//        int candleWidth = Math.max((int) (minCandleWidth * scale), minCandleWidth);
//        int x = index * (candleWidth + 1);
//        int y = getHeight() - 20; // Высота, на которой будет стрелка
//
//        g2d.setColor(Color.PINK);
//        g2d.fillPolygon(new int[]{x, x + 10, x - 10}, new int[]{y, y + 10, y + 10}, 3);
        // Отрисовываем стрелку (треугольник) под свечой

    private void drawHighlightedMark(Graphics2D g2d, int index) {
        int candleWidth = Math.max((int) (minCandleWidth * scale), minCandleWidth);
        Svecha candle = candlesticks.get(index);

        int x = index * (candleWidth + 1) + candleWidth / 2; // Центр свечи по оси X
        int y = (int) ((maxPrice - candle.getLow()) / (maxPrice - minPrice) * getHeight()); // Позиция низа свечи по оси Y

        // Поднимаем стрелку чуть выше низа свечи
        int arrowHeight = 10; // Высота стрелки
        int offset = 5; // Дополнительное смещение от низа свечи
        y = y - arrowHeight - offset;

        g2d.setColor(Color.PINK);
        g2d.fillPolygon(new int[]{x, x + 5, x - 5}, new int[]{y, y + arrowHeight, y + arrowHeight}, 3);
        // Отрисовываем стрелку (треугольник) над низом свечи



    ////////////


    }

    private void updatePreferredSize() {
        int width = (int) (candlesticks.size() * (minCandleWidth * scale + 1));
        int height = getHeight();
        setPreferredSize(new Dimension(width, height));
        revalidate(); // Важно: уведомить контейнер о изменении размера компонента
    }


    // Вывод информации о текущей свече в консоль на русском языке
    public void testConsole(int i ){
        // Вывод информации о текущей свече в консоль на русском языке
        Svecha currentCandle = candlesticks.get(i);
//        System.out.println("Свеча " + i + ": Открытие=" + currentCandle.getOpen() + ", Максимум=" + currentCandle.getHigh() +
//                ", Минимум=" + currentCandle.getLow() + ", Закрытие=" + currentCandle.getClose() +
//                ", Цвет=" + (currentCandle.getClose() > currentCandle.getOpen() ? "Зеленый" : "Красный")); // todo последняя свеча отображение не коректно - так как еще не зафиксированно

        Svecha currentCandlePrev = candlesticks.get(i-1); // предыдущая свеча
//        System.out.println("Свеча " + (i-1) + ": Открытие=" + currentCandlePrev.getOpen() + ", Максимум=" + currentCandlePrev.getHigh() +
//                ", Минимум=" + currentCandlePrev.getLow() + ", Закрытие=" + currentCandlePrev.getClose() +
//                ", Цвет=" + (currentCandlePrev.getClose() > currentCandlePrev.getOpen() ? "Зеленый" : "Красный"));
    }

//    public void dobavlenyeSvechiRunTime(Svecha newCandle) {
//        // Добавляем новую свечу в список
//        candlesticks.add(newCandle);
//
//        // Можно также обновить цены закрытия, если они используются для расчетов
//        calculatePriceRange();
//
//        // Обновляем предпочтительный размер компонента, если он зависит от количества свечей
//        updatePreferredSize();
//
//        // Перерисовываем компонент
//        repaint();
//    }

    public void dobavlenyeSvechiRunTime(Svecha newCandle) {
        // Добавляем новую свечу в список свечей
        candlesticks.add(newCandle);

        // Рассчитываем и добавляем данные Боллинджера и SMA для новой свечи
        Double[] newBollingerBands = calculateBollingerBandsForNewCandle(newCandle);
        double newSMA = calculateSMAForNewCandle(newCandle);

        bollingerBands.add(newBollingerBands);
        smaValues.add(newSMA);

        // Обновляем остальные данные и перерисовываем компонент
        calculatePriceRange();
        updatePreferredSize();
        repaint();
    }

    private double calculateSMAForNewCandle(Svecha newCandle) {
        final int period = 22; // Период для расчета SMA
        if (candlesticks.size() >= period) {
            double sum = 0.0;
            for (int i = candlesticks.size() - period; i < candlesticks.size(); i++) {
                sum += candlesticks.get(i).getClose(); // Используем цену закрытия свечи
            }
            return sum / period; // Возвращаем среднее значение
        } else {
            return 0.0; // Если недостаточно данных, возвращаем 0 или другое подходящее значение
        }
    }


    // Этот метод должен быть реализован вами для расчета полос Боллинджера для новой свечи
    private Double[] calculateBollingerBandsForNewCandle(Svecha newCandle) {
        // Ваш метод должен расчитывать SMA и стандартное отклонение на основе всего списка свечей
        // Ниже примерный псевдокод, который нужно адаптировать под вашу логику расчета
        final int period = 22; // примерный период для SMA и стандартного отклонения
        final double k = 2.0; // множитель для стандартного отклонения
        Double[] bollingerBands = new Double[3];

        if (candlesticks.size() >= period) {
            // Расчет SMA
            double sum = 0.0;
            for (int i = candlesticks.size() - period; i < candlesticks.size(); i++) {
                sum += candlesticks.get(i).getClose(); // предполагается, что у Svecha есть метод getClose()
            }
            double sma = sum / period;
//            bollingerBands[1] = sma; // средняя полоса

            // Расчет стандартного отклонения
            double squareSum = 0.0;
            for (int i = candlesticks.size() - period; i < candlesticks.size(); i++) {
                squareSum += Math.pow(candlesticks.get(i).getClose() - sma, 2);
            }
            double standardDeviation = Math.sqrt(squareSum / period);

            // Расчет верхней и нижней полос
            bollingerBands[0] = sma + (standardDeviation * k); // верхняя полоса
            bollingerBands[1] = sma - (standardDeviation * k); // нижняя полоса
        } else {



            // Если свечей недостаточно для расчета, можно возвратить null или заполнить нулями
            return null; // или new Double[]{0.0, 0.0, 0.0};
        }

        return bollingerBands;
    }




}


