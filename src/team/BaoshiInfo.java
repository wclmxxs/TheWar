package team;

import java.util.ArrayList;
import java.util.List;

public class BaoshiInfo {

	private List<Baoshi> bs;
	private List<Baoshi> wait;

	public void BaoshiInfo() {
		this.bs = new ArrayList();
		this.wait = new ArrayList();
	}

	public List getbaoshis() {
		return bs;
	}

	public void setbaoshis(Baoshi bs) {
		this.bs.add(bs);
	}

	public List getwaits() {
		return wait;
	}

	public void setwaits(List wait) {
		this.wait = wait;
	}

	public void addwaits(Baoshi baoshi) {
		this.wait.add(baoshi);

	}
}
