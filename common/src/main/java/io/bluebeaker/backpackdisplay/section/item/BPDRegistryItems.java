package io.bluebeaker.backpackdisplay.section.item;

import io.bluebeaker.backpackdisplay.ConfigProvider;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntryBase;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntryList;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntrySingle;
import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import io.bluebeaker.backpackdisplay.utils.ItemUtils;
import io.bluebeaker.backpackdisplay.utils.NumberUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.*;

public class BPDRegistryItems {
    public static HashMap<Item,List<IDisplaySlotEntry>> registry = new HashMap<Item,List<IDisplaySlotEntry>>();

    public static void updateFromConfig(){
        registry.clear();
        for (String rule : ConfigProvider.getConfig().itemSection.displayRules){
            try {
                addRule(rule);
            } catch (Exception e) {
                BackpackDisplayMod.logError("Error when processing rule '"+rule+"': \n"+e.toString());
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
        Item item = ItemUtils.getItemFromID(itemID);
        if(item== Items.AIR) return;
        Set<Integer> metadataList;

        if(itemsplit.length>=3) metadataList=NumberUtils.parseMeta(itemsplit[2]);
        else metadataList=new HashSet<Integer>();

        IDisplaySlotEntry entry = buildEntryFromStringRule(type, nbtRule, metadataList);

        addEntry(item, entry);
        if(ConfigProvider.getConfig().verbose_info)
            BackpackDisplayMod.logInfo("Adding entry with "+item.toString()+"type:"+type+", entry: "+entry.toString());
    }
    public static IDisplaySlotEntry buildEntryFromStringRule(String type, String nbtRule, Set<Integer> metadataList){
        IDisplaySlotEntry entry = switch (type) {
            case "single" -> new DisplaySlotEntrySingle(metadataList, nbtRule);
            case "list" -> new DisplaySlotEntryList(metadataList, nbtRule);
            default -> new DisplaySlotEntryBase(metadataList, nbtRule);
        };

        return entry;
    }
    public static void addEntry(Item item, IDisplaySlotEntry entry){
        if(!registry.containsKey(item)){
            registry.put(item, new ArrayList<IDisplaySlotEntry>());
        }
        registry.get(item).add(entry);
    }
}
