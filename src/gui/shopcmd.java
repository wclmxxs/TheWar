package gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import main.TheWar;

public class shopcmd {

	public void cmd(Player s, String[] args) {
		if ((args.length == 3 || args.length == 2) && s.isOp() && args[0].equalsIgnoreCase("set")) {
			File file = new File("plugins/TheWar/shop.yml");
			FileConfiguration shop = YamlConfiguration.loadConfiguration(file);
			String name = args[1] + "商店";
			int size = 9;
			Inventory inv = null;
			if (!shop.contains(name)) {
				shop.createSection(name);
			}

			if (args.length == 3) {

				size = Integer.valueOf(args[2]);
				shop.getConfigurationSection(name).set("size", size);
				inv = Bukkit.createInventory(null, size, name);
			} else {
				size = shop.getConfigurationSection(name).getInt("size");
				int i = 0;
				inv = Bukkit.createInventory(null, size, name);
				for (Object item1 : shop.getConfigurationSection(name).getList("inv")) {
					ItemStack item = (ItemStack) item1;
					inv.setItem(i++, item);
				}
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
			String name = args[1] + "商店";
			if (!shop.contains(name)) {
				s.sendMessage("该商店不存在");
				return;
			}
			ConfigurationSection cs = shop.getConfigurationSection(name);
			int i = 0;
			Inventory inv = Bukkit.createInventory(null, cs.getInt("size"), name);
			for (Object item1 : cs.getList("inv")) {
				ItemStack item = (ItemStack) item1;
				if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
						&& item.getItemMeta().getDisplayName().contains("升级村民")) {
					ItemMeta meta = item.getItemMeta();
					List<String> lore = new ArrayList();
					lore.add("§a奖励类型: level");
					String level = String.valueOf(TheWar.playerinfo.get(s).getlevel() + 1);
					ConfigurationSection csa = TheWar.config.getConfigurationSection("level");
					lore.add("下一级");
					lore.add("§a价值点数: " + csa.getConfigurationSection("point").getInt(level));
					lore.add("§a增加护甲: " + csa.getConfigurationSection("armour").getInt(level));
					lore.add("§a增加血量: " + csa.getConfigurationSection("health").getInt(level));
					meta.setLore(lore);
					item.setItemMeta(meta);
				}
				inv.setItem(i++, item);
			}
			s.openInventory(inv);
		}

	}

}
