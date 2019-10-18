package baoshieffect;

import java.util.List;

import main.TheWar;

public class addlist {

	public enum playerlist implements d {

		进攻 {
			@Override
			public List getlist() {
				return TheWar.attack;
			}

			@Override
			public int getint() {
				return 0;
			}
		},
		守备 {
			@Override
			public List getlist() {
				return TheWar.protect;
			}

			@Override
			public int getint() {
				return 1;
			}
		},
		爆发 {
			@Override
			public List getlist() {
				return TheWar.critical;
			}

			@Override
			public int getint() {
				return 2;
			}
		},
		功能 {
			@Override
			public List getlist() {
				return TheWar.function;
			}

			@Override
			public int getint() {
				return 3;
			}
		},
		持久 {
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
