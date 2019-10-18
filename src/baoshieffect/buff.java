package baoshieffect;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import baoshieffect.addlist.playerlist;
import main.TheWar;
import team.Baoshi;
import team.BaoshiInfo;
import team.PlayerInfo;

public class buff {

	public void kind(Player p) {
		PlayerInfo pi = TheWar.playerinfo.get(p);
		BaoshiInfo bi = pi.getbi();
		List<Baoshi> bs = bi.getbaoshis();
		int[] ints = { 0, 0, 0, 0, 0 };
		for (Baoshi b : bs) {
			String[] bls = { "进攻", "守备", "爆发", "功能", "持久" };
			for (int i = 0; i < bls.length; i++) {
				if (b.getkind().contains(bls[i])) {
					ints[i] = ints[i] + 1;
				}
			}
		}
		for (int i = 0; i < ints.length; i++) {
			switch (ints[i]) {
			case 0:
			case 1:
			case 2:
				pi.setkints(0, i);
				break;
			case 3:
			case 4:
			case 5:
				pi.setkints(1, i);
				break;
			case 6:
			case 7:
			case 8:
				pi.setkints(2, i);
				break;
			default:
				pi.setkints(3, i);
				;
			}
		}
		for (playerlist pl : playerlist.values()) {
			if (pi.getkints()[pl.getint()] >= 1) {
				if (!pl.getlist().contains(p)) {
					pl.getlist().add(p);
				}
				continue;
			}
			if (pl.getlist().contains(p)) {
				pl.getlist().remove(p);
			}
		}
	}

	public void quality(Player p) {
		PlayerInfo pi = TheWar.playerinfo.get(p);
		BaoshiInfo bi = pi.getbi();
		List<Baoshi> bs = bi.getbaoshis();
		int[] ints = { 0, 0, 0, 0, 0 };
		for (Baoshi b : bs) {
			String[] bls = { "白", "绿", "蓝", "黄", "紫" };
			for (int i = 0; i < bls.length; i++) {
				if (b.getquality().contains(bls[i])) {
					ints[i] = ints[i] + 1;
				}
			}
		}
		for (int i = 0; i < ints.length; i++) {
			if (i == 4) {
				switch (ints[i]) {
				case 0:
					pi.setints(0, i);
					break;
				case 1:
					pi.setints(1, i);
					break;
				default:
					pi.setints(2, i);
					break;
				}
			}
			switch (ints[i]) {
			case 0:
			case 1:
			case 2:
				pi.setints(0, i);
				break;
			case 3:
			case 4:
				pi.setints(1, i);
				break;
			case 5:
			case 6:
				pi.setints(2, i);
				break;
			default:
				pi.setints(3, i);
				;
			}
		}
		setspeed(pi, p);
		heal(pi, p);
		addheal(pi, p);

	}

	// 白卡减速
	public void lowspeed(PlayerInfo pi, Player p) {
		if (pi.getints()[0] >= 1) {
			if (!TheWar.white.contains(p)) {
				TheWar.white.add(p);
			}
		} else {
			if (TheWar.white.contains(p)) {
				TheWar.white.remove(p);
			}
		}
	}

	public void onDamage(EntityDamageByEntityEvent e) {
		Player p = null;
		if (e.getDamager() instanceof Player) {
			p = (Player) e.getDamager();
		}
		if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
			p = (Player) ((Projectile) e.getDamager()).getShooter();
		}
		if (p == null) {
			return;
		}
		Player entity = (Player) e.getEntity();
		int random = new Random().nextInt(100);
		PlayerInfo pi = TheWar.playerinfo.get(p);
		white(p, e);
		attack(p, e, random, pi);
		critical(p, e, random, pi);
		persistent(p, e, pi);
		function(p, entity, e, random, pi);
		protect(entity, e, random, pi);
	}

	private void persistent(Player p, EntityDamageByEntityEvent e, PlayerInfo pi) {
		if (TheWar.persistent.contains(p)) {
			double k = 0;
			if (pi.getkints()[4] >= 1) {
				k = 0.1;
			}
			if (pi.getkints()[4] >= 2) {
				k = 0.25;
			}
			if (pi.getkints()[4] >= 3) {
				k = 0.4;
			}
			if (e.getDamage() * k < p.getMaxHealth() - p.getHealth()) {
				p.setHealth(p.getHealth() + e.getDamage() * k);
			} else {
				p.setMaxHealth(p.getHealth());
			}
		}

	}

	private void function(Player p, Player entity, EntityDamageByEntityEvent e, int random, PlayerInfo pi) {
		if (TheWar.function.contains(p)) {
			int k = 0;
			int d = 0;
			int j = TheWar.playerinfo.get(entity).getpoint();
			if (pi.getkints()[3] >= 15) {
				k = 15;
				d = 10;
			}
			if (pi.getkints()[3] >= 30) {
				k = 30;
				d = 20;
			}
			if (pi.getkints()[3] >= 50) {
				k = 50;
				d = 30;
			}
			if (k > random) {
				if (d > j) {
					d = j;
				}
				p.sendMessage("您偷取了点数: " + d);
				entity.sendMessage("您被偷取了点数: " + d);
				TheWar.playerinfo.get(entity).addpoint(-d);
				TheWar.playerinfo.get(p).addpoint(d);
			}
		}
	}

	private void critical(Player p, EntityDamageByEntityEvent e, int random, PlayerInfo pi) {
		if (TheWar.critical.contains(p)) {
			int k = 0;
			if (pi.getkints()[2] >= 1) {
				k = 10;
			}
			if (pi.getkints()[2] >= 2) {
				k = 25;
			}
			if (pi.getkints()[2] >= 3) {
				k = 35;
			}
			if (k > random) {
				e.setDamage(10000);
				p.sendMessage("您打出了10000伤害,可能秒杀了对手");
			}
		}

	}

	private void protect(Player p, EntityDamageByEntityEvent e, int random, PlayerInfo pi) {
		if (TheWar.protect.contains(p)) {
			int k = 0;
			if (pi.getkints()[1] >= 1) {
				k = 10;
			}
			if (pi.getkints()[1] >= 2) {
				k = 25;
			}
			if (pi.getkints()[1] >= 3) {
				k = 35;
			}
			if (k > random) {
				e.setDamage(0);
				p.sendMessage("您躲避了伤害");
				e.setCancelled(true);
			}
		}

	}

	private void attack(Player p, EntityDamageByEntityEvent e, int random, PlayerInfo pi) {
		if (TheWar.attack.contains(p)) {
			int k = 0;
			if (pi.getkints()[0] >= 1) {
				k = 15;
			}
			if (pi.getkints()[0] >= 2) {
				k = 30;
			}
			if (pi.getkints()[0] >= 3) {
				k = 50;
			}
			if (k > random) {
				e.setDamage(e.getDamage() * 3);
				p.sendMessage("您打出了3倍伤害");
			}
		}

	}

	// 白卡减速
	private void white(Player p, EntityDamageByEntityEvent e) {
		if (TheWar.white.contains(p)) {
			if (e.getEntity() instanceof Player) {
				if (TheWar.playerinfo.get(p).getints()[0] >= 2) {
					((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2), true);
				} else {
					((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1), true);
				}
			}
		}

	}

	// 紫卡回村民血
	public void addheal(PlayerInfo pi, Player p) {
		if (pi.getints()[4] >= 1) {
			if (!TheWar.addheal.contains(p)) {
				TheWar.addheal.add(p);
			}
		} else {
			if (TheWar.addheal.contains(p)) {
				TheWar.addheal.remove(p);
			}
		}
	}

	// 绿卡回血区
	public void heal(PlayerInfo pi, Player p) {
		if (pi.getints()[1] >= 1) {
			if (!TheWar.heal.contains(p)) {
				TheWar.heal.add(p);
			}
		} else {
			if (TheWar.heal.contains(p)) {
				TheWar.heal.remove(p);
			}
		}
	}

	public void onHeal() {
		Plugin ppa = Bukkit.getPluginManager().getPlugin("TheWar");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ppa, new Runnable() {
			@Override
			public void run() {
				if (TheWar.heal.size() > 0) {
					for (Player p : TheWar.heal) {
						PlayerInfo pi = TheWar.playerinfo.get(p);
						if (pi.getints()[1] >= 3) {
							heals(p, 6);
							continue;
						}
						if (pi.getints()[1] >= 2) {
							heals(p, 4);
							continue;
						}
						if (pi.getints()[1] >= 1) {
							heals(p, 2);
							continue;
						}
					}
				}
				if (TheWar.addheal.size() > 0) {
					for (Player p : TheWar.addheal) {
						PlayerInfo pi = TheWar.playerinfo.get(p);
						if (pi.gethealth() + 4 < pi.getzombie().getMaxHealth()) {
							if (pi.getints()[4] >= 2) {
								pi.sethealth(4);
								continue;
							} else {
								pi.sethealth(2);
							}
						}
					}
				}
			}

		}, 0L, 20L);
	}

	private void heals(Player p, int amount) {
		if (p.getHealth() + amount <= p.getMaxHealth()) {
			p.setHealth(p.getHealth() + amount);
		} else {
			p.setHealth(p.getMaxHealth());
		}

	}

	// 蓝卡加速区
	public void setspeed(PlayerInfo pi, Player p) {
		if (pi.getints()[2] >= 2) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, -1, 2), true);
			return;
		}
		if (pi.getints()[1] == 1) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, -1, 2), true);
			return;
		}
		if (p.hasPotionEffect(PotionEffectType.SPEED)) {
			p.removePotionEffect(PotionEffectType.SPEED);
		}
	}

	// 黄卡加点区
	public int getyellow(PlayerInfo pi) {
		if (pi.getints()[3] >= 2) {
			return 22;
		}
		if (pi.getints()[3] >= 1) {
			return 10;
		}

		return 0;
	}
}
