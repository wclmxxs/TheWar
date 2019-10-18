package team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;

import main.TheWar;

public class PlayerInfo {
	private String name;
	private int health;
	private int point;
	private Villager zombie;
	private int armour;
	private int level;
	private BaoshiInfo bi;
	private int time;
	private int rtime;
	private Inventory inv;
	private Integer[] qints;
	// 白绿黄蓝紫
	private Integer[] kints;

	// 进攻；守备；爆发；功能；持久
	public PlayerInfo(String name) {
		this.name = name;
		this.qints = new Integer[5];
		this.kints = new Integer[5];
		this.rtime = 0;
		for (int i = 0; i < qints.length; i++) {
			qints[i] = 0;
			kints[i] = 0;
		}
		this.point = 0;
		this.time = 0;
		this.health = TheWar.config.getInt("zombie");
		this.armour = 0;
		this.level = 0;
		this.bi = new BaoshiInfo(name);

	}


	public void setkints(int i, int j) {
		this.kints[j] = i;
	}

	public Integer[] getkints() {
		return this.kints;
	}

	public void setints(int i, int j) {
		this.qints[j] = i;
	}

	public Integer[] getints() {
		return this.qints;
	}

	public Inventory getinv() {
		return this.inv;
	}

	public void setinv(Inventory inv) {
		this.inv = inv;
	}

	public Integer getlevel() {
		return this.level;
	}

	public BaoshiInfo getbi() {
		return this.bi;
	}


	public void addlevel() {
		this.level = this.level + 1;
	}
	
	public void addpoint(int pp) {
		this.point = this.point + pp;
		if (Bukkit.getPlayer(name) != null) {
			new main.ScoreboardManager().createScoreboard(Bukkit.getPlayer(name));
		}
	}

	public Integer getpoint() {
		return this.point;
	}

	public void setarmour(int armour)
	{
		this.armour = this.armour + armour;
	}
	
	
	
	public void setdamage(double finaldamage)
	{
		double damage = finaldamage - armour;
		if(damage < 0)
		{
			damage = 1;
		}
		
		sethealth(-damage);
	}
	
	public void sethealth(double pp) {
		this.health = (int) (this.health + pp);
		
		this.zombie.setHealth(this.health);
	}

	public void setmaxhealth(int pp) {
		this.zombie.setMaxHealth(this.zombie.getMaxHealth() + pp);
	}

	public Integer gethealth() {
		return this.health;
	}

	public void setzombie(Villager zombie) {
		this.zombie = zombie;
	}

	public Villager getzombie() {
		return this.zombie;
	}

	public int gettime() {
		// TODO Auto-generated method stub
		return this.time;
	}

	public void settime(int time) {
		// TODO Auto-generated method stub
		this.time = time;
	}

	public int getrtime() {
		// TODO Auto-generated method stub
		return this.rtime;
	}

	public void setrtime(int rtime) {
		// TODO Auto-generated method stub
		this.rtime = rtime;
	}
}
