package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

	GamePanel gp;
	
	public OBJ_Key(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Key";
		down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nUsed for opening things."; 
		price = 100;
		
	}
	
	public void use (Entity entity) {
		
	}
	
}
