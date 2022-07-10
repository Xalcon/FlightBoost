package net.xalcon.flightboost;

import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.xalcon.flightboost.enchantments.EnchantmentBoostCoolDownReduction;
import net.xalcon.flightboost.enchantments.EnchantmentFlightDuration;
import net.xalcon.flightboost.items.BoostCharmItem;
import net.xalcon.flightboost.items.ChargedBoostCharmItem;
import net.xalcon.flightboost.platform.registration.RegistrationProvider;
import net.xalcon.flightboost.platform.registration.RegistryObject;

public class ModRegistry
{
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registry.ITEM_REGISTRY, Constants.MOD_ID);
    public static final RegistrationProvider<Enchantment> ENCHANTENTS = RegistrationProvider.get(Registry.ENCHANTMENT_REGISTRY, Constants.MOD_ID);

    public static final RegistryObject<EnchantmentFlightDuration> enchantmentFlightDuration =
            ENCHANTENTS.register("flight_duration", EnchantmentFlightDuration::new);
    public static final RegistryObject<EnchantmentBoostCoolDownReduction> enchantmentCoolDownReduction =
            ENCHANTENTS.register("cool_down_reduction", EnchantmentBoostCoolDownReduction::new);

    public static final RegistryObject<BoostCharmItem> itemBoostCharm = ITEMS.register("infinity_boost_charm", () ->
            new BoostCharmItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<BoostCharmItem> itemChargedBoostCharm = ITEMS.register("charged_boost_charm", () ->
            new ChargedBoostCharmItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static RegistryObject<BoostCharmItem> itemEnergeticBoostCharm;

    public static void initialize()
    {
    }
}
