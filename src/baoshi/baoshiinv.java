package baoshi;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import main.TheWar;
import team.Baoshi;
import team.BaoshiInfo;

public class baoshiinv {

	public Inventory geting(Inventory inv, Player p) {
		BaoshiInfo bi = TheWar.playerinfo.get(p).getbi();
		FileConfiguration baoshi = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/baoshi.yml"));
		List<Baoshi> baoshis = bi.getbaoshis();
		int i = 0;
		if (baoshis.size() == 0) {
			p.sendMessage("您还没有宝石");
			return inv;
		}
		for (Baoshi bs : baoshis) {
			ConfigurationSection cs = baoshi.getConfigurationSection(bs.getname())
					.getConfigurationSection(String.valueOf(bs.getlevel()));
			ItemStack item = new ItemStack(cs.getInt("id"), 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(bs.getname());
			List<String> lore = cs.getStringList("lore");
			lore.add("§e等级: " + bs.getlevel());
			lore.add("§e品质: " + bs.getquality());
			lore.add("§e种类: " + bs.getkind());
			lore.add("§a左键下场,§c右键卖出");
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(i++, item);
		}
		return inv;
	}

	public Inventory getwait(Inventory inv, Player p) {
		BaoshiInfo bi = TheWar.playerinfo.get(p).getbi();
		FileConfiguration baoshi = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/baoshi.yml"));
		List<Baoshi> waits = bi.getwaits();
		if (waits.size() == 0) {
			p.sendMessage("您还没有宝石");
			return inv;
		}
		int i = 0;
		for (Baoshi bs : waits) {
			ConfigurationSection cs = baoshi.getConfigurationSection(bs.getname())
					.getConfigurationSection(String.valueOf(bs.getlevel()));
			ItemStack item = new ItemStack(cs.getInt("id"), 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(bs.getname());
			List<String> lore = cs.getStringList("lore");
			lore.add("§e等级: " + bs.getlevel());
			lore.add("§e品质: " + bs.getquality());
			lore.add("§e种类: " + bs.getkind());
			lore.add("§a左键上场,§c右键卖出");
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(i++, item);
		}
		return inv;
	}

}
