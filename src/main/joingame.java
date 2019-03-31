package main;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import team.PlayerInfo;
import team.TeamInfo;

public class joingame {

	public List<Player> gaming = main.TheWar.gaming;
	public TeamInfo red = main.TheWar.red;
	public TeamInfo green = main.TheWar.green;
	public TeamInfo blue = main.TheWar.blue;
	public TeamInfo yellow = main.TheWar.yellow;
	public FileConfiguration config = main.TheWar.config;
	public void join(Player p) {
		TeamInfo team = null;
		;
		if (!main.TheWar.playerteam.containsKey(p)) {
			team = new team.addTeam().getminSizeTeam();
			new team.addTeam().addteam(p, team);
		}
 else {
			team = TheWar.playerteam.get(p);
		}
		Location loc = null;
		Location locs = null;
		gaming.add(p);
		if (red.getplayers().contains(p)) {
			p.teleport((Location) config.get("red"));
			locs = ((Location) config.get("red"));
		}
		if (green.getplayers().contains(p)) {
			p.teleport((Location) config.get("green"));
			locs = ((Location) config.get("green"));
		}
		if (blue.getplayers().contains(p)) {
			p.teleport((Location) config.get("blue"));
			locs = ((Location) config.get("blue"));
		}
		if (yellow.getplayers().contains(p)) {
			p.teleport((Location) config.get("yellow"));
			locs = ((Location) config.get("yellow"));
		}
		int x = 0, z = 0;
		if (team.getname().contains("蓝队") || team.getname().contains("黄队")) {
			z = new Random().nextInt(config.getInt("range")) - config.getInt("range") / 2;
		} else {
			x = new Random().nextInt(config.getInt("range")) - config.getInt("range") / 2;
		}
		loc = new Location(Bukkit.getWorld("world"),
 locs.getX() + x, locs.getY(), locs.getZ() + z);
		while (team.getlocs().contains(loc)) {
			loc = new Location(Bukkit.getWorld("world"),
 locs.getX() + x, locs.getY(), locs.getZ() + z);
		}

		team.addloc(loc);
		p.sendMessage("您已经加入了游戏");
		p.getInventory().clear();
		producezombie(loc, p);
	}

	private void producezombie(Location loc, Player p) {
		Villager zombie = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		zombie.setMaxHealth(config.getInt("zombie"));
		zombie.setHealth(config.getInt("zombie"));
		zombie.setCustomName(p.getName());
		zombie.setCustomNameVisible(true);
		PlayerInfo pi = TheWar.playerinfo.get(p);
		pi.setzombie(zombie);
		pi.addpoint(config.getInt("base"));
		pi.setmaxhealth(config.getInt("zombie"));
		new gaming.addpoint().addpoint(config, p);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("TheWar"), new Runnable() {
			@Override
			public void run() {
				if (zombie != null && zombie.getHealth() > 0) {
					zombie.teleport(loc);
				} else {
					return;
				}
			}
		}, 0l, 20l);

	}
}
