package com.example.asusx555l.projecttoolbar.beans;

import java.io.Serializable;

public class Expense implements Serializable {

    public enum Currency {
        USD, EUR, RUB
    }

    private double money;
    private Currency currency;
    private String date;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
