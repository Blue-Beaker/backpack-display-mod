package io.bluebeaker.backpackdisplay.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.util.IngredientMap;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author Blue_Beaker
 */
@ZenClass("mods.backpackdisplay.BackpackDisplayFluid")
@ZenRegister

public class BackpackDisplayFluidCT {
    private static final IngredientMap<IContainerFunctionFluid> PREVIEW_FUNCTIONS = new IngredientMap<>();

    
    /**
     * @param ingredient IIngredient representing items to add tooltip for
     * @param function Function to get displayed items from the container item: IItemStack->IItemStack[]
     */
    @ZenMethod
    public static void addBackDisplay(IIngredient ingredient, IContainerFunctionFluid function) {
        CraftTweakerAPI.apply(new AddBackpackDisplayAction(ingredient, function));
    }

    public static List<IContainerFunctionFluid> getFunctions(IItemStack item) {
        return PREVIEW_FUNCTIONS.getEntries(item);
    }

    /**
     * @param item IItemstack to get items from
     * @return list of IItemstack
     */
    public static List<ILiquidStack> getDisplayFluids(IItemStack item) {
        List<ILiquidStack> items = new ArrayList<ILiquidStack>();
        for(IContainerFunctionFluid func:getFunctions(item)){
            for(ILiquidStack stack : func.process(item)){
                items.add(stack);
            }
        }
        return items;
    }

    private static class AddBackpackDisplayAction implements IAction {

        private final IIngredient ingredient;
        private final IContainerFunctionFluid function;
        
        public AddBackpackDisplayAction(IIngredient ingredient, IContainerFunctionFluid function) {
            this.ingredient = ingredient;
            this.function = function;
        }
        
        @Override
        public void apply() {
            PREVIEW_FUNCTIONS.register(ingredient, function);
        }
        
        
        @Override
        public String describe() {
            return "Adding backpack display fluid for " + ingredient;
        }
    }
}