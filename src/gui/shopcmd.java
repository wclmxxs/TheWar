package gui;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class shopcmd {

	public void cmd(Player s, String[] args) {
		if ((args.length == 3 || args.length == 2) && s.isOp() && args[0].equalsIgnoreCase("set")) {
			File file = new File("plugins/TheWar/shop.yml");
			FileConfiguration shop = YamlConfiguration.loadConfiguration(file);
			String name = args[1];
			int size = 9;
			Inventory inv = null;
			if (!shop.contains(name)) {
				shop.createSection(name);
			}
			if (args.length == 3) {
				size = Integer.valueOf(args[2]);
				inv = Bukkit.createInventory(null, size, name + "商店");
			} else {
				size = shop.getConfigurationSection(name).getInt("size");
				inv = (Inventory) shop.getConfigurationSection(name).get("inv");
			}

			s.openInventory(inv);

			try {
				shop.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (args.length == 2 && args[0].equalsIgnoreCase("open")) {
			File file = new File("plugins/TheWar/shop.yml");
			FileConfiguration shop = YamlConfiguration.loadConfiguration(file);
			String name = args[1];
			if (!shop.contains(name)) {
				s.sendMessage("该商店不存在");
				return;
			}
			ConfigurationSection cs = shop.getConfigurationSection(name);
			Inventory inv = new gui.shopgui().getInventory(cs.getInt("size"), name);
			s.openInventory(inv);
		}

	}

}
