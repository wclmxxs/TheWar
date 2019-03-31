package tag;

public class teams {
	enum team {

		red("§c[红队]"), green("§a[绿队]"), yellow("§e[黄队]"), blue("§b[蓝队]");
		private String team;

		private team(String a) {
			team = a;
		}

		public String getname() {

			return this.team;
		}

	}

}
