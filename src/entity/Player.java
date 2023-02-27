package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity{

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public boolean attackCanceled = false;
	public boolean lightUpdated = false;

	
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
		getPlayerAttackImage();
		setItems();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		defaultSpeed = 6;
		speed = defaultSpeed;
		direction = "down";
		
		//Default player status
		level = 1;
		maxLife = 6;
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		strength = 1; //The more strength you have the more damage that you'll do
		dexterity = 1; // the more dexterity you have the less damage that you'll received
		exp = 0; 
		nextLevelExp = 5;
		coin = 550;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);
		attack = getAttack(); //Total attack value is decided by strength and weapon
		defense = getDefense(); //the total defense is decided by dexterity and your shield. So both of these stats are based on multiple elements
	}
	
	public void setDefaultPosition() {
		
		gp.currentMap = 0;
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		
		
		
		
		direction = "down";
	}
	
	public void restoreLifeAndMana(){
		life = maxLife;
		mana = maxMana;
		invincible = false;
	}
	
	public void setItems() {
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
	}
	
	public int getAttack()	{
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
		
	}
	
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
		
		
	}
	
	public void getPlayerAttackImage() {
		
		if (currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
		}
		if (currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
		}
	}
	
	public void getSleepingImage(BufferedImage image) {
		up1 = image;
		up2 = image;
		down1 = image;
		down2 = image;
		left1 = image;
		left2 = image;
		right1 = image;
		right2 = image;
	}
	
	public void update() {
		
		if (attacking == true) {
			attacking();
		}
		else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
		
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
		
		//checks collision with the interactive tiles. Using the @SuppressWarnings annotation as a way to supress the compiler from thinking iTileIndex isn't used,
		//when it is being used with the method of checkEntity as a way to ensure collision. Good to know to prevent the accidental deletion of code. 
		@SuppressWarnings(value = { "unused"})
		int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
		
		//checks the event
		gp.eHandler.checkEvent();
		
		//if collision is false, then the player can move
		
		if (collisionOn == false && keyH.enterPressed == false) {
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		
		if (keyH.enterPressed == true && attackCanceled == false) {
			gp.playSE(7);
			attacking = true;
			spriteCounter = 0;
		}
		
		attackCanceled = false;
		
		gp.keyH.enterPressed = false;
		
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
		//this means that if the key is pressed and the previous projectile is still alive/active then you're unable to shoot another projectile
		if (gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
			
			// sets the default coordinates, and direction, and who is shooting the projectile. The boolean, sets the projectile to be alive.
			projectile.set(gp.currentMap, worldX, worldY, direction, true, this);
			
			//subtract the cost for using the projectile
			projectile.subtractResource(this);
			
			// Add it to the list
			//gp.projectileList.add(projectile);
			
			//Checking the vacancy of the projectiles
			for (int i = 0; i < gp.projectile[1].length; i++) {
				if (gp.projectile[gp.currentMap][i] == null) {
					gp.projectile[gp.currentMap][i] = projectile;
					break;
				}
			}
			
			
			shotAvailableCounter = 0;
			
			gp.playSE(10);
		}
		
		//This needs to be outside of the key if statement to work whether or not you're moving
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
		if (life > maxLife) {
			life = maxLife;
		}
		if(mana > maxMana) {
			mana = maxMana;
		}
		if (life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
			gp.playSE(12);
		}
	}
	
	public void attacking () {
		spriteCounter++;
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <25) {
			spriteNum = 2;
			
			//Save the current WorldX, Y and solid area values
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//Adjust the player's worldX and Y for the attackArea
			switch (direction) {
			case "up": worldY -= attackArea.height;break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			
			//attackArea becomes the solid area
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			//checks the monster collision with the update world X and world Y and solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);
			
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);
			
			int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
			damageProjectile(projectileIndex);
			
			//After checking collision, then restore the original data. 
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
			
		}
		if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	public void pickUpObject(int i) {

		if (i != 999) {
			
			//Pickup only items
			
			if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
				
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
				
			} else if(gp.obj[gp.currentMap][i].type == type_obstacle){
				if(keyH.enterPressed == true) {
					attackCanceled = true;
					gp.obj[gp.currentMap][i].interact();
				}
			}
			else {
			
			//Inventory items
			
			String text;
			
			if (canObtainItem(gp.obj[gp.currentMap][i]) == true) {
				gp.playSE(1);
				text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
			}
			else {
				text = "You cannot carry any more items!";
			}
			
			gp.ui.addMessage(text);
			gp.obj[gp.currentMap][i] = null;
		}
		}
	}
	
	public void interactNPC(int i) {
		if (gp.keyH.enterPressed == true) {
		if (i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[gp.currentMap][i].speak();
		}
		}
		
	}
	
	public void contactMonster(int i) {
		if (i != 999) {
			if (invincible == false && gp.monster[gp.currentMap][i].dying == false) {
				int damage = gp.monster[gp.currentMap][i].attack - defense;
			gp.playSE(6);
			life -= damage;
			invincible = true;
			}
		}
	}
	
	public void damageMonster(int i, int attack, int knockBackPower) { 
		if (i != 999) {
			
			if(gp.monster[gp.currentMap][i].invincible == false) {
				gp.playSE(5);
				
				if (knockBackPower > 0) {
				knockBack(gp.monster[gp.currentMap][i], currentWeapon.knockBackPower);
				}
				
				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if (damage < 0 ) {
					damage = 0;
				}
				gp.monster[gp.currentMap][i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				
				
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				
				if(gp.monster[gp.currentMap][i].life <= 0) {
					gp.monster[gp.currentMap][i].dying = true;
					gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
					gp.ui.addMessage("+"+ gp.monster[gp.currentMap][i].exp + "EXP");
					exp += gp.monster[gp.currentMap][i].exp;
					checkLevelUp();
				}
				
			}
			
		}
	}
	
	public void knockBack(Entity entity, int knockBackPower) {
		entity.direction = direction;
		entity.speed += knockBackPower;
		entity.knockBack = true;
	}
	
	public void damageInteractiveTile(int i) {
		if (i != 999 && gp.iTile[gp.currentMap][i].destructible == true && gp.iTile[gp.currentMap][i].invincible == false && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true) {
			
			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			
			
			//Used to generate the particle
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
			
			if (gp.iTile[gp.currentMap][i].life <= 0) {
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
			}
		}
	}
	
	public void damageProjectile(int i) {
		if (i != 999) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			projectile.alive = false;
			generateParticle(projectile, projectile);
		}
	}
	
	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp*2;
			maxLife +=2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(8);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You are level +" + level + " now! \n" +
					"You feel stronger!";
		}
		
	}
	
	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
		
		if (itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
				
			}
			if (selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			if (selectedItem.type == type_light) {
				if (currentLight == selectedItem) {
					currentLight = null;
				}
				else {
					currentLight = selectedItem;
				}
				lightUpdated = true;
			}
			if (selectedItem.type == type_consumable) {
				//later consumables will be added into the game
				if (selectedItem.use(this) == true) {
					if (selectedItem.amount > 1) {
						selectedItem.amount--;
					} else {
						inventory.remove(itemIndex);
					}
					
				}
			}
		}
	}
	
	public int searchItemInInventory(String itemName) {
		int itemIndex = 999;
		
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}
		return itemIndex;
	}
	
	public boolean canObtainItem(Entity item) {
		
		boolean canObtain = false;
		
		//Check to see whether or not an item is stackable
		if (item.stackable == true) {
			int index = searchItemInInventory(item.name);
			
			if (index != 999) {
				inventory.get(index).amount++;
				canObtain = true;
				
			}
			else { 
				//this is a new item and must be checked to see whether or not it can go inside of one's inventory.
				if (inventory.size() != maxInventorySize) {
					inventory.add(item);
					canObtain = true;
				}
			}
		}
		else {
			// Not stackable so check whether or not it can be added into the inventory
			if (inventory.size() != maxInventorySize) {
				inventory.add(item);
				canObtain = true;
			}
		}
		return canObtain;
	}
	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if (attacking == false) {
				if (spriteNum == 1 ) {image=up1;}
				if (spriteNum == 2 ) {image=up2;}
			}
			if (attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1 ) {image=attackUp1;}
				if (spriteNum == 2 ) {image=attackUp2;}
			}
			break;
		case "down":
			if (attacking == false) {
				if (spriteNum ==1) {image=down1;}
				if (spriteNum ==2) {image=down2;}
			}
			if (attacking == true) {
				if (spriteNum ==1) {image=attackDown1;}
				if (spriteNum ==2) {image=attackDown2;}
			}
			break;
		case "left":
			if (attacking == false) {
				if (spriteNum ==1) {image=left1;}
				if (spriteNum ==2) {image=left2;}
			}
			if (attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum ==1) {image=attackLeft1;}
				if (spriteNum ==2) {image=attackLeft2;}
			}
			break;
		case "right":
			if (attacking == false) {
				if (spriteNum ==1) {image=right1;}
				if (spriteNum ==2) {image=right2;}
			}
			if (attacking == true ) {
				if (spriteNum ==1) {image=attackRight1;}
				if (spriteNum ==2) {image=attackRight2;}
			}
			break;
		}
		
		//drawing the player so they are somewhat more transparent depending on whether or not they are under the timer of being invincible
		if (invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//Debugging Text
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible:" + invincibleCounter, 10, 400);
	}
	
	
}
