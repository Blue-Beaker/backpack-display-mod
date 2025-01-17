package io.bluebeaker.backpackdisplay;

public class BPDConfig {
    private static final BPDConfig INSTANCE = new BPDConfig();

    public static BPDConfig getInstance(){
        return INSTANCE;
    }

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

    public int tooltipWidth = 9;

    public int tooltipHeight = 4;

    public int full_digits = 4;

    public static enum KeybindType{
        NOT_NEEDED,
        PRESSED,
        RELEASED
    }

    public KeybindType needs_keybind = KeybindType.NOT_NEEDED;

    public int offset_x = 0;
    public int offset_y = -8;

    public float label_scale = 0.5f;

    public boolean verbose_info = false;


    public FluidSection fluidSection = new FluidSection();
    public static class FluidSection{
        public boolean simpleRule = true;
        public boolean simpleRuleBlacklist = false;
        public String[] simpleContainers = {};
    }

    public String[] priorities = {"items:0"};

    public Colors colors = new Colors();

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
            public int alpha = 255;
            public int red = 255;
            public int green = 255;
            public int blue = 255;
            public int getColor(){
                return (alpha<<24)+(red<<16)+(green<<8)+blue;
            }
        }
    }
}