package com.dezzy.makingmagic.screen;

import com.dezzy.makingmagic.MakingMagic;
import com.dezzy.makingmagic.container.BasicAquaCollectorContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BasicAquaCollectorScreen extends ContainerScreen<BasicAquaCollectorContainer> {
    private final ResourceLocation GUI = new ResourceLocation(MakingMagic.MOD_ID, "textures/gui/basic_collector.png");

    public BasicAquaCollectorScreen(BasicAquaCollectorContainer screenContainer, PlayerInventory pInventory, ITextComponent titleIn) {
        super(screenContainer, pInventory, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f,1f,1f,1f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
    }
}
