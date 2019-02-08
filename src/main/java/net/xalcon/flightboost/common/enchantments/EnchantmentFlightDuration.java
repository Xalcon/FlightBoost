package net.xalcon.flightboost.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.xalcon.flightboost.FlightBoost;
import net.xalcon.flightboost.common.init.ModEnchantments;

public class EnchantmentFlightDuration extends Enchantment
{
    public EnchantmentFlightDuration()
    {
        super(Rarity.COMMON, ModEnchantments.ENCHANT_TYPE_BOOST_CHARM, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
        this.setRegistryName(FlightBoost.MODID, "flight_duration");
        this.setName("flight_duration");
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    @Override
    public int getMaxLevel()
    {
        return 4;
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     *
     * @param enchantmentLevel
     */
    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 1 + (enchantmentLevel - 1) * 11;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     *
     * @param enchantmentLevel
     */
    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 11;
    }
}
