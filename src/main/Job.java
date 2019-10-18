package main;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Job {
	private List<ItemStack> items;
	private int Damageadd;
	private int Defendadd;
	private String name;
	private Skill skill;

	public Job(String name, List<ItemStack> items, Skill skill, int damage, int defend) {
		this.setName(name);
		this.setItems(items);
		this.skill = skill;
		this.setDamageadd(damage);
		this.setDefendadd(defend);
	}

	public int getDamageadd() {
		return Damageadd;
	}

	public void setDamageadd(int damageadd) {
		Damageadd = damageadd;
	}

	public int getDefendadd() {
		return Defendadd;
	}

	public void setDefendadd(int defendadd) {
		Defendadd = defendadd;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public void setItems(List<ItemStack> items) {
		this.items = items;
	}

}
