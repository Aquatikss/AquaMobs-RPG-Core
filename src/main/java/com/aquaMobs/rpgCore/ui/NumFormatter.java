package com.aquaMobs.rpgCore.ui;

import java.text.DecimalFormat;

public class NumFormatter {

    public static String formatCommas(double value) {
        DecimalFormat df = new DecimalFormat("#,###,###,###");
        return df.format(value);
    }

    public static String format(long num) {

        String disp;

        String toNum;

        byte displacement;

        String s = String.valueOf(num);

        if (s.length() >= 4 && s.length() <= 6) {
            disp = "K";
        } else if (s.length() >= 7 && s.length() <= 9) {
            disp = "M";
        } else if (s.length() >= 10 && s.length() <= 12) {
            disp = "B";
        } else if (s.length() >= 13 && s.length() <= 15) {
            disp = "T";
        } else if (s.length() >= 16 && s.length() <= 18) {
            disp = "QA";
        } else if (s.length() >= 19) {
            disp = "QT";
        } else {
            disp = "";
        }

        if (s.length() == 4 || s.length() == 7 || s.length() == 10 || s.length() == 13 || s.length() == 16 || s.length() == 19) {
            displacement = 1;
        } else if (s.length() == 5 || s.length() == 8 || s.length() == 11 || s.length() == 14 || s.length() == 17) {
            displacement = 2;
        } else if (s.length() == 6 || s.length() == 9 || s.length() == 12 || s.length() == 15 || s.length() == 18) {
            displacement = 3;
        } else {
            displacement = 0;
        }

        if (displacement != 0) {
            toNum = "";
            for (int i = 0; i < displacement+2; i++) {

                if (i == displacement) {
                    toNum += ".";
                }

                toNum += String.valueOf(s.charAt(i));

            }
        } else {
            toNum = String.valueOf(num);
        }

        if (displacement != 0) {
            while (toNum.endsWith("0")) {
                toNum = toNum.substring(0, toNum.length()-1);
            }
        }

        if (toNum.endsWith(".")) {
            toNum = toNum.substring(0, toNum.length()-1);
        }

        return toNum + disp;

    }

}