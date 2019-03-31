package team;

import org.bukkit.entity.Player;

public class message {

	public void broadcast(Player p, TeamInfo ti, String a) {
		for (Player pp : ti.getplayers()) {
			pp.sendMessage(a);
		}

	}

}
