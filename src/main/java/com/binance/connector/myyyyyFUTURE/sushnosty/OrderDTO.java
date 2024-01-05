package com.binance.connector.myyyyyFUTURE.sushnosty;

public class OrderDTO {
   public String symbol;            // Символ  ////////////////////////////////////////////////////

   public long orderId ;                // Идентификатор ордера  ///////////////////////////////////////////

   public String side;                // Сторона (покупка/продажа)   /////////////////////////////////

   public double originalQuantity;   // Исходное количество   ///////////////////////////////////////////

   public String orderStatus;        // Статус ордера  ////////////////////////////////////////////////

   public String tip;



    public OrderDTO(String symbol, String side, double originalQuantity, String orderStatus, long orderId) {
        this.symbol = symbol;
        this.side = side;
        this.originalQuantity = originalQuantity;
        this.orderStatus = orderStatus;
        this.orderId = orderId;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
