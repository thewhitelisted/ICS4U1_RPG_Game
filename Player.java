import arc.*;
import java.awt.image.BufferedImage;

public class Player {
	BufferedImage imgIcon;
	int intpx = 4;
	int intpy = 4;
	int intphp = 50;
	int intatk = 10;
	int intdef = 15;
	
	public Player(BufferedImage imgIcon) {
		this.imgIcon = imgIcon;
	}
	
	public Player(BufferedImage imgIcon, int intpx, int intpy, int intatk, int intdef) {
		this.imgIcon = imgIcon;
	}
	
	public BufferedImage icon() {
		return this.imgIcon;
	}
	
	public boolean is_dead() {
		return intphp < 1;
	}
}
