package gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class team {

	public Inventory teaminv() {
		Inventory inv = Bukkit.createInventory(null, 9, "���ѡ�����");
		{

			ItemStack item = new ItemStack(35, 1, (short) 14);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("��c���:red");
			List<String> lore = new ArrayList();
			lore.add("���");
			for (Player p : main.TheWar.red.getplayers()) {
				lore.add(p.getName());
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(0, item);
		}
		{
			ItemStack item = new ItemStack(35, 1, (short) 3);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("��b����:blue");
			List<String> lore = new ArrayList();
			lore.add("���");
			for (Player p : main.TheWar.blue.getplayers()) {
				lore.add(p.getName());
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(1, item);
		}
		{
			ItemStack item = new ItemStack(35, 1, (short) 4);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("��e�ƶ�:yellow");
			List<String> lore = new ArrayList();
			lore.add("���");
			for (Player p : main.TheWar.yellow.getplayers()) {
				lore.add(p.getName());
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(2, item);
		}
		{
			ItemStack item = new ItemStack(35, 1, (short) 5);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("��a�̶�:green");
			List<String> lore = new ArrayList();
			lore.add("���");
			for (Player p : main.TheWar.green.getplayers()) {
				lore.add(p.getName());
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(3, item);
		}
		return inv;
	}

}
