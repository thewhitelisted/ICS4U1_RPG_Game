// copymon, an RPG created by Christopher Lee

// TODO:
		// Player position, etc
		// Enemies
		// Death screen
		// Stats display
		// Fighting
			// pokemon style fighting?
		// how to clear text????

import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Main {
	public static void main (String[] args) {
		// start screen
		start_menu();
		
		// 30x30 sprites, 600x600 minimum for map alone
		Console con = new Console("Copymon", 1000,600);
		
		// map and textures, see function render_map()
		String[][] strMap = load_map("map.csv");
		BufferedImage imgGrass = con.loadImage("grass.png");
		BufferedImage imgTree = con.loadImage("tree.png");
		BufferedImage imgWater = con.loadImage("water.png");
		BufferedImage imgNone = con.loadImage("none.png");
		
		// player render and stats
		BufferedImage imgPlayer = con.loadImage("character.png");
		int intpx = 4;
		int intpy = 4;
		int intphp = 100;
		
		// main loop
		while (true) {
			render_map(con, strMap, imgGrass, imgTree, imgWater, imgNone);
			con.drawImage(imgPlayer, intpx*30, intpy*30);
			display_stats(con);
			con.repaint();
		}
	}
	
	// function to display start menu
	public static void start_menu(Console con) {
		con.drawString("Copymon.", 440, 250);
		con.drawString("Press any key to continue", 335, 300);
		con.getKey();
		// TODO: how to clear text???
	}
	
	// function to display player stats
	public static void display_stats(Console con) {
		con.drawString("Player stats", 750, 250);
	}
	
	// function to load a csv file into a 2x2 array of length 20
	public static String[][] load_map(String strMap_name) {
		TextInputFile txtMap = new TextInputFile(strMap_name);
		String strLoaded_map[][] = new String[20][20];
		String strMap_line[];
		
		// read map to variable
		for (int i = 0; i < 20; i++) {
			strMap_line = txtMap.readLine().split(",");
			for (int j = 0; j < 20; j++) {
				strLoaded_map[i][j] = strMap_line[j];
			}
		}
		return strLoaded_map;
	}
	
	// function to render the map
	public static void render_map(Console con, String[][] strMap, BufferedImage imgGrass, BufferedImage imgTree, BufferedImage imgWater, BufferedImage imgNone) {
		// render code, cool wonky advanced stuff
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (strMap[i][j].equals("g")) {
					con.drawImage(imgGrass, j*30, i*30);
				} else if (strMap[i][j].equals("t")) {
					con.drawImage(imgTree, j*30, i*30);
				} else if (strMap[i][j].equals("w")) {
					con.drawImage(imgWater, j*30, i*30);
				} else {
					con.drawImage(imgNone, j*30, i*30);
				}
			}
		}
	}
}
