package com.example.care2u.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PrescriptionOrder {

    private String start_order_date;
    private String estimated_delivery_date;
    private String address;
    private String orderID;
    private boolean paid;

    public PrescriptionOrder() {

        this.orderID = generateOrderID();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String start_order_date = formatter.format(date);
        this.start_order_date = start_order_date;


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        Date estimated_date = calendar.getTime();
        String estimated_delivery_date = formatter.format(estimated_date);
        this.estimated_delivery_date = estimated_delivery_date;

        this.paid = false;
    }

    public String getStart_order_date() {
        return start_order_date;
    }

    public void setStart_order_date(String start_order_date) {
        this.start_order_date = start_order_date;
    }

    public String getEstimated_delivery_date() {
        return estimated_delivery_date;
    }

    public void setEstimated_delivery_date(String estimated_delivery_date) {
        this.estimated_delivery_date = estimated_delivery_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }


    public String generateOrderID(){
        Random random = new Random();
        StringBuilder orderID = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            int r = random.nextInt(2);
            if (r == 0) {
                orderID.append(random.nextInt(10));
            } else {
                orderID.append((char)(random.nextInt(26) + 'A'));
            }
        }
        return String.valueOf(orderID);
    }
}
