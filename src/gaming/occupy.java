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
			 * 首先,判断是否已经被占领,若被占领则该队- 1
			 * 然后,若占领成功则新队伍 + 1
			 * 将玻璃的颜色进行改变
			 * 
			 */
			if (!TheWar.points.get(loc).getname().equalsIgnoreCase("mm"))
			{
				TeamInfo tid = TheWar.points.get(loc);
				tid.addpoint(-1);
				Bukkit.broadcastMessage(
						ti.getname() + "队伍的" + p.getName() + "占领了 " + tid.getname() + ChatColor.RESET + "的一个据点");
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
				Bukkit.broadcastMessage(ti.getname() + "队伍的" + p.getName() + "占领了一个无主据点");
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

		Bukkit.broadcastMessage(ti.getname() + "队伍的" + p.getName() + "占领了中心据点");
		ti.setoccupy(null);
		ti.setdeeppoint(true);
		Block block = loc.getBlock();
		// BlockState state = block.getState();
		// state.setData(new MaterialData(ti.getid()));
		// state.update();
		block.setData(ti.getid());

	}
}
