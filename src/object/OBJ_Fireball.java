package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile{
	// a subclass of the Projectile class, and Entity is the parent class. THis allows for the projectile to inherit some of the entity properties while also creatiing
	//a lot of flexibility so you don't have to rewrite code for other entities that are non-flying/non-projectile based. IE projectile specific things
	
	GamePanel gp;
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
	
	name = "Fireball";
	speed = 10;
	maxLife = 80;
	life = maxLife;
	attack = 2;
	useCost = 1;
	alive = false;
	knockBackPower = 0;
	getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);
		
	}
	
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if (user.mana >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
	public void subtractResource(Entity user) {
		user.mana -= useCost;
	}
	
	public Color getParticleColor() {
		Color color = new Color(240, 50, 0);
		return color;
	}
	
	public int getParticleSize() {
		int size = 10; //6 pixels in size
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
}
