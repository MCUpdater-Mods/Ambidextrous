package mod.ambidextrous;

import mod.ambidextrous.core.ClientSetup;
import mod.ambidextrous.core.Config;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Ambidextrous.MODID, dist = Dist.CLIENT)
public class AmbidextrousClient {
	public AmbidextrousClient(IEventBus modEventBus, ModContainer modContainer) {
		modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

		modEventBus.addListener(ClientSetup::registerKeys);
	}
}
