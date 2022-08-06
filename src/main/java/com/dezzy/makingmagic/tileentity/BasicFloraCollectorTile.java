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


public class BasicFloraCollectorTile extends TileEntity implements ITickableTileEntity{

    //handles entity inventory
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private final int TICK_RATE = 20;
    private final int MAX_TIME = 14 * TICK_RATE;
    private final int LAND_DURABILITY = 64;
    private int timePerFlora = MAX_TIME;
    private int timer = 0;
    private int validCount = 0;
    private int extractCount = 0;

    public BasicFloraCollectorTile()
    {
        this(MakingMagicTileEntity.BASIC_FLORA_COLLECTOR_TILE.get());
    }

    public BasicFloraCollectorTile(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    public void tick() {
        //if the timer reaches the max time add another raw flora to the inventory
        //start in a bottom corner
        validCount = 0;
        int x = pos.getX() - 1;
        int y = pos.getY() - 1;
        int z = pos.getZ() - 1;
        BlockPos[] validBlocks = new BlockPos[27];
        //check layer
        for (int i = 0; i < 3; i++) {
            //check row
            for (int j = 0; j < 3; j++) {
                //check collum
                for(int k = 0; k < 3; k++)
                {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    if (blockIsValidLog(world.getBlockState(checkPos).getBlock()) || blockIsValidLeaf(world.getBlockState(checkPos).getBlock())
                            || blockIsValidGrass(world.getBlockState(checkPos).getBlock())) {
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
        //add flora to collector
        if (validCount > 0) {
            timePerFlora = MAX_TIME - (validCount * (TICK_RATE / 2));
            if (timer >= timePerFlora) {
                this.itemHandler.insertItem(0, new ItemStack(MakingMagicItems.RAW_FLORA.get()), false);
                extractCount++;
                timer = 0;
            }
            //if there is a full stack in the collector stop adding to the timer
            if (this.itemHandler.getStackInSlot(0).getCount() < 64) {
                timer++;
            }
        }
        //destroy the land  if (extractCount >= LAND_DURABILITY) {
        if(extractCount >= LAND_DURABILITY) {
            extractCount = 0;
            if(!world.isRemote())
            {
                int min = 0;
                int max = validCount;
                int blockIndex = (int) Math.floor(Math.random() * (max - min + 1) + min);
                //use array to store all the valid block pos and then use random
                //number to index the array to target the block
                Block targetBlock = world.getBlockState(validBlocks[blockIndex]).getBlock();
                Block changeBlock = Blocks.AIR;
                if (blockIsValidGrass(targetBlock)) {
                    changeBlock = MakingMagicBlocks.DUST_GRASS.get();
                } else if (blockIsValidLog(targetBlock)) {
                    changeBlock = MakingMagicBlocks.DUST_OAK.get();
                } else if (blockIsValidLeaf(targetBlock)) {
                    changeBlock = Blocks.AIR;
                }
                System.out.println("Attempted to place: " + changeBlock.toString() + " at " + validBlocks[blockIndex].toString());
                try
                {
                    world.setBlockState(validBlocks[blockIndex], changeBlock.getDefaultState());
                }catch (Exception e)
                {
                    System.out.println("Failed to change world block");
                }

            }
        }
    }

    //load nbt data from world save
    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("floraColInv"));
        super.read(state, nbt);
    }

    //write nbt data to world save
    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.put("floraColInv", itemHandler.serializeNBT());
        return super.write(nbt);
    }



    //create the inventory handler
    private ItemStackHandler createHandler()
    {
        //only one slot for this entity
        return new ItemStackHandler(1)
        {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                //make sure that only raw flora can be in the slot
                return stack.getItem() == MakingMagicItems.RAW_FLORA.get();
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

    //Helper functions to check all the valid blocks
    private boolean blockIsValidLog(Block check)
    {
        boolean ret = false;
        //Check valid logs
        if(check == Blocks.OAK_LOG || check == Blocks.DARK_OAK_LOG || check == Blocks.ACACIA_LOG
                || check == Blocks.BIRCH_LOG || check == Blocks.JUNGLE_LOG || check == Blocks.SPRUCE_LOG)
        {
            ret = true;
        }
        return ret;
    }

    private boolean blockIsValidLeaf(Block check)
    {
        boolean ret = false;
        if(check == Blocks.OAK_LEAVES || check == Blocks.DARK_OAK_LEAVES || check == Blocks.ACACIA_LEAVES
                || check == Blocks.BIRCH_LEAVES || check == Blocks.JUNGLE_LEAVES || check == Blocks.SPRUCE_LEAVES)
        {
            ret = true;
        }
        return ret;
    }

    private boolean blockIsValidGrass(Block check)
    {
        return check == Blocks.GRASS_BLOCK;
    }

}
