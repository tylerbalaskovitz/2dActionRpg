package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

	GamePanel gp;
	Entity loot;
	boolean opened = false;
	
	//have both the variables at the same time and during the save/load class, have the loot return to the same
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
		setDialogue();
	}
	
	
	public void setDialogue(){
		dialogues[0][0] = "You opend the chest and find a " + loot.name + "!" + "\n...But you cannot carry anymore items!";
		dialogues[1][0] = "You opend the chest and find a " + loot.name + "!" + "\nThe " + loot.name + " has been added to your inventory!";
		dialogues[2][0] =  "It's empty.";
		
		
	}
	
	public void interact() {
		if (opened == false) {
			gp.playSE(3);
			
			
			if (gp.player.canObtainItem(loot) == false) {
				startDialogue(this, 0);
			}
			else {
				startDialogue(this, 1);
				down1 = image2;
				setOpened(true);
				this.opened = true;
			}
			
			} else if (this.loot != null && opened == true){
				setDialogue();
				startDialogue(this, 2);
				gp.ui.drawDialogueScreen();
			}
	}

	
}
