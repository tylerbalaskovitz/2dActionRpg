package monster;

import java.util.Random;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_SkeletonLord extends Entity {
GamePanel gp;

public static final String monName = "Skeleton Lord";
	
	public MON_SkeletonLord(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		boss = true;
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
		sleep = true;
		//setting a solid area of the slime. Since it's smaller it needs to be customized.
		
		int size = gp.tileSize*5;
		solidArea.x = 4;
		solidArea.y = 4;
		solidArea.width = size - 48*2;
		solidArea.height = size - 48;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 170;
		attackArea.height = 170;
		motion1_duration = 25;
		motion2_duration = 50;
		
		getImage();
		getAttackImage();
		setDialogue();
	}
	
	public void getImage() {
		
		int i = 5;
		
		if (inRage == false) {
			up1 = setup("/monster/skeletonlord_up_1", gp.tileSize*i, gp.tileSize*i);
			up2 = setup("/monster/skeletonlord_up_2", gp.tileSize*i, gp.tileSize*i);
			down1 = setup("/monster/skeletonlord_down_1", gp.tileSize*i, gp.tileSize*i);
			down2 = setup("/monster/skeletonlord_down_2", gp.tileSize*i, gp.tileSize*i);
			left1 = setup("/monster/skeletonlord_left_1", gp.tileSize*i, gp.tileSize*i);
			left2 = setup("/monster/skeletonlord_left_2", gp.tileSize*i, gp.tileSize*i);
			right1 = setup("/monster/skeletonlord_right_1", gp.tileSize*i, gp.tileSize*i);
			right2 = setup("/monster/skeletonlord_right_2", gp.tileSize*i, gp.tileSize*i);
		}
		if (inRage == true) {
			up1 = setup("/monster/skeletonlord_phase2_up_1", gp.tileSize*i, gp.tileSize*i);
			up2 = setup("/monster/skeletonlord_phase2_up_2", gp.tileSize*i, gp.tileSize*i);
			down1 = setup("/monster/skeletonlord_phase2_down_1", gp.tileSize*i, gp.tileSize*i);
			down2 = setup("/monster/skeletonlord_phase2_down_2", gp.tileSize*i, gp.tileSize*i);
			left1 = setup("/monster/skeletonlord_phase2_left_1", gp.tileSize*i, gp.tileSize*i);
			left2 = setup("/monster/skeletonlord_phase2_left_2", gp.tileSize*i, gp.tileSize*i);
			right1 = setup("/monster/skeletonlord_phase2_right_1", gp.tileSize*i, gp.tileSize*i);
			right2 = setup("/monster/skeletonlord_phase2_right_2", gp.tileSize*i, gp.tileSize*i);
		}
	}
	
	public void getAttackImage() {
				int i = 5;
				
				if (inRage == false) {
					attackUp1 = setup("/monster/skeletonlord_attack_up_1", gp.tileSize*i, gp.tileSize*i*2);
					attackUp2 = setup("/monster/skeletonlord_attack_up_2", gp.tileSize*i, gp.tileSize*i*2);
					attackDown1 = setup("/monster/skeletonlord_attack_down_1", gp.tileSize*i, gp.tileSize*i*2);
					attackDown2 = setup("/monster/skeletonlord_attack_down_2", gp.tileSize*i, gp.tileSize*i*2);
					attackLeft1 = setup("/monster/skeletonlord_attack_left_1", gp.tileSize*i*2, gp.tileSize*i);
					attackLeft2 = setup("/monster/skeletonlord_attack_left_2", gp.tileSize*i*2, gp.tileSize*i);
					attackRight1 = setup("/monster/skeletonlord_attack_right_1", gp.tileSize*i*2, gp.tileSize*i);
					attackRight2 = setup("/monster/skeletonlord_attack_right_2", gp.tileSize*i*2, gp.tileSize*i);
				}
				if (inRage == true) {
					attackUp1 = setup("/monster/skeletonlord_phase2_attack_up_1", gp.tileSize*i, gp.tileSize*i*2);
					attackUp2 = setup("/monster/skeletonlord_phase2_attack_up_2", gp.tileSize*i, gp.tileSize*i*2);
					attackDown1 = setup("/monster/skeletonlord_phase2_attack_down_1", gp.tileSize*i, gp.tileSize*i*2);
					attackDown2 = setup("/monster/skeletonlord_phase2_attack_down_2", gp.tileSize*i, gp.tileSize*i*2);
					attackLeft1 = setup("/monster/skeletonlord_phase2_attack_left_1", gp.tileSize*i*2, gp.tileSize*i);
					attackLeft2 = setup("/monster/skeletonlord_phase2_attack_left_2", gp.tileSize*i*2, gp.tileSize*i);
					attackRight1 = setup("/monster/skeletonlord_phase2_attack_right_1", gp.tileSize*i*2, gp.tileSize*i);
					attackRight2 = setup("/monster/skeletonlord_phase2_attack_right_2", gp.tileSize*i*2, gp.tileSize*i);
				}
	}
	
	public void setDialogue() {
		dialogues[0][0] = "No one can steal my treausre!";
		dialogues[0][1] = "You will die here! You little prick!";
		dialogues[0][2] = "Unless.....";
		dialogues[0][3] = "You want to have fun playing with my bones.";
		
	}
	
	public void setAction() {
		
		if (inRage == false && life < maxLife/2) {
			inRage = true;
			getImage();
			getAttackImage();
			defaultSpeed++;
			speed = defaultSpeed;
			attack*=2;
		}
	
		if (getTileDistance(gp.player) < 10) {
			moveTowardPlayer(60);
		} else {
			getRandomDirection(120);
		}
		
		if (attacking == false) {
			checkAttackOrNot(60, gp.tileSize*10, gp.tileSize*5);
		}
	}
	

	
	public void damageReaction() {
		
		actionLockCounter = 0;
		//direction = gp.player.direction;
		onPath = true;
	}
	
	public void checkDrop() {
		
		gp.bossBattleOn = false;
		Progress.skeletonLordDefeated = true;
		
		//restore the previouis music
		gp.stopMusic();
		gp.playMusic(19);
		
		//seraching for iron doors in the multidimensional array and deleting them.
		for (int i = 0; i < gp.obj[1].length; i++) {
			if (gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(new OBJ_Door_Iron(gp).name)) {
				gp.playSE(21);
				
				gp.obj[gp.currentMap][i] = null;
			}
			
			
		}
		
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
