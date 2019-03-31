package gaming;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import main.TheWar;
import team.PlayerInfo;
import team.TeamInfo;

public class addpoint {

	public void addpoint(FileConfiguration config, Player p) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("TheWar"), new Runnable() {
			@Override
			public void run() {


					PlayerInfo pi = TheWar.playerinfo.get(p);
					TeamInfo ti = TheWar.playerteam.get(p);
					int k = 0;
					if (ti.isdeeppoint()) {
						k = config.getInt("deeppoint");
					}
					int kk = config.getInt("point") * ti.getpoint();
					pi.addpoint(config.getInt("add") + kk + k);


			}
		}, 0L, config.getInt("time") * 20L);
	}
}
