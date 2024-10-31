package io.bluebeaker.backpackdisplay;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = BackpackDisplayMod.MODID,type = Type.INSTANCE,category = "general")
public class BPDConfig {
    @Comment("Example")
    @LangKey("config.examplemod.example.name")
    public static String[] displayRules = {
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
        "minecraft:black_shulker_box#list#BlockEntityTag.Items"};
}