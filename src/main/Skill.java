package main;

import java.util.Calendar;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Skill {
	private static HashMap<Player, String> skill;
	private String name;
	private int duration;
	private int time;
	private int cooldown;

	public Skill(String name, int i, int j) {
		if (skill == null) {
			skill = new HashMap();
		}
		this.name = name;
		this.cooldown = i;
		this.duration = j;
	}

	public String getName() {

		return name;
	}

	public boolean MFunction(Player p) {
		if (skill.containsKey(p) && skill.get(p).equalsIgnoreCase("千钧束缚")) {
			return true;
		} else {
			return false;
		}
	}

	public double AFunction(Player d, Player en, double damage) {

		switch (name) {
		case "蟒王举盾":
			if (skill.containsKey(d) && skill.get(d).equalsIgnoreCase("蟒王举盾")) {
				if (d.getItemInHand() != null && d.getItemInHand().getTypeId() == 71) {
					damage = 0;
				}
			}
			break;
		case "极致狂化":
			if (skill.containsKey(d) && skill.get(d).equalsIgnoreCase("极致狂化")) {
				damage = 2 * damage;
			}
			break;

		}
		return damage;
	}

	public double DFunction(Player d, Player en, double damage) {
		switch (name) {
		case "蟒王举盾":
			if (skill.containsKey(en) && skill.get(en).equalsIgnoreCase("蟒王举盾")) {
				if (en.getItemInHand() != null && en.getItemInHand().getTypeId() == 71) {
					damage = 0;
				}
			}
			break;
		case "极致狂化":
			if (skill.containsKey(en) && skill.get(en).equalsIgnoreCase("极致狂化")) {
				damage = damage * 1.5;
			}
			break;
		}
		return damage;
	}

	public void RFunction(Player p, Object target, ItemStack item) {
		if (isCooldown() != 0) {
			p.sendMessage("您现在无法使用技能,冷却: " + isCooldown());
			return;
		}
		if (skill.containsKey(p) && skill.get(p).equals("千钧束缚")) {
			p.sendMessage("您正被束缚,无法使用技能");
			return;
		}
		switch (name) {
		case "基础技能":
			AllSkill.jcjn(p);
			break;
		case "蟒王举盾":
			skill.put(p, "蟒王举盾");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("TheWar"),
					new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							skill.remove(p);
						}

					}, duration);
			break;
		case "大天使术":
			if (target != null) {
				AllSkill.大天使术(p, (Player) target);
			}
			break;
		case "千钧束缚":
			if (target != null) {
				AllSkill.千钧束缚(p, (Player) target);
				skill.put((Player) target, "千钧束缚");
				Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("TheWar"),
						new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								skill.remove(target);
							}

						}, duration);
			}
			break;
		case "极致狂化":
			AllSkill.极致狂化(p);
			skill.put(p, "极致狂化");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("TheWar"),
					new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							skill.remove(target);
						}

					}, duration);
			break;
		case "向前冲锋":
			AllSkill.向前冲锋(p, duration);
			break;
		}
		Calendar c = Calendar.getInstance();
		time = c.get(Calendar.HOUR_OF_DAY) * 3600 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND);
		p.sendMessage("使用成功");
	}

	public int isCooldown() {
		Calendar c = Calendar.getInstance();
		int currenttime = c.get(Calendar.HOUR_OF_DAY) * 3600 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND);
		if (currenttime - time < 0 || currenttime - time >= cooldown) {
			return 0;
		} else {
			return cooldown - (currenttime - time);
		}
	}

	public void setTime() {
		Calendar c = Calendar.getInstance();
		time = c.get(Calendar.HOUR_OF_DAY) * 3600 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND);
	}

	public int getDuration() {
		return duration;
	}

}
