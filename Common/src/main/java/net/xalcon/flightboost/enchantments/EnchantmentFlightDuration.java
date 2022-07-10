package net.xalcon.flightboost.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.xalcon.flightboost.items.BoostCharmItem;
import net.xalcon.flightboost.platform.Services;

public class EnchantmentFlightDuration extends Enchantment
{
    public EnchantmentFlightDuration()
    {
        super(Rarity.COMMON, Services.PLATFORM.getBoostCharmEnchantmentCategory(), new EquipmentSlot[]{ EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });
    }

    @Override
    public int getMinCost(int level)
    {
        return 8 + (level - 1) * 9;
    }

    @Override
    public int getMaxCost(int level)
    {
        return super.getMinCost(level) + 50;
    }

    @Override
    public int getMaxLevel()
    {
        return 4;
    }
}
