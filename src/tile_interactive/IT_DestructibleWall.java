package tile_interactive;

import java.awt.Color;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class IT_DestructibleWall extends InteractiveTile {

	public IT_DestructibleWall(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
	
		down1 = setup("/tiles_interactive/destructiblewall", gp.tileSize, gp.tileSize);
		destructible = true;
		life = 3;
	}

	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		
		//this boolean sets whether or not the player can knock the tree down.
		if (entity.currentWeapon.type == type_pickaxe) {
			isCorrectItem = true;
		}
		
		return isCorrectItem;
	}
	
	public void playSE() {
		gp.playSE(11);
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		return tile;
	}
	
	public Color getParticleColor() {
		Color color = new Color(65, 65, 65);
		return color;
	}
	
	public int getParticleSize() {
		int size = 6; //6 pixels in size
		return size;
	}
	
	public int getParticleSpeed() {
		//how fast the particles move
		int speed = 1;
		return speed;
	}
	
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
	
	public void checkDrop() {
		
		//Casting a D100 die
		int i = new Random().nextInt(100)+1;
		
		//Set the monster drop
		if (i  < 50 ) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if (i >= 50 && i < 80) {
			dropItem(new OBJ_Heart(gp));
		}
		
		if (i >= 80 && i <100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}
}
