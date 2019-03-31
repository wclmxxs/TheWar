package team;

public class Baoshi {
	private String name;
	private String kind;
	private String quality;
	private int level;

	public Baoshi(String name, String kind, String quality) {
		this.level = 1;
		this.name = name;
		this.kind = kind;
		this.quality = quality;
	}

	public void addlevel() {
		this.level = this.level + 1;
	}

	public String getname() {
		return this.name;
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
