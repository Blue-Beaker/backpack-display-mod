package io.bluebeaker.backpackdisplay.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static final Pattern patternTemplate = Pattern.compile("\\{([^\\\\{}]*?)}");

    public static List<String> fillInTemplates(String input){
        return fillInTemplatesRecursive(input,10);
    }
    public static List<String> fillInTemplatesRecursive(String input,int maxDepth){
        if(maxDepth<=0)
            return Collections.singletonList(input);
        Matcher matcher = patternTemplate.matcher(input);
        if(!matcher.find())
            return Collections.singletonList(input);

        List<String> result = new ArrayList<>();
        for (String s : matcher.group(1).split(",")) {
            String replaced = matcher.replaceFirst(s);
            result.addAll(fillInTemplatesRecursive(replaced, maxDepth-1));
        }
        return result;
    }
}
