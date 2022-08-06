package com.dezzy.makingmagic.item;

import com.dezzy.makingmagic.MakingMagic;
import com.dezzy.makingmagic.fluid.MakingMagicFluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MakingMagicItems {

    //Item register
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MakingMagic.MOD_ID);


    //Items
    public static final RegistryObject<Item> DUST = ITEMS.register("dust", () -> new Item(new Item.Properties().group(MakingMagicTab.MAKINGMAGIC_GROUP)));
    public static final RegistryObject<Item> RAW_FLORA = ITEMS.register("raw_flora", () -> new Item(new Item.Properties().group(MakingMagicTab.MAKINGMAGIC_GROUP)));
    public static final RegistryObject<Item> RAW_AQUA = ITEMS.register("raw_aqua", () -> new Item(new Item.Properties().group(MakingMagicTab.MAKINGMAGIC_GROUP)));
    public static final RegistryObject<Item> RAW_IGNIS = ITEMS.register("raw_ignis", () -> new Item(new Item.Properties().group(MakingMagicTab.MAKINGMAGIC_GROUP)));
    public static final RegistryObject<Item> RAW_TERRA = ITEMS.register("raw_terra", () -> new Item(new Item.Properties().group(MakingMagicTab.MAKINGMAGIC_GROUP)));
    public static final RegistryObject<Item> CON_FLORA = ITEMS.register("con_flora", () -> new Item(new Item.Properties().group(MakingMagicTab.MAKINGMAGIC_GROUP)));
    public static final RegistryObject<Item> CON_AQUA = ITEMS.register("con_aqua", () -> new Item(new Item.Properties().group(MakingMagicTab.MAKINGMAGIC_GROUP)));
    public static final RegistryObject<Item> CON_IGNIS = ITEMS.register("con_ignis", () -> new Item(new Item.Properties().group(MakingMagicTab.MAKINGMAGIC_GROUP)));
    public static final RegistryObject<Item> CON_TERRA = ITEMS.register("con_terra", () -> new Item(new Item.Properties().group(MakingMagicTab.MAKINGMAGIC_GROUP)));
    public static final RegistryObject<Item> BAD_WATER_BUCKET = ITEMS.register("bad_water_bucket", () -> new BucketItem(() -> MakingMagicFluids.BAD_WATER.get(),
            new Item.Properties().maxStackSize(1).group(MakingMagicTab.MAKINGMAGIC_GROUP)));

    //Register function
    public static void register(IEventBus eventBus)
    {
        //Register our items
        ITEMS.register(eventBus);
    }
}
