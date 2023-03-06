package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.GamePanel;

public class SaveLoad {
	
	GamePanel gp;
	
	public SaveLoad(GamePanel gp) {
		this.gp = gp;
	}
	
	
	public void save() {
		try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
				
				DataStorage ds = new DataStorage();
				 
				ds.level = gp.player.level;
				ds.maxLife = gp.player.maxLife;
				ds.life = gp.player.life;
				ds.maxMana = gp.player.maxMana;
				ds.mana = gp.player.mana;
				ds.strength = gp.player.strength;
				ds.exp = gp.player.exp;
				ds.nextLevelExp = gp.player.nextLevelExp;
				ds.coin = gp.player.coin;
				
				ds.itemAmounts.clear();
				ds.itemAmounts.clear();
				
				//Player Inventory
				for (int i = 0; i < gp.player.inventory.size(); i++) {
					ds.itemNames.add(gp.player.inventory.get(i).name);
					ds.itemAmounts.add(gp.player.inventory.get(i).amount);
				}
				
				ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
				ds.currentShieldSlot = gp.player.getCurrentShieldSlot();
				
				//Write the DataStorage object
				
				//objects on the map
				ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
				ds.mapObjectWorldX = new int [gp.maxMap][gp.obj[1].length];
				ds.mapObjectWorldY = new int [gp.maxMap][gp.obj[1].length];
				ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
				ds.mapObjectOpened = new boolean [gp.maxMap][gp.obj[1].length];;
				
				for (int mapNum =0; mapNum < gp.maxMap; mapNum++) {
					for (int i = 0; i < gp.obj[1].length; i++) {
						if (gp.obj[mapNum][i] == null) {
							ds.mapObjectNames[mapNum][i] = "NA";
						}else {
							ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
							ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
							ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
							if (gp.obj[mapNum][i].loot != null) {
								ds.mapObjectLootNames[mapNum][i]=gp.obj[mapNum][i].loot.name;
								
							}
							ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
						}
					}
				}
				
				oos.writeObject(ds);
				oos.close();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public void load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			
			//read the datastorage object
			DataStorage ds = (DataStorage)ois.readObject();
			
			gp.player.level = ds.level;
			gp.player.maxLife = ds.maxLife;
			gp.player.life = ds.life;
			gp.player.maxMana = ds.maxMana;
			gp.player.mana = ds.mana;
			gp.player.strength = ds.strength;
			gp.player.dexterity = ds.dexterity;
			gp.player.exp = ds.exp;
			gp.player.nextLevelExp = ds.nextLevelExp;
			gp.player.coin = ds.coin;
			
			gp.player.inventory.clear();
			
			for (int i = 0; i < ds.itemNames.size(); i++) {
				for (int j = 0; j < gp.gameItems.itemList.size(); j++) {
					if (ds.itemNames.get(i).equals(gp.gameItems.itemList.get(j).name)){
						gp.player.inventory.add(gp.gameItems.itemList.get(j));
							gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
					}
				}
			}
			
			//player equipment
			gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
			gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
			
			gp.player.getAttack();
			gp.player.getDefense();
			gp.player.getPlayerAttackImage();
			
			//loading objects on the name.
			for (int mapNum =0; mapNum < gp.maxMap; mapNum++) {
				for (int i = 0; i < gp.obj[1].length; i++) {
					if(ds.mapObjectNames[mapNum][i].equals("NA")) {
						gp.obj[mapNum][i] = null;
					}
					else if (ds.mapObjectNames[mapNum][i].equals(gp.gameItems.itemList.get(i).name)) {
							gp.obj[mapNum][i] = gp.gameItems.itemList.get(i);
							gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
							gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
								if (ds.mapObjectLootNames[mapNum][i] != null && gp.obj[mapNum][i].loot.name.equals(gp.gameItems.itemList.get(i).name)) {
									gp.obj[mapNum][i].loot = gp.gameItems.itemList.get(i);
								}
								gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
								if (gp.obj[mapNum][i].opened == true) {
									gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
						}
						
					}
						
				}
			}
			
			ois.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
