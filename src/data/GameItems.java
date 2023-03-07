package data;

import java.util.ArrayList;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Fireball;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Rock;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;

public class GameItems {
	ArrayList<Entity> itemList = new ArrayList<>();
	GamePanel gp;
	
	public GameItems(GamePanel gp) {
		this.gp = gp;
		
		addGameItems();
	}
	
	public void addGameItems() {
		itemList.clear();
		itemList.add(new OBJ_Axe(gp));
		itemList.add(new OBJ_Boots(gp));
		itemList.add(new OBJ_Chest(gp));
		itemList.add(new OBJ_Coin_Bronze(gp));
		itemList.add(new OBJ_Door(gp));
		itemList.add(new OBJ_Fireball(gp));
		itemList.add(new OBJ_Heart(gp));
		itemList.add(new OBJ_Key(gp));
		itemList.add(new OBJ_Lantern(gp));
		itemList.add(new OBJ_ManaCrystal(gp));
		itemList.add(new OBJ_Potion_Red(gp));
		itemList.add(new OBJ_Rock(gp));
		itemList.add(new OBJ_Shield_Blue(gp));
		itemList.add(new OBJ_Shield_Wood(gp));
		itemList.add(new OBJ_Sword_Normal(gp));
		itemList.add(new OBJ_Tent(gp));
	}
	
	public Entity getGameItem( String itemName) {
		addGameItems();
		Entity item = null;
		for (int i = 0; i < itemList.size(); i++) {
			if (itemName.equals(itemList.get(i).name)) {
				item = itemList.get(i);
			}
		}
		return item;
	}
	
}
