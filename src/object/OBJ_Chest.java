package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

	GamePanel gp;
	Entity loot;
	boolean opened = false;
	
	public OBJ_Chest(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
	
		
		type = type_obstacle;
		name = "Chest";
		image = setup("/objects/chest", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
		down1 = image;
		collision = true;
		solidArea.x = 4;
		solidArea.y = 16;
		solidArea.width = 40;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	
	public void setLoot(Entity loot) {
		this.loot = loot;
	}
	
	public void interact() {
		gp.gameState = gp.dialogueState;
		if (opened == false) {
			gp.playSE(3);
			
			StringBuilder sb = new StringBuilder();
			sb.append("You opend the chest and find a " + loot.name + "!");
			
			if (gp.player.canObtainItem(loot) == false) {
				sb.append("\n...But you cannot carry anymore items!");
			}
			else {
				sb.append("\nThe " + loot.name + " has been added to your inventory!");
				down1 = image2;
				setOpened(true); 
			}
			gp.ui.currentDialogue = sb.toString();
			
			} else {
				gp.ui.currentDialogue = "It's empty.";
				setOpened(true); 
			}
	}

	
}
