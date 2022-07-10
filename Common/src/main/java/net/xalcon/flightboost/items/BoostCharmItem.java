package net.xalcon.flightboost.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.xalcon.flightboost.FlightBoost;
import net.xalcon.flightboost.ModRegistry;
import net.xalcon.flightboost.platform.Services;

public class BoostCharmItem extends Item
{
    public BoostCharmItem(Properties properties)
    {
        super(properties.stacksTo(1));
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack)
    {
        return true;
    }

    @Override
    public int getEnchantmentValue()
    {
        return 10;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack)
    {
        return UseAnim.NONE;
    }

    @Override
    public boolean canBeHurtBy(DamageSource damageSource)
    {
        return !damageSource.isExplosion();
    }

    protected boolean usePower(ItemStack itemStack)
    {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        if(player.isFallFlying())
        {
            var heldItem = player.getItemInHand(hand);


            if(!level.isClientSide)
            {
                if(!usePower(heldItem))
                    return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));

                player.getCooldowns().addCooldown(this, getCoolDown(heldItem));

                var fakeRocket = new ItemStack(Items.FIREWORK_ROCKET);
                fakeRocket.getOrCreateTagElement("Fireworks").putByte("Flight", (byte)getFlightLevel(heldItem));
                FireworkRocketEntity rocket = new FireworkRocketEntity(level, fakeRocket, player);
                level.addFreshEntity(rocket);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
    }

    private int getCoolDown(ItemStack itemStack)
    {
        int lvl = EnchantmentHelper.getItemEnchantmentLevel(ModRegistry.enchantmentCoolDownReduction.get(), itemStack);
        return Math.max(0, Services.PLATFORM.getConfig().getBoostCharmBaseCooldown() - lvl * Services.PLATFORM.getConfig().getBoostCharmCdrPerLevel());
    }

    private int getFlightLevel(ItemStack itemStack)
    {
        return EnchantmentHelper.getItemEnchantmentLevel(ModRegistry.enchantmentFlightDuration.get(), itemStack);
    }
}
