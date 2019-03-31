package main;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class loadschematic {
	public static boolean loadIslandSchematic(FileConfiguration config) {
		if (config.contains("zone")) {
		List<String> blocks = config.getStringList("zone");
		for (String loc1 : blocks) {
			String[] loc2 = loc1.split(":");
			Location loc = new Location(Bukkit.getWorld("world"), Integer.valueOf(loc2[0]), Integer.valueOf(loc2[1]),
					Integer.valueOf(loc2[2]));
			int id = Integer.valueOf(loc2[3]);
			loc.getBlock().setTypeId(id);
		}
		return true;
		} else {
			return false;
		}
	}
}
