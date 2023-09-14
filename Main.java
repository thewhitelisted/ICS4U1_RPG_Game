// copymon, an RPG created by Christopher Lee

// TODO:
		// Enemies
			// enemy position, randomly generated?
		// Fighting
			// pokemon style fighting?
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
		
		// player render and stats... using objects cuz im cool like that
		Player player = new Player(con.loadImage("character.png"));
		player.imgBattleicon = con.loadImage("player_battle.png");
		int intKeyPressed;
		
		// enemy renders, make location random
		Entity[] enemies = {new Entity(con.loadImage("squirtle.png"), 12, 13, 5, 10), new Entity(con.loadImage("charmander.png"), 3, 12, 10, 15)};
		enemies[0].imgBattleicon = con.loadImage("squirtle_battle.png");
		enemies[0].imgDMG = con.loadImage("squirtle_atk.png");
		enemies[1].imgBattleicon = con.loadImage("charmander_battle.png");
		
		// main loop
		while (true) {
			display_stats(con, player.intphp, player.intatk, player.intdef);
			render_map(con, strMap, imgGrass, imgTree, imgWater, imgBuilding, imgNone);
			render_enemies(con, enemies);
			con.drawImage(player.icon(), player.intpx*30, player.intpy*30);
			con.repaint();
			
			// movement behavior
			intKeyPressed = con.getKey();
			// w
			if (intKeyPressed == 119 || intKeyPressed == 87 && player.can_move(strMap, 0, -1)) {
				player.intpy--;
			// s
			} else if (intKeyPressed == 115 || intKeyPressed == 83 && player.can_move(strMap, 0, 1)) {
				player.intpy++;
			// a
			} else if (intKeyPressed == 97 || intKeyPressed == 65 && player.can_move(strMap, -1, 0)) {
				player.intpx--;
			// d
			} else if (intKeyPressed == 100 || intKeyPressed == 68 && player.can_move(strMap, 1, 0)) {
				player.intpx++;
			}
			
			// end game if hits water
			if (player.on_water(strMap)) {
				death_menu(con);
			} else if (player.in_building(strMap) && player.intphp < 50) {
				if (player.intphp + 10 > 50) {
					player.intphp = 50;
				} else {
					player.intphp += 10;
				}
			} 
			
			for (int i = 0; i < enemies.length; i++) {
				if (player.intpx == enemies[i].intpx && player.intpy == enemies[i].intpy && enemies[i].intphp > 0) {
					fight_enemy(con, enemies[i], player);
				}
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
	public static void display_stats(Console con, int intphp, int intatk, int intdef) {
		con.setDrawColor(new Color(0,0,0));
		con.fillRect(601, 0, 399, 600);
		con.setDrawColor(new Color(255,255,255));
		con.drawString("Player stats", 650, 50);
		con.drawString("HP: " + intphp, 650, 100);
		con.drawString("Attack: " + intatk, 650, 150);
		con.drawString("Defence: " + intdef, 800, 100);
	}
	
	// function to display enemy stats
	public static void display_estats(Console con, int intphp, int intatk, int intdef) {
		con.drawString("Enemy stats", 650, 225);
		con.drawString("HP: " + intphp, 650, 275);
		con.drawString("Attack: " + intatk, 650, 325);
		con.drawString("Defence: " + intdef, 800, 275);
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
	
	// function to render enemies on the map
	public static void render_enemies(Console con, Entity[] enemies) {
		for (int i = 0; i < enemies.length; i++) {
			con.drawImage(enemies[i].icon(), enemies[i].intpx * 30, enemies[i].intpy * 30);
		}
	}
	
	public static void render_battle(Console con, Entity enemy, Player player, BufferedImage imgbg, BufferedImage imgatkbtn) {
		con.setBackgroundColor(new Color(0,0,0));
		con.setDrawColor(new Color(255,255,255));
		
		// display stats
		display_stats(con, player.intphp, player.intatk, player.intdef);
		display_estats(con, enemy.intphp, enemy.intatk, enemy.intdef);
			
		// draw screen
		con.drawImage(imgbg, 0,0);
		con.drawImage(imgatkbtn, 650, 375);
		con.drawImage(enemy.imgBattleicon, 375, 250);
		con.drawImage(player.imgBattleicon, 50, 350);
	}
	
	// function for battle sequence
	public static void fight_enemy(Console con, Entity enemy, Player player) {
		// load images
		BufferedImage imgbg = con.loadImage("battle_bg.png");
		BufferedImage imgatkbtn = con.loadImage("attack.png");
		
		while (true) {
			render_battle(con, enemy, player, imgbg, imgatkbtn);
			con.repaint();
			
			while (true) {
				con.sleep(16);
				int intMouse = con.currentMouseButton();
				con.sleep(16);
				if (con.currentMouseButton() == 1) {
					break;
				}
			}
			
			if (con.currentMouseX() > 650 && con.currentMouseX() < 950 && con.currentMouseY() < 475 && con.currentMouseY() > 375) {
				System.out.println("bam");
				enemy.intphp-= 10;
			}
			
			if (enemy.intphp < 1) {
				break;
			}
			con.sleep(50);
			
			
			
		}
		
	}
	
	/*public static void user_turn(Console con, Player player) {
		
	}*/
}
