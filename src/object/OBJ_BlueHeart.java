package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity{

	GamePanel gp;
	
	public OBJ_BlueHeart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		name = "Blue Heart";
		down1 = setup("/objects/blueheart", gp.tileSize, gp.tileSize);
		
		setDialogues();
	}
	
	public void setDialogues() {
		
		dialogues[0][0] = "You pick up a beautiful blue gem.";
		dialogues[0][1] = "You find the Blue Heart, the legendary treasure!";
		
		
	}
	
	public boolean use(Entity entity) {
		gp.gameState = gp.cutSceneState;
		gp.csManager.sceneNum = gp.csManager.ending;
		return true;
	}
	
}
