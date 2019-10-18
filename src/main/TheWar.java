package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ChatColor;
import team.PlayerInfo;
import team.TeamInfo;

public class TheWar extends JavaPlugin implements Listener {

	public static TeamInfo red;
	public static TeamInfo blue;
	public static TeamInfo green;
	public static TeamInfo yellow;
	public static TeamInfo tt;
	public static HashMap<String, TeamInfo> playerteam;
	public static HashMap<Player, PlayerInfo> playerinfo;
	public static List<Player> gaming;
	public static List<TeamInfo> isgaming;
	public static boolean isgame;
	public static FileConfiguration config;
	public static HashMap<Location, TeamInfo> points;
	public static Location deeppoint;
	public static List<Player> heal;
	public static List<Player> addheal;
	public static List<Player> white;
	public static List<Player> attack;
	public static List<Player> protect;
	public static List<Player> critical;
	public static List<Player> function;
	public static List<Player> persistent;
	public static List<Job> jobs;
	public static List<Skill> skills;
	public static HashMap<Player, Job> pjob;

	@Override
	public void onEnable() {
		File file = new File("plugins/TheWar/Job");
		if (!file.exists()) {
			file.mkdir();
		}
		saveDefaultConfig();
		this.config = getConfig();
		skills = new ArrayList();
		jobs = new ArrayList();
		pjob = new HashMap();
		gui.job.createskill();
		gui.job.createjob();

		red = new TeamInfo("§c[红队]", (byte) 14);
		green = new TeamInfo("§a[绿队]", (byte) 5);
		blue = new TeamInfo("§b[蓝队]", (byte) 3);
		yellow = new TeamInfo("§e[黄队]", (byte) 4);
		tt = new TeamInfo("mm", (byte) 0);
		isgaming = new ArrayList();
		heal = new ArrayList();
		white = new ArrayList();
		addheal = new ArrayList();
		attack = new ArrayList();
		protect = new ArrayList();
		critical = new ArrayList();
		function = new ArrayList();
		persistent = new ArrayList();
		isgame = false;
		playerteam = new HashMap();
		playerinfo = new HashMap();
		gaming = new ArrayList();
		points = new HashMap();
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		getCommand("thewar").setExecutor(this);
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("游戏已经载入,四个队伍已经载入");
		getLogger().info("作者:Mxxs");
		getLogger().info("QQ:2040005066");
		if (!config.contains("deeppoint") || !config.contains("points")) {
			getLogger().info("点还未设置");
		} else {
			deeppoint = (Location) config.get("deeppoint");
			List<Location> point1 = (List<Location>) config.getList("points");
			for (Location loc : point1) {
				points.put(loc, tt);
			}
		}

		if (new main.loadschematic()
				.loadIslandSchematic(YamlConfiguration.loadConfiguration(new File("plugins/TheWar/map.yml")))) {
				getLogger().info("地图载入成功");
			} else {
				getLogger().info("地图载入失败.....");
			}
		if (new gaming.init().init()) {
			getLogger().info("nametag初始化成功,村民清除成功");
		}

		if (new gaming.init().init()) {
			getLogger().info("侧边栏开启");
		}
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (args.length == 0 && s.isOp()) {
			s.sendMessage("/thewar join random/red/green/blue/yellow  加入游戏");
			s.sendMessage("/thewar job create <name>       创建一个职业");
			s.sendMessage("/thewar job delete <name>       删除一个职业");
			s.sendMessage("/thewar job skill <name> <skill>   为该职业添加一个技能,玩家右键使用");
			s.sendMessage("/thewar job damage <name> <amount>      给该职业攻击加成,可以为负数");
			s.sendMessage("/thewar job defend <name> <amount>      给该职业防御加成,可以为负数");
			s.sendMessage("/thewar job items <name>        设置背包内物品为该职业物品,将在玩家重生时给予");
			s.sendMessage("/thewar setspawn 设置等待游戏出生点");
			s.sendMessage("/thewar setred 红队出生点");
			s.sendMessage("/thewar setgreen  绿队出生点");
			s.sendMessage("/thewar setblue  蓝队出生点");
			s.sendMessage("/thewar setyellow  黄队出生点");
			s.sendMessage("/thewar setpoint  设置脚下为可占点,可以有多个");
			s.sendMessage("/thewar setdeeppoint  设置脚下为超级点,只能有一个");
			s.sendMessage("/thewar save  用木棍左键一点再右键一点后保存地图");
			s.sendMessage("/thewar open [shop] 打开商店");
			s.sendMessage("/thewar set [shop] [size]    将你背包内的物品设置为商店");
			s.sendMessage(
					"Name\n打开商店: shop\n宝石\n宝石等待区\n宝石上场区\nLore\n价值点数: amount\n奖励类型: item/baoshi/level\n宝石种类: [kind]\n宝石品质: [quality]");
		}
		if ((args.length == 3 || args.length == 4) && args[0].equalsIgnoreCase("job") && s.isOp()) {
			String job = args[2];
			String value = "";
			if (args.length == 4) {
				value = args[3];
			}
			FileConfiguration fjob = YamlConfiguration
					.loadConfiguration(new File("plugins/TheWar/Job/" + job + ".yml"));
			switch (args[1]) {
			case "create":
				if (fjob.contains("name")) {
					s.sendMessage("该职业已经存在");
					return true;
				} else {
					s.sendMessage("创建成功");
					fjob.set("name", job);
				}
				break;
			case "delete":
				if (!fjob.contains("name")) {
					s.sendMessage("该职业不存在");
					return true;
				} else {
					new File("plugins/TheWar/Job/" + job + ".yml").delete();
					s.sendMessage("删除成功");
				}
				break;
			case "skill":
				fjob.set("skill", value);
				s.sendMessage("设置成功");
				break;
			case "damage":
				fjob.set("damage", value);
				s.sendMessage("设置成功");
				break;
			case "defend":
				fjob.set("defend", value);
				s.sendMessage("设置成功");
				break;
			case "items":
				List<ItemStack> list = new ArrayList();
				for (int i = 0; i < ((Player) s).getInventory().getSize(); i++) {
					if (((Player) s).getInventory().getItem(i) != null) {
						list.add(((Player) s).getInventory().getItem(i));
					}
				}
				fjob.set("items", list);
				s.sendMessage("设置成功");
				break;
			}
			try {
				fjob.save(new File("plugins/TheWar/Job/" + job + ".yml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (args.length != 0 && (args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("set"))) {
			new gui.shopcmd().cmd((Player) s, args);
		}
		if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
			TeamInfo team = null;
			if (args[1].equalsIgnoreCase("random")) {
				team = new team.addTeam().getminSizeTeam();

			}
			if (args[1].equalsIgnoreCase("red")) {
				team = red;
			}
			if (args[1].equalsIgnoreCase("green")) {
				team = green;
			}
			if (args[1].equalsIgnoreCase("blue")) {
				team = blue;
			}
			if (args[1].equalsIgnoreCase("yellow")) {
				team = yellow;
			}

			new team.addTeam().addteam((Player) s, team);
			for (String ps : playerteam.keySet()) {
				Player p = Bukkit.getPlayer(ps);
				p.sendMessage("§e当前游戏人数: " + playerteam.size() + "/" + config.getInt("max"));
			}
			if (playerteam.size() >= config.getInt("min") && !isgame) {
				for (String ps : playerteam.keySet()) {
					Player p = Bukkit.getPlayer(ps);
					p.sendMessage("§a游戏人数已经达到" + config.getInt("min") + "人,将在60秒后开始..");

				}
				isgame = true;

				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {

						new gaming.gamestart().start();

					}
				}, 50 * 20L);

				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

					@Override
					public void run() {

						new gaming.gamestart().endstart();

					}
				}, 70 * 20L);

			}
		}
		if (args.length == 1 && s.isOp()) {
			new main.setpoints().set(s, args, config);
		}
		return true;
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		new main.zombiedamaged().quit(e, config);

		if (playerteam.containsKey(e.getPlayer().getName())) {
			playerteam.remove(e.getPlayer().getName());
		}
		if(red.getplayers().contains(e.getPlayer()))
		{
			red.removeplayer(e.getPlayer());
		}
		if(green.getplayers().contains(e.getPlayer()))
		{
			green.removeplayer(e.getPlayer());
		}
		if(blue.getplayers().contains(e.getPlayer()))
		{
			blue.removeplayer(e.getPlayer());
		}
		if(yellow.getplayers().contains(e.getPlayer()))
		{
			yellow.removeplayer(e.getPlayer());
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e) {
		new baoshieffect.event().onInteract(e);
		if (e.getRightClicked() != null && e.getRightClicked() instanceof Villager) {
			if (playerteam.get(Bukkit.getPlayer(e.getRightClicked().getCustomName()).getName()).getplayers()
					.contains(e.getPlayer())) {
				Bukkit.dispatchCommand(e.getPlayer(), "thewar open base");
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

		if (e.getPlayer().isOp() && e.getPlayer().getItemInHand() != null
				&& e.getPlayer().getItemInHand().getTypeId() == 280
				&& e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			main.setpoints.loc2 = e.getClickedBlock().getLocation();
			e.getPlayer().sendMessage("loc2设置成功");
		}
		if (e.getPlayer().isOp() && e.getPlayer().getItemInHand() != null
				&& e.getPlayer().getItemInHand().getTypeId() == 280
				&& e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			main.setpoints.loc1 = e.getClickedBlock().getLocation();
			e.getPlayer().sendMessage("loc1设置成功");
		}
		if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()
				&& e.getItem().getItemMeta().getDisplayName().contains("§e选择队伍")) {
			Inventory inv = new gui.team().teaminv();
			e.getPlayer().openInventory(inv);
		}
		if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()
				&& e.getItem().getItemMeta().getDisplayName().contains("§a选择职业")) {
			Inventory inv = new gui.job().jobinv();
			e.getPlayer().openInventory(inv);
		}
		if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()
				&& e.getItem().getItemMeta().getDisplayName().contains("回到大厅")) {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF(config.getString("hubserver"));
			((PluginMessageRecipient) e.getPlayer()).sendPluginMessage(this, "BungeeCord", out.toByteArray());
		}

	}

	@EventHandler
	public void onOpen(InventoryOpenEvent e) {
		if (e.getInventory().getName().contains("villager")) {
			e.setCancelled(true);
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

				@Override
				public void run() {
					e.getPlayer().closeInventory();
					Bukkit.dispatchCommand(e.getPlayer(), "thewar open base");

				}
			}, 2L);

		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (!config.getBoolean("game")) {
			return;
		}
		Player p = e.getPlayer();
		TeamInfo ti = TheWar.playerteam.get(p.getName());
		Block from = e.getFrom().getBlock().getRelative(BlockFace.DOWN);
		Block to = e.getTo().getBlock().getRelative(BlockFace.DOWN);
		if(!points.containsKey(from.getLocation())&&
				!points.containsKey(to.getLocation())&&
				!deeppoint.equals(from.getLocation())&&
				!deeppoint.equals(to.getLocation()))
		{
			return;
		}
		if (deeppoint.equals(to.getLocation()) || deeppoint.equals(from.getLocation())) {
			if (ti.isOccupy() == null && to.getTypeId() == 95) {
				if (TheWar.playerteam.get(p.getName()).isdeeppoint()) {
					return;
				}
				p.sendMessage("您将在3秒后占领据点");
				ti.setoccupy(p);
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {

						new gaming.occupy().occupydeep(p, to.getLocation(), ti);

					}
				}, config.getInt("occupytime") * 20L);
			}
			if (ti.isOccupy() != null && ti.isOccupy().equals(p) && to.getTypeId() != 95) {
				ti.setoccupy(null);

				new team.message().broadcast(p, ti, p.getName() + "占领中心据点被中断");

			}
		}
		if (points.containsKey(from.getLocation()) || points.containsKey(to.getLocation())) {
		if (ti.isOccupy() == null && to.getTypeId() == 95)
		{
				if (TheWar.playerteam.containsKey(p.getName())
						&& TheWar.points.get(to.getLocation()).equals(TheWar.playerteam.get(p.getName()))) {
					return;
				}
				p.sendMessage("您将在3秒后占领据点");
			ti.setoccupy(p);
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				@Override
				public void run() {

					new gaming.occupy().occupy(p, to.getLocation(), ti);

				}
			}, config.getInt("occupytime") * 20L);
		}
		if (ti.isOccupy() != null && ti.isOccupy().equals(p) && to.getTypeId() != 95)
		{
			ti.setoccupy(null);
			new team.message().broadcast(p, ti, p.getName() + "占领据点被中断");
		}
		}
	}

	@EventHandler
	public void onInteractatair(PlayerInteractEvent e) {
		if (!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		pjob.get(e.getPlayer()).getSkill().RFunction(e.getPlayer(), null, e.getPlayer().getItemInHand());
	}

	@EventHandler
	public void onInteractatentity(PlayerInteractAtEntityEvent e) {
		if (e.getRightClicked() == null) {
			return;
		}
		pjob.get(e.getPlayer()).getSkill().RFunction(e.getPlayer(), e.getRightClicked(), e.getPlayer().getItemInHand());
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		Player en = null;
		Player d = null;
		if (e.getEntity() instanceof Player) {
			en = (Player) e.getEntity();
		}
		if (e.getDamager() instanceof Player) {
			d = (Player) e.getDamager();
		}
		if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
			d = (Player) ((Projectile) e.getDamager()).getShooter();
		}
		int add = 0;
		int reduce = 0;
		if (d != null) {
			add = pjob.get(d).getDamageadd();
		}
		if (en != null) {
			reduce = pjob.get(en).getDefendadd();
		}
		e.setDamage(e.getDamage() + add - reduce);
		if (d != null) {
			e.setDamage(pjob.get(d).getSkill().AFunction(d, en, e.getDamage()));
		}
		if (en != null) {
			e.setDamage(pjob.get(en).getSkill().DFunction(d, en, e.getDamage()));
		}
	}

	@EventHandler
	public void onMove1(PlayerMoveEvent e) {
		if (pjob.containsKey(e.getPlayer()) && pjob.get(e.getPlayer()).getSkill().MFunction(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getClickedInventory() != null && e.getInventory().getName().contains("商店")) {
			if (!e.getWhoClicked().isOp()) {
			e.setCancelled(true);
			new gui.shopevent().onClick(e);
			}

		}
		if (e.getInventory() != null && e.getInventory().getName().contains("宝石")) {
			new baoshi.baoshievent().onClick(e);
		}

		if (!e.getWhoClicked().isOp() && !gaming.contains(e.getWhoClicked())) {
			e.setCancelled(true);
		}
		if (e.getClickedInventory() != null
				&& e.getClickedInventory().getName().equalsIgnoreCase("点击选择队伍")) {
			e.setCancelled(true);

			if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()
					&& e.getCurrentItem().getItemMeta().hasDisplayName()
					&& e.getCurrentItem().getItemMeta().getDisplayName().contains(":")) {

				Bukkit.dispatchCommand(e.getWhoClicked(),
						"thewar join " + e.getCurrentItem().getItemMeta().getDisplayName().split(":")[1]);
				e.getWhoClicked().closeInventory();
			}


		}
		if (e.getClickedInventory() != null
				&& e.getClickedInventory().getName().equalsIgnoreCase("点击选择职业")) {
			if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()
					&& e.getCurrentItem().getItemMeta().hasDisplayName()) {
				for (Job j : jobs) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(j.getName())) {
						pjob.put((Player) e.getWhoClicked(), j);
						e.getWhoClicked().sendMessage("切换职业成功: " + j.getName());
					}
				}
			}

			e.setCancelled(true);
		}
	}

	@EventHandler
	public void join(PlayerJoinEvent e) {
		pjob.put(e.getPlayer(), jobs.get(0));
		if (!config.getBoolean("game")) {
			return;
		}
		e.setJoinMessage(null);
		e.getPlayer().getInventory().clear();
		Bukkit.dispatchCommand(e.getPlayer(), "thewar join random");
		if (config.contains("spawn")) {
			e.getPlayer().teleport((Location) config.get("spawn"));
		}
		if (gaming.size() != 0) {
			e.getPlayer().sendMessage("游戏已经开始,您暂时无法参与");
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF("hub1");
			((PluginMessageRecipient) e.getPlayer()).sendPluginMessage(this, "BungeeCord", out.toByteArray());
		}
		{
			ItemStack item = new ItemStack(339);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§e选择队伍§7右键");
			item.setItemMeta(meta);
			e.getPlayer().getInventory().setItem(0, item);
		}
		{
		ItemStack item = new ItemStack(341, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§a回到大厅§7右键");
		item.setItemMeta(meta);
		e.getPlayer().getInventory().setItem(8, item);
		}
		{
			ItemStack item = new ItemStack(276, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§a选择职业§7右键");
			item.setItemMeta(meta);
			e.getPlayer().getInventory().setItem(1, item);
		}

	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Location loc = e.getBlock().getLocation();
		String a = loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ() + ":" + e.getBlock().getTypeId();
		if (!e.getPlayer().isOp() && config.getStringList("blocks").contains(a)) {
			e.setCancelled(true);
		}
		if (!e.getPlayer().isOp() && !gaming.contains(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}
		if ((e.getBlock().getLocation().getBlockY() >= config.getInt("block")
				|| e.getBlock().getLocation().getBlockY() <= config.getInt("low")) && !e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onplace(BlockPlaceEvent e) {
		if (!e.getPlayer().isOp() && !gaming.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
		Location loc = e.getBlock().getLocation();
		for (int j = 0; j < 5; j++) {
			if (loc.add(0, -1, 0).getBlock().getTypeId() == 138) {
				e.setCancelled(true);
			}
		}
		if ((e.getBlock().getLocation().getBlockY() >= config.getInt("block")
				|| e.getBlock().getLocation().getBlockY() <= config.getInt("low")) && !e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void oncommand(PlayerCommandPreprocessEvent e) {
		if (gaming.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void ondeath(PlayerDeathEvent e) {
		Player d = null;
		if (e.getEntity().getKiller() instanceof Player) {
			d = e.getEntity().getKiller();
		}
		if (e.getEntity().getKiller() instanceof Projectile
				&& ((Projectile) e.getEntity().getKiller()).getShooter() instanceof Player) {
			d = (Player) ((Projectile) e.getEntity().getKiller()).getShooter();
		}
		if (d != null) {
			String name = ChatColor.RED + "武器";
			if (d.getItemInHand() != null && d.getItemInHand().hasItemMeta()
					&& d.getItemInHand().getItemMeta().hasDisplayName()) {
				name = d.getItemInHand().getItemMeta().getDisplayName();
			}
			e.setDeathMessage(d.getName() + "使用" + name + "击杀" + e.getEntity().getName());
		}
		e.getEntity().spigot().respawn();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				if (gaming.contains(e.getEntity()))
			{
					e.getEntity().teleport((Location) config.get("spawn"));

					spawn(e.getEntity());
			}
 else {
					e.getEntity().teleport((Location) config.get("spawn"));
				}
				e.getEntity().getInventory().clear();
			}
		}, 15L);

	}

	protected void spawn(Player p) {
		int k = new baoshieffect.event().respawn(p);
		new main.ScoreboardManager().createScoreboard(p);
		p.sendMessage("您将在" + k / 20 + "秒后重生");
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				p.getInventory().clear();
				for (ItemStack item : TheWar.pjob.get(p).getItems()) {
					p.getInventory().addItem(item);
				}
				new tag.setname().setScoreboard(p, TheWar.playerteam.get(p.getName()).getname());
				p.setHealth(p.getMaxHealth());
				if (red.getplayers().contains(p)) {
					p.teleport((Location) config.get("red"));
		}
				if (green.getplayers().contains(p)) {
					p.teleport((Location) config.get("green"));
				}
				if (blue.getplayers().contains(p)) {
					p.teleport((Location) config.get("blue"));
				}
				if (yellow.getplayers().contains(p)) {
					p.teleport((Location) config.get("yellow"));
				}
				p.sendMessage("您已经重生");
				Calendar calendar = Calendar.getInstance();
				int time = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 + calendar.get(Calendar.MINUTE) * 60
						+ calendar.get(Calendar.SECOND);

				playerinfo.get(p).setrtime(time);

				if (gaming.contains(p)) {
					new baoshieffect.event().setMaxHealth(p);
					new baoshieffect.buff().kind(p);
					new baoshieffect.buff().quality(p);
				}
			}
		}, k);

	}

	@EventHandler
	public void onClose(InventoryCloseEvent e)
	{

		if(e.getInventory()!=null&&e.getInventory().getName().contains("商店"))
		{
		new gui.shopevent().onSave(e);
	
		}
	}
	@EventHandler
	public void ondamage(EntityDamageByEntityEvent e) {
		Player p = null;
		if (e.getDamager() instanceof Player) {
			p = (Player) e.getDamager();
		}
		if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
			p = (Player) ((Projectile) e.getDamager()).getShooter();
		}
		if (p == null) {
			return;
		}
		if (p != null && e.getEntity() instanceof Player) {
			Calendar calendar = Calendar.getInstance();
			int time = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 + calendar.get(Calendar.MINUTE) * 60
					+ calendar.get(Calendar.SECOND);
			if ((time - playerinfo.get(e.getEntity()).getrtime() < 15
					&& time - playerinfo.get(e.getEntity()).getrtime() > 0)) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + "玩家重生15秒内不可被攻击");
				return;
			}
			if ((time - playerinfo.get(p).getrtime() < 15 && time - playerinfo.get(p).getrtime() > 0)) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + "玩家重生15秒内不可攻击别人");
				return;
			}
		}
		canceldamage(e);
		if (playerinfo.containsKey(e.getDamager())) {
			Calendar c = Calendar.getInstance();
			int time = c.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 + c.get(Calendar.MINUTE) * 60 * 1000
					+ c.get(Calendar.SECOND) * 1000
					+ c.get(Calendar.MILLISECOND);
			double limit = new baoshieffect.event().dspeed(p);
			if (time - playerinfo.get(e.getDamager()).gettime() >= limit) {
				playerinfo.get(p).settime(time);
			} else {
				e.setCancelled(true);
				return;
			}
		}
		if (e.getEntity() instanceof Player && !gaming.contains(e.getEntity())) {
			e.setCancelled(true);
			return;
		}
		new baoshieffect.event().onDamage(e);
		if (e.getEntity().getLocation().getBlockY() > 100) {
			e.setCancelled(true);
		}
		if (e.getEntity() instanceof Villager) {
			new main.zombiedamaged().damage(e, config);
		}


		if (e.getEntity() instanceof Player) {
			new baoshieffect.buff().onDamage(e);
		}
		if ((e.getDamager() instanceof Player)) {
			if (!gaming.contains(e.getDamager())) {
				e.setCancelled(true);
			}
		}
		new baoshieffect.event().onEffect(e);
	}

	private void canceldamage(EntityDamageByEntityEvent e) {
		Player p = null;
		Player d = null;
		if (e.getEntity() instanceof Player) {
			p = (Player) e.getEntity();
		}
		if (e.getDamager() instanceof Player) {
			d = (Player) e.getDamager();
		}
		if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
			d = (Player) ((Projectile) e.getDamager()).getShooter();
		}
		if (p != null && d != null && main.TheWar.playerteam.get(p.getName()).getplayers().contains(d)) {
			e.setCancelled(true);
			return;
		}

	}
}
