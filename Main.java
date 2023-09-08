// copymon, an RPG created by Christopher Lee

// TODO:
		// Render player
		// Player position, etc
		// Enemies
		// Death screen
		// Stats display
		// Fighting

import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Main {
	public static void main (String[] args) {
		// 30x30 sprites, 600x600 minimum for map alone
		Console con = new Console(1000,600);
		
		// map and textures, see function render_map()
		String map[][] = load_map("map.csv");
		BufferedImage grass = con.loadImage("grass.png");
		BufferedImage tree = con.loadImage("tree.png");
		BufferedImage water = con.loadImage("water.png");
		BufferedImage none = con.loadImage("none.png");
		
		// start screen
		start_menu(con);
		
		// main loop
		while (true) {
			render_map(con, map, grass, tree, water, none);
			con.repaint();
		}
	}
	
	// function to display start menu
	public static void start_menu(Console con) {
		con.drawString("Copymon.", 440, 250);
		con.drawString("Press any key to continue", 335, 300);
		con.getKey();
		con.setBackgroundColor(new Color(0,0,0));
	}
	
	// function to load a csv file into a 2x2 array of length 20
	public static String[][] load_map(String map_name) {
		TextInputFile map = new TextInputFile(map_name);
		String loaded_map[][] = new String[20][20];
		String map_line[];
		
		// read map to variable
		for (int i = 0; i < 20; i++) {
			map_line = map.readLine().split(",");
			for (int j = 0; j < 20; j++) {
				loaded_map[i][j] = map_line[j];
			}
		}
		return loaded_map;
	}
	
	// function to render the map
	public static void render_map(Console con, String[][] map, BufferedImage grass, BufferedImage tree, BufferedImage water, BufferedImage none) {
		// render code, cool wonky advanced stuff
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (map[i][j].equals("g")) {
					con.drawImage(grass, j*30, i*30);
				} else if (map[i][j].equals("t")) {
					con.drawImage(tree, j*30, i*30);
				} else if (map[i][j].equals("w")) {
					con.drawImage(water, j*30, i*30);
				} else {
					con.drawImage(none, j*30, i*30);
				}
			}
		}
	}
}

