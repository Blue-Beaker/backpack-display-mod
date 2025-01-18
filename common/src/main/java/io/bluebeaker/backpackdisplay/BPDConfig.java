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
                "minecraft:{white,orange,magenta,light_blue,yellow,lime,pink,gray,silver,cyan,purple,blue,brown,gree,red,black}_shulker_box#list#BlockEntityTag.Items",

                //Storage Drawers (Creative ctrl+pick block)
                "storagedrawers:{oak,birch,jungle,spruce,acacia,dark_oak,mangrove,cherry,bamboo,crimson,warped,framed}_{full,half}_drawers_{1,2,4}#list#{BlockEntityTag,tile}.Drawers;Item;Count",

                //Compacting drawers
                "storagedrawers:compacting_drawers_{2,3}#single#BlockEntityTag.Drawers.Items.0.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.0.Conv",
                "storagedrawers:compacting_drawers_{2,3}#single#BlockEntityTag.Drawers.Items.1.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.1.Conv",
                "storagedrawers:compacting_drawers_3#single#BlockEntityTag.Drawers.Items.2.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.2.Conv",

                "storagedrawers:compacting_drawers_{2,3}#single#tile.Drawers.Items.0.Item;tile.Drawers.Count;/tile.Drawers.Items.0.Conv",
                "storagedrawers:compacting_drawers_{2,3}#single#tile.Drawers.Items.1.Item;tile.Drawers.Count;/tile.Drawers.Items.1.Conv",
                "storagedrawers:compacting_drawers_3#single#tile.Drawers.Items.2.Item;tile.Drawers.Count;/tile.Drawers.Items.2.Conv",
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
    public String[] priorities = {"items:0","fluid:-1"};

    public boolean verbose_info = false;
}