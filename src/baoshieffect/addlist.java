package baoshieffect;

import java.util.List;

import main.TheWar;

public class addlist {

	public enum playerlist implements d {

		���� {
			@Override
			public List getlist() {
				return TheWar.attack;
			}

			@Override
			public int getint() {
				return 0;
			}
		},
		�ر� {
			@Override
			public List getlist() {
				return TheWar.protect;
			}

			@Override
			public int getint() {
				return 1;
			}
		},
		���� {
			@Override
			public List getlist() {
				return TheWar.critical;
			}

			@Override
			public int getint() {
				return 2;
			}
		},
		���� {
			@Override
			public List getlist() {
				return TheWar.function;
			}

			@Override
			public int getint() {
				return 3;
			}
		},
		�־� {
			@Override
			public List getlist() {
				return TheWar.persistent;
			}

			@Override
			public int getint() {
				return 4;
			}
		};

	}
}
