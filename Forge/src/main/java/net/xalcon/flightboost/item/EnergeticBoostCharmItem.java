package net.xalcon.flightboost.item;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.xalcon.flightboost.items.BoostCharmItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnergeticBoostCharmItem extends BoostCharmItem
{
    private static int PoweredBoostCharmEnergyPerBoost = 32;

    public EnergeticBoostCharmItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isBarVisible(ItemStack p_150899_)
    {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack)
    {
        var optional = stack.getCapability(CapabilityEnergy.ENERGY, null).resolve();

        if(optional.isEmpty()) return 0;
        var energy = optional.get();
        return (int) (13 * (1.0 - ((double)energy.getEnergyStored() / energy.getMaxEnergyStored())));
    }

    @Override
    protected boolean usePower(ItemStack itemStack)
    {
        var optional = itemStack.getCapability(CapabilityEnergy.ENERGY, null).resolve();
        if(optional.isEmpty()) return false;
        var energy = optional.get();
        int cost = PoweredBoostCharmEnergyPerBoost;
        int storageAmount = energy.getEnergyStored();
        if(storageAmount > cost)
        {
            ((ItemEnergyStorage) energy).setEnergyStored(storageAmount - cost);
            return true;
        }
        return false;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        return new ICapabilityProvider()
        {
            ItemEnergyStorage storage = new ItemEnergyStorage()
            {
                private static int PoweredBoostCharmMaxEnergy = 4 * 1024;
                @Override
                public void setEnergyStored(int amount)
                {
                    stack.getOrCreateTag().putInt("charge", Mth.clamp(amount, 0, PoweredBoostCharmMaxEnergy));
                }

                @Override
                public int getEnergyStored()
                {
                    var tag = stack.getOrCreateTag();
                    return tag.getInt("charge");
                }
            };

            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
            {
                return CapabilityEnergy.ENERGY.orEmpty(cap, LazyOptional.of(() -> storage));
            }
        };
    }

    private abstract class ItemEnergyStorage implements IEnergyStorage
    {
        private static int PoweredBoostCharmMaxEnergy = 4 * 1024;

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate)
        {
            int energy = this.getEnergyStored();
            int energyReceived = Math.min(this.getMaxEnergyStored() - energy, Math.min(PoweredBoostCharmMaxEnergy / 40, maxReceive));
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
            return PoweredBoostCharmMaxEnergy;
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
