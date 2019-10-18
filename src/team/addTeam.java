package team;

import java.util.Arrays;

import org.bukkit.entity.Player;

public class addTeam {

	public TeamInfo red = main.TheWar.red;
	public TeamInfo green = main.TheWar.green;
	public TeamInfo blue = main.TheWar.blue;
	public TeamInfo yellow = main.TheWar.yellow;

	public TeamInfo getminSizeTeam() {
		TeamInfo teaminfo = null;
		Integer[] size = { red.getplayers().size(), blue.getplayers().size(), green.getplayers().size(),
				yellow.getplayers().size() };
		Arrays.sort(size);
		TeamInfo[] teams = { red, green, blue, yellow };
		for (TeamInfo team : teams) {
			if (team.getplayers().size() == size[0]) {
				return team;
			}
		}
		return teaminfo;
	}

	public void addteam(Player p, TeamInfo team) {

		Integer[] size = { red.getplayers().size(), blue.getplayers().size(), green.getplayers().size(),
				yellow.getplayers().size() };
		Arrays.sort(size);
		if (team.getplayers().size() - 1 >= size[0]) {
			p.sendMessage("该队伍人数过多,或者有一个队伍人数过少");
			return;
		}
		if (main.TheWar.playerteam.containsKey(p.getName())) {
			main.TheWar.playerteam.get(p.getName()).removeplayer(p);
			main.TheWar.playerteam.remove(p.getName());
		}
		team.addplayer(p);
		main.TheWar.playerteam.put(p.getName(), team);
		p.sendMessage("§6加入队伍成功: " + team.getname());
		new tag.setname().setScoreboard(p, team.getname());
	}
}
