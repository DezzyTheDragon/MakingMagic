package com.dezzy.makingmagic.tileentity;

import com.dezzy.makingmagic.block.MakingMagicBlocks;
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

public class BasicTerraCollectorTile extends TileEntity implements ITickableTileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private final int TICK_RATE = 20;
    private final int MAX_TIME = 14 * TICK_RATE;
    private final int LAND_DURABILITY = 64;
    private int timePerTerra = MAX_TIME;
    private int timer = 0;
    private int validCount = 0;
    private int extractCount = 0;

    public BasicTerraCollectorTile()
    {
        this(MakingMagicTileEntity.BASIC_TERRA_COLLECTOR_TILE.get());
    }

    public BasicTerraCollectorTile(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    public void tick() {
        validCount = 0;
        int x = pos.getX() - 1;
        int y = pos.getY() - 1;
        int z = pos.getZ() - 1;
        BlockPos[] validBlocks = new BlockPos[27];
        //check layer
        for(int i = 0; i < 3; i++)
        {
            //check row
            for(int j = 0; j < 3; j++)
            {
                //check collum
                for(int k = 0; k < 3; k++)
                {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    if(isValidBlock(world.getBlockState(checkPos).getBlock()))
                    {
                        validBlocks[validCount] = checkPos;
                        validCount++;
                    }
                    x++;
                }
                z++;
                x = pos.getX() - 1;
            }
            y++;
            z = pos.getZ() - 1;
        }

        if(validCount > 0)
        {
            timePerTerra = MAX_TIME - (validCount * (TICK_RATE / 2));
            if(timer >= timePerTerra)
            {
                this.itemHandler.insertItem(0, new ItemStack(MakingMagicItems.RAW_TERRA.get()), false);
                timer = 0;
                extractCount++;
            }
            if(this.itemHandler.getStackInSlot(0).getCount() < 64)
            {
                timer++;
            }
        }

        if(extractCount >= LAND_DURABILITY)
        {
            extractCount = 0;
            if(!world.isRemote())
            {
                int min = 0;
                int max = validCount;
                int blockIndex = (int)Math.floor(Math.random() * (max - min + 1) + min);
                Block targetBlock = world.getBlockState(validBlocks[blockIndex]).getBlock();
                world.setBlockState(validBlocks[blockIndex], MakingMagicBlocks.DUST_STONE.get().getDefaultState());
            }
        }

    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("terraColInv"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.put("terraColInv", itemHandler.serializeNBT());
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
                return stack.getItem() == MakingMagicItems.RAW_TERRA.get();
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

    //check helper function
    private boolean isValidBlock(Block targetBlock)
    {
        boolean ret = false;
        //normal blocks
        if(targetBlock == Blocks.STONE || targetBlock == Blocks.GRANITE || targetBlock == Blocks.ANDESITE || targetBlock == Blocks.DIORITE)
        {
            ret = true;
        }
        else if(targetBlock == Blocks.IRON_ORE || targetBlock == Blocks.GOLD_ORE || targetBlock == Blocks.COAL_ORE || targetBlock == Blocks.LAPIS_ORE
        || targetBlock == Blocks.REDSTONE_ORE || targetBlock == Blocks.DIAMOND_ORE || targetBlock == Blocks.EMERALD_ORE)
        {
            ret = true;
        }

        return ret;
    }

}
