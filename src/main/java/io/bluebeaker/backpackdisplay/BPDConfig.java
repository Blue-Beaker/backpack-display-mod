package io.bluebeaker.backpackdisplay;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = BackpackDisplayMod.MODID,type = Type.INSTANCE,category = "general")
public class BPDConfig {
    @Comment({"Rules for getting contained items of container items for the tooltip to display.",
    "Format: '<modID>:<itemID>[:meta]#<rule type>#<rule definition>'",
    "rule type 'single': single item stored in certain path of NBT;",
    "rule type 'list': a list of items stored under a NBTTagList",
    "rule type 'dummy': fixed items intended for testing the display"})
    @LangKey("config.backpackdisplay.displayrules.name")
    public static String[] displayRules = {
        //Vanilla Shulker Boxes
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
        "storagedrawers:basicdrawers#list#BlockEntityTag.Drawers;Item;Count",
        "storagedrawers:customdrawers#list#BlockEntityTag.Drawers;Item;Count",
        "storagedrawers:compdrawers#single#BlockEntityTag.Drawers.Items.0.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.0.Conv",
        "storagedrawers:compdrawers#single#BlockEntityTag.Drawers.Items.1.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.1.Conv",
        "storagedrawers:compdrawers#single#BlockEntityTag.Drawers.Items.2.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.2.Conv",
        //Storage Drawers (Digged)
        "storagedrawers:basicdrawers#list#tile.Drawers;Item;Count",
        "storagedrawers:customdrawers#list#tile.Drawers;Item;Count",
        "storagedrawers:compdrawers#single#tile.Drawers.Items.0.Item;tile.Drawers.Count;/tile.Drawers.Items.0.Conv",
        "storagedrawers:compdrawers#single#tile.Drawers.Items.1.Item;tile.Drawers.Count;/tile.Drawers.Items.1.Conv",
        "storagedrawers:compdrawers#single#tile.Drawers.Items.2.Item;tile.Drawers.Count;/tile.Drawers.Items.2.Conv",
        //Forestry Backpacks
        "forestry:apiarist_bag#list#Slots",
        "forestry:lepidopterist_bag#list#Slots",
        "forestry:miner_bag#list#Slots",
        "forestry:miner_bag_t2#list#Slots",
        "forestry:digger_bag#list#Slots",
        "forestry:digger_bag_t2#list#Slots",
        "forestry:forester_bag#list#Slots",
        "forestry:forester_bag_t2#list#Slots",
        "forestry:hunter_bag#list#Slots",
        "forestry:hunter_bag_t2#list#Slots",
        "forestry:adventurer_bag#list#Slots",
        "forestry:adventurer_bag_t2#list#Slots",
        "forestry:builder_bag#list#Slots",
        "forestry:builder_bag_t2#list#Slots"
    };

    @Comment("Max width of the backpack tooltip, in slots")
    @LangKey("config.backpackdisplay.tooltip_rows.name")
    @Config.RangeInt(min = 1,max = 36)
    public static int tooltipWidth = 9;

    @Comment("Max height of the backpack tooltip, in slots.")
    @LangKey("config.backpackdisplay.tooltip_height.name")
    @Config.RangeInt(min = 1,max = 36)
    public static int tooltipHeight = 4;

    @Comment("The item label will keep this count of digits before using k, M and G prefixes")
    @LangKey("config.backpackdisplay.full_digits.name")
    @Config.RangeInt(min = 3)
    public static int full_digits = 4;

    enum KeybindType{
        NOT_NEEDED,
        PRESSED,
        RELEASED
    }

    @Comment("Whether a keybind is needed to show the container tooltip.")
    @LangKey("config.backpackdisplay.needs_keybind.name")
    public static KeybindType needs_keybind = KeybindType.NOT_NEEDED;
    

    @Comment("Offset of the backpack display")
    @LangKey("config.backpackdisplay.offset_x.name")
    public static int offset_x = 0;
    @Comment("Offset of the backpack display")
    @LangKey("config.backpackdisplay.offset_y.name")
    public static int offset_y = -8;

    @Comment("Scale of the count label in display when number is too long")
    @LangKey("config.backpackdisplay.label_scale.name")
    public static float label_scale = 0.5f;

    @Comment("Show verbose info when loading mod. Useful for writing new rules.")
    @LangKey("config.backpackdisplay.verbose_info.name")
    public static boolean verbose_info = false;

    public static Colors colors = new Colors();

    public static class Colors{
        // public static int backgroundColor = 0xF0100010;
        // public static int borderColorStart = 0x505000FF;
        public Color backgroundColor = new Color(0xF0100010);
        public Color borderColor = new Color(0x505000FF);
        public static class Color{
            public Color(int color){
                alpha=color>>24&255;
                red=color>>16&255;
                green=color>>8&255;
                blue=color&255;
            }
            @Config.SlidingOption
            @Config.RangeInt(min = 0,max = 255)
            public int alpha = 255;
            @Config.SlidingOption
            @Config.RangeInt(min = 0,max = 255)
            public int red = 255;
            @Config.SlidingOption
            @Config.RangeInt(min = 0,max = 255)
            public int green = 255;
            @Config.SlidingOption
            @Config.RangeInt(min = 0,max = 255)
            public int blue = 255;
            public int getColor(){
                return (alpha<<24)+(red<<16)+(green<<8)+blue;
            }
        }
    }
}