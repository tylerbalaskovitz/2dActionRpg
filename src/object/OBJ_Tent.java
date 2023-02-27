package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity{

	GamePanel gp;
	public OBJ_Tent(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Tent";
		down1 = setup("/objects/tent", gp.tileSize, gp.tileSize);
		description = "[Tent]\nYou can sleep until\nnext morning.";
		price = 300;
		stackable = true;
		
	}
	
	public boolean use(Entity entity) {

		gp.gameState = gp.sleepState;
		gp.playSE(14);
		gp.player.life = gp.player.maxLife;
		gp.player.mana = gp.player.maxMana;
		//By returning the false value the tent is not used in the game, but allows itself to be used again and again.
		
		//THis passes the tente image from down1 above into the getSleepingImage() method which then replaces the image of the player's movement in all directions with the tent
		//since the player is 'sleeping' Ie going through the cycle of sleeping. 
		gp.player.getSleepingImage(down1);
		return true;
		
	}

}
