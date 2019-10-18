package gaming;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.TheWar;
import team.PlayerInfo;

public class gamestart {

	public static int i = 10;
	private static int number;
	private static boolean kk;

	public void start() {

		FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/TheWar/config.yml"));
		number = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("TheWar"),
				new Runnable() {
			@Override
			public void run() {

				if (i > 0) {

					if (main.TheWar.playerteam.size() >= config.getInt("min")) {
								for (String ps : TheWar.playerteam.keySet()) {
									Player p = Bukkit.getPlayer(ps);
						p.sendMessage("§a游戏将在" + i + "s后开始.");
								}
					} else {
								if (main.TheWar.isgame) {
									for (String ps : TheWar.playerteam.keySet()) {
										Player p = Bukkit.getPlayer(ps);
						p.sendMessage("§a游戏人数不足" + config.getInt("min") + "人,游戏取消.");
								}
								}
						main.TheWar.isgame = false;
						return;
					}

				}
				if (i == 0) {
							kk = false;
							for (String ps : TheWar.playerteam.keySet()) {
								Player p = Bukkit.getPlayer(ps);
								PlayerInfo pi = new PlayerInfo(p.getName());
								main.TheWar.playerinfo.put(p, pi);
								p.sendMessage("§e游戏开始了.");
								if (!TheWar.isgaming.contains(TheWar.playerteam.get(p.getName()))) {
									TheWar.isgaming.add(TheWar.playerteam.get(p.getName()));
								}
								new main.joingame().join(p);
							}
							new baoshieffect.buff().onHeal();
							kk = true;

				}


				i--;
			}
		}, 0L, 20L);

	}

	public void endstart() {
		if (kk = true) {
		Bukkit.getScheduler().cancelTask(number);
		}
	}
}
