package main;

import java.io.File;
import java.util.ArrayList;
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
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import team.PlayerInfo;
import team.TeamInfo;

public class TheWar extends JavaPlugin implements Listener {

	public static TeamInfo red;
	public static TeamInfo blue;
	public static TeamInfo green;
	public static TeamInfo yellow;
	public static TeamInfo tt;
	public static HashMap<Player, TeamInfo> playerteam;
	public static HashMap<Player, PlayerInfo> playerinfo;
	public static List<Player> gaming;
	public static List<TeamInfo> isgaming;
	public static boolean isgame;
	public static FileConfiguration config;
	public static HashMap<Location, TeamInfo> points;
	public static Location deeppoint;

	@Override
	public void onEnable() {
		red = new TeamInfo("§c[红队]", (byte) 14);
		green = new TeamInfo("§a[绿队]", (byte) 5);
		blue = new TeamInfo("§b[蓝队]", (byte) 3);
		yellow = new TeamInfo("§e[黄队]", (byte) 4);
		tt = new TeamInfo("mm", (byte) 0);
		isgaming = new ArrayList();
		isgaming.add(red);
		isgaming.add(green);
		isgaming.add(blue);
		isgaming.add(yellow);
		isgame = false;
		playerteam = new HashMap();
		playerinfo = new HashMap();
		gaming = new ArrayList();
		points = new HashMap();

		getCommand("minemc").setExecutor(this);
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("游戏已经载入,四个队伍已经载入");
		getLogger().info("作者:Mxxs");
		getLogger().info("QQ:2040005066");
		saveDefaultConfig();
		this.config = getConfig();
		if (!config.contains("deeppoint") || !config.contains("points")) {
			getLogger().info("点还未设置");
		} else {
			deeppoint = (Location) config.get("deeppoint");
			List<Location> point1 = (List<Location>) config.getList("points");
			for (Location loc : point1) {
				points.put(loc, tt);
			}
		}
		if (!new File("plugins/TheWar/minemc.schematic").exists()) {
			getLogger().info("minemc.schematic不存在于TheWar文件夹中....");
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
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (args.length == 0 && s.isOp()) {
			s.sendMessage("/minemc join/setspawn/setred/setgreen/setblue/setyellow/setpoint/setdeeppoint/save");
			s.sendMessage("/minemc open [shop]");
			s.sendMessage("/minemc set [shop] [size]");
			s.sendMessage("Name/n打开商店: shop/nLore/n价值点数: amount");
		}
		if ((args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("set")) && args.length != 0) {
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
			for (Player p : playerteam.keySet()) {
				p.sendMessage("§e当前游戏人数: " + playerteam.size() + "/" + config.getInt("max"));
			}
			if (playerteam.size() >= config.getInt("min") && !isgame) {
				for (Player p : playerteam.keySet()) {

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
		return false;
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
	}

	@EventHandler
	public void onOpen(InventoryOpenEvent e) {
		if (e.getInventory().getName().contains("villager")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		TeamInfo ti = TheWar.playerteam.get(p);
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
				ti.setoccupy(p);
				Bukkit.broadcastMessage(ti.getname() + "开始占领中心据点,5秒后将成功");
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
			ti.setoccupy(p);
			Bukkit.broadcastMessage(ti.getname() + "开始占领据点");
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
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory() != null && e.getInventory().getName().contains("商店")) {
			e.setCancelled(true);
			new gui.shopevent().onClick(e);
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
						"minemc join " + e.getCurrentItem().getItemMeta().getDisplayName().split(":")[1]);
				e.getWhoClicked().closeInventory();
			}

		}
		if (e.getClickedInventory() != null
				&& e.getClickedInventory().getName().equalsIgnoreCase("点击选择职业")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void join(PlayerJoinEvent e) {
		if (config.contains("spawn")) {
			e.getPlayer().teleport((Location) config.get("spawn"));
		}
		if (gaming.size() != 0) {
			e.getPlayer().sendMessage("游戏已经开始,您暂时无法参与");
		}
		{
			ItemStack item = new ItemStack(339);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§e选择队伍§7右键");
			item.setItemMeta(meta);
			e.getPlayer().getInventory().setItem(8, item);
		}
		{
			ItemStack item = new ItemStack(339);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§e选择职业§7右键");
			item.setItemMeta(meta);
			e.getPlayer().getInventory().setItem(0, item);
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
		if (e.getBlock().getLocation().getBlockY() >= config.getInt("block")
				&& e.getBlock().getLocation().getBlockY() <= config.getInt("low") && !e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onplace(BlockPlaceEvent e) {
		if (!e.getPlayer().isOp() && !gaming.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
		if (e.getBlock().getLocation().getBlockY() >= config.getInt("block")
				&& e.getBlock().getLocation().getBlockY() <= config.getInt("low") && !e.getPlayer().isOp()) {
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
		e.getEntity().spigot().respawn();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				if (gaming.contains(e.getEntity()))
			{
					e.getEntity().teleport((Location) config.get("spawn"));
					e.getEntity().sendMessage("您将在10秒后重生");
					spawn(e.getEntity());
			}
 else {
					e.getEntity().teleport((Location) config.get("spawn"));
				}

			}
		}, 15L);

	}

	protected void spawn(Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				p.getInventory().clear();
				new tag.setname().setScoreboard(p, TheWar.playerteam.get(p).getname());
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
			}
		}, 200L);

	}

	@EventHandler
	public void ondamage(EntityDamageByEntityEvent e) {

		if (e.getEntity() instanceof Villager) {
			new main.zombiedamaged().damage(e, config);
		}
		canceldamage(e);
		if ((e.getDamager() instanceof Player)) {
			if (!gaming.contains(e.getDamager())) {
				e.setCancelled(true);
			}
		}

	}

	private void canceldamage(EntityDamageByEntityEvent e) {
		Player p = null;
		Player d = null;
		if (e.getEntity() instanceof Player) {
			p = (Player) e.getEntity();
		}
		if (e.getDamager() instanceof Player) {
			p = (Player) e.getDamager();
		}

		if (e.getEntity() instanceof Projectile && ((Projectile) e.getEntity()).getShooter() instanceof Player) {
			d = (Player) ((Projectile) e.getEntity()).getShooter();
		}
		if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
			d = (Player) ((Projectile) e.getDamager()).getShooter();
		}
		if (p != null && d != null && main.TheWar.playerteam.get(p).getplayers().contains(d)) {
			e.setCancelled(true);
			d.sendMessage("请勿攻击队友");
		}

	}
}
