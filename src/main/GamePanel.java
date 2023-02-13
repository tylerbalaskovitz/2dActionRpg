package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	//Screen Settings
	final int originalTileSize = 16; // 16x16 tile - default size of the character, map tiles etc.
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;// 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	
	//World Seetings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	//FPS
	
	int FPS = 60;
	
	
	//System
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound se = new Sound();
	Sound music = new Sound();
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this); 
	
	Thread gameThread;
	
	//Entity and Object
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[20];
	public Entity npc[] = new Entity[10];
	//the number of monsters we can display at the same time. 
	public Entity monster[] = new Entity[20];
	//This is used to create a large entity list so that way the order can be sorted out the order so the entity with the lower worldY value comes in at index 0.
	//this is used to solve the drawing order for entities so there isn't any awkard overlapping and what not. 
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> entityList = new ArrayList<>();
	
	//Game State Integers. This is used so that way depending on the state of the game, the same key can do multiple things at the same time. 
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		

	}
	
	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		playMusic(0);
		stopMusic();
		gameState = titleState;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime)/drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				System.out.println("DELTA: "+ delta);
				System.out.println("TIMER: " + timer);
				System.out.println("LAST TIME: " + lastTime);
				drawCount = 0;
				timer = 0;
			}
			
		}
		
		
		
	}
	public void update() {
		if (gameState == playState) {
		player.update();
		
		//NPC update
		for(int i = 0; i < npc.length; i++) {
			if(npc[i] != null) {
				npc[i].update();
			}
		}
		
		for (int i = 0; i < monster.length; i++) {
			if (monster[i] != null) {
				if(monster[i].alive == true && monster[i].dying == false) {
					monster[i].update();
				}
				if(monster[i].alive == false) {
					monster[i].checkDrop();
					monster[i] = null;
				}
			}
		}
		
		//The array list allows you to perform the action on the ArrayList with projectiles. The projectiles have an update method that is executed while they're alive.
		// if the projecitle is no longer alive, the second if statement is executed removing it from the list and thus the projectile is destroyed. 
		for (int i = 0; i < projectileList.size(); i++) {
			if (projectileList.get(i) != null) {
				if (projectileList.get(i).alive == true) {
					projectileList.get(i).update();
				}
				if(projectileList.get(i).alive == false) {
					projectileList.remove(i);
				}
			}
		}
		
		}
		if(gameState == pauseState) {
			
		}

	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		//Graphics 2D is a subclass and has more function than the standard Graphics class
		Graphics2D g2 =  (Graphics2D)g;
		
		//Code that's used to Debug the rest of the game
		long drawStart = 0;
		if (keyH.showDebugText == true) {
			drawStart = System.nanoTime();
		}
		//Title Screen
		if (gameState == titleState) {
			
		}
		
		
		//Others
		
		else {
		//Drawing the tile
		tileM.draw(g2);
		
		//Adding entities to the ArrayList
		entityList.add(player);
		for(int i = 0; i < npc.length; i++) {
			if (npc[i] != null) {
				entityList.add(npc[i]);
			}
		}
		
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				entityList.add(obj[i]);
			}
		}
		
		for(int i = 0; i < monster.length; i++) {
			if(monster[i] != null) {
				entityList.add(monster[i]);
			}
		}
		for (int i = 0; i < projectileList.size(); i++) {
			if (projectileList.get(i) != null) {
				entityList.add(projectileList.get(i));
			}
		}
		
		//Sorting the array list
		Collections.sort(entityList, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				int result = Integer.compare(e1.worldY, e2.worldY);
				return result;
			}
			
		});
		//Draw Entities
		for(int i = 0; i < entityList.size(); i++) {
			entityList.get(i).draw(g2);
		}
		
		//Reset entityList otherwise it gets larger in every loop and will consume more resources
		entityList.clear();
		
		//UI since it comes at the top of the layers
		ui.draw(g2);
		}
	
		//debugging stuff
		
		if (keyH.showDebugText == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(new Font("Arial", Font.PLAIN, 20));
			g2.setColor(Color.white);
			
			int x = 10;
			int y = 400;
			int lineHeight = 20;
			
			g2.drawString("WorldX" + player.worldX, x, y); y+=lineHeight;
			g2.drawString("WorldY" + player.worldY, x, y); y+=lineHeight;
			
			g2.drawString("Col"+ (player.worldX + player.solidArea.x)/tileSize,x, y);y+=lineHeight;
			g2.drawString("Row"+ (player.worldY + player.solidArea.y)/tileSize,x, y);y+=lineHeight;
			
			
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, x, y);
			
			System.out.println("Draw Time: " + passed);
		}
		
		if (gameState == titleState) {
			ui.draw(g2);
		}
		
		
		g2.dispose();
		
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}


}
