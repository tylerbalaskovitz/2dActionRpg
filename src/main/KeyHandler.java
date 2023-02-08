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
		//Title state
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		//Play state
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		//Pause State
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		//Dialogue State
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		//Character State
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}
		
	}
	
	public void titleState (int code) {
		if (gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum<0) {
					gp.ui.commandNum=2;
				}
			}
			
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum>2) {
					gp.ui.commandNum=0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
				}
				if(gp.ui.commandNum == 1) {
					//adding serialization later
				}
				if(gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}	
		}
	
		else if(gp.ui.titleScreenState == 1) {
			
				if (code == KeyEvent.VK_W) {
					gp.ui.commandNum--;
					if (gp.ui.commandNum<0) {
						gp.ui.commandNum=3;
					}
				}
				
				if (code == KeyEvent.VK_S) {
					gp.ui.commandNum++;
					if(gp.ui.commandNum>3) {
						gp.ui.commandNum=0;
					}
				}
				if (code == KeyEvent.VK_ENTER) {
					if(gp.ui.commandNum == 0) {
						System.out.println("Do some fighter stuff");
						gp.gameState=gp.playState;
						gp.playMusic(0);
					}
					if(gp.ui.commandNum == 1) {
						System.out.println("Do some thief stuff");
						gp.gameState=gp.playState;
						gp.playMusic(0);
					}
					if(gp.ui.commandNum == 2) {
						System.out.println("Do some sorcerer stuff");
						gp.gameState=gp.playState;
						gp.playMusic(0);
					}
					if(gp.ui.commandNum == 3) {
						gp.ui.titleScreenState = 0;
					}
				}	
			}
	}
	
	public void playState(int code) {
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
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
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
	
	public void pauseState(int code) {
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}
	}
	public void dialogueState (int code) {
		if (code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}
	
	public void characterState ( int code) {
		if (code == KeyEvent.VK_C);
		gp.gameState = gp.playState;
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
