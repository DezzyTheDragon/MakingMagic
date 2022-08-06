package com.dezzy.makingmagic.client;

import com.dezzy.makingmagic.MakingMagic;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MakingMagic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ClientEvents {
    private ClientEvents(){}

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event)
    {
        if(KeyInit.testKey.isPressed())
        {
            System.out.println("HELLO WORLD!!!!!!!");
        }
    }
}
