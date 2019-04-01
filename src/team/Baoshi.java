package team;

public class Baoshi {
	private String name;
	private String kind;
	private String quality;
	private int level;
	private int price;

	public Baoshi(String name, String kind, String quality, int price) {
		this.level = 1;
		this.name = name;
		this.kind = kind;
		this.quality = quality;
		this.price = price;
	}

	public void addlevel() {
		this.level = this.level + 1;
	}

	public String getname() {
		return this.name;
	}

	public Integer getprice() {
		return this.price * getlevel();
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
