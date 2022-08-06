package com.dezzy.makingmagic.fluid;

import com.dezzy.makingmagic.MakingMagic;
import com.dezzy.makingmagic.block.MakingMagicBlocks;
import com.dezzy.makingmagic.item.MakingMagicItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.swing.*;

public class MakingMagicFluids {

    public static final ResourceLocation WATER_STILL_RE = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RE = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RE = new ResourceLocation("block/water_overlay");

    public static DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MakingMagic.MOD_ID);

    public static final RegistryObject<FlowingFluid> BAD_WATER = FLUIDS.register("bad_water", () -> new ForgeFlowingFluid.Source(MakingMagicFluids.BAD_WATER_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BAD_WATER_FLOWING = FLUIDS.register("bad_water_flowing", () -> new ForgeFlowingFluid.Flowing(MakingMagicFluids.BAD_WATER_PROPERTIES));

    public static final ForgeFlowingFluid.Properties BAD_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(() -> BAD_WATER.get(),
            () -> BAD_WATER_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RE, WATER_FLOWING_RE).color(0xBF090909).sound(SoundEvents.BLOCK_LAVA_AMBIENT).overlay(WATER_OVERLAY_RE))
            .slopeFindDistance(4).levelDecreasePerBlock(3).block(() -> MakingMagicFluids.BAD_WATER_BLOCK.get()).bucket(() -> MakingMagicItems.BAD_WATER_BUCKET.get());

    public static final RegistryObject<FlowingFluidBlock> BAD_WATER_BLOCK = MakingMagicBlocks.BLOCKS.register("bad_water", () -> new FlowingFluidBlock(
            () -> MakingMagicFluids.BAD_WATER.get(), AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100f).noDrops()));

    public static void register(IEventBus eventBus)
    {
        FLUIDS.register(eventBus);
    }
}
