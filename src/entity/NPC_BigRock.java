package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_BigRock extends Entity{

	public NPC_BigRock(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 4;
		getImage();
		setDialogue();
		solidArea.x = 8;
		solidArea.y= 16;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		solidArea.width = 44;
		solidArea.height = 40;
		dialogueSet = -1;
		
		
	}
	
	public void getImage() {
		
		up1 = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		
	}
	
	public void setDialogue() {
		
		dialogues[0][0] = "It's a giant rock. Maybe, it can move.";
	}
	
	public void setAction() {	}
	
	public void speak() {
		facePlayer();
		startDialogue(this, dialogueSet);
		
		dialogueSet++;
		
		if (dialogues[dialogueSet][0] == null) {
			dialogueSet = 0;
		}
		
		//onPath = true;
	}
		
}
