package net.xalcon.flightboost.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.xalcon.flightboost.FlightBoost;
import net.xalcon.flightboost.FlightBoostConfig;
import net.xalcon.flightboost.common.init.ModItems;
import net.xalcon.flightboost.common.utils.ReflectionUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = FlightBoost.MODID)
public class ItemBoostCharmBasic extends ItemBoostCharm
{
    public final static String INTERNAL_NAME = "boost_charm_basic";

    public ItemBoostCharmBasic()
    {
        super(INTERNAL_NAME);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            ItemStack stack = new ItemStack(this);
            items.add(stack);
            ItemStack filledStack = stack.copy();
            this.setStorageAmount(filledStack, FlightBoostConfig.BasicBoostCharmStorage);
            items.add(filledStack);
        }
    }

    private int getStorageAmount(ItemStack stack)
    {
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt == null)
        {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        return nbt.getInteger("boost_storage");
    }

    private void setStorageAmount(ItemStack stack, int amount)
    {
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt == null)
        {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        nbt.setInteger("boost_storage", MathHelper.clamp(amount, 0, FlightBoostConfig.BasicBoostCharmStorage));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1.0 - ((double)getStorageAmount(stack) / FlightBoostConfig.BasicBoostCharmStorage);
    }

    @Override
    protected boolean usePower(ItemStack stack, EntityPlayer player, boolean simulate)
    {
        if(player.capabilities.isCreativeMode) return true;
        int cost = this.getEffectiveCost(stack, FlightBoostConfig.BasicBoostCharmPowerPerUse);
        int storageAmount = this.getStorageAmount(stack);
        if(storageAmount > cost)
        {
            if(!simulate)
                this.setStorageAmount(stack, (storageAmount - cost));
            return true;
        }
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        return FlightBoostConfig.BasicBoostCharmStorage;
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
        if(flagIn.isAdvanced())
            tooltip.add(new TextComponentTranslation(FlightBoost.MODID + ".charm_basic.power", this.getStorageAmount(stack), FlightBoostConfig.BasicBoostCharmStorage).getFormattedText());
    }

    public void chargeFromExplosion(ItemStack stack, double size)
    {
        int stored = this.getStorageAmount(stack);
        int add = (int) (size * FlightBoostConfig.BasicBoostCharmExplosionRepairModifier);
        this.setStorageAmount(stack, stored + add);
    }

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event)
    {
        Entity exploder = ReflectionUtils.getExplosionExploder(event.getExplosion());
        if(exploder instanceof EntityItem && ((EntityItem) exploder).getItem().getItem() == ModItems.boostCharmBasic)
            return;

        List<EntityItem> entities = event.getAffectedEntities().stream()
            .filter(e -> e instanceof EntityItem && ((EntityItem) e).getItem().getItem() == ModItems.boostCharmBasic)
            .map(e -> (EntityItem)e)
            .collect(Collectors.toList());

        if(entities.isEmpty()) return;

        Explosion explosion = event.getExplosion();
        float size = ReflectionUtils.getExplosionSize(explosion);
        float sizePerCharm = (float) Math.min(size / entities.size(), FlightBoostConfig.BasicBoostCharmMaximumExplosionAbsorbtion);

        entities.forEach(itemEntity ->
        {
            event.getAffectedBlocks().clear();
            event.getAffectedEntities().clear();
            ItemStack itemStack = itemEntity.getItem();

            ModItems.boostCharmBasic.chargeFromExplosion(itemStack, sizePerCharm);
        });

        float maxAbsorb = (float) (FlightBoostConfig.BasicBoostCharmMaximumExplosionAbsorbtion * entities.size());
        if(size > maxAbsorb)
        {
            final float delta = size - maxAbsorb;
            final Entity e = entities.get(0);
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() ->
            {
                e.world.createExplosion(e, e.posX, e.posY, e.posZ, delta, true);
            });
        }
    }
}
