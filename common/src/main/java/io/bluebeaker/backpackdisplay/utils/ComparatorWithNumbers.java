package io.bluebeaker.backpackdisplay.utils;

import java.util.Comparator;
import java.util.Objects;

public class ComparatorWithNumbers implements Comparator<String> {

    @Override
    public int compare(String arg0, String arg1) {
        String[] spl0 = splitByNumbers(arg0);
        String[] spl1 = splitByNumbers(arg1);
        int length = Math.min(spl0.length, spl1.length);
        for (int i = 0; i < length; i++) {
            if (!Objects.equals(spl0[i], spl1[i])) {
                int result=compareSubString(spl0[i], spl1[i]);
                if(result!=0) return result;
            }
        }
        return 0;
    }

    public int compareSubString(String arg0, String arg1) {
        if (arg0 == arg1)
            return 0;
        int length = Math.min(arg0.length(), arg1.length());
        for (int i = 0; i < length; i++) {
            int result;
            if (isDigit(arg0.charAt(i)) && isDigit(arg1.charAt(i))) {
                result = Integer.signum(Integer.parseInt(arg0) - Integer.parseInt(arg1));
            } else {
                result = arg0.compareTo(arg1);
            }
            if (result != 0)
                return result;
        }
        return 0;
    }

    public boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public String[] splitByNumbers(String arg0) {
        return arg0.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
    }
}
