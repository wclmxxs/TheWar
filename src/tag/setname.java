package tag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import tag.teams.team;

public class setname {
	private static Scoreboard sc;

	public void init()
	  {
	    this.sc = Bukkit.getScoreboardManager().getNewScoreboard();
		for (team team : team.values())
	    {
			String name = team.getname();
			this.sc.registerNewTeam(name);
			this.sc.getTeam(name).setPrefix(name);
	    }
	  }

	public void setScoreboard(Player p, String name) {

		String team = name;
		Team t = this.sc.getTeam(team);
		t.addPlayer(p);
		p.setPlayerListName(t.getPrefix() + p.getName() + ChatColor.RESET);
		p.setCustomNameVisible(true);
		p.setCustomName(t.getPrefix() + p.getName() + ChatColor.RESET);
		p.setDisplayName(t.getPrefix() + p.getName() + ChatColor.RESET);
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.setScoreboard(this.sc);
		}
	}
}
