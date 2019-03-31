package team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;

public class PlayerInfo {
	private String name;
	private int health;
	private int point;
	private Villager zombie;

	public PlayerInfo(String name) {
		this.name = name;
		this.point = 0;
		this.health = 0;
	}

	public void addpoint(int pp) {
		this.point = this.point + pp;
		Bukkit.getPlayer(name).sendMessage("当前点数: " + point);
	}

	public Integer getpoint() {
		return this.point;
	}

	public void sethealth(int pp) {
		this.health = this.health + pp;
		if (this.health > this.zombie.getMaxHealth()) {
			setmaxhealth(this.health);
		}
		this.zombie.setHealth(this.health);
	}

	public void setmaxhealth(int pp) {
		this.zombie.setMaxHealth(pp);
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
