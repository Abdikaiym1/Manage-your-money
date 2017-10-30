package com.example.asusx555l.projecttoolbar.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Expense2 implements Parcelable {

    public enum Currency {
        USD(0), EUR(1), RUB(2);

        int number;
        Currency(int number) {
            this.number = number;
        }

        static Currency getByNum(int num) {
            for (Currency c : values())
                if (c.number == num) return c;
            return null;
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(money);
        parcel.writeInt(currency.number);
        parcel.writeString(date);
    }

    public static final Parcelable.Creator<Expense2> CREATOR = new Creator<Expense2>() {
        @Override
        public Expense2 createFromParcel(Parcel parcel) {
            Expense2 exp = new Expense2();
            exp.setMoney(parcel.readDouble());
            exp.setDate(parcel.readString());
            exp.setCurrency(Currency.getByNum(parcel.readInt()));
            return exp;
        }

        @Override
        public Expense2[] newArray(int size) {
            return new Expense2[size];
        }
    };
}
