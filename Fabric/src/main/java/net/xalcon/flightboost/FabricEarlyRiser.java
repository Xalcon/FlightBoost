package net.xalcon.flightboost;

import com.chocohead.mm.api.ClassTinkerers;

public class FabricEarlyRiser implements Runnable
{
    @Override
    public void run()
    {
        var builder = ClassTinkerers.enumBuilder("net.minecraft.world.item.enchantment.EnchantmentCategory");
        builder.addEnumSubclass("BOOST_CHARM", "net.xalcon.flightboost.asm.BoostCharmEnchantmentCategory");
        builder.build();
    }
}