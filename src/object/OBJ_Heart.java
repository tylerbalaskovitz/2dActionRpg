package object;



import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{

GamePanel gp;
	
	public OBJ_Heart(GamePanel gp) {
		
		super(gp);
		name = "Key";
		image = setup("/objects/heart_full.png");
		image2 = setup("/objects/heart_half.png");
		image3 = setup("/objects/heart_blank.png");
		
	}
	
}
