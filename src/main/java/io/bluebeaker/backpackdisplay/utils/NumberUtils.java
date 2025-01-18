package io.bluebeaker.backpackdisplay.utils;

import io.bluebeaker.backpackdisplay.BPDConfig;

import java.util.HashSet;
import java.util.Set;

public class NumberUtils {

    public static String getItemCountRepresentation(int number) {
        if (Math.log10(number) < BPDConfig.full_digits)
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

    public static String getFluidCountRepresentation(int number) {
        if (number >= 100000) {
            return getItemCountRepresentation(number / 1000) + "B";
        } else if (number >= 1000) {
            return String.format("%.1fB", number / 100 / 10.0);
        } else {
            return String.valueOf(number)+"mB";
        }
    }

    /**
     * @param metaString Comma-separated list of accepted meta values, may use '-' to define a range.
     * For Example: 1,2,5-9 -> {1,2,5,6,7,8,9}
     * @return A set of acceptable meta values
     */
    public static Set<Integer> parseMeta(String metaString){
        Set<Integer> metadataList = new HashSet<Integer>();
        for (String metaEntry:metaString.split(",")){
            if(metaEntry.contains("-")){
                String[] splitted2=metaEntry.split("-");
                int lower = Integer.parseInt(splitted2[0]);
                int higher = Integer.parseInt(splitted2[1]);
                for(int i = lower;i<higher;i++){
                    metadataList.add(i);
                }
            }else{
                metadataList.add(Integer.parseInt(metaEntry));
            }
        }
        return metadataList;
    }

}
