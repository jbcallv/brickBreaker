package brickBreaker;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Gameplay gamePlay = new Gameplay();
		frame.setBounds(10, 10, 700, 600);
		frame.setTitle("Brick Breaker");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gamePlay);
		
		// checks if user wants AI mode
		try {
			if (args[0].equalsIgnoreCase("AI")) {
				startAI(gamePlay);
			}
		} catch (Exception e) {
			// do nuhin
		}
	}
	
	private static void startAI(Gameplay gp) {
		gp.gameOver = true;
		while (gp.gameOver == true) {
			// checks position of ball to move paddle appropriately. Uses decision tree
			if (gp.ballPosX < gp.playerx) {
				if (gp.playerx < 2) {
					gp.playerx = 0;
				} else {
					// move left
					gp.gameOver = true;
					gp.playerx -= 10;
				}
			}
			else if (gp.ballPosX > gp.playerx) {
					if (gp.playerx >= 600) {
						gp.playerx = 600;
					} else {
						// move right
						gp.gameOver = true;
						gp.playerx += 10;
					}
			} else {
				gp.playerx = gp.playerx;
			}
			gp.repaint();
		}
	}

}
