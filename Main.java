import arc.*;
import java.awt.image.BufferedImage;

public class Main {
	public static void main (String[] args) {
		// 30x30 sprites, 600x600 minimum
		Console con = new Console(1000,600);
		String map[][] = load_map("map.csv");
		BufferedImage grass = con.loadImage("grass.png");
		BufferedImage tree = con.loadImage("tree.png");
		BufferedImage water = con.loadImage("water.png");
		
		while (true) {
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					if (map[i][j].equals("g")) {
						con.drawImage(grass, j*30, i*30);
					} else if (map[i][j].equals("t")) {
						con.drawImage(tree, j*30, i*30);
					} else if (map[i][j].equals("w")) {
						con.drawImage(water, j*30, i*30);
					}
				}
			}
			con.repaint();
		}
	}
	
	// function to load a csv file into a 2x2 array of length 20
	public static String[][] load_map(String map_name) {
		TextInputFile map = new TextInputFile(map_name);
		String loaded_map[][] = new String[20][20];
		String map_line[];
		for (int i = 0; i < 20; i++) {
			map_line = map.readLine().split(",");
			for (int j = 0; j < 20; j++) {
				loaded_map[i][j] = map_line[j];
			}
		}
		return loaded_map;
	}
}

