package gui;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.TheWar;
import team.Baoshi;
import team.BaoshiInfo;
import team.PlayerInfo;

public class shopevent {

	public void giveSomething(String type, ItemStack item,Player p)
	{
		FileConfiguration config = TheWar.config;
		ConfigurationSection hp = config.getConfigurationSection("level").getConfigurationSection("health");
		ConfigurationSection ar = config.getConfigurationSection("level").getConfigurationSection("armour");
		PlayerInfo pi = TheWar.playerinfo.get(p);
		if(type.equalsIgnoreCase("level"))
		{
			pi.addlevel();
			pi.setarmour(ar.getInt(String.valueOf(pi.getlevel())));
			pi.sethealth(hp.getInt(String.valueOf(pi.getlevel())));
			pi.setmaxhealth(hp.getInt(String.valueOf(pi.getlevel())));
		}
		if (type.equalsIgnoreCase("baoshi")) {

			BaoshiInfo bi = pi.getbi();
			if (bi.getwaits().size() == config.getInt("wait")) {
				p.sendMessage("�ﵽ����,����ʧ��");
				return;
			}
			String kind = item.getItemMeta().getLore().toString().split("����: ")[1].split("[")[1].split("]")[0];
			String quality = item.getItemMeta().getLore().toString().split("Ʒ��: ")[1].split("[")[1].split("]")[0];
			Baoshi baoshi = new Baoshi(item.getItemMeta().getDisplayName().split(": ")[1], kind, quality);
			bi.addwaits(baoshi);
			new gaming.checkbaoshi().check(bi, baoshi, p);
			if (bi.getwaits().size() == config.getInt("wait")) {
				p.sendMessage("���ĵȴ�����ʯ�Ѿ��ﵽ����,�´ι��򽫿۳�����,�������ñ�ʯ");
			}
		}
		if (type.equalsIgnoreCase("item")) {
			p.getInventory().addItem(item);
		}
		p.sendMessage("����ɹ�.");
	}
	public void onClick(InventoryClickEvent e) {

		ItemStack item = e.getCurrentItem();
		if(item!=null&&item.hasItemMeta())
		{
			if(item.getItemMeta().hasDisplayName()&&item.getItemMeta().getDisplayName().contains("���̵�:"))
			{
				String name = item.getItemMeta().getDisplayName().split(": ")[1];
				File file = new File("plugins/TheWar/shop.yml");
				FileConfiguration shop = YamlConfiguration.loadConfiguration(file);
				Inventory inv = (Inventory) shop.getConfigurationSection(name).get("inv");
			}
			if(item.getItemMeta().hasLore()&&item.getItemMeta().getLore().toString().contains("��ֵ����: "))
			{
				String type = null;
				for(String lore : item.getItemMeta().getLore())
				{
					if(lore.contains("��ֵ����: "))
					{
					   int point = Integer.valueOf(lore.split(": ")[1]);
						if(TheWar.playerinfo.get(e.getWhoClicked()).getpoint() >= point)
						{
							TheWar.playerinfo.get(e.getWhoClicked()).addpoint(- point);
							if(type == null)
							{
								for(String lore1 : item.getItemMeta().getLore())
								{
									if(lore1.contains("��������: "))
									{
										type = lore.split(": ")[1];
									}
								}
							}
							giveSomething(type, item, (Player) e.getWhoClicked());
						}
						else
						{
							e.getWhoClicked().sendMessage("���ĵ�������: " + point);
						}
					}
					break;
				}
			}
		}

	}
	
	public void onSave(InventoryCloseEvent e) {
		if(e.getPlayer().isOp())
		{
			File file = new File("plugins/TheWar/shop.yml");
			FileConfiguration shop = YamlConfiguration.loadConfiguration(file);
			String name = e.getInventory().getName().split("�̵�")[0];
			shop.getConfigurationSection(name).set("inv", e.getInventory());
			
			try {
				shop.save(file);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.getPlayer().sendMessage("�洢�ɹ�");
		}

	}

}
