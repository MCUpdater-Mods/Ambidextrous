package mod.ambidextrous.core;

import mod.ambidextrous.network.AmbidextrousChannel;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Ambidextrous.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static void init(final FMLCommonSetupEvent event) {
        AmbidextrousChannel.init();
    }
}
