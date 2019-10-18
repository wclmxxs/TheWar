package main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import team.Baoshi;
import team.PlayerInfo;
import team.TeamInfo;

public class ScoreboardManager
{


	public static String[] colorcodes = { "&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&a", "&b", "&c",
			"&d", "&e", "&f" };
  public static void createScoreboard(Player player)
  {
    Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();

    Objective o = board.registerNewObjective("Scoreboard", "dummy");

		o.setDisplayName("§e种类和品质");

    o.setDisplaySlot(DisplaySlot.SIDEBAR);

		List<String> text = new ArrayList();

    int size = text.size();
		TeamInfo ti = TheWar.playerteam.get(player.getName());
		PlayerInfo pi = TheWar.playerinfo.get(player);
		text.add("§6您的队伍: " + ti.getname());
		int d = 0;
		if (ti.isdeeppoint()) {
			d = 1;
		}
		int h = TheWar.config.getInt("add") + d * TheWar.config.getInt("deeppoint")
				+ ti.getpoint() * TheWar.config.getInt("point");
		text.add("§6每" + TheWar.config.getInt("time") + "秒点数: " + h);
		text.add("§e当前点数: " + pi.getpoint());
		text.add("§6队友人数: " + ti.getplayers().size());
		text.add("§6村民血量:  " + pi.gethealth() + "/" + pi.getzombie().getMaxHealth());
		text.add("§6您的职业: " + TheWar.pjob.get(player).getName());
		text.add("§6当前佩戴的宝石: ");
		for (Object bb : pi.getbi().getbaoshis()) {
			Baoshi b = (Baoshi) bb;
			if (b != null) {
				text.add(b.getname() + " §a--Lv." + b.getlevel());
			}
		}
		text.add("§6当前生效多宝石buff");
		String name = null;
		for (int i = 0; i < pi.getints().length; i++) {
			if (i == 0) {
				name = "§f白级";
			}
			if (i == 1) {
				name = "§a绿级";
			}
			if (i == 2) {
				name = "§b蓝级";
			}
			if (i == 3) {
				name = "§e黄级";
			}
			if (i == 4) {
				name = "§5紫级";
			}
			if (pi.getints()[i] != 0) {
			text.add(name + "buff级别: " + pi.getints()[i]);
			}
		}
		for (int i = 0; i < pi.getkints().length; i++) {
			if (i == 0) {
				name = "§6进攻";
			}
			if (i == 1) {
				name = "§6守备";
			}
			if (i == 2) {
				name = "§6爆发";
			}
			if (i == 3) {
				name = "§6功能";
			}
			if (i == 4) {
				name = "§6持久";
			}
			if (pi.getkints()[i] != 0) {
			text.add(name + "buff级别: " + pi.getkints()[i]);
			}
		}
    String f = "";

    for (String s : text)
    {


			f = s;

        int currentLine = size - 1;

        if ((currentLine <= 15) && (currentLine-- > 0))
        {
          f = f + colorcodes[(currentLine--)];
        }

        o.getScore(ChatColor.translateAlternateColorCodes('&', f)).setScore(--size);

    }

    player.setScoreboard(board);
  }
}