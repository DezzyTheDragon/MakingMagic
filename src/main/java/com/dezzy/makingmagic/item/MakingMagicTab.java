package com.dezzy.makingmagic.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class MakingMagicTab {
    public static final ItemGroup MAKINGMAGIC_GROUP = new ItemGroup("makingmagictab")
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MakingMagicItems.DUST.get());
        }
    };
}
