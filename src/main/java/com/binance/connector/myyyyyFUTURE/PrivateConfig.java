package com.binance.connector.myyyyyFUTURE;

public final class PrivateConfig {
    private PrivateConfig() {
    }
//    public static final String BASE_URL = "https://testnet.binance.vision";

//    https://fapi.binance.com






    public static final String TESTNET_API_KEY = "7b0ec2bb8ef88dd7f8e6c3ab26e3ad53013658565a8d6d9bd16353bb1d130cb5";
    public static final String TESTNET_SECRET_KEY = "da9b0ccee6681685e85cdf5ffc13e4e8a896165d7937ee60299a6f633dad8cb1"; // Unnecessary if TESTNET_PRIVATE_KEY_PATH is used
    public static final String TESTNET_PRIVATE_KEY_PATH = ""; //Key must be PKCS#8 standard





    public static final String MONEY_FILE = "C:\\USDT пары .txt";
    public static final String TIMENG = "15m";
    //    public static final String TIMENG = "15m";
//    public static final String INFOOCYFRAHPOSLEZAPYTOY = "C:\\parsedPairsInfoFuture.txt";
    public static final String INFOOCYFRAHPOSLEZAPYTOY = "C:\\parsedPairsInfoMONEYFUTURE.txt";

    public static final int BOLIDJERPERIOD = 22;
    public static final int NACHALNUYBALANS = 100;
    public static final int NA_ODIN_ORDER_V_USDT = 6;


    public static final double STOPLOSSVPROCENTAH = 0.35; //todo 0.35 = 35%
    public static final double STOPLIMIDOSKOLKIUBYTOK = 0.37;



    public static final int RASPARALELIVANYE_POTOKOV = 10; //todo


    public static final int USLOVIEVIHODACOLSVECHEYINAMESTE = 4;
    public static final int USLOVIYAVIHODAPROCENTSCITAEMUYNAMESTE = 3;

    public static final int USLOVIYAVIHODAPODRYDZELENYH = 3;

    public static final int SLMINIMCHTOBEGONETROGAT = 5; // todo можно больще так как все ради TP
    public static final int TPMINIMUMCHOBEGONETROGAT = 2;


    public static final int PRIEMLEMUYPROCENTDOBOLINJERA = 10;
    public static final double OTSKOLKIRAZHVOSTDOLGHENBITBOLSHETRENDA = 0.8;
    public static final double DOSKOLKIRAZHVOSTDOLGHENBITBOLSHETRENDA = 2.5;

    public static final double PERESTANOVKATPPROCENTDOBAVKIKSMA = 2.0;








//    Следуйте инструкциям официальной документации Spot API Key, чтобы заменить URL-адреса конечных точек API следующими значениями:
//    Spot API URL
//    Spot Test Network URL
//    https://api.binance.com/api	https://testnet.binance.vision/api
//    wss://stream.binance.com:9443/ws	wss://testnet.binance.vision/ws
//    wss://stream.binance.com:9443/stream	wss://testnet.binance.vision/stream

//    Spot API URL	Spot Test Network URL
//    https://api.binance.com/api	https://testnet.binance.vision/api
//    wss://ws-api.binance.com/ws-api/v3	wss://testnet.binance.vision/ws-api/v3
//    wss://stream.binance.com:9443/ws	wss://testnet.binance.vision/ws
//    wss://stream.binance.com:9443/stream	wss://testnet.binance.vision/stream
}
