package main;

import java.awt.Graphics2D;

public class CutsceneManager {

	GamePanel gp;
	Graphics2D g2;
	public int sceneNum;
	public int scenePhase;
	
	//Scene number
	public final int NA = 0;
	public final int skeletonLord = 1;
	
	public CutsceneManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void draw (Graphics2D g2	) {
		this.g2 = g2;
	}
	
}
