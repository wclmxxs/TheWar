package main;

import org.bukkit.entity.Player;

public class AllSkill {

	public static void jcjn(Player p) {
		if (p.getHealth() + 5 <= p.getMaxHealth()) {
			p.setHealth(p.getHealth() + 5);
		} else {
			p.setHealth(p.getMaxHealth());
		}
	}

	public static void 蟒王举盾(Player p) {

	}

	public static void 大天使术(Player p, Player target) {
		target.setHealth(target.getMaxHealth());
		target.sendMessage("生命已经被回满,回复者: " + p.getName());

	}

	public static void 千钧束缚(Player p, Player target) {
		target.sendMessage("您被束缚了,失去移动和使用技能能力");

	}

	public static void 极致狂化(Player p) {
		// TODO Auto-generated method stub

	}

	public static void 向前冲锋(Player p, int duration) {
		p.getVelocity().add(p.getLocation().getDirection().multiply(duration));

	}
}
