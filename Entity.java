import arc.*;
import java.awt.image.BufferedImage;

// parent class, used for enemies
public class Entity {
	// attributes
	BufferedImage imgIcon;
	int intpx = 4;
	int intpy = 4;
	int intphp = 50;
	int intatk = 10;
	int intdef = 15;
	
	// constructors
	public Entity(BufferedImage imgIcon) {
		this.imgIcon = imgIcon;
	}
	
	public Entity(BufferedImage imgIcon, int intpx, int intpy, int intatk, int intdef) {
		this.imgIcon = imgIcon;
		this.intpx = intpx;
		this.intpy = intpy;
		this.intatk = intatk;
		this.intdef = intdef;
	}
	
	// get icon
	public BufferedImage icon() {
		return this.imgIcon;
	}
	
	// check if entity is dead
	public boolean is_dead() {
		return intphp < 1;
	}
}

// player class, inherits entity stuff
class Player extends Entity {
	// attributes
	BufferedImage imgIcon;
	
	// constructor
	public Player(BufferedImage imgIcon) {
		super(imgIcon);
		this.imgIcon = imgIcon;
	}
	
	// check if player can move to square
	public boolean can_move(String strMap[][], int intxdir, int intydir) {
		return !strMap[this.intpy+intydir][this.intpx+intxdir].equals("t");
	}
	
	// check if player is on water
	public boolean on_water(String strMap[][]) {
		return strMap[this.intpy][this.intpx].equals("w");
	}
	
	public boolean in_building(String strMap[][]) {
		return strMap[this.intpy][this.intpx].equals("b");
	}
}
