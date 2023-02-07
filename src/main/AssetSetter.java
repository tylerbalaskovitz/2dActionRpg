package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;

public class AssetSetter {

	
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
	}
	
	public void setNPC() {
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize * 9;
		gp.npc[0].worldY = gp.tileSize * 10;
		
	}
	
	public void setMonster() {
		
		gp.monster[0] = new MON_GreenSlime(gp);
		gp.monster[0].worldX = gp.tileSize*23;
		gp.monster[0].worldY = gp.tileSize*37;
		
		gp.monster[1] = new MON_GreenSlime(gp);
		gp.monster[1].worldX = gp.tileSize*11;
		gp.monster[1].worldY = gp.tileSize*10;
		
		gp.monster[2] = new MON_GreenSlime(gp);
		gp.monster[2].worldX = gp.tileSize*11;
		gp.monster[2].worldY = gp.tileSize*11;
		
		gp.monster[3] = new MON_GreenSlime(gp);
		gp.monster[3].worldX = gp.tileSize*9;
		gp.monster[3].worldY = gp.tileSize*11;
		
	}
}
