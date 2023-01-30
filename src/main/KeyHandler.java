package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
	
	//Variables used for Debugging
	boolean checkDrawTime = false; 
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();//returns the number of the key associated with it
		if (gp.gameState == gp.playState) {
			if (code == KeyEvent.VK_W) {
				upPressed = true;
			}
			
			if (code == KeyEvent.VK_S) {
				downPressed=true;
			}
			
			if (code == KeyEvent.VK_A) {
				leftPressed=true;
			}
			
			if (code == KeyEvent.VK_D) {
				rightPressed=true;
			}
			
			if (code == KeyEvent.VK_P) {
				gp.gameState = gp.pauseState;
			}
			if (code == KeyEvent.VK_ENTER) {
				enterPressed = true;
				
			}
			
			
			//Used to enable/disable Debugging information
			if (code == KeyEvent.VK_T) {
				if (checkDrawTime == false) {
					checkDrawTime = true;
				}
				else if (checkDrawTime == true) {
					checkDrawTime = false;
				}
				
			}
			
		}
		//Pause State
		else if (gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_P) {
				gp.gameState = gp.playState;
			}
			
			
		}
		//Dialogue State
		else if (gp.gameState == gp.dialogueState) {
			if (code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.playState;
			}
			
		}
		
	}
		
	

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();//returns the number of the key associated with it
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		
		if (code == KeyEvent.VK_S) {
			downPressed=false;
		}
		
		if (code == KeyEvent.VK_A) {
			leftPressed=false;
		}
		
		if (code == KeyEvent.VK_D) {
			rightPressed=false;
		}
		
		
	}

	
	
}
