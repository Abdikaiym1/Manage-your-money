package com.example.asusx555l.projecttoolbar;


import android.util.Log;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public class ValueToCurrentValue {

    private static final String USD = "USD";
    private static final String EUR = "EUR";
    private static final String RUB = "RUB";

    public BigDecimal convertValute(String curValute, BigDecimal[] bigDecimals, String nameMoney) {
        BigDecimal curMoney = null;
        BigDecimal valueEUR = bigDecimals[1];
        BigDecimal valueUSD = bigDecimals[0];
        BigDecimal money = bigDecimals[2];

        if (Objects.equals(curValute, USD)) {
            if (Objects.equals(nameMoney, EUR)) {
                curMoney = (money.multiply(valueEUR)).divide(valueUSD, 2, BigDecimal.ROUND_HALF_UP);
            } else if (Objects.equals(nameMoney, RUB)) {
                curMoney = (money.divide(valueUSD, 2, BigDecimal.ROUND_HALF_UP));
            } else curMoney = money;
            return curMoney.stripTrailingZeros();
        } else if (Objects.equals(curValute, EUR)) {
            if (Objects.equals(nameMoney, USD)) {
                curMoney = (money.multiply(valueUSD)).divide(valueEUR, 2, BigDecimal.ROUND_HALF_UP);
            } else if (Objects.equals(nameMoney, RUB)) {
                curMoney = (money.divide(valueEUR, 2, BigDecimal.ROUND_HALF_UP));
            } else curMoney = money;
            return curMoney.stripTrailingZeros();
        } else {
            if (Objects.equals(nameMoney, USD)) {
                curMoney = money.multiply(valueUSD);
            } else if (Objects.equals(nameMoney, EUR)) {
                curMoney = money.multiply(valueEUR);
            } else curMoney = money;
            curMoney = curMoney.stripTrailingZeros();
            return curMoney.setScale(2, BigDecimal.ROUND_DOWN);
        }
    }

    public BigDecimal[] convertValuteRadioButtonSpend(String curValute, String nameMoney, BigDecimal[] dym, BigDecimal[] vauteMoney) {
        BigDecimal[] curMoney = new BigDecimal[dym.length];

        BigDecimal valueEUR = vauteMoney[1];
        BigDecimal valueUSD = vauteMoney[0];

        switch (curValute) {
            case USD:
                switch (nameMoney) {
                    case EUR:
                        for (int i = 0; i < dym.length; i++) {
                            curMoney[i] = (dym[i].multiply(valueUSD)).divide(valueEUR, 2, BigDecimal.ROUND_HALF_UP);
                            curMoney[i].stripTrailingZeros();
                        }
                        break;
                    case RUB:
                        for (int i = 0; i < dym.length; i++) {
                            curMoney[i] = dym[i].multiply(valueUSD).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                        break;
                }
                return curMoney;
            case EUR:
                switch (nameMoney) {
                    case USD:
                        for (int i = 0; i < dym.length; i++) {
                            curMoney[i] = (dym[i].multiply(valueEUR)).divide(valueUSD, 2, BigDecimal.ROUND_HALF_UP);
                            curMoney[i].stripTrailingZeros();
                        }
                        break;
                    case RUB:
                        for (int i = 0; i < dym.length; i++) {
                            curMoney[i] = (dym[i].multiply(valueEUR)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                        break;
                }
                return curMoney;
            default:
                switch (nameMoney) {
                    case USD:
                        for (int i = 0; i < dym.length; i++) {
                            curMoney[i] = dym[i].divide(valueUSD, 2, BigDecimal.ROUND_HALF_UP);
                            curMoney[i].stripTrailingZeros();
                        }
                        break;
                    case EUR:
                        for (int i = 0; i < dym.length; i++) {
                            curMoney[i] = dym[i].divide(valueEUR, 2, BigDecimal.ROUND_HALF_UP);
                            curMoney[i].stripTrailingZeros();
                        }
                        break;
                }
                return curMoney;
        }
    }
}
