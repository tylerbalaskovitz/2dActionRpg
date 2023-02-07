package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth /2 - (gp.tileSize/2);
		screenY = gp.screenHeight /2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y= 16;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		solidArea.width = 32;
		solidArea.height = 32;
		
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 6;
		direction = "down";
		
		//Default player status
		maxLife = 6;
		life = maxLife;
	}
	
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1");
		up2 = setup("/player/boy_up_2");
		down1 = setup("/player/boy_down_1");
		down2 = setup("/player/boy_down_2");
		left1 = setup("/player/boy_left_1");
		left2 = setup("/player/boy_left_2");
		right1 = setup("/player/boy_right_1");
		right2 = setup("/player/boy_right_2");
		
		
	}
	
	
	public void update() {
		
		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
		
		if (keyH.upPressed == true) {
			direction = "up";
			
		}
		
		
		if (keyH.downPressed == true) {
			direction = "down";
			
		}
		
		if (keyH.leftPressed == true) {
			direction = "left";
			
		}
		
		if (keyH.rightPressed == true ) {
			direction = "right";
			
		}
		
		//Check tile collision
		collisionOn = false;
		
		gp.cChecker.checkTile(this);
		
		//Check object collision
		
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
		
		//Checking NPC Collision
		int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
		interactNPC(npcIndex);
		//checks the collision of monsters
		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
		contactMonster(monsterIndex);
		
		//checks the event
		gp.eHandler.checkEvent();
		
		gp.keyH.enterPressed = false;
		
		//if collision if false, then the player can move
		
		if (collisionOn == false) {
			switch(direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}
		
		spriteCounter++;
		if (spriteCounter > 15) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		}
		
		//This needs to be outside of the key if statement to work whether or not you're moving
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void pickUpObject(int i) {

		if (i != 999) {
			
		}
	
	}
	
	public void interactNPC(int i) {
		
		if (i != 999) {
			
			if (gp.keyH.enterPressed == true) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		
		}
		
	}
	
	public void contactMonster(int i) {
		if (i != 999) {
			if (invincible == false) {
			life -=1;
			invincible = true;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if (spriteNum == 1 ) {
			image=up1;
			}
			if (spriteNum == 2 ) {
				image=up2;
				}
			break;
		case "down":
			if (spriteNum ==1) {
			image=down1;
			}
			if (spriteNum ==2) {
			image=down2;
			}
			break;
		case "left":
			if (spriteNum ==1) {
			image=left1;
			}
			if (spriteNum ==2) {
			image=left2;
			}
			break;
		case "right":
			if (spriteNum ==1) {
			image=right1;
			}
			if (spriteNum ==2) {
			image=right2;
			}
			break;
		
		}
		
		//drawing the player so they are somewhat more transparent depending on whether or not they are under the timer of being invincible
		if (invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		
		g2.drawImage(image, screenX, screenY, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//Debugging Text
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible:" + invincibleCounter, 10, 400);
	}
	
	
}
