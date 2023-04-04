package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {

	public OBJ_Pickaxe(GamePanel gp) {
		super(gp);
		
		type = type_pickaxe;
		name = "Pickaxe";
		down1 = setup("/objects/pickaxe", gp.tileSize, gp.tileSize);
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[Pickaxe]\nYou will dig it!";
		price = 75;
		knockBackPower = 10;
		motion1_duration = 10;
		motion2_duration = 30;
	}
	
	
}
