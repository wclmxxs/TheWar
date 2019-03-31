package gui;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.TheWar;

public class shopevent {

	public void giveSomething(String type, ItemStack item)
	{
		��ʬ����
		��ʯ�ֿ�
		��Ʒ
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
							giveSomething(type,item);
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
