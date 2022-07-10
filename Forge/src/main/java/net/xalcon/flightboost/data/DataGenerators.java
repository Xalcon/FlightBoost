package net.xalcon.flightboost.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.xalcon.flightboost.Constants;
import net.xalcon.flightboost.data.client.ModItemModelProvider;
import net.xalcon.flightboost.data.common.ModRecipeProvider;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(event.includeClient(), new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(event.includeServer(), new ModRecipeProvider(gen));
    }
}

