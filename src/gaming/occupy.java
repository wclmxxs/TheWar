package gaming;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import main.TheWar;
import net.md_5.bungee.api.ChatColor;
import team.TeamInfo;

public class occupy {

	public void occupy(Player p,Location loc, TeamInfo ti)
	{

		if(ti.isOccupy().equals(p))
		{
			/*
			 * ����,�ж��Ƿ��Ѿ���ռ��,����ռ����ö�- 1
			 * Ȼ��,��ռ��ɹ����¶��� + 1
			 * ����������ɫ���иı�
			 * 
			 */
			if (!TheWar.points.get(loc).getname().equalsIgnoreCase("mm"))
			{
				TeamInfo tid = TheWar.points.get(loc);
				tid.addpoint(-1);
				Bukkit.broadcastMessage(
						ti.getname() + "�����" + p.getName() + "ռ���� " + tid.getname() + ChatColor.RESET + "��һ���ݵ�");
				ti.setoccupy(null);
				TheWar.points.remove(loc);
				TheWar.points.put(loc, ti);
				ti.addpoint(1);
				Block block = loc.getBlock();
				// BlockState state = block.getState();
				// state.setData(new MaterialData(ti.getid()));
				// state.update();
				block.setData(ti.getid());
			}
 else {
				Bukkit.broadcastMessage(ti.getname() + "�����" + p.getName() + "ռ����һ�������ݵ�");
				ti.setoccupy(null);
				TheWar.points.remove(loc);
				TheWar.points.put(loc, ti);
				ti.addpoint(1);
				Block block = loc.getBlock();
				// BlockState state = block.getState();
				// state.setData(new MaterialData(ti.getid()));
				// state.update();
				block.setData(ti.getid());
			}
		}
	}

	public void occupydeep(Player p, Location loc, TeamInfo ti) {
		if (ti.isOccupy() == null || !ti.isOccupy().equals(p)) {
			return;
		}
		for (TeamInfo tii : TheWar.isgaming) {
			if (tii.isdeeppoint()) {
				tii.setdeeppoint(false);
			}
		}

		Bukkit.broadcastMessage(ti.getname() + "�����" + p.getName() + "ռ�������ľݵ�");
		ti.setoccupy(null);
		ti.setdeeppoint(true);
		Block block = loc.getBlock();
		// BlockState state = block.getState();
		// state.setData(new MaterialData(ti.getid()));
		// state.update();
		block.setData(ti.getid());

	}
}
