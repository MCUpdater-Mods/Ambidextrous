package mod.ambidextrous.core;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("ambidextrous")
public class Ambidextrous
{
	public static final String MODID = "ambidextrous";
	public static final Logger LOGGER = LogManager.getLogger();

	public Ambidextrous() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
	}
}
