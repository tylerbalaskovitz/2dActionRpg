package data;

import java.io.Serializable;
import java.util.ArrayList;


public class DataStorage implements Serializable{
	private static final long serialVersionUID = -5900677515883187253L;

	
	public DataStorage() {
	}
	
	//Player stats
	int level, maxLife, life, mana, maxMana, strength, dexterity, exp, nextLevelExp, coin;
	
	//player inventory
	ArrayList<String> itemNames = new ArrayList<>();
	ArrayList<Integer> itemAmounts = new ArrayList<>();
	
	
	int currentWeaponSlot;
	int currentShieldSlot;
	
	String mapObjectNames[][];
	int mapObjectWorldX[][];
	int mapObjectWorldY[][];
	String mapObjectLootNames[][];
	boolean mapObjectOpened[][];
	

	
}

