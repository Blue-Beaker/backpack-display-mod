package io.bluebeaker.backpackdisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntryDummy;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntryList;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntrySingle;
import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;

public class BackpackDisplayRegistry {
    public static HashMap<String,List<IDisplaySlotEntry>> registry = new HashMap<String,List<IDisplaySlotEntry>>();
    
    public static void updateFromConfig(){
        registry.clear();
        for (String rule : BPDConfig.vanillalikeRules){
            addRule(rule);
        }
    }
    public static void addRule(String rule){
        String[] splitted = rule.split("(?<!\\\\)#");
        if(splitted.length<3) return;
        String item = splitted[0].trim();
        String type = splitted[1].trim().toLowerCase();
        String nbtRule = splitted[2].trim();
        IDisplaySlotEntry entry = null;
        switch (type) {
            case "dummy":
                entry=new DisplaySlotEntryDummy();
                break;
            case "single":
                entry=new DisplaySlotEntrySingle(nbtRule);
                break;
            case "list":
                entry=new DisplaySlotEntryList(nbtRule);
                break;
            default:
                BackpackDisplayMod.logInfo("Unknown item entry type '"+type+"' in rule '"+rule+"'.");
                break;
        }
        if (entry!=null){
            addEntry(item, entry);
        }
    }
    public static void addEntry(String item, IDisplaySlotEntry entry){
        if(!registry.containsKey(item)){
            registry.put(item, new ArrayList<IDisplaySlotEntry>());
        }
        registry.get(item).add(entry);
    }
}
