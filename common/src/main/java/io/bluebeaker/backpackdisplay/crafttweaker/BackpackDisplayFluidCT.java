package io.bluebeaker.backpackdisplay.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.util.IngredientMap;
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
    public static List<IFluidStack> getDisplayFluids(IItemStack item) {
        List<IFluidStack> items = new ArrayList<IFluidStack>();
        for(IContainerFunctionFluid func:getFunctions(item)){
            for(IFluidStack stack : func.process(item)){
                items.add(stack);
            }
        }
        return items;
    }
    
    public static class AddBackpackDisplayAction implements IAction {

        private final IIngredient ingredient;
        private final IContainerFunctionFluid function;
        private IngredientMap.IngredientMapEntry<IContainerFunctionFluid> entry;
        
        public AddBackpackDisplayAction(IIngredient ingredient, IContainerFunctionFluid function) {
            this.ingredient = ingredient;
            this.function = function;
        }
        
        @Override
        public void apply() {
            entry = PREVIEW_FUNCTIONS.register(ingredient, function);
        }

        public void undo(){
            PREVIEW_FUNCTIONS.unregister(entry);
        }

        @Override
        public String describe() {
            return "Adding backpack display fluid for " + ingredient;
        }
    }
}
