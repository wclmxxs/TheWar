package gaming;

import java.util.List;

import org.bukkit.entity.Player;

import team.Baoshi;
import team.BaoshiInfo;

public class checkbaoshi {

	public void check(BaoshiInfo bi, Baoshi baoshi, Player p) {
		int amount = 0;
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
			p.sendMessage("����" + baoshi.getname() + "��ʯ�ɹ����ڵȼ�Ϊ: " + baoshi.getlevel());
			check(bi, baoshi, p);

		}

	}

}
