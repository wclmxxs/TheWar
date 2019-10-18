package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import main.Job;
import main.Skill;
import main.TheWar;

public class job {

	public Inventory jobinv() {
		Inventory inv = Bukkit.createInventory(null, 18, "���ѡ��ְҵ");
		int i = 0;
		for (Job job : TheWar.jobs) {
			ItemStack item = new ItemStack(399);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(job.getName());
			List<String> lore = new ArrayList();
			lore.add("��aӵ�м���: " + job.getSkill().getName());
			lore.add("��a�����ӳ�: " + job.getDamageadd());
			lore.add("��a�����ӳ�: " + job.getDefendadd());
			lore.add("��a��Ʒ��Ŀ: " + job.getItems().size());
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(i++, item);
		}
		return inv;
	}

	public static void createjob() {
		// TODO Auto-generated method stub
		{
			List<ItemStack> items = new ArrayList();
			items.add(new ItemStack(276));
			Job job = new Job("����ְҵ", items, TheWar.skills.get(0), 0, 0);
			TheWar.jobs.add(job);
		}
		File root = new File("plugins/TheWar/Job");
		for (File f : root.listFiles()) {
			FileConfiguration fjob = YamlConfiguration.loadConfiguration(f);
			for (Skill skill : TheWar.skills) {
				if (skill.getName().equalsIgnoreCase(fjob.getString("skill"))) {
					Job job = new Job(fjob.getString("name"), (List<ItemStack>) fjob.getList("items"), skill,
							fjob.getInt("damage"), fjob.getInt("defend"));
					TheWar.jobs.add(job);
				}
			}
		}
	}

	public static void createskill() {
		// TODO Auto-generated method stub
		Skill skill = new Skill("��������", 10, 0);
		TheWar.skills.add(skill);
		for (String bb : TheWar.config.getConfigurationSection("skill").getKeys(false)) {
			TheWar.skills.add(new Skill(bb,
					TheWar.config.getConfigurationSection("skill").getConfigurationSection(bb).getInt("cooldown"),
					TheWar.config.getConfigurationSection("skill").getConfigurationSection(bb).getInt("duration")));
		}
	}

}
