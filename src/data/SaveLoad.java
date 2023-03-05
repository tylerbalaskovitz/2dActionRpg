package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entity.Entity;
import main.GamePanel;

public class SaveLoad {
	
	GamePanel gp;
	
	public SaveLoad(GamePanel gp) {
		this.gp = gp;
	}
	
	public Entity getObject(String itemName) {
		Entity obj = null;
		switch(itemName) {
		
		}
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
				
				//Player Inventory
				for (int i = 0; i < gp.player.inventory.size(); i++) {
					ds.itemNames.add(gp.player.inventory.get(i).name);
					ds.itemAmounts.add(gp.player.inventory.get(i).amount);
				}
				
				
				//Write the DataStorage object
				
				oos.writeObject(ds);
				
				
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
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
