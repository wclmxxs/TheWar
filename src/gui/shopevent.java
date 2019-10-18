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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import main.TheWar;
import team.Baoshi;
import team.BaoshiInfo;
import team.PlayerInfo;

public class shopevent {

	public void giveSomething(String type, ItemStack item, Player p, int price)
	{
		FileConfiguration config = TheWar.config;
		ConfigurationSection hp = config.getConfigurationSection("level").getConfigurationSection("health");
		ConfigurationSection ar = config.getConfigurationSection("level").getConfigurationSection("armour");
		PlayerInfo pi = TheWar.playerinfo.get(p);
		if (type.equalsIgnoreCase("renew")) {
			Inventory inv = getInv();
			p.closeInventory();
			p.openInventory(inv);
		}
		if(type.equalsIgnoreCase("level"))
		{
			pi.addlevel();
			pi.setarmour(ar.getInt(String.valueOf(pi.getlevel())));
			pi.setmaxhealth(hp.getInt(String.valueOf(pi.getlevel())));
			pi.sethealth(hp.getInt(String.valueOf(pi.getlevel())));
			p.closeInventory();
		}
		if (type.equalsIgnoreCase("baoshi")) {

			BaoshiInfo bi = pi.getbi();
			if (bi.getwaits().size() == config.getInt("wait")) {
				p.sendMessage("达到上限,购买失败");
				return;
			}
			String kind = null, quality = null;
			for (String aa : item.getItemMeta().getLore()) {
				if (aa.contains("种类")) {
					kind = aa.split(": ")[1];

				}
				if (aa.contains("品质")) {
					quality = aa.split(": ")[1];
				}
			}
			Baoshi baoshi = new Baoshi(item.getItemMeta().getDisplayName(), kind, quality, price);
			bi.addwaits(baoshi);
			new baoshi.checkbaoshi().check(bi, baoshi, p);
			if (bi.getwaits().size() == config.getInt("wait")) {
				p.sendMessage("您的等待区宝石已经达到上限,下次购买将扣除点数,但不会获得宝石");
			}
		}
		if (type.equalsIgnoreCase("item")) {
			p.getInventory().addItem(item);
		}
		p.sendMessage("购买成功.");
	}
	public void onClick(InventoryClickEvent e) {

		ItemStack item = e.getCurrentItem();
		if(item!=null&&item.hasItemMeta())
		{
			if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("宝石商店")) {
				Inventory inv = null;
				if (TheWar.playerinfo.get(e.getWhoClicked()).getinv() != null) {
					inv = TheWar.playerinfo.get(e.getWhoClicked()).getinv();
				} else {
					inv = getInv();
				}

				e.getWhoClicked().openInventory(inv);
			}
			if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("宝石等待区")) {
				Inventory inv = Bukkit.createInventory(null, 9, "宝石等待区");
				inv = new baoshi.baoshiinv().getwait(inv, (Player) e.getWhoClicked());
				e.getWhoClicked().openInventory(inv);
			}
			if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("宝石上场区")) {
				Inventory inv = Bukkit.createInventory(null, 9, "宝石上场区");
				inv = new baoshi.baoshiinv().geting(inv, (Player) e.getWhoClicked());
				e.getWhoClicked().openInventory(inv);
			}
			if(item.getItemMeta().hasDisplayName()&&item.getItemMeta().getDisplayName().contains("打开商店:"))
			{
				String name = item.getItemMeta().getDisplayName().split(": ")[1] + "商店";
				File file = new File("plugins/TheWar/shop.yml");
				FileConfiguration shop = YamlConfiguration.loadConfiguration(file);
				ConfigurationSection cs = shop.getConfigurationSection(name);
				int i = 0;
				Inventory inv = Bukkit.createInventory(null, cs.getInt("size"), name);
				for (Object item1 : cs.getList("inv")) {
					ItemStack items = (ItemStack) item1;
					inv.setItem(i++, items);
				}
				e.getWhoClicked().openInventory(inv);
			}
			if (item.getItemMeta().hasLore() && item.getItemMeta().getLore().toString().contains("价值点数"))
			{
				for(String lore : item.getItemMeta().getLore())
				{
					if(lore.contains("价值点数: "))
					{
					   int point = Integer.valueOf(lore.split(": ")[1]);
						if(TheWar.playerinfo.get(e.getWhoClicked()).getpoint() >= point)
						{
							TheWar.playerinfo.get(e.getWhoClicked()).addpoint(- point);
							String type = null;
								for(String lore1 : item.getItemMeta().getLore())
								{
									if(lore1.contains("奖励类型: "))
									{
										type = lore1.split(": ")[1];
									if (type.equalsIgnoreCase("baoshi")) {
										Inventory inv = Bukkit.createInventory(null, 9, "宝石商店");
										for (int i = 0; i < e.getInventory().getSize(); i++) {
											if (e.getSlot() != i) {
												inv.setItem(i, e.getInventory().getItem(i));

											}
										}
										((Player) e.getWhoClicked()).closeInventory();
										((Player) e.getWhoClicked()).openInventory(inv);
									}
									}
								}

							giveSomething(type, item, (Player) e.getWhoClicked(), point);
						}
						else
						{
							e.getWhoClicked().sendMessage("您的点数不足: " + point);
						}
					}

				}
			}
		}

	}
	
	private Inventory getInv() {
		Inventory inv = Bukkit.createInventory(null, 9, "宝石商店");
		for (int i = 0; i < 5; i++) {
			inv.setItem(i, new baoshi.getbaoshi().get());
		}
		ItemStack item1 = new ItemStack(399);
		ItemMeta meta = item1.getItemMeta();
		meta.setDisplayName("§a点击刷新宝石");
		List<String> lore = new ArrayList();
		lore.add("§e奖励类型: renew");
		lore.add("§e价值点数: 5");
		meta.setLore(lore);
		item1.setItemMeta(meta);
		inv.setItem(8, item1);
		return inv;
	}
	public void onSave(InventoryCloseEvent e) {
		if (e.getInventory().getName().contains("宝石商店")) {
			if (TheWar.playerinfo.containsKey(e.getPlayer())) {
				PlayerInfo pi = TheWar.playerinfo.get(e.getPlayer());
				pi.setinv(e.getInventory());
			}

		}
		if(e.getPlayer().isOp())
		{
			File file = new File("plugins/TheWar/shop.yml");
			FileConfiguration shop = YamlConfiguration.loadConfiguration(file);
			String name = e.getInventory().getName();
			ConfigurationSection cs = shop.getConfigurationSection(name);
			Inventory inv = e.getInventory();
			List<ItemStack> items = new ArrayList();
			for (int i = 0; i < inv.getSize(); i++) {
				items.add(inv.getItem(i));

			}
			cs.set("inv", items);
			
			try {
				shop.save(file);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.getPlayer().sendMessage("存储成功");
		}

	}

}
