package com.dezzy.makingmagic.tileentity;

import com.dezzy.makingmagic.MakingMagic;
import com.dezzy.makingmagic.block.MakingMagicBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MakingMagicTileEntity {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MakingMagic.MOD_ID);

    public static RegistryObject<TileEntityType<BasicFloraCollectorTile>> BASIC_FLORA_COLLECTOR_TILE = TILE_ENTITY.register("basic_flora_collector_tile",
            () -> TileEntityType.Builder.create(BasicFloraCollectorTile::new, MakingMagicBlocks.BASIC_FLORA_COLLECTOR.get()).build(null));
    public static RegistryObject<TileEntityType<BasicTerraCollectorTile>> BASIC_TERRA_COLLECTOR_TILE = TILE_ENTITY.register("basic_terra_collector_tile",
            () -> TileEntityType.Builder.create(BasicTerraCollectorTile::new, MakingMagicBlocks.BASIC_TERRA_COLLECTOR.get()).build(null));
    public static RegistryObject<TileEntityType<BasicIgnisCollectorTile>> BASIC_IGNIS_COLLECTOR_TILE = TILE_ENTITY.register("basic_ignis_collector_tile",
            () -> TileEntityType.Builder.create(BasicIgnisCollectorTile::new, MakingMagicBlocks.BASIC_IGNIS_COLLECTOR.get()).build(null));
    public static RegistryObject<TileEntityType<BasicAquaCollectorTile>> BASIC_AQUA_COLLECTOR_TILE = TILE_ENTITY.register("basic_aqua_collector_tile",
            () -> TileEntityType.Builder.create(BasicAquaCollectorTile::new, MakingMagicBlocks.BASIC_AQUA_COLLECTOR.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        TILE_ENTITY.register(eventBus);
    }
}
