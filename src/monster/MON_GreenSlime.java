package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_GreenSlime extends Entity{
	GamePanel gp;
	
	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_monster;
		name = "Green Slime";
		defaultSpeed = 1;
		speed = defaultSpeed;;
		maxLife = 4;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 2;
		projectile = new OBJ_Rock(gp);
		//setting a solid area of the slime. Since it's smaller it needs to be customized.
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	
	public void getImage() {
		
		up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
	}
	
	
	public void setAction() {
		
		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int tileDistance = (xDistance + yDistance)/gp.tileSize;
		
		if (onPath == true) {
			if (tileDistance > 20) {
				onPath = false;
			}
			
			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
			searchPath(goalCol, goalRow);
			int i = new Random().nextInt(200) + 1;
			if (i < 197 && projectile.alive == false && shotAvailableCounter == 30) {
				projectile.set(gp.currentMap, worldX, worldY, direction, true, this);

				//Check vancancy
				for (int j = 0; j < gp.projectile[1].length; j++) {
					if (gp.projectile[gp.currentMap][j] == null) {
						gp.projectile[gp.currentMap][j] = projectile;
						break;
					}
				}
				
				shotAvailableCounter = 0;
			}
			
		} else {
			if (tileDistance < 5) {
				int i = new Random().nextInt(100) +1;
				if (i > 50) {
					onPath = true;
				}
			}
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
	
	public void damageReaction() {
		
		actionLockCounter = 0;
		//direction = gp.player.direction;
		onPath = true;
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
