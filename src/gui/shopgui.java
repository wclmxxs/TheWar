package gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class shopgui {

	public Inventory getInventory(int size, String name) {
		Inventory inv = Bukkit.createInventory(null, size, name + "ил╣Й");

		return inv;
	}
}
