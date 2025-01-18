package io.bluebeaker.backpackdisplay;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = BackpackDisplayMod.MOD_ID)
public class BPDConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public Appearance appearance = new Appearance();

    public static class Appearance {

        public int tooltipWidth = 9;

        public int tooltipHeight = 4;

        public int full_digits = 4;

        public int offset_x = 0;
        public int offset_y = -8;

        public float label_scale = 0.5f;

        @ConfigEntry.Gui.CollapsibleObject
        public Color backgroundColor = new Color(0xF0100010);
        @ConfigEntry.Gui.CollapsibleObject
        public Color borderColor = new Color(0x505000FF);

        public static class Color{
            public Color(int color){
                alpha=color>>24&255;
                red=color>>16&255;
                green=color>>8&255;
                blue=color&255;
            }
            @ConfigEntry.BoundedDiscrete(min = 0L,max = 255L)
            public int alpha = 255;
            @ConfigEntry.BoundedDiscrete(min = 0L,max = 255L)
            public int red = 255;
            @ConfigEntry.BoundedDiscrete(min = 0L,max = 255L)
            public int green = 255;
            @ConfigEntry.BoundedDiscrete(min = 0L,max = 255L)
            public int blue = 255;
            public int getColor(){
                return (alpha<<24)+(red<<16)+(green<<8)+blue;
            }
        }
    }

    @ConfigEntry.Gui.CollapsibleObject
    public ItemSection itemSection = new ItemSection();
    public static class ItemSection{
        public String[] displayRules = {
                //Vanilla Shulker Boxes
                "minecraft:shulker_box#list#BlockEntityTag.Items",
                "minecraft:white_shulker_box#list#BlockEntityTag.Items",
                "minecraft:orange_shulker_box#list#BlockEntityTag.Items",
                "minecraft:magenta_shulker_box#list#BlockEntityTag.Items",
                "minecraft:light_blue_shulker_box#list#BlockEntityTag.Items",
                "minecraft:yellow_shulker_box#list#BlockEntityTag.Items",
                "minecraft:lime_shulker_box#list#BlockEntityTag.Items",
                "minecraft:pink_shulker_box#list#BlockEntityTag.Items",
                "minecraft:gray_shulker_box#list#BlockEntityTag.Items",
                "minecraft:silver_shulker_box#list#BlockEntityTag.Items",
                "minecraft:cyan_shulker_box#list#BlockEntityTag.Items",
                "minecraft:purple_shulker_box#list#BlockEntityTag.Items",
                "minecraft:blue_shulker_box#list#BlockEntityTag.Items",
                "minecraft:brown_shulker_box#list#BlockEntityTag.Items",
                "minecraft:green_shulker_box#list#BlockEntityTag.Items",
                "minecraft:red_shulker_box#list#BlockEntityTag.Items",
                "minecraft:black_shulker_box#list#BlockEntityTag.Items",
                //Storage Drawers (Creative ctrl+pick block)
                "storagedrawers:oak_full_drawers_1#list#BlockEntityTag.Drawers;Item;Count",
                "storagedrawers:oak_full_drawers_2#list#BlockEntityTag.Drawers;Item;Count",
                "storagedrawers:oak_full_drawers_4#list#BlockEntityTag.Drawers;Item;Count",

                "storagedrawers:birch_full_drawers_1#list#BlockEntityTag.Drawers;Item;Count",
                "storagedrawers:birch_full_drawers_2#list#BlockEntityTag.Drawers;Item;Count",
                "storagedrawers:birch_full_drawers_4#list#BlockEntityTag.Drawers;Item;Count",

                "storagedrawers:jungle_full_drawers_1#list#BlockEntityTag.Drawers;Item;Count",
                "storagedrawers:jungle_full_drawers_2#list#BlockEntityTag.Drawers;Item;Count",
                "storagedrawers:jungle_full_drawers_4#list#BlockEntityTag.Drawers;Item;Count",
                
                "storagedrawers:spruce_full_drawers_1#list#BlockEntityTag.Drawers;Item;Count",
                "storagedrawers:spruce_full_drawers_2#list#BlockEntityTag.Drawers;Item;Count",
                "storagedrawers:spruce_full_drawers_4#list#BlockEntityTag.Drawers;Item;Count",

                //Compacting drawers
                "storagedrawers:compacting_drawers_2#single#BlockEntityTag.Drawers.Items.0.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.0.Conv",
                "storagedrawers:compacting_drawers_2#single#BlockEntityTag.Drawers.Items.1.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.1.Conv",

                "storagedrawers:compacting_drawers_3#single#BlockEntityTag.Drawers.Items.0.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.0.Conv",
                "storagedrawers:compacting_drawers_3#single#BlockEntityTag.Drawers.Items.1.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.1.Conv",
                "storagedrawers:compacting_drawers_3#single#BlockEntityTag.Drawers.Items.2.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.2.Conv",
                //Storage Drawers (Digged)

                //Forestry Backpacks [Disabled, forestry haven't updated yet]
//                "forestry:apiarist_bag#list#Slots",
//                "forestry:lepidopterist_bag#list#Slots",
//                "forestry:miner_bag#list#Slots",
//                "forestry:miner_bag_t2#list#Slots",
//                "forestry:digger_bag#list#Slots",
//                "forestry:digger_bag_t2#list#Slots",
//                "forestry:forester_bag#list#Slots",
//                "forestry:forester_bag_t2#list#Slots",
//                "forestry:hunter_bag#list#Slots",
//                "forestry:hunter_bag_t2#list#Slots",
//                "forestry:adventurer_bag#list#Slots",
//                "forestry:adventurer_bag_t2#list#Slots",
//                "forestry:builder_bag#list#Slots",
//                "forestry:builder_bag_t2#list#Slots"
        };
    }


    @ConfigEntry.Gui.CollapsibleObject
    public FluidSection fluidSection = new FluidSection();
    public static class FluidSection{
        public boolean simpleRule = true;
        public boolean simpleContainerListIsBlacklist = false;
        public String[] simpleContainerList = {};
    }

    public KeybindType keybindRequirement = KeybindType.NOT_NEEDED;
    public static enum KeybindType{
        NOT_NEEDED,
        PRESSED,
        RELEASED
    }
    public String[] priorities = {"items:0","fluids:-1"};

    public boolean verbose_info = false;
}