package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Config implements Serializable{
	private static final long serialVersionUID = 5404409005335428113L;
	GamePanel gp;
	boolean fullScreenOn;
	int musicVolumeScale;
	int soundVolumeScale;
	
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	
	public Config() {
		
	}
	
	public void saveConfig() {
		try {
			FileOutputStream fos = new FileOutputStream("config.dat");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
		
			Config savedConfig = new Config();
			savedConfig.fullScreenOn = gp.fullScreenOn;
			savedConfig.musicVolumeScale = gp.music.volumeScale;
			savedConfig.soundVolumeScale = gp.se.volumeScale;
			
			oos.writeObject(savedConfig);
			oos.close();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	
	}
	
	public void loadConfig() {
		
		try {
		FileInputStream	fis = new FileInputStream("config.dat");
		BufferedInputStream bis = new BufferedInputStream(fis);
		ObjectInputStream ois = new ObjectInputStream(bis);
		
		Config loadConfig = (Config)ois.readObject();
		 gp.fullScreenOn = loadConfig.fullScreenOn;
		 gp.music.volumeScale = loadConfig.musicVolumeScale;
		 gp.se.volumeScale = loadConfig.soundVolumeScale;
		
		 ois.close();
	
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
