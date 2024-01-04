package com.binance.connector.myyyyyFUTURE.parsery;

import com.binance.connector.myyyyyFUTURE.suchnostyotservera.acaunt.AccountUpdate;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ParserUserSoceta {

    public static AccountUpdate parseAccountUpdate(String json)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, AccountUpdate.class);
        } catch (IOException e) {
            System.out.println("Есепшен в parseAccountUpdate");
            throw new RuntimeException(e);
        }
    }
}
