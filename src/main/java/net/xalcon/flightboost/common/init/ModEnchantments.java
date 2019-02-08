package net.xalcon.flightboost.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.flightboost.FlightBoost;
import net.xalcon.flightboost.common.enchantments.EnchantmentBoostCooldownReduction;
import net.xalcon.flightboost.common.enchantments.EnchantmentFlightDuration;
import net.xalcon.flightboost.common.items.ItemBoostCharmBasic;

@Mod.EventBusSubscriber(modid = FlightBoost.MODID)
@GameRegistry.ObjectHolder(FlightBoost.MODID)
public class ModEnchantments
{
    public static EnumEnchantmentType ENCHANT_TYPE_BOOST_CHARM = EnumHelper.addEnchantmentType("boost_charm", i -> i instanceof ItemBoostCharmBasic);

    @GameRegistry.ObjectHolder("flight_duration")
    public static EnchantmentFlightDuration flightDuration;
    @GameRegistry.ObjectHolder("boost_cooldown_reduction")
    public static EnchantmentBoostCooldownReduction cooldownReduction;

    @SubscribeEvent
    public static void onRegisterEnchants(RegistryEvent.Register<Enchantment> event)
    {
        event.getRegistry().register(flightDuration = new EnchantmentFlightDuration());
        event.getRegistry().register(cooldownReduction = new EnchantmentBoostCooldownReduction());
    }
}
