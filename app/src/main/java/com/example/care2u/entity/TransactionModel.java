package com.example.care2u.entity;

public class TransactionModel {
    private String transaction_amount,testing;

    public TransactionModel() {
    }

    public TransactionModel(String transaction_amount,String testing) {
        this.transaction_amount = transaction_amount;
        this.testing=testing;
    }

    public String getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(String transaction_amount) {
        this.transaction_amount = transaction_amount;
    }
}
