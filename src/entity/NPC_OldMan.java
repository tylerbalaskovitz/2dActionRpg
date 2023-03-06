package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		getImage();
		setDialogue();
		solidArea.x = 8;
		solidArea.y= 16;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		solidArea.width = 32;
		solidArea.height = 32;
		
	}
	
	public void getImage() {
		
		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
		
	}
	
	public void setDialogue() {
		
		dialogues[0][0] = "Hello, back in the day,";
		dialogues[0][1] = "I used to sell nice rock";
		dialogues[0][2] = "Now, all I do is sell ho's.";
		dialogues[0][3] = "Lemme know if you're buying you";
		
		dialogues[1][0] = "If you become tired, try resting at the water";
		dialogues[1][1] = "However, the monsters reappear when you rest.\nThey see it as a moment to safely return.";
		dialogues[1][2] = "Anyway, I'm going to go back to slinging";
				
		dialogues[2][0] = "I wonder how to open these doors...";
	}
	
	public void setAction() {
		
		if (onPath == true) {
			/*
			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
			*/
			int goalCol = 12;
			int goalRow = 10;
			
			searchPath(goalCol, goalRow);
		}
		else {
		actionLockCounter++;
		
			if(actionLockCounter == 120) {
			
			Random random = new Random();
			int i = random.nextInt(100) + 1; //pick a number from 1 to 100;
			
				if (i <= 25) {
					direction = "up";
				}
				
				if (i > 25 && i <= 50 ) {
					direction = "down";
				}
				
				if (i > 50 && i <= 75) {
					direction = "left";
				}
				
				if (i > 75 && i <=100 ) {
					direction = "right";
				}
				
				actionLockCounter = 0;
			}
		}
	}
		
	public void facePlayer() {

	super.facePlayer();
	
	onPath = true;
	
	}
	
}
