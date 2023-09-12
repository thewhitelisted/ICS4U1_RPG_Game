// copymon, an RPG created by Christopher Lee

// TODO:
		// Enemies
			// enemy position, randomly generated?
		// Stats display
		// Fighting
			// pokemon style fighting?
			// pokemon are enemies, once killed, you can turn into them
		// second map

import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Main {
	public static void main (String[] args) {
		// 30x30 sprites, 600x600 minimum for map alone
		Console con = new Console("Copymon", 1000,600);
		
		// start screen
		start_menu(con);
		
		// map and textures, see function render_map()
		String[][] strMap = load_map("map.csv");
		BufferedImage imgGrass = con.loadImage("grass.png");
		BufferedImage imgTree = con.loadImage("tree.png");
		BufferedImage imgWater = con.loadImage("water.png");
		BufferedImage imgBuilding = con.loadImage("building.png");
		BufferedImage imgNone = con.loadImage("none.png");
		
		// player render and stats
		BufferedImage imgPlayer = con.loadImage("character.png");
		int intpx = 4;
		int intpy = 4;
		int intphp = 50;
		int intKeyPressed;
		
		// enemy renders
		BufferedImage imgSquirt = con.loadImage("squirtle.png");
		BufferedImage imgChar = con.loadImage("charmander.png");
		
		
		// main loop
		while (true) {
			render_map(con, strMap, imgGrass, imgTree, imgWater, imgBuilding, imgNone);
			con.drawImage(imgPlayer, intpx*30, intpy*30);
			display_stats(con);
			con.repaint();
			
			// movement behavior
			intKeyPressed = con.getKey();
			System.out.println(intKeyPressed);
			// w
			if (intKeyPressed == 119 || intKeyPressed == 87 && can_move(intpx, intpy-1, strMap)) {
				intpy--;
			// s
			} else if (intKeyPressed == 115 || intKeyPressed == 83 && can_move(intpx, intpy+1, strMap)) {
				intpy++;
			// a
			} else if (intKeyPressed == 97 || intKeyPressed == 65 && can_move(intpx-1, intpy, strMap)) {
				intpx--;
			// d
			} else if (intKeyPressed == 100 || intKeyPressed == 68 && can_move(intpx+1, intpy, strMap)) {
				intpx++;
			}
			
			// end game if hits water
			if (is_water(intpx, intpy, strMap)) {
				death_menu(con);
			}
		}
	}
	
	// function to display start menu
	public static void start_menu(Console con) {
		con.drawString("Copymon.", 440, 250);
		con.drawString("Press any key to continue", 335, 300);
		con.getKey();
		con.setBackgroundColor(new Color(0,0,0));
	}
	
	// function to display death menu
	public static void death_menu(Console con) {
		con.setBackgroundColor(new Color(0,0,0));
		con.setDrawColor(new Color(255,255,255));
		con.drawString("You died!", 440, 250);
		con.drawString("Press any key to close prompt", 325, 300);
		con.getKey();
		for (int i = 3; i > 0; i--) {
			con.setBackgroundColor(new Color(0,0,0));
			con.setDrawColor(new Color(255,255,255));
			con.drawString("Closing in " + (i), 425, 300);
			con.sleep(1000);
		}
		con.closeConsole();
		
	}
	
	// function to display player stats
	public static void display_stats(Console con) {
		con.setDrawColor(new Color(255,255,255));
		con.drawString("Player stats", 650, 50);
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
		txtMap.close();
		return strLoaded_map;
	}
	
	// function to render the map
	public static void render_map(Console con, String[][] strMap, BufferedImage imgGrass, BufferedImage imgTree, BufferedImage imgWater, BufferedImage imgBuilding, BufferedImage imgNone) {
		// render code, cool wonky advanced stuff
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (strMap[i][j].equals("g")) {
					con.drawImage(imgGrass, j*30, i*30);
				} else if (strMap[i][j].equals("t")) {
					con.drawImage(imgTree, j*30, i*30);
				} else if (strMap[i][j].equals("w")) {
					con.drawImage(imgWater, j*30, i*30);
				} else if (strMap[i][j].equals("b")) {
					con.drawImage(imgBuilding, j*30, i*30);
				} else {
					con.drawImage(imgNone, j*30, i*30);
				}
			}
		}
	}
	
	// function to check if you can move to a square
	public static boolean can_move(int intx, int inty, String[][] strMap) {
		return !strMap[inty][intx].equals("t");
	}
	
	// function to check if you are on water
	public static boolean is_water(int intpx, int intpy, String[][] strMap) {
		return strMap[intpy][intpx].equals("w");
	}
}
