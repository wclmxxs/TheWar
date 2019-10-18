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
		if (skill.containsKey(p) && skill.get(p).equalsIgnoreCase("ǧ������")) {
			return true;
		} else {
			return false;
		}
	}

	public double AFunction(Player d, Player en, double damage) {

		switch (name) {
		case "�����ٶ�":
			if (skill.containsKey(d) && skill.get(d).equalsIgnoreCase("�����ٶ�")) {
				if (d.getItemInHand() != null && d.getItemInHand().getTypeId() == 71) {
					damage = 0;
				}
			}
			break;
		case "���¿�":
			if (skill.containsKey(d) && skill.get(d).equalsIgnoreCase("���¿�")) {
				damage = 2 * damage;
			}
			break;

		}
		return damage;
	}

	public double DFunction(Player d, Player en, double damage) {
		switch (name) {
		case "�����ٶ�":
			if (skill.containsKey(en) && skill.get(en).equalsIgnoreCase("�����ٶ�")) {
				if (en.getItemInHand() != null && en.getItemInHand().getTypeId() == 71) {
					damage = 0;
				}
			}
			break;
		case "���¿�":
			if (skill.containsKey(en) && skill.get(en).equalsIgnoreCase("���¿�")) {
				damage = damage * 1.5;
			}
			break;
		}
		return damage;
	}

	public void RFunction(Player p, Object target, ItemStack item) {
		if (isCooldown() != 0) {
			p.sendMessage("�������޷�ʹ�ü���,��ȴ: " + isCooldown());
			return;
		}
		if (skill.containsKey(p) && skill.get(p).equals("ǧ������")) {
			p.sendMessage("����������,�޷�ʹ�ü���");
			return;
		}
		switch (name) {
		case "��������":
			AllSkill.jcjn(p);
			break;
		case "�����ٶ�":
			skill.put(p, "�����ٶ�");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("TheWar"),
					new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							skill.remove(p);
						}

					}, duration);
			break;
		case "����ʹ��":
			if (target != null) {
				AllSkill.����ʹ��(p, (Player) target);
			}
			break;
		case "ǧ������":
			if (target != null) {
				AllSkill.ǧ������(p, (Player) target);
				skill.put((Player) target, "ǧ������");
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
		case "���¿�":
			AllSkill.���¿�(p);
			skill.put(p, "���¿�");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("TheWar"),
					new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							skill.remove(target);
						}

					}, duration);
			break;
		case "��ǰ���":
			AllSkill.��ǰ���(p, duration);
			break;
		}
		Calendar c = Calendar.getInstance();
		time = c.get(Calendar.HOUR_OF_DAY) * 3600 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND);
		p.sendMessage("ʹ�óɹ�");
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
