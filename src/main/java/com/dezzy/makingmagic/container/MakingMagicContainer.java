package com.dezzy.makingmagic.container;

import com.dezzy.makingmagic.MakingMagic;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MakingMagicContainer {
    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MakingMagic.MOD_ID);

    public static final RegistryObject<ContainerType<BasicCollectorContainer>> BASIC_FLORA_COLLECTOR_CONTAINER = CONTAINERS.register("basic_flora_collector_container",
            () -> IForgeContainerType.create(((windowId, inv, data) ->
            {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new BasicCollectorContainer(windowId, world, pos, inv, inv.player);
            }
            )));

    public static final RegistryObject<ContainerType<BasicTerraCollectorContainer>> BASIC_TERRA_COLLECTOR_CONTAINER = CONTAINERS.register("basic_terra_collector_container",
            () -> IForgeContainerType.create(((windowId, inv, data) ->
            {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new BasicTerraCollectorContainer(windowId, world, pos, inv, inv.player);
            }
            )));

    public static final RegistryObject<ContainerType<BasicIgnisCollectorContainer>> BASIC_IGNIS_COLLECTOR_CONTAINER = CONTAINERS.register("basic_ignis_collector_container",
            () -> IForgeContainerType.create(((windowId, inv, data) ->
            {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new BasicIgnisCollectorContainer(windowId, world, pos, inv, inv.player);
            }
            )));

    public static final RegistryObject<ContainerType<BasicAquaCollectorContainer>> BASIC_AQUA_COLLECTOR_CONTAINER = CONTAINERS.register("basic_aqua_collector_container",
            () -> IForgeContainerType.create(((windowId, inv, data) ->
            {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new BasicAquaCollectorContainer(windowId, world, pos, inv, inv.player);
            }
            )));

    public static void register(IEventBus eventBus)
    {
        CONTAINERS.register(eventBus);
    }
}
