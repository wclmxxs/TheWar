package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class setpoints {

	public static Location loc2 = null;
	public static Location loc1 = null;
	public void set(CommandSender s, String[] args, FileConfiguration config) {
		if (args[0].equalsIgnoreCase("setspawn")) {
			config.set("spawn", ((Player) s).getLocation());
			try {
				config.save(new File("plugins/TheWar/config.yml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			config = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/config.yml"));
			s.sendMessage("设置成功.");
		}
		if (args[0].equalsIgnoreCase("save")) {
			FileConfiguration map = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/map.yml"));
			List<String> blocks = new main.savezone().savezone(loc1, loc2);
			List<String> zone = new main.savezone().savezones(loc1, loc2);
			config.set("blocks", blocks); // 不包含空气
			map.set("zone", zone);
			try {
				config.save(new File("plugins/TheWar/config.yml"));
				map.save(new File("plugins/TheWar/map.yml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			config = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/config.yml"));
			s.sendMessage("设置成功.");
		}
		if (args[0].equalsIgnoreCase("setred")) {
			config.set("red", ((Player) s).getLocation());
			try {
				config.save(new File("plugins/TheWar/config.yml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			config = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/config.yml"));
			s.sendMessage("设置成功.");
		}
		if (args[0].equalsIgnoreCase("setblue")) {
			config.set("blue", ((Player) s).getLocation());
			try {
				config.save(new File("plugins/TheWar/config.yml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s.sendMessage("设置成功.");
			config = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/config.yml"));
		}
		if (args[0].equalsIgnoreCase("setgreen")) {
			config.set("green", ((Player) s).getLocation());
			try {
				config.save(new File("plugins/TheWar/config.yml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			config = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/config.yml"));
			s.sendMessage("设置成功.");
		}
		if (args[0].equalsIgnoreCase("setyellow")) {
			config.set("yellow", ((Player) s).getLocation());
			try {
				config.save(new File("plugins/TheWar/config.yml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			config = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/config.yml"));
			s.sendMessage("设置成功.");
		}
		if (args[0].equalsIgnoreCase("setpoint")) {
			List<Location> locs = new ArrayList();
			if (config.contains("points")) {
				locs = (List<Location>) config.getList("points");
			}
			locs.add(loc2);
			config.set("points", locs);
			try {
				config.save(new File("plugins/TheWar/config.yml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			config = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/config.yml"));
			s.sendMessage("设置成功.");
		}
		if (args[0].equalsIgnoreCase("setdeeppoint")) {
			config.set("deeppoint", loc2);
			try {
				config.save(new File("plugins/TheWar/config.yml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			config = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/config.yml"));
			s.sendMessage("设置成功.");
		}


	}

}
