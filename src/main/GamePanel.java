package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable{

	//Screen Settings
	final int originalTileSize = 16; // 16x16 tile - default size of the character, map tiles etc.
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;// 48x48 tile
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //960 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	
	//World Seetings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int maxMap = 10;
	public int currentMap = 0;
	
	//For full screen
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false;
	
	//FPS
	
	int FPS = 60;
	
	
	//System
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound se = new Sound();
	Sound music = new Sound();
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this); 
	Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);
	EnvironmentManager eManager = new EnvironmentManager(this);
	Thread gameThread;
	
	//Entity and Object
	public Player player = new Player(this, keyH);
	public Entity obj[][] = new Entity[maxMap][20];
	public Entity npc[][] = new Entity[maxMap][10];
	//the number of monsters we can display at the same time. 
	public Entity monster[][] = new Entity[maxMap][20];
	//This is used to create a large entity list so that way the order can be sorted out the order so the entity with the lower worldY value comes in at index 0.
	//this is used to solve the drawing order for entities so there isn't any awkard overlapping and what not. 
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
	public Entity projectile[][] = new Entity [maxMap][20];
//	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	public ArrayList<Entity> entityList = new ArrayList<>();
	
	//Game State Integers. This is used so that way depending on the state of the game, the same key can do multiple things at the same time. 
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionsState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;
	
	
	
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
		aSetter.setInteractiveTile();
		eManager.setup();
		gameState = titleState;
		
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		
		//everything g2 draws will be recorded in the temporary screen
		g2 = (Graphics2D)tempScreen.getGraphics();
		
		if (fullScreenOn == true) {
		setFullScreen();
		}
		
		
	}
	
	public void retry() {
		player.restoreLifeAndMana();
		aSetter.setNPC();
		aSetter.setMonster();
		player.setDefaultPosition();
		player.restoreLifeAndMana();
	}
	
	public void restart() {
		player.restoreLifeAndMana();
		player.setDefaultValues();
		player.setDefaultPosition();
		player.setItems();
		aSetter.setInteractiveTile();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setObject();
	}
	
	public void setFullScreen() {
		
		//get local screen device
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		//get full screen width and height
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
		
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
				drawToTempScreen(); //draws everything to the buffered image
				drawToScreen(); //draws everything to the screen
				delta--;
				drawCount++;
				System.gc();
				
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
		for(int i = 0; i < npc[1].length; i++) {
			if(npc[currentMap][i] != null) {
				npc[currentMap][i].update();
			}
		}
		
		for (int i = 0; i < monster[1].length; i++) {
			if (monster[currentMap][i] != null) {
				if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
					monster[currentMap][i].update();
				}
				if(monster[currentMap][i].alive == false) {
					monster[currentMap][i].checkDrop();
					monster[currentMap][i] = null;
				}
			}
		}
		
		//The array list allows you to perform the action on the ArrayList with projectiles. The projectiles have an update method that is executed while they're alive.
		// if the projecitle is no longer alive, the second if statement is executed removing it from the list and thus the projectile is destroyed. 
		for (int i = 0; i < projectile[1].length; i++) {
			if (projectile[currentMap][i] != null) {
				if (projectile[currentMap][i].alive == true) {
					projectile[currentMap][i].update();
				}
				if(projectile[currentMap][i].alive == false) {
					projectile[currentMap][i] = null;
				}
			}
		}
		
		for (int i = 0; i< particleList.size(); i++) {
			if(particleList.get(i) != null) {
				if(particleList.get(i).alive == true) {
					particleList.get(i).update();
				}
				if (particleList.get(i).alive == false) {
					particleList.remove(i);
				}
			}
		}
		
		for (int i = 0; i < iTile[1].length; i++) {
			if (iTile[currentMap][i] != null) {
				iTile[currentMap][i].update();
			}
		}
		eManager.update();
		
		}
		if(gameState == pauseState) {
			
		}
		
	}
	
	public void drawToTempScreen() {

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
		
		for(int i = 0; i < iTile[1].length; i++) {
			if (iTile[currentMap][i] != null) {
				iTile[currentMap][i].draw(g2);
			}
		}
		
		//Adding entities to the ArrayList
		entityList.add(player);
		for(int i = 0; i < npc[1].length; i++) {
			if (npc[currentMap][i] != null) {
				entityList.add(npc[currentMap][i]);
			}
		}
		
		for(int i = 0; i < obj[1].length; i++) {
			if(obj[currentMap][i] != null) {
				entityList.add(obj[currentMap][i]);
			}
		}
		
		for(int i = 0; i < monster[1].length; i++) {
			if(monster[currentMap][i] != null) {
				entityList.add(monster[currentMap][i]);
			}
		}
		for (int i = 0; i < projectile[1].length; i++) {
			if (projectile[currentMap][i] != null) {
				entityList.add(projectile[currentMap][i]);
			}
		}
		for (int i =0; i < particleList.size(); i++) {
			if (particleList.get(i) != null) {
				entityList.add(particleList.get(i));
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
		
		//Drawing the environment
		eManager.draw(g2);
		
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
	}
	
	// the paintComponent method is used to draw things directly to the JPanel in this case GamePanel, but because we're creating a temporary buffered screen,
	//it allows for resizability and better performance when resizing
	
	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
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
