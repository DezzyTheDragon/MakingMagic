/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.client.model.animation;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.animation.Event;
import net.minecraftforge.common.animation.IEventHandler;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Generic {@link TileGameRenderer} that works with the Forge model system and animations.
 */
public class TileEntityRendererAnimation<T extends TileEntity> extends TileEntityRenderer<T> implements IEventHandler<T>
{
    public TileEntityRendererAnimation(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    protected static BlockRendererDispatcher blockRenderer;

    @Override
    public void render(T te, float partialTick, MatrixStack mat, IRenderTypeBuffer renderer, int light, int otherlight)
    {
        LazyOptional<IAnimationStateMachine> cap = te.getCapability(CapabilityAnimation.ANIMATION_CAPABILITY);
        if(!cap.isPresent())
        {
            return;
        }
        if(blockRenderer == null) blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
        BlockPos pos = te.getPos();
        IBlockDisplayReader world = MinecraftForgeClient.getRegionRenderCacheOptional(te.getWorld(), pos)
            .map(IBlockDisplayReader.class::cast).orElseGet(() -> te.getWorld());
        BlockState state = world.getBlockState(pos);
        IBakedModel model = blockRenderer.getBlockModelShapes().getModel(state);
        IModelData data = model.getModelData(world, pos, state, ModelDataManager.getModelData(te.getWorld(), pos));
        if (data.hasProperty(Properties.AnimationProperty))
        {
            @SuppressWarnings("resource")
            float time = Animation.getWorldTime(Minecraft.getInstance().world, partialTick);
            cap
                .map(asm -> asm.apply(time))
                .ifPresent(pair -> {
                    handleEvents(te, time, pair.getRight());

                    // TODO: caching?
                    data.setData(Properties.AnimationProperty, pair.getLeft());
                    blockRenderer.getBlockModelRenderer().renderModel(world, model, state, pos, mat, renderer.getBuffer(Atlases.getSolidBlockType()), false, new Random(), 42, light, data);
                });
        }
    }

    @Override
    public void handleEvents(T te, float time, Iterable<Event> pastEvents) {}
}
