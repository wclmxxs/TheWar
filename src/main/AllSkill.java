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

	public static void �����ٶ�(Player p) {

	}

	public static void ����ʹ��(Player p, Player target) {
		target.setHealth(target.getMaxHealth());
		target.sendMessage("�����Ѿ�������,�ظ���: " + p.getName());

	}

	public static void ǧ������(Player p, Player target) {
		target.sendMessage("����������,ʧȥ�ƶ���ʹ�ü�������");

	}

	public static void ���¿�(Player p) {
		// TODO Auto-generated method stub

	}

	public static void ��ǰ���(Player p, int duration) {
		p.getVelocity().add(p.getLocation().getDirection().multiply(duration));

	}
}
