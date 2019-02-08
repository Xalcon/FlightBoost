package net.xalcon.flightboost.common.init;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.flightboost.FlightBoost;
import net.xalcon.flightboost.common.items.ItemBoostCharm;
import net.xalcon.flightboost.common.items.ItemBoostCharmBasic;
import net.xalcon.flightboost.common.items.ItemBoostCharmPowered;

@Mod.EventBusSubscriber(modid = FlightBoost.MODID)
@GameRegistry.ObjectHolder(FlightBoost.MODID)
public class ModItems
{
    @GameRegistry.ObjectHolder(ItemBoostCharmBasic.INTERNAL_NAME)
    public static ItemBoostCharmBasic boostCharmBasic;
    @GameRegistry.ObjectHolder(ItemBoostCharmPowered.INTERNAL_NAME)
    public static ItemBoostCharmPowered boostCharmPowered;
    @GameRegistry.ObjectHolder("boost_charm_infinite")
    public static ItemBoostCharm boostCharmInfinite;

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(boostCharmBasic = new ItemBoostCharmBasic());
        event.getRegistry().register(boostCharmPowered = new ItemBoostCharmPowered());
        event.getRegistry().register(boostCharmInfinite = new ItemBoostCharm("boost_charm_infinite"));
    }
}
