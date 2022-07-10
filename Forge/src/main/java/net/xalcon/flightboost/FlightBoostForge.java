package net.xalcon.flightboost;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.xalcon.flightboost.item.EnergeticBoostCharmItem;
import net.xalcon.flightboost.items.ChargedBoostCharmItem;

@Mod(Constants.MOD_ID)
public class FlightBoostForge
{
    public FlightBoostForge() {
        FlightBoost.init();

        ModRegistry.itemEnergeticBoostCharm = ModRegistry.ITEMS.register("energetic_boost_charm", () ->
                new EnergeticBoostCharmItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

        MinecraftForge.EVENT_BUS.addListener(this::onExplosionDetonation);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FlightBoostConfigForge.spec);
    }

    private void onExplosionDetonation(ExplosionEvent.Detonate event)
    {
        var entities = event.getAffectedEntities();
        var items = entities
                .stream()
                .filter(e -> e instanceof ItemEntity item && item.getItem().getItem() instanceof ChargedBoostCharmItem)
                .map(e -> (ItemEntity)e)
                .toList();
        if(items.size() > 0)
        {
            event.getAffectedEntities().clear();
            event.getAffectedBlocks().clear();
            var r = event.getExplosion().radius;

            var explosionLocation = event.getExplosion().getPosition();
            items.forEach(entity -> {
                var itemStack = entity.getItem();
                var item = (ChargedBoostCharmItem)itemStack.getItem();
                item.onNearbyExplosion(itemStack, entity.position(), explosionLocation, r);
            });
        }
    }
}