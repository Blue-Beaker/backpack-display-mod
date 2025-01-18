package io.bluebeaker.backpackdisplay;

import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntryBase;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntryList;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntrySingle;
import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import io.bluebeaker.backpackdisplay.utils.NumberUtils;
import io.bluebeaker.backpackdisplay.utils.StringUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class BPDRegistryItems {
    public static HashMap<Item,List<IDisplaySlotEntry>> registry = new HashMap<Item,List<IDisplaySlotEntry>>();

    public static void updateFromConfig(){
        registry.clear();
        for (String rule : BPDConfig.displayRules){
            for (String filledRule : StringUtils.fillInTemplates(rule)) {
                try {
                    addRule(filledRule);
                } catch (Exception e) {
                    BackpackDisplayMod.logError("Error when processing rule '"+filledRule+"': \n"+e.toString());
                }
            }
        }
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
        if(item==null)
            return;
        Set<Integer> metadataList;

        if(itemsplit.length>=3) metadataList=NumberUtils.parseMeta(itemsplit[2]);
        else metadataList=new HashSet<Integer>();

        IDisplaySlotEntry entry = buildEntryFromStringRule(type, nbtRule, metadataList);

        addEntry(item, entry);
        if(BPDConfig.verbose_info)
            BackpackDisplayMod.logInfo("Adding entry with "+item.toString()+"type:"+type+", entry: "+entry.toString());
    }
    public static IDisplaySlotEntry buildEntryFromStringRule(String type,String nbtRule,Set<Integer> metadataList){
        IDisplaySlotEntry entry;
        switch (type) {
            case "dummy":
                entry=new DisplaySlotEntryBase(metadataList,nbtRule);
                break;
            case "single":
                entry=new DisplaySlotEntrySingle(metadataList,nbtRule);
                break;
            case "list":
                entry=new DisplaySlotEntryList(metadataList,nbtRule);
                break;
            default:
                BackpackDisplayMod.logInfo("Unknown item entry type '"+type+"'.");
                entry=new DisplaySlotEntryBase(metadataList,nbtRule);
                break;
        }

        return entry;
    }
    public static void addEntry(Item item, IDisplaySlotEntry entry){
        if(!registry.containsKey(item)){
            registry.put(item, new ArrayList<IDisplaySlotEntry>());
        }
        registry.get(item).add(entry);
    }
}
