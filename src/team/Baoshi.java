package team;

public class Baoshi {
	private String name;
	private String kind;
	private String quality;
	private int level;
	private int price;
	private int time;

	public Baoshi(String name, String kind, String quality, int price) {
		this.level = 1;
		this.name = name;
		this.kind = kind;
		this.quality = quality;
		this.price = price;
		this.time = 0;
	}

	public void addlevel() {
		this.level = this.level + 1;
	}

	public String getname() {
		return this.name;
	}

	public Integer gettime() {
		return this.time;
	}

	public void settime(int time) {
		this.time = time;
	}
	public Integer getprice() {
		if (getlevel() != 1) {
			return this.price * (getlevel() - 1) * 3;
		} else {
			return this.price;
		}
	}

	public Integer getlevel() {
		return this.level;
	}

	public String getkind() {
		return this.kind;
	}

	public String getquality() {
		return this.quality;
	}

}
