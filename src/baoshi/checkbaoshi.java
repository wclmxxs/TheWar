package baoshi;

import java.util.List;

import org.bukkit.entity.Player;

import team.Baoshi;
import team.BaoshiInfo;

public class checkbaoshi {

	public void check(BaoshiInfo bi, Baoshi baoshi, Player p) {
		int amount = 0;
		if (baoshi.getlevel() == 3) {
			return;
		}
		List<Baoshi> wait = bi.getwaits();
		for (Baoshi b1 : wait) {
			if (b1.equals(baoshi)) {
				amount = amount + 1;
			}
		}
		if (amount == 3) {
			wait.remove(baoshi);
			baoshi.addlevel();
			wait.add(baoshi);
			bi.setwaits(wait);
			p.sendMessage("升级" + baoshi.getname() + "宝石成功现在等级为: " + baoshi.getlevel());
			check(bi, baoshi, p);

		}

	}

	public void checkbaoshi(BaoshiInfo bi, Baoshi baoshi, Player p) {
		int amount = 0;
		if (baoshi.getlevel() == 3) {
			return;
		}
		List<Baoshi> baoshis = bi.getbaoshis();
		for (Baoshi b1 : baoshis) {
			if (b1.equals(baoshi)) {
				amount = amount + 1;
			}
		}
		if (amount == 3) {
			baoshis.remove(baoshi);
			baoshi.addlevel();
			baoshis.add(baoshi);
			bi.setbaoshis(baoshis);
			p.sendMessage("升级" + baoshi.getname() + "宝石成功现在等级为: " + baoshi.getlevel());
			checkbaoshi(bi, baoshi, p);

		}

	}

}
