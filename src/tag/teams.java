package tag;

public class teams {
	enum team {

		red("��c[���]"), green("��a[�̶�]"), yellow("��e[�ƶ�]"), blue("��b[����]");
		private String team;

		private team(String a) {
			team = a;
		}

		public String getname() {

			return this.team;
		}

	}

}
