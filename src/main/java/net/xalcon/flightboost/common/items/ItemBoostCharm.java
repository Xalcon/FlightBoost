package net.xalcon.flightboost.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.xalcon.flightboost.FlightBoost;
import net.xalcon.flightboost.FlightBoostConfig;
import net.xalcon.flightboost.common.init.ModEnchantments;

public class ItemBoostCharm extends Item
{
    public ItemBoostCharm(String internalName)
    {
        this.setRegistryName(FlightBoost.MODID, internalName);
        this.setTranslationKey(FlightBoost.MODID + "." + internalName);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setNoRepair();
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     *
     * @param stack
     */
    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return super.getTranslationKey(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return enchantment == ModEnchantments.flightDuration
            || enchantment == ModEnchantments.cooldownReduction
            || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public int getItemEnchantability()
    {
        return 3;
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return true;
    }

    protected int getEffectiveCost(ItemStack stack, int baseCost)
    {
        int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("minecraft:efficiency"), stack);
        double efficiency = MathHelper.clamp((FlightBoostConfig.BoostCharmMaxEfficiency * efficiencyLevel) / 5.0, 0, 1);
        return (int) (baseCost - baseCost * efficiency);
    }

    @Override
    public String getHighlightTip(ItemStack item, String displayName)
    {
        // Workaround for the item re-equip animation crap
        // TODO: find a better way to hide the item name everytime the item is used
        if(FlightBoost.proxy.isOnCooldown(item.getItem()))
            return "";
        return super.getHighlightTip(item, displayName);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if (playerIn.isElytraFlying())
        {
            ItemStack heldItem = playerIn.getHeldItem(handIn);
            ItemStack itemstack = heldItem.copy(); // make a copy of the item in case usePower() destroys the itemstack

            if(!this.usePower(heldItem, playerIn, worldIn.isRemote))
                return new ActionResult<>(EnumActionResult.FAIL, heldItem);

            if (!worldIn.isRemote)
            {
                playerIn.getCooldownTracker().setCooldown(this, this.getCooldown(itemstack));
                int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.flightDuration, itemstack);

                NBTTagCompound fireworksNbt = new NBTTagCompound();
                fireworksNbt.setByte("Flight", (byte) level);
                itemstack.setTagInfo("Fireworks", fireworksNbt);
                EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(worldIn, itemstack, playerIn);
                worldIn.spawnEntity(entityfireworkrocket);
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
        }
        else
        {
            return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }
    }

    /**
     * use power
     * @param stack the item stack
     * @param player the player using the charm
     * @return true if there was enough power, false otherwise
     */
    protected boolean usePower(ItemStack stack, EntityPlayer player, boolean simulate)
    {
        return true;
    }

    private int getCooldown(ItemStack heldItem)
    {
        int cdr = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.cooldownReduction, heldItem);
        return Math.max(0, FlightBoostConfig.BoostCharmBaseCooldown - cdr * FlightBoostConfig.CooldownReductionPerLevel);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return false;
    }
}
