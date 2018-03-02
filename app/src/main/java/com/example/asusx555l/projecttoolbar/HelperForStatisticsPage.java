package com.example.asusx555l.projecttoolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HelperForStatisticsPage {

    public int defineDWM(String date) {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date expenseDate = null;
        Date currentDate = new Date();
        try {
            expenseDate = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar currentCalendar = Calendar.getInstance();
        Calendar expenseCalendar = Calendar.getInstance();
        expenseCalendar.setTime(expenseDate);
        int currentYEAR = currentCalendar.get(Calendar.YEAR);
        int expenseYEAR = expenseCalendar.get(Calendar.YEAR);

        assert expenseDate != null;
        long diff = expenseDate.getTime() - currentDate.getTime();
        int countDays = (int) (diff / (24 * 60 * 60 * 1000));

        if (countDays == 0) {
            return 1;
        } else if (currentYEAR == expenseYEAR) {
            if (expenseCalendar.get(Calendar.WEEK_OF_YEAR) == currentCalendar.get(Calendar.WEEK_OF_YEAR)) {
                return 2;
            } else if (expenseCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)) {
                return 3;
            }
        }
        return 0;
    }
}