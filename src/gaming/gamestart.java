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
								for (Player p : TheWar.playerteam.keySet()) {
						p.sendMessage("��a��Ϸ����" + i + "s��ʼ.");
								}
					} else {
								for (Player p : TheWar.playerteam.keySet()) {
						p.sendMessage("��a��Ϸ��������" + config.getInt("min") + "��,��Ϸȡ��.");
								}
						main.TheWar.isgame = false;
						return;
					}

				}
				if (i == 0) {
							kk = false;
							for (Player p : TheWar.playerteam.keySet()) {
								PlayerInfo pi = new PlayerInfo(p.getName());
								main.TheWar.playerinfo.put(p, pi);
					p.sendMessage("��e��Ϸ��ʼ��.");

								new main.joingame().join(p);
							}

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