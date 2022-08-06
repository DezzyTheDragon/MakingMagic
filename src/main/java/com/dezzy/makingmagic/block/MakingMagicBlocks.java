package com.dezzy.makingmagic.block;

import com.dezzy.makingmagic.MakingMagic;
import com.dezzy.makingmagic.item.MakingMagicItems;
import com.dezzy.makingmagic.item.MakingMagicTab;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class MakingMagicBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MakingMagic.MOD_ID);

    //basic blocks
    public static final RegistryObject<Block> DUST_GRASS = registerBlock("dust_grass",
            () -> new Block(AbstractBlock.Properties.create(Material.SAND).hardnessAndResistance(0.1f).sound(SoundType.SAND)));
    public static final RegistryObject<Block> DUST_BLOCK = registerBlock("dust_block",
            () -> new Block(AbstractBlock.Properties.create(Material.SAND).hardnessAndResistance(0.1f).sound(SoundType.SAND)));
    public static final RegistryObject<Block> DUST_OAK = registerBlock("dust_oak",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.SAND).hardnessAndResistance(0.1f).sound(SoundType.SAND)));
    public static final RegistryObject<Block> DUST_STONE = registerBlock("dust_stone",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.SAND).hardnessAndResistance(0.1f).sound(SoundType.SAND)));
    /*
    public static final RegistryObject<Block> MAGIC_BRICK = registerBlock("magic_brick",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6).sound(SoundType.STONE).harvestLevel(0).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    */

    //Basic collectors
    public static final RegistryObject<Block> BASIC_FLORA_COLLECTOR = registerBlock("basic_flora_collector",
            () -> new BasicFloraCollectorBlock(AbstractBlock.Properties.create(Material.IRON)));
    public static final RegistryObject<Block> BASIC_AQUA_COLLECTOR = registerBlock("basic_aqua_collector",
            () -> new BasicAquaCollectorBlock(AbstractBlock.Properties.create(Material.IRON)));
    public static final RegistryObject<Block> BASIC_IGNIS_COLLECTOR = registerBlock("basic_ignis_collector",
            () -> new BasicIgnisCollectorBlock(AbstractBlock.Properties.create(Material.IRON)));
    public static final RegistryObject<Block> BASIC_TERRA_COLLECTOR = registerBlock("basic_terra_collector",
            () -> new BasicTerraCollectorBlock(AbstractBlock.Properties.create(Material.IRON)));
    //portal structure blocks
    public static final RegistryObject<Block> PORTAL_CORE = registerBlock("portal_core", () -> new Block(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> PORTAL_INPUT = registerBlock("portal_input", () -> new PortalInputBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> PORTAL_BLOCK = registerBlock("portal_block", () -> new Block(AbstractBlock.Properties.create(Material.ROCK)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> ret = BLOCKS.register(name, block);
        registerBlockItem(name, ret);
        return ret;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block)
    {
        MakingMagicItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(MakingMagicTab.MAKINGMAGIC_GROUP)));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
