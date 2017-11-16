package com.example.asusx555l.projecttoolbar.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class Expense implements Serializable {

    public static final String KEY = "Expense";

    public enum Currency {
        USD, EUR, RUB
    }

    private BigDecimal money;
    private Currency currency;
    private String date;
    private boolean spend;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
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

    public boolean isSpend() {
        return spend;
    }

    public void setSpend(boolean spend) {
        this.spend = spend;
    }
}
