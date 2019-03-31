package team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeamInfo implements Serializable {

	private String team;
	private List<Player> players;
	private List<Location> points;
	private int point;
	private boolean deeppoint;
	private List<Location> locs;
	private Player occupying;
	private byte id;

	public TeamInfo(String team, byte aa) {
		this.team = team;
		this.players = new ArrayList();
		this.point = 0;
		this.locs = new ArrayList();
		this.points = new ArrayList();
		this.deeppoint = false;
		this.occupying = null;
		this.id = aa;
	}

	public List getlocs() {
		return this.locs;
	}

	public byte getid() {
		return this.id;
	}

	public void addloc(Location loc) {
		locs.add(loc);
	}

	public List getpoints() {
		return this.points;
	}

	public void addpoint(Location loc) {
		points.add(loc);
	}

	public Integer getpoint() {
		return this.point;
	}

	public Player isOccupy() {
		return this.occupying;
	}

	public void setoccupy(Player occupy) {
		this.occupying = occupy;
	}
	public boolean isdeeppoint() {
		return this.deeppoint;
	}

	public void addpoint(int point) {
		this.point = this.point + point;
	}

	public void setdeeppoint(boolean deeppoint) {
		this.deeppoint = deeppoint;
	}

	public List<Player> getplayers() {
		return players;
	}

	public String getname() {
		return this.team;
	}
	public void addplayer(Player p) {
		players.add(p);
	}

	public void removeplayer(Player p) {
		players.remove(p);
	}
}
