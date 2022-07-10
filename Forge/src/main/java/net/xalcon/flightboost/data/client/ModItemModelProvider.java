package net.xalcon.flightboost.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.xalcon.flightboost.Constants;
import net.xalcon.flightboost.ModRegistry;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        this.singleTexture(ModRegistry.itemBoostCharm.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/charm1"));
        this.singleTexture(ModRegistry.itemChargedBoostCharm.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/charm3"));
        this.singleTexture(ModRegistry.itemEnergeticBoostCharm.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/charm2"));
    }

}
