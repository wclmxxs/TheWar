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

import team.PlayerInfo;

public class zombiedamaged {

	public void damage(EntityDamageByEntityEvent e, FileConfiguration config) {
		Villager zombie = (Villager) e.getEntity();
		Player p = Bukkit.getPlayer(e.getEntity().getCustomName());
		if (e.getDamager() instanceof Player && main.TheWar.playerteam.get(p).getplayers().contains(e.getDamager())) {
			e.setCancelled(true);
			return;
		}
		if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player
				&& main.TheWar.playerteam.get(p).getplayers().contains(((Projectile) e.getDamager()).getShooter())) {
			e.setCancelled(true);
			return;
		}
		if (zombie.getHealth() - e.getFinalDamage() <= 0) {

			if (p != null) {
				p.teleport((Location) config.get("spawn"));
				p.sendMessage("��4���Ľ�ʬ��ɱ��,�㱻���͵�������,/n�ȴ���Ϸ����,��Ҳ������ǰ�˳�,�����Խ�����");
				p.getInventory().clear();
				p.updateInventory();
				main.TheWar.gaming.remove(p);
				boolean teamislose = true;
				for (Player pa : Bukkit.getOnlinePlayers()) {
					if (main.TheWar.playerteam.get(p).getplayers().contains(pa)) {
						teamislose = false;
					}
					pa.sendMessage("��e��l��ҡ�6" + zombie.getCustomName() + "��e��l�Ѿ�����6" + e.getDamager() + "��e��l��̭");
				}
				if (teamislose) {
					teamlose(p);
				}
			}
		}
		
		PlayerInfo pi = TheWar.playerinfo.get(p);
		e.setCancelled(true);
		pi.setdamage(e.getFinalDamage());

	}

	public void quit(PlayerQuitEvent e, FileConfiguration config) {
		Player p = e.getPlayer();
		if (p != null) {
			p.teleport((Location) config.get("spawn"));
			p.sendMessage("��4���Ľ�ʬ��ɱ��,�㱻���͵�������,/n�ȴ���Ϸ����,��Ҳ������ǰ�˳�,�����Խ�����");
			p.getInventory().clear();
			p.updateInventory();
			main.TheWar.gaming.remove(p);
			boolean teamislose = true;
			for (Player pa : Bukkit.getOnlinePlayers()) {
				if (main.TheWar.playerteam.get(p).getplayers().contains(pa)) {
					teamislose = false;
				}
				pa.sendMessage("��e��l��ҡ�6" + p.getName() + "��e��l�Ѿ�����6" + "�˳���Ϸ" + "��e��l��̭");
				main.TheWar.playerinfo.get(p).getzombie().damage(100000);
			}
			if (teamislose) {
				teamlose(p);
			}
		}
	}

	private void teamlose(Player p) {
		main.TheWar.isgaming.remove(main.TheWar.playerteam.get(p));
		Bukkit.broadcastMessage("��c�ö����Ѿ�����̭: " + main.TheWar.playerteam.get(p).getname());
		if (main.TheWar.isgaming.size() == 1) {
			Bukkit.broadcastMessage("��e�ö���ȡ����ʤ��: " + main.TheWar.isgaming.get(0).getname());
			Bukkit.broadcastMessage("��e�ö���ȡ����ʤ��: " + main.TheWar.isgaming.get(0).getname());
			Bukkit.broadcastMessage("��e�ö���ȡ����ʤ��: " + main.TheWar.isgaming.get(0).getname());
			Bukkit.broadcastMessage("��6�ö������: ");
			for (Player pa : main.TheWar.isgaming.get(0).getplayers()) {
				Bukkit.broadcastMessage("��a" + pa.getName());
			}
			Bukkit.broadcastMessage("��6��Ϸ����10������");
			for (Entity entity : Bukkit.getWorld("world").getEntities()) {
				if (entity instanceof Villager) {
					((Villager) entity).damage(10000000);
				}
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("TheWar"),
					new Runnable() {
						@Override
						public void run() {

							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");

						}
					}, 10 * 20L);
		}

	}

}
