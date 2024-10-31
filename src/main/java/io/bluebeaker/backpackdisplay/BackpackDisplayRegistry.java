package io.bluebeaker.backpackdisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntryBase;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntryList;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntrySingle;
import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BackpackDisplayRegistry {
    public static HashMap<Item,List<IDisplaySlotEntry>> registry = new HashMap<Item,List<IDisplaySlotEntry>>();

    public static void updateFromConfig(){
        registry.clear();
        for (String rule : BPDConfig.displayRules){
            addRule(rule);
        }
    }
    public static Set<Integer> parseMetadata(String metadataString){
        Set<Integer> metadataList = new HashSet<Integer>();
        for (String metaEntry:metadataString.split(",")){
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
    public static void addRule(String rule){
        String[] splitted = rule.split("(?<!\\\\)#");
        if(splitted.length<3) return;
        String itemStr = splitted[0].trim();
        String type = splitted[1].trim().toLowerCase();
        String nbtRule = splitted[2].trim();

        String[] itemsplit = itemStr.split(":");
        String modid = itemsplit[0];
        String resourceid = itemsplit[1];
        ResourceLocation itemID = new ResourceLocation(modid, resourceid);
        if(!Item.REGISTRY.containsKey(itemID)) return;
        Item item = Item.REGISTRY.getObject(itemID);
        Set<Integer> metadataList;
        if(itemsplit.length>=3) metadataList=parseMetadata(itemsplit[2]);
        else metadataList=new HashSet<Integer>();

        IDisplaySlotEntry entry = null;
        switch (type) {
            case "dummy":
                entry=new DisplaySlotEntryBase(metadataList);
                break;
            case "single":
                entry=new DisplaySlotEntrySingle(metadataList,nbtRule);
                break;
            case "list":
                entry=new DisplaySlotEntryList(metadataList,nbtRule);
                break;
            default:
                BackpackDisplayMod.logInfo("Unknown item entry type '"+type+"' in rule '"+rule+"'.");
                break;
        }
        if (entry!=null){
            addEntry(item, entry);
        }
    }
    public static void addEntry(Item item, IDisplaySlotEntry entry){
        if(!registry.containsKey(item)){
            registry.put(item, new ArrayList<IDisplaySlotEntry>());
        }
        registry.get(item).add(entry);
    }
}
