package net.xalcon.flightboost.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.xalcon.flightboost.FlightBoost;
import net.xalcon.flightboost.common.init.ModItems;

@Mod.EventBusSubscriber(modid = FlightBoost.MODID)
public class ModelEventHandler
{
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        ModelLoader.setCustomModelResourceLocation(ModItems.boostCharmBasic, 0, new ModelResourceLocation(ModItems.boostCharmBasic.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.boostCharmPowered, 0, new ModelResourceLocation(ModItems.boostCharmPowered.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.boostCharmInfinite, 0, new ModelResourceLocation(ModItems.boostCharmInfinite.getRegistryName(), "inventory"));
    }
}
