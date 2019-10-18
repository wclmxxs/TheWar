package baoshieffect;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.Plugin;

import main.TheWar;
import team.Baoshi;
import team.BaoshiInfo;

public class event {

	private FileConfiguration baoshi = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/baoshi.yml"));

	/*
	 * ����ʯ
	 */
	public void onInteract(PlayerInteractAtEntityEvent e) {

		if (!TheWar.gaming.contains(e.getPlayer())) {
			return;
		}
		Calendar calendar = Calendar.getInstance();
		int time = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 + calendar.get(Calendar.MINUTE) * 60
				+ calendar.get(Calendar.SECOND);
		BaoshiInfo bi = TheWar.playerinfo.get(e.getPlayer()).getbi();
		List<Baoshi> bs = bi.getbaoshis();

		for (Baoshi b : bs) {
			if (b.getname().contains("����ʯ")) {
				if (new baoshieffect.mathcd().iscd("����ʯ", String.valueOf(b.getlevel()), time, b.gettime())) {
					e.getPlayer().teleport(e.getRightClicked());
					b.settime(time);
				} else {
					e.getPlayer()
							.sendMessage("��ȴ����: " + (baoshi.getConfigurationSection("����ʯ")
									.getConfigurationSection(String.valueOf(b.getlevel())).getInt("cd") - time
									+ b.gettime()) + "s");
				}
			}
		}
	}

	/*
	 * �����˺�
	 */

	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			BaoshiInfo bi = TheWar.playerinfo.get(p).getbi();
			List<Baoshi> bs = bi.getbaoshis();
			int evasion = 0;
			int armour = 0;
			for (Baoshi b : bs) {
				if (b.getname().contains("�𳵱�ʯ")) {
					if (b.getlevel() == 1) {
						evasion = evasion + 1;
					} else {
						evasion = evasion + (b.getlevel() - 1) * 3;
					}
				}
				if (b.getname().contains("���ױ�ʯ")) {
					if (b.getlevel() == 1) {
						armour = armour + 1;
					} else {
						armour = armour + (b.getlevel() - 1) * 2;
					}
				}
			}
			int random = new Random().nextInt(100);
			if (random < evasion) {
				e.setCancelled(true);
				e.getEntity().sendMessage("�������˹���");
				return;
			}
			e.setDamage(e.getDamage() - armour);
		}

		/*
		 * <<<< ����Ϊ��������
		 * 
		 * 
		 * 
		 * 
		 * ����Ϊ������
		 */
		Player p = null;
		if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
			p = (Player) ((Projectile) e.getDamager()).getShooter();
		}
		if (e.getDamager() instanceof Player) {
			p = (Player) e.getDamager();
		}
		if (p == null) {
			return;
		}
		if (!TheWar.playerinfo.containsKey(p)) {
			return;
		}
		BaoshiInfo bi = TheWar.playerinfo.get(p).getbi();
		List<Baoshi> bs = bi.getbaoshis();
		int damage = 0;
		for (Baoshi b : bs) {
			if (b.getname().contains("������ʯ")) {
				if (b.getlevel() == 1) {
					damage = damage + 1;
				} else if (b.getlevel() == 2) {
					damage = damage + 3;
				} else {
					damage = damage + 5;
				}
			}
		}

		e.setDamage(e.getDamage() + damage);
	}

	/*
	 * ����Ч��
	 */
	public void onEffect(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = null;
		if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
			p = (Player) ((Projectile) e.getDamager()).getShooter();
		}
		if (e.getDamager() instanceof Player) {
			p = (Player) e.getDamager();
		}
		if (p != null) {
			BaoshiInfo bi = TheWar.playerinfo.get(p).getbi();
			List<Baoshi> bs = bi.getbaoshis();
			int firechance = 0;
			int firedamage = 0;
			int stealhp = 0;
			int truedamage = 0;
			for (Baoshi b : bs) {
				if (b.getname().contains("ȼ�ű�ʯ")) {
					if (b.getlevel() == 1) {
						firechance = firechance + 2;
					} else if (b.getlevel() == 2) {
						firechance = firechance + 5;
					} else {
						firechance = firechance + 8;
					}
				}
				if (b.getname().contains("ȼ����ʯ")) {
					if (b.getlevel() == 1) {
						firedamage = firedamage + 1;
					} else if (b.getlevel() == 2) {
						firedamage = firedamage + 2;
					} else {
						firedamage = firedamage + 3;
					}
				}
				if (b.getname().contains("��Ѫ��ʯ")) {
					if (b.getlevel() == 1) {
						stealhp = stealhp + 2;
					} else if (b.getlevel() == 2) {
						stealhp = stealhp + 5;
					} else {
						stealhp = stealhp + 10;
					}
				}
				if (b.getname().contains("��ս��ʯ")) {
					if (b.getlevel() == 1) {
						truedamage = truedamage + 2;
					} else if (b.getlevel() == 2) {
						truedamage = truedamage + 4;
					} else {
						truedamage = truedamage + 6;
					}
				}
				// ��ʵ�˺�
				if (((Player) e.getEntity()).getHealth() - truedamage > 0) {
					((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getHealth() - truedamage);
				} else {
					((Player) e.getEntity()).damage(200);
				}

				// ��Ѫ
				double hp = e.getDamage() * stealhp / 100.0;
				if (p.getHealth() + hp < p.getMaxHealth()) {
					p.setHealth(p.getHealth() + hp);
				} else {
					p.setHealth(p.getMaxHealth());
				}

				// ��ȼ
				int random = new Random().nextInt(100);
				if (firechance > random) {
					((Player) e.getEntity()).setFireTicks(5);
					Plugin ppa = Bukkit.getPluginManager().getPlugin("TheWar");
					final int dd = firedamage;
					final int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(ppa, new Runnable() {
						@Override
						public void run() {
							if (((Player) e.getEntity()) != null) {
								((Player) e.getEntity()).damage(dd);
							}
						}
					}, 0L, ((Player) e.getEntity()).getFireTicks() / 5);
					Bukkit.getScheduler().scheduleSyncDelayedTask(ppa, new Runnable() {
						@Override
						public void run() {
							Bukkit.getScheduler().cancelTask(task);
						}
					}, ((Player) e.getEntity()).getFireTicks());
				}

			}
		}
	}

	/*
	 * ���ʯ
	 */
	public int respawn(Player p) {
		BaoshiInfo bi = TheWar.playerinfo.get(p).getbi();
		List<Baoshi> bs = bi.getbaoshis();
		for (Baoshi b : bs) {
			if (b.getname().equalsIgnoreCase("���ʯ")) {
				ConfigurationSection sc = baoshi.getConfigurationSection("���ʯ")
						.getConfigurationSection(String.valueOf(b.getlevel()));
				return 200 - sc.getInt("cd") * 20;
			}
		}
		return 200;
	}

	// ���ٱ�ʯ
	public double dspeed(Player p) {
		BaoshiInfo bi = TheWar.playerinfo.get(p).getbi();
		List<Baoshi> bs = bi.getbaoshis();
		double k = 0;
		for (Baoshi b : bs) {
			if (b.getname().equalsIgnoreCase("�ؾ���ʯ")) {
				ConfigurationSection sc = baoshi.getConfigurationSection("�ؾ���ʯ")
						.getConfigurationSection(String.valueOf(b.getlevel()));
				k = k + sc.getDouble("cd");
			}
		}
		return 1000.0 - k * 1000;
	}

	public void setMaxHealth(Player p) {
		int hp = 0;
		BaoshiInfo bi = TheWar.playerinfo.get(p).getbi();
		List<Baoshi> bs = bi.getbaoshis();
		for (Baoshi b : bs) {
			if (b.getname().equalsIgnoreCase("������ʯ")) {
				hp = hp + b.getlevel() * 4;
			}
		}
		p.setMaxHealth(20 + hp);
	}
}
