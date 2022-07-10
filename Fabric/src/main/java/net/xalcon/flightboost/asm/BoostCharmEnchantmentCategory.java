package net.xalcon.flightboost.asm;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.xalcon.flightboost.items.BoostCharmItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

public class BoostCharmEnchantmentCategory extends EnchantmentCategoryMixin
{
    @Override
    public boolean canEnchant(Item item)
    {
        return item instanceof BoostCharmItem;
    }
}

@Mixin(EnchantmentCategory.class)
abstract class EnchantmentCategoryMixin
{
    @Shadow
    public abstract boolean canEnchant(Item item);
}