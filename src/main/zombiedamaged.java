package main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import team.PlayerInfo;


public class zombiedamaged {

	public boolean mathrange(Location loc1, Location loc2) {
		double x = Math.abs(loc1.getBlockX() - loc2.getBlockX());
		double z = Math.abs(loc1.getBlockZ() - loc2.getBlockZ());
		double range = Math.sqrt(x * x + z * z);
		if (range < 8) {
			return false;
		}
		return true;
	}

	public void damage(EntityDamageByEntityEvent e, FileConfiguration config) {
		Villager zombie = (Villager) e.getEntity();
		Player p = Bukkit.getPlayer(e.getEntity().getCustomName());

		if (e.getDamager() instanceof Player
				&& main.TheWar.playerteam.get(p.getName()).getplayers().contains(e.getDamager())) {
			e.setCancelled(true);
			return;
		}
		if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player
				&& main.TheWar.playerteam.get(p.getName()).getplayers()
						.contains(((Projectile) e.getDamager()).getShooter())) {
			e.setCancelled(true);
			return;
		}
		if (mathrange(zombie.getLocation(), p.getLocation())) {
			p.sendMessage("§4您的僵尸正在被攻击!!");
		}
		e.getDamager().sendMessage("§c血量: " + zombie.getHealth());
		if (zombie.getHealth() - e.getFinalDamage() <= 0) {

			if (p != null) {
				p.teleport((Location) config.get("spawn"));
				p.sendMessage("§4您的僵尸被杀死,你被传送到出生点,/n等待游戏结束,您也可以提前退出,分数仍将计入");
				p.getInventory().clear();
				p.updateInventory();
				main.TheWar.gaming.remove(p);
				boolean teamislose = true;
				for (Player pa : Bukkit.getOnlinePlayers()) {
					if (main.TheWar.playerteam.get(p.getName()) != null
							&& main.TheWar.playerteam.get(p.getName()).getplayers().contains(pa)
							&& TheWar.gaming.contains(pa) && !pa.equals(p)) {
						teamislose = false;
					}
					pa.sendMessage(
							"§e§l玩家§6" + zombie.getCustomName() + "§e§l已经被§6" + e.getDamager().getName() + "§e§l淘汰");

				}
				if (teamislose) {
					teamlose(p);
				}
				return;
			}
		}
		

		PlayerInfo pi = TheWar.playerinfo.get(p);
		e.setCancelled(true);
		pi.setdamage(e.getFinalDamage());


	}

	public void quit(PlayerQuitEvent e, FileConfiguration config) {
		Player p = e.getPlayer();
		if (p != null && main.TheWar.gaming.contains(p)) {
			main.TheWar.gaming.remove(p);
			boolean teamislose = true;
			for (Player pa : Bukkit.getOnlinePlayers()) {
				if (main.TheWar.playerteam.get(p.getName()) != null && TheWar.gaming.contains(pa)
						&& main.TheWar.playerteam.get(p.getName()).getplayers().contains(pa)
						&& !pa.equals(p)) {
					teamislose = false;
				}
				pa.sendMessage("§e§l玩家§6" + p.getName() + "§e§l已经被§6" + "退出游戏" + "§e§l淘汰");
				main.TheWar.playerinfo.get(p).getzombie().damage(100000);
			}
			if (teamislose) {
				teamlose(p);
			}
		}
	}

	private void teamlose(Player p) {
		main.TheWar.isgaming.remove(main.TheWar.playerteam.get(p.getName()));
		Bukkit.broadcastMessage(
				main.TheWar.playerteam.get(p.getName()).getname() + "已经被淘汰,剩余队伍" + main.TheWar.isgaming.size());
		if (main.TheWar.isgaming.size() == 1) {
			Bukkit.broadcastMessage("§e该队伍取得了胜利: " + main.TheWar.isgaming.get(0).getname());
			Bukkit.broadcastMessage("§e该队伍取得了胜利: " + main.TheWar.isgaming.get(0).getname());
			Bukkit.broadcastMessage("§e该队伍取得了胜利: " + main.TheWar.isgaming.get(0).getname());
			Bukkit.getScheduler().cancelAllTasks();
			Bukkit.broadcastMessage("§6该队伍玩家: ");
			for (Player pa : main.TheWar.isgaming.get(0).getplayers()) {
				Bukkit.broadcastMessage("§a" + pa.getName());
			}
			Bukkit.broadcastMessage("§6游戏将在10秒后结束");
			for (Entity entity : Bukkit.getWorld("world").getEntities()) {
				if (entity instanceof Villager) {
					((Villager) entity).damage(10000000);
				}
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("TheWar"),
					new Runnable() {
						@Override
						public void run() {

							for (Player pa : Bukkit.getOnlinePlayers()) {
								ByteArrayDataOutput out = ByteStreams.newDataOutput();
								out.writeUTF("Connect");
								out.writeUTF("hub1");
								pa.sendPluginMessage(Bukkit.getPluginManager().getPlugin("TheWar"), "BungeeCord",
										out.toByteArray());
							}
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");

						}
					}, 10 * 20L);
		}

	}

}
