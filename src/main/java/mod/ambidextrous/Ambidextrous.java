package mod.ambidextrous;

import mod.ambidextrous.network.AmbidextrousChannel;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Ambidextrous.MODID)
public class Ambidextrous
{
	public static final String MODID = "ambidextrous";
	public static final Logger LOGGER = LogManager.getLogger();

	public Ambidextrous(IEventBus modEventBus, ModContainer modContatiner) {
		modEventBus.addListener(AmbidextrousChannel::register);
	}
}
