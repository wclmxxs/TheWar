package team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;

public class PlayerInfo {
	private String name;
	private int health;
	private int point;
	private Villager zombie;
	private int armour;
	private int level;
	private BaoshiInfo bi;

	public PlayerInfo(String name) {
		this.name = name;
		this.point = 0;
		this.health = 0;
		this.armour = 0;
		this.level = 0;
		this.bi = new BaoshiInfo();
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
		Bukkit.getPlayer(name).sendMessage("��ǰ����: " + point);
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
		if (this.health > this.zombie.getMaxHealth()) {
			setmaxhealth(this.health);
		}
		
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

}
