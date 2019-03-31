package gaming;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;

public class init {

	public boolean init() {
		new tag.setname().init();

		for (Entity entity : Bukkit.getWorld("world").getEntities()) {
			if (entity instanceof Villager) {
				((Villager) entity).damage(10000000);
			}
		}
		return true;
	}
}
