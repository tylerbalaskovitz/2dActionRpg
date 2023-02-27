package main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import tile_interactive.IT_DryTree;

public class AssetSetter {

	
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;

	}
	
	public void setObject() {
		int mapNum = 0;
		int i = 0;
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*25;
		gp.obj[mapNum][i].worldY = gp.tileSize*19;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Lantern(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*18;
		gp.obj[mapNum][i].worldY = gp.tileSize*20;
		i++;
		
		
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*21;
		gp.obj[mapNum][i].worldY = gp.tileSize*19;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*26;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*37;
		gp.obj[mapNum][i].worldY = gp.tileSize*9;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*12;
		gp.obj[mapNum][i].worldY = gp.tileSize*9;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*22;
		gp.obj[mapNum][i].worldY = gp.tileSize*27;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*22;
		gp.obj[mapNum][i].worldY = gp.tileSize*28;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Heart(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*22;
		gp.obj[mapNum][i].worldY = gp.tileSize*29;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*22;
		gp.obj[mapNum][i].worldY = gp.tileSize*31;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*14;
		gp.obj[mapNum][i].worldY = gp.tileSize*28;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*12;
		gp.obj[mapNum][i].worldY = gp.tileSize*12;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Key(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*33;
		gp.obj[mapNum][i].worldY = gp.tileSize*37;
		i++;
		
		
		
	}
	
	public void setNPC() {
		int mapNum = 0;
		int i = 0;
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 17;
		gp.npc[mapNum][i].worldY = gp.tileSize * 21;
		i++;
		//i++ is used for everything within the first map. 

		//since a different map is used, that's a different valu for the multidimensional array, meaning that the second dimension of the two
		//dimensional array needs to be put back to 0 otherwise an exception will be thrown.
		mapNum = 1;
		i = 0;
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 12;
		gp.npc[mapNum][i].worldY = gp.tileSize * 7;
		
	}
	
	public void setMonster() {
		int mapNum = 0;
		int i = 0;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*36;
		gp.monster[mapNum][i].worldY = gp.tileSize*10;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*37;
		gp.monster[mapNum][i].worldY = gp.tileSize*6;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*39;
		gp.monster[mapNum][i].worldY = gp.tileSize*9;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*24;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*34;
		gp.monster[mapNum][i].worldY = gp.tileSize*42;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*38;
		gp.monster[mapNum][i].worldY = gp.tileSize*42;
		i++;
	}
	
	public void setInteractiveTile() {
		int mapNum = 0;
		
		int i = 0;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 12); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 12); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 29, 12); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 12); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 12); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 12); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 29, 22); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 20, 20); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 20, 21); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 20, 22); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 22, 24); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 23, 24); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 24, 24); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 40); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 14, 40); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 15, 40); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 16, 40); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 17, 40); i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 18, 40); i++;
		
	}
}
