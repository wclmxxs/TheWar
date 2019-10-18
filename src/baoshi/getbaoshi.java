package baoshi;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class getbaoshi {

	public ItemStack get() {
		FileConfiguration baoshi = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/baoshi.yml"));
		int random = new Random().nextInt(baoshi.getKeys(false).size());
		ConfigurationSection cs = null;
		String name = null;
		int i = 0;
		for (String aa : baoshi.getKeys(false)) {
			if (random == i++) {
				name = aa;
				cs = baoshi.getConfigurationSection(aa);
				break;
			}
		}
		ItemStack item = new ItemStack(cs.getConfigurationSection("1").getInt("id"));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		List<String> lore = cs.getConfigurationSection("1").getStringList("lore");
		lore.add("§e奖励类型: baoshi");
		lore.add("§e价值点数: " + cs.getInt("price"));
		lore.add("§e宝石种类: " + cs.getString("kind"));
		lore.add("§e宝石品质: " + cs.getString("quality"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

}
