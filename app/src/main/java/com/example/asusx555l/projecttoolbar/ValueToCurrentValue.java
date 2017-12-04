package com.example.asusx555l.projecttoolbar;


import android.util.Log;

import java.math.BigDecimal;
import java.util.Objects;

public class ValueToCurrentValue {

    public BigDecimal convetValute(String curValute, BigDecimal[] bigDecimals, String nameMoney) {
        BigDecimal curMoney = null;
        BigDecimal valueEUR = bigDecimals[1];
        BigDecimal valueUSD = bigDecimals[0];
        BigDecimal money = bigDecimals[2];

        if (Objects.equals(curValute, "USD")) {
            if (Objects.equals(nameMoney, "EUR")) {
                curMoney = (money.multiply(valueEUR)).divide(valueUSD, 2, BigDecimal.ROUND_HALF_UP);
            } else if (Objects.equals(nameMoney, "RUB")) {
                curMoney = (money.divide(valueUSD, 2, BigDecimal.ROUND_HALF_UP));
                Log.e("DAte", String.valueOf(curMoney));
            } else curMoney = money;
            return curMoney.stripTrailingZeros();
        } else if (Objects.equals(curValute, "EUR")) {
            if (Objects.equals(nameMoney, "USD")) {
                curMoney = (money.multiply(valueUSD)).divide(valueEUR, 2, BigDecimal.ROUND_HALF_UP);
            } else if (Objects.equals(nameMoney, "RUB")) {
                curMoney = (money.divide(valueUSD, 2, BigDecimal.ROUND_HALF_UP));
            } else curMoney = money;
            return curMoney.stripTrailingZeros();
        } else {
            if (Objects.equals(nameMoney, "USD")) {
                curMoney = money.multiply(valueUSD);
            } else if (Objects.equals(nameMoney, "EUR")) {
                curMoney = money.multiply(valueEUR);
            } else curMoney = money;
            curMoney = curMoney.stripTrailingZeros();
            return curMoney.setScale(2, BigDecimal.ROUND_DOWN);
        }
    }
}
