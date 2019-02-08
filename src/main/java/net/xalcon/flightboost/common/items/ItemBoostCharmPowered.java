package net.xalcon.flightboost.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.xalcon.flightboost.FlightBoost;
import net.xalcon.flightboost.FlightBoostConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBoostCharmPowered extends ItemBoostCharm
{
    public final static String INTERNAL_NAME = "boost_charm_powered";

    public ItemBoostCharmPowered()
    {
        super(INTERNAL_NAME);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
        if(energy == null) return 0;
        return 1.0 - ((double)energy.getEnergyStored() / energy.getMaxEnergyStored());
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            ItemStack stack = new ItemStack(this);
            items.add(stack);

            ItemStack filledStack = stack.copy();
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage instanceof ItemEnergyStorage)
            {
                ((ItemEnergyStorage) storage).setEnergyStored(FlightBoostConfig.PoweredBoostCharmMaxEnergy);
                items.add(filledStack);
            }
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new ICapabilityProvider()
        {
            ItemEnergyStorage storage = new ItemEnergyStorage()
            {
                @Override
                public int getEnergyStored()
                {
                    NBTTagCompound nbt = stack.getTagCompound();
                    return nbt != null ? nbt.getInteger("boost_power") : 0;
                }

                @Override
                public void setEnergyStored(int amount)
                {
                    NBTTagCompound nbt = stack.getTagCompound();
                    if(nbt == null)
                    {
                        stack.setTagCompound(nbt = new NBTTagCompound());
                    }
                    nbt.setInteger("boost_power", MathHelper.clamp(amount, 0, FlightBoostConfig.PoweredBoostCharmMaxEnergy));
                }
            };

            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
            {
                return capability == CapabilityEnergy.ENERGY;
            }

            @Nullable
            @Override
            public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
            {
                return capability == CapabilityEnergy.ENERGY
                    ? CapabilityEnergy.ENERGY.cast(this.storage)
                    : null;
            }
        };
    }

    @Override
    protected boolean usePower(ItemStack stack, EntityPlayer player, boolean simulate)
    {
        if(player.capabilities.isCreativeMode) return true;

        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
        if(!(energy instanceof ItemEnergyStorage)) return false;
        int cost = this.getEffectiveCost(stack, FlightBoostConfig.PoweredBoostCharmEnergyPerBoost);
        int storageAmount = energy.getEnergyStored();
        if(storageAmount > cost)
        {
            if(!simulate)
                ((ItemEnergyStorage) energy).setEnergyStored(storageAmount - cost);
            return true;
        }
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return enchantment == Enchantment.getEnchantmentByLocation("minecraft:efficiency") || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
        if(energy == null) return;
        tooltip.add(new TextComponentTranslation(FlightBoost.MODID + ".charm_powered.energy", energy.getEnergyStored(), energy.getMaxEnergyStored()).getFormattedText());
    }

    private abstract class ItemEnergyStorage implements IEnergyStorage
    {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate)
        {
            int energy = this.getEnergyStored();
            int energyReceived = Math.min(this.getMaxEnergyStored() - energy, Math.min(FlightBoostConfig.PoweredBoostCharmMaxEnergy / 40, maxReceive));
            if(!simulate)
                this.setEnergyStored(energy + energyReceived);
            return energyReceived;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate)
        {
            return 0;
        }

        @Override
        public int getMaxEnergyStored()
        {
            return FlightBoostConfig.PoweredBoostCharmMaxEnergy;
        }

        @Override
        public boolean canExtract()
        {
            return false;
        }

        @Override
        public boolean canReceive()
        {
            return true;
        }

        public abstract void setEnergyStored(int amount);
    }
}
