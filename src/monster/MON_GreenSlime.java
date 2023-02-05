package monster;

import entity.Entity;
import main.GamePanel;

public class MON_GreenSlime extends Entity{

	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		
		name = "Green Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		
		//setting a solid area of the slime. Since it's smaller it needs to be customized.
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
	}
	
	public void getImage() {
		
		up1 = setup("/monster/greenslime_down_1");
		up2 = setup("/monster/greenslime_down_");
		
	}

}
