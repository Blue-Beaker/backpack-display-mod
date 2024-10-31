package io.bluebeaker.backpackdisplay;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = BackpackDisplayMod.MODID,type = Type.INSTANCE,category = "general")
public class BPDConfig {
    @Comment("Example")
    @LangKey("config.examplemod.example.name")
    public static String[] displayRules = {};
}