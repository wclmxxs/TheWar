package baoshi;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.TheWar;
import team.Baoshi;
import team.BaoshiInfo;

public class baoshievent {
	public void onClick(InventoryClickEvent e) {
		e.setCancelled(true);
		ItemStack item = e.getCurrentItem();
		if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()
				|| !item.getItemMeta().hasLore()) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
		BaoshiInfo bi = TheWar.playerinfo.get(p).getbi();

		if (e.getClick().equals(ClickType.RIGHT)) {

		if (e.getInventory().getName().contains("�ȴ���")) {
				List<Baoshi> waits = bi.getwaits();
				String lore = item.getItemMeta().getLore().toString();
				String name = item.getItemMeta().getDisplayName();
				int level = Integer.valueOf(lore.split("�ȼ�: ")[1].split("[")[1].split("]")[0]);

				for (int i = 0; i < waits.size(); i++) {
					Baoshi bb = waits.get(i);
					if (bb.getname().equals(name) && bb.getlevel() == level) {
						int price = waits.get(i).getprice();
						waits.remove(i);
						p.sendMessage("�����ɹ�");
						TheWar.playerinfo.get(p).addpoint(price);
						bi.setwaits(waits);
						e.getInventory().remove(e.getSlot());
						p.updateInventory();
						break;
					}
				}
		}
		if (e.getInventory().getName().contains("�ϳ���")) {
				List<Baoshi> baoshis = bi.getbaoshis();
				String lore = item.getItemMeta().getLore().toString();
				String name = item.getItemMeta().getDisplayName();
				int level = Integer.valueOf(lore.split("�ȼ�: ")[1].split("[")[1].split("]")[0]);

				for (int i = 0; i < baoshis.size(); i++) {
					Baoshi bb = baoshis.get(i);
					if (bb.getname().equals(name) && bb.getlevel() == level) {
						int price = baoshis.get(i).getprice();
						baoshis.remove(i);
						p.sendMessage("�����ɹ�");
						TheWar.playerinfo.get(p).addpoint(price);
						bi.setbaoshis(baoshis);
						e.getInventory().remove(e.getSlot());
						p.updateInventory();
						break;
					}
				}
			}
		}
		if (e.getClick().equals(ClickType.LEFT)) {
			List<Baoshi> waits = bi.getwaits();
			List<Baoshi> baoshis = bi.getbaoshis();
			if (e.getInventory().getName().contains("�ȴ���")) {

				if (baoshis.size() >= TheWar.playerinfo.get(p).getlevel()) {
					p.sendMessage("����ǰ�ĵȼ�ֻ���ϳ�" + TheWar.playerinfo.get(p).getlevel() + "����ʯ");
					return;
				}
				String lore = item.getItemMeta().getLore().toString();
				String name = item.getItemMeta().getDisplayName();
				int level = Integer.valueOf(lore.split("�ȼ�: ")[1].split("[")[1].split("]")[0]);

				for (int i = 0; i < waits.size(); i++) {
					Baoshi bb = waits.get(i);
					if (bb.getname().equals(name) && bb.getlevel() == level) {
						waits.remove(i);
						baoshis.add(bb);
						bi.setwaits(waits);
						bi.setbaoshis(baoshis);
						new baoshi.checkbaoshi().checkbaoshi(bi, bb, p);
						p.closeInventory();
						Inventory inv = new baoshiinv().getwait(Bukkit.createInventory(null, 9, "��ʯ�ȴ���"), p);
						p.openInventory(inv);
					}
				}

			}
			if (e.getInventory().getName().contains("�ϳ���")) {
				if (waits.size() >= TheWar.config.getInt("wait")) {
					p.sendMessage("����ǰ�ĵȼ�ֻ��ӵ��" + TheWar.playerinfo.get(p).getlevel() + "����ʯ�ڵȴ���");
					return;
				}
				String lore = item.getItemMeta().getLore().toString();
				String name = item.getItemMeta().getDisplayName();
				int level = Integer.valueOf(lore.split("�ȼ�: ")[1].split("[")[1].split("]")[0]);

				for (int i = 0; i < baoshis.size(); i++) {
					Baoshi bb = baoshis.get(i);
					if (bb.getname().equals(name) && bb.getlevel() == level) {
						baoshis.remove(i);
						waits.add(bb);
						bi.setwaits(waits);
						bi.setbaoshis(baoshis);
						new baoshi.checkbaoshi().check(bi, bb, p);
						p.closeInventory();
						Inventory inv = new baoshiinv().getwait(Bukkit.createInventory(null, 9, "��ʯ�ϳ���"), p);
						p.openInventory(inv);
					}
				}
			}
		}
	}
}
