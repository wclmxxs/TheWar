package main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class savezone {

	public List<String> savezone(Location loc1, Location loc2) {
		List<String> blocks = new ArrayList();
		int x1 = loc1.getBlockX();
		int y1 = loc1.getBlockY();
		int z1 = loc1.getBlockZ();
		int x2 = loc2.getBlockX();
		int y2 = loc2.getBlockY();
		int z2 = loc2.getBlockZ();
		if (x1 > x2) {
			int b = x1;
			x1 = x2;
			x2 = b;
		}
		if (y1 > y2) {
			int b = y1;
			y1 = y2;
			y2 = b;
		}
		if (z1 > z2) {
			int b = z1;
			z1 = z2;
			z2 = b;
		}
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
					Block block = loc.getBlock();
					if (block.getTypeId() != 0) {
						blocks.add(x + ":" + y + ":" + z + ":" + block.getTypeId());
					}
				}
			}
		}
		return blocks;
	}

	public List<String> savezones(Location loc1, Location loc2) {
		List<String> blocks = new ArrayList();
		int x1 = loc1.getBlockX();
		int y1 = loc1.getBlockY();
		int z1 = loc1.getBlockZ();
		int x2 = loc2.getBlockX();
		int y2 = loc2.getBlockY();
		int z2 = loc2.getBlockZ();
		if (x1 > x2) {
			int b = x1;
			x1 = x2;
			x2 = b;
		}
		if (y1 > y2) {
			int b = y1;
			y1 = y2;
			y2 = b;
		}
		if (z1 > z2) {
			int b = z1;
			z1 = z2;
			z2 = b;
		}
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
					Block block = loc.getBlock();
					blocks.add(x + ":" + y + ":" + z + ":" + block.getTypeId());
				}
			}
		}
		return blocks;
	}

}
