package io.bluebeaker.backpackdisplay.utils;

import io.bluebeaker.backpackdisplay.ConfigProvider;

public class NumberUtils {

    public static String getItemCountRepresentation(long number) {
        if (Math.log10(number) < ConfigProvider.getConfig().appearance.full_digits)
            return String.valueOf(number);
        if (number >= 1000000000) {
            return String.format("%.1fG", number / 100000000 / 10.0);
        } else if (number >= 100000000) {
            return String.format("%dM", number / 1000000);
        } else if (number >= 1000000) {
            return String.format("%.1fM", number / 100000 / 10.0);
        } else if (number >= 100000) {
            return String.format("%dk", number / 1000);
        } else if (number >= 1000) {
            return String.format("%.1fk", number / 100 / 10.0);
        } else {
            return String.valueOf(number);
        }
    }

    public static String getFluidCountRepresentation(long number) {
        if (number >= 100000) {
            return getItemCountRepresentation(number / 1000) + "B";
        } else if (number >= 1000) {
            return String.format("%.1fB", number / 100 / 10.0);
        } else {
            return String.valueOf(number)+"mB";
        }
    }
}
