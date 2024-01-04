package com.binance.connector.myyyyyFUTURE.parsery;

import com.binance.connector.myyyyyFUTURE.sushnosty.Svecha;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class SvecnoyParser {

            public static Svecha parseSvecha(JSONObject jsonObj) {

            JSONObject data = jsonObj.getJSONObject("data");
            JSONObject k = data.getJSONObject("k");

            String money = k.getString("s");
            long openTime = k.getLong("t");
            double open = k.getDouble("o");
            double high = k.getDouble("h");
            double low = k.getDouble("l");
            double close = k.getDouble("c");

            Color color = (close >= open) ? Color.GREEN : Color.RED;

            return new Svecha(color,money, openTime, open, high, low, close);
        }
    }


