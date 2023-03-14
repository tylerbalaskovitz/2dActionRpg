package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_SkeletonLord extends Entity {
GamePanel gp;

public static final String monName = "Skeleton Lord";
	
	public MON_SkeletonLord(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_monster;
		name = "Skeleton Lord";
		defaultSpeed = 1;
		speed = defaultSpeed;;
		maxLife = 50;
		life = maxLife;
		attack = 10;
		defense = 2;
		exp = 50;
		knockBackPower = 5;
		//setting a solid area of the slime. Since it's smaller it needs to be customized.
		
		int size = gp.tileSize*5;
		solidArea.x = 4;
		solidArea.y = 4;
		solidArea.width = 40;
		solidArea.height = 44;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 48;
		attackArea.height = 48;
		motion1_duration = 40;
		motion2_duration = 85;
		
		getImage();
		getAttackImage();
	}
	
	public void getImage() {
		
		int i = 5;
		
		up1 = setup("/monster/skeletonlord_up_1", gp.tileSize*i, gp.tileSize*i);
		up2 = setup("/monster/skeletonlord_up_2", gp.tileSize*i, gp.tileSize*i);
		down1 = setup("/monster/skeletonlord_down_1", gp.tileSize*i, gp.tileSize*i);
		down2 = setup("/monster/skeletonlord_down_2", gp.tileSize*i, gp.tileSize*i);
		left1 = setup("/monster/skeletonlord_left_1", gp.tileSize*i, gp.tileSize*i);
		left2 = setup("/monster/skeletonlord_left_2", gp.tileSize*i, gp.tileSize*i);
		right1 = setup("/monster/skeletonlord_right_1", gp.tileSize*i, gp.tileSize*i);
		right2 = setup("/monster/skeletonlord_right_2", gp.tileSize*i, gp.tileSize*i);
	}
	
	public void getAttackImage() {
				int i = 5;
				attackUp1 = setup("/monster/skeletonlord_attack_up_1", gp.tileSize*i, gp.tileSize*i*2);
				attackUp2 = setup("/monster/skeletonlord_attack_up_2", gp.tileSize*i, gp.tileSize*i*2);
				attackDown1 = setup("/monster/skeletonlord_attack_down_1", gp.tileSize*i, gp.tileSize*i*2);
				attackDown2 = setup("/monster/skeletonlord_attack_down_2", gp.tileSize*i, gp.tileSize*i*2);
				attackLeft1 = setup("/monster/skeletonlord_attack_left_1", gp.tileSize*i*2, gp.tileSize*i);
				attackLeft2 = setup("/monster/skeletonlord_attack_left_2", gp.tileSize*i*2, gp.tileSize*i);
				attackRight1 = setup("/monster/skeletonlord_attack_right_1", gp.tileSize*i*2, gp.tileSize*i);
				attackRight2 = setup("/monster/skeletonlord_attack_right_2", gp.tileSize*i*2, gp.tileSize*i);
	}
	
	public void setAction() {
	
		if (onPath == true) {
			//Check if it stops chasing the player, by its distance and the chance it will continue to chase.
			checkStopChasingOrNot(gp.player, 15, 100);
			
			//Searches for the direction to go via A* by getting the player's Column value and Row value.
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

			
		} else {
			
			//Check if it starts chasing or not
			checkStartChasingOrNot(gp.player, 5, 100);
			
			getRandomDirection(120);
		}
		
		if (attacking == false) {
			checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
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
