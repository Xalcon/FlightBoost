package net.xalcon.flightboost.data.common;


import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.xalcon.flightboost.Constants;
import net.xalcon.flightboost.ModRegistry;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder
{
    public ModRecipeProvider(DataGenerator gen)
    {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder
                .shaped(ModRegistry.itemChargedBoostCharm.get(), 1)
                .pattern("grg")
                .pattern("rEr")
                .pattern("grg")
                .define('E', Items.EMERALD)
                .define('g', Tags.Items.GLASS)
                .define('r', Items.FIREWORK_ROCKET)
                .unlockedBy("has_rockets", has(Items.FIREWORK_ROCKET))
                .save(consumer, new ResourceLocation(Constants.MOD_ID, "charged_boostcharm1"));

        ShapedRecipeBuilder
                .shaped(ModRegistry.itemEnergeticBoostCharm.get(), 1)
                .pattern(" r ")
                .pattern("rCr")
                .pattern(" r ")
                .define('C', ModRegistry.itemBoostCharm.get())
                .define('r', Items.REDSTONE)
                .unlockedBy("has_rockets", has(Items.FIREWORK_ROCKET))
                .save(consumer, new ResourceLocation(Constants.MOD_ID, "energetic_boostcharm1"));
    }
}

