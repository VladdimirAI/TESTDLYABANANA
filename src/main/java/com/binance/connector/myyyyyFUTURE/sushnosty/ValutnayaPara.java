package com.binance.connector.myyyyyFUTURE.sushnosty;

public class ValutnayaPara {
    String symbol;
    String tickSize;
    String stepSize;
    public int cifrPosleZapytoyDlyaLotaVoVTOROYMONETE;
    public int cifrPriPokupkePoslezapatouCENAvUSDT;

    public ValutnayaPara(String symbol, String tickSize, String stepSize) {
        if (symbol.equals("ACHUSDT")) {
            System.out.println();
        }
        this.symbol = symbol;
        this.tickSize = tickSize;
        this.stepSize = stepSize;
        this.cifrPosleZapytoyDlyaLotaVoVTOROYMONETE = calculatePrecisionStep();
        this.cifrPriPokupkePoslezapatouCENAvUSDT = calculatePrecisionTiket();
    }

    private int calculatePrecisionStep() {
        // Удаляем нули с конца строки, чтобы получить точное количество значащих цифр
        stepSize = stepSize.indexOf(".") < 0 ? stepSize : stepSize.replaceAll("0*$", "").replaceAll("\\.$", "");
        String[] parts = stepSize.split("\\.");
        return parts.length > 1 ? parts[1].length() : 0;
    }

    private int calculatePrecisionTiket() {
        // Удаляем нули с конца строки, чтобы получить точное количество значащих цифр
        tickSize = tickSize.indexOf(".") < 0 ? tickSize : tickSize.replaceAll("0*$", "").replaceAll("\\.$", "");
        String[] parts = tickSize.split("\\.");
        return parts.length > 1 ? parts[1].length() : 0;
    }

    @Override
    public String toString() {
        return "ValutnayaPara{" +
                "symbol='" + symbol + '\'' +
                ", tickSize='" + tickSize + '\'' +
                ", stepSize='" + stepSize + '\'' +
                ", cifrPosleZapytoyDlyaLota=" + cifrPosleZapytoyDlyaLotaVoVTOROYMONETE +
                ", cifrPriPokupkePoslezapatou=" + cifrPriPokupkePoslezapatouCENAvUSDT +
                '}';
    }
}
