package net.xalcon.flightboost.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.xalcon.flightboost.Constants;
import net.xalcon.flightboost.FlightBoost;
import net.xalcon.flightboost.platform.Services;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChargedBoostCharmItem extends BoostCharmItem
{
    public ChargedBoostCharmItem(Properties properties)
    {
        super(properties);
    }

    public void onNearbyExplosion(ItemStack itemStack, Vec3 location, Vec3 explosionLocation, float radius)
    {
        // The strength is stronger the closer the item is to explosion center
        // bigger radius explosions also increase the strength
        // a radius of 10 with a distance of 5 would yield a strength of 5
        // a radius of 5 with a distance of 0 would yield a strength of 5
        // a radius of 5 with a distance of 5 would yield a strength of 0
        var strength = radius - location.distanceTo(explosionLocation);
        Constants.LOG.info("Explosion with strength {} absorbed", strength);

        var MAX_CHARGE = Services.PLATFORM.getConfig().getChargedBoostCharmMaxCharge();
        var charge = itemStack.getOrCreateTag().getInt("charge") + strength * Services.PLATFORM.getConfig().getChargedBoostCharmExplosionChargeMultiplier();
        if(charge > MAX_CHARGE)
            charge = MAX_CHARGE;
        itemStack.getTag().putInt("charge", (int)charge);
    }

    @Override
    protected boolean usePower(ItemStack itemStack)
    {
        var charge = itemStack.getOrCreateTag().getInt("charge");
        if(charge <= 0)
            return false;
        charge -= Services.PLATFORM.getConfig().getChargedBoostCharmChargePerUse();
        if(charge < 0)
            charge = 0;
        itemStack.getTag().putInt("charge", charge);
        return true;
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack)
    {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack itemStack)
    {
        return (int) (13 * ((itemStack.getOrCreateTag().getInt("charge")) / (float)Services.PLATFORM.getConfig().getChargedBoostCharmMaxCharge()));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        super.appendHoverText(itemStack, level, components, flag);
        var charge = itemStack.getOrCreateTag().getInt("charge");
        components.add(Component.translatable("item.flightboost.charged_boost_charm.tooltip", charge));
    }
}
