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

	public void setbaoshis(List bs) {
		this.bs = bs;
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

	public void removewaits(Baoshi baoshi) {
		this.wait.remove(baoshi);

	}

	public void addbaoshis(Baoshi baoshi) {
		this.bs.add(baoshi);

	}

	public void removebaoshis(Baoshi baoshi) {
		this.bs.remove(baoshi);

	}
}
