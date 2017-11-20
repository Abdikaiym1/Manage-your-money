package com.example.asusx555l.projecttoolbar;

public class StringToIntegerDate {
    private int codeMonth[] = {1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6};

    public int getDay(String Date) {
        int end = 0;
        for (int i = 0; i < Date.length(); i++) {
            if (Date.charAt(i) == '-') {
                end = i;
                break;
            }
        }
        return Integer.parseInt(Date.substring(0, end));
    }

    public int getMonth(String Date) {
        int code[] = new int[2];
        int count = 0;
        for (int i = 0; i < Date.length(); i++) {
            if (Date.charAt(i) == '-') {
                code[count] = i;
                count++;
            }
        }
        return Integer.parseInt(Date.substring(code[0] + 1, code[1]));
    }

    public int getYear(String Date) {
        int begin = 0;
        for (int i = Date.length() - 1; i >= 0; i--) {
            if (Date.charAt(i) == '-') {
                begin = i;
                break;
            }
        }
        return Integer.parseInt(Date.substring(begin + 1, Date.length()));
    }

    public int dayOfWeek(String Date) {
        int dmy[] = {getDay(Date), getMonth(Date), getYear(Date)};
        int vYear = dmy[2];

        if (dmy[2] % 4 != 0) {
            while (vYear % 4 != 0) {
                vYear = vYear - 1;
            }
        }

        int codeYear = ((6 + (vYear % 100) + (vYear % 100) / 4) % 7) + (dmy[2] - vYear);
        int res = ((codeYear + codeMonth[dmy[1]] + dmy[0]) % 7);

        if (dmy[2] % 4 == 0 && (dmy[1] == 0 || dmy[1] == 1)) {
            res = ((codeYear + codeMonth[dmy[1]] + dmy[0] - 1) % 7);
        }

        return res;
    }
}
