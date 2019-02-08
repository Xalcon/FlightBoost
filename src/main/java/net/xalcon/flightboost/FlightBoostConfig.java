package net.xalcon.flightboost;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = FlightBoost.MODID)
@Mod.EventBusSubscriber(modid = FlightBoost.MODID)
public class FlightBoostConfig
{
    @Config.RangeInt(min = 0)
    @Config.Comment("Maximum amount of power the basic boost charm can store")
    public static int BasicBoostCharmStorage = 12800;

    @Config.RangeInt(min = 0)
    @Config.Comment("Amount of stored power used per usage, before enchantments")
    public static int BasicBoostCharmPowerPerUse = 100;

    @Config.Comment({
        "The modifier that is applied to the explosion size",
        "For a list of explosion sizes, see https://minecraft.gamepedia.com/Explosion#Explosion_strength",
        "Exmaple: An explosion modifier of 800 will result in the charm being repaired by ~3200 if tnt detonates really close to the charm"
    })
    @Config.RangeDouble(min = 0)
    public static double BasicBoostCharmExplosionRepairModifier = 800.0f;

    @Config.Comment({
        "The maximum explosion size the charm can absorb at once",
        "If the explosion size is higher than this value, the charm will only absorb the difference",
        "This thing only exists so people cannot (easily) cheese huge explosions",
        "Disclaimer: Probably has no affect for DraconicEvolution Explosions"
    })
    @Config.RangeDouble(min = 0)
    public static double BasicBoostCharmMaximumExplosionAbsorbtion = 8;


    @Config.Comment({
        "Maximum efficiency. A value of 1 equals 100% efficiency, effectivly reducing the cost to 0",
        "This value is the maximum efficiency at level 5 of the efficiency enchant."
    })
    @Config.RangeDouble(min = 0, max = 1)
    public static double BoostCharmMaxEfficiency = 0.75;

    @Config.RangeInt(min = 0)
    @Config.Comment("Base cooldown before enchantments in ticks (20 Ticks = 1 sec)")
    public static int BoostCharmBaseCooldown = 150;

    @Config.RangeInt(min = 0)
    @Config.Comment("The amount of cooldown reduction per level in ticks. (Enchantment)")
    public static int CooldownReductionPerLevel = 20;

    @Config.RangeInt(min = 0)
    @Config.Comment("maximum amount of energy the powered charm can store")
    public static int PoweredBoostCharmMaxEnergy = 25000;

    @Config.RangeInt(min = 0)
    @Config.Comment("maximum amount of energy the powered charm can store")
    public static int PoweredBoostCharmEnergyPerBoost = 250;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent event)
    {
        if(event.getModID().equals(FlightBoost.MODID))
        {
            ConfigManager.sync(FlightBoost.MODID, Config.Type.INSTANCE);
        }
    }
}
