package data;

import java.io.Serializable;
import java.util.ArrayList;

import main.GamePanel;

public class DataStorage implements Serializable{

	
	public DataStorage() {
	}
	
	//Player stats
	int level, maxLife, life, mana, maxMana, strength, dexterity, exp, nextLevelExp, coin;
	
	//player inventory
	ArrayList<String> itemNames = new ArrayList<>();
	ArrayList<Integer> itemAmounts = new ArrayList<>();
	
	
	int currentWeaponSlot;
	int currentShieldSlot;
	

	
}

