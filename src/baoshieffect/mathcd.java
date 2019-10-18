package baoshieffect;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class mathcd {
	private FileConfiguration baoshi = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/baoshi.yml"));

	public boolean iscd(String name, String level, int time, int btime) {
		if (time - btime >= baoshi.getConfigurationSection(name).getConfigurationSection(level).getInt("cd")
				|| time - btime < 0) {
			return true;
		}
		return false;
	}
}
