package com.dezzy.makingmagic.tileentity;

import com.dezzy.makingmagic.item.MakingMagicItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BasicIgnisCollectorTile extends TileEntity implements ITickableTileEntity {
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private final int TICK_RATE = 20;
    private final int LAND_DRUABILITY = 64;
    private final int MAX_TIME = 14 * TICK_RATE;
    private int timePerIgnis = MAX_TIME;
    private int timer = 0;
    private int validCount = 0;
    private int extractCount = 0;

    public  BasicIgnisCollectorTile() {this(MakingMagicTileEntity.BASIC_IGNIS_COLLECTOR_TILE.get());}

    public BasicIgnisCollectorTile(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    public void tick() {
        validCount = 0;
        int x = pos.getX() - 1;
        int y = pos.getY() - 1;
        int z = pos.getZ() - 1;
        BlockPos[] validBlocks = new BlockPos[27];

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                for(int k = 0; k < 3; k++)
                {
                    BlockPos checkBlock = new BlockPos(x, y, z);
                    if(world.getBlockState(checkBlock).getBlock() == Blocks.LAVA)
                    {
                        validBlocks[validCount] = checkBlock;
                        validCount++;
                    }
                    x++;
                }
                x = pos.getX() - 1;
                z++;
            }
            z = pos.getZ() - 1;
            y++;
        }

        if(validCount > 0)
        {
            timePerIgnis = MAX_TIME - (validCount * (TICK_RATE / 2));
            if(timer >= timePerIgnis)
            {
                this.itemHandler.insertItem(0, new ItemStack(MakingMagicItems.RAW_IGNIS.get()), false);
                timer = 0;
                extractCount++;
            }
            if(this.itemHandler.getStackInSlot(0).getCount() < 64)
            {
                timer++;
            }
        }
        if(extractCount >= LAND_DRUABILITY)
        {
            extractCount = 0;
            if(!world.isRemote())
            {
                int min = 0;
                int max = validCount;
                int blockIndex = (int)Math.floor(Math.random() * (max - min + 1) + min);
                Block targetBlock = world.getBlockState(validBlocks[blockIndex]).getBlock();
                world.setBlockState(validBlocks[blockIndex], Blocks.COBBLESTONE.getDefaultState());
            }
        }

    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("ignisColInv"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.put("ignisColInv", itemHandler.serializeNBT());
        return super.write(nbt);
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler(1)
        {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == MakingMagicItems.RAW_IGNIS.get();
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return handler.cast();
        }
        return super.getCapability(cap);
    }
}
