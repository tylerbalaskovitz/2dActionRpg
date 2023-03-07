package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_Red_Slime extends Entity {

	GamePanel gp;
	
	public MON_Red_Slime(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_monster;
		name = "Green Slime";
		defaultSpeed = 2;
		speed = defaultSpeed;;
		maxLife = 8;
		life = maxLife;
		attack = 7;
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
		
		up1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
	}
	
	
	public void setAction() {
	
		if (onPath == true) {
			//Check if it stops chasing the player, by its distance and the chance it will continue to chase.
			checkStopChasingOrNot(gp.player, 15, 100);
			
			//Searches for the direction to go via A* by getting the player's Column value and Row value.
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

			//checks whether or not it shoots a projectile
			checkShootOrNot(200, 30);
			
		} else {
			
			//Check if it starts chasing or not
			checkStartChasingOrNot(gp.player, 5, 100);
			
			getRandomDirection();
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
