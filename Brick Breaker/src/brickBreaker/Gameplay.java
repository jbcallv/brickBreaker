package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineListener;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	int max = 600;
	int min = 200;
	int random_intX = (int) (Math.random() * (max - min + 1) + min);
	int random_intY = (int) (Math.random() * (max - min + 1) + min);
	
	private boolean gameOver = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer time;
	private int delay = 8;
	
	private int playerx = 310;
	
	private int ballPosX = random_intX;
	private int ballPosY = random_intY;
	private int ballDirX = -1;
	private int ballDirY = -2;
	
	private MapGen map;
	
	
	public Gameplay() {
		map = new MapGen(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		time = new Timer(delay, this);
		time.start();
		
	}
	
	public void paint(Graphics g) {
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// draw bricks
		map.draw((Graphics2D) g);
		
		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// score
		g.setColor(Color.green);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
		
		// paddle
		g.setColor(Color.green);
		g.fillRect(playerx, 550, 100, 8);
		
		// ball
		g.setColor(Color.blue);
		g.fillRect(ballPosX, ballPosY, 20, 20);
		
		if (totalBricks <= 0) {
			gameOver = false;
			ballDirX = 0;
			ballDirY = 0;
			g.setColor(Color.green);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won", 260, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Start the Next Level", 200, 350);
		}
		
		if (ballPosY > 570) {
			gameOver = false;
			ballDirX = 0;
			ballDirY = 0;
			g.setColor(Color.green);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
			
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		time.start();
		
		if (gameOver) {
			
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerx, 550, 100, 8))) {
				ballDirY = -ballDirY;
			}
			
			A : for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if (ballPosX + 19 <= brickRect.x || ballPosX +1 >= brickRect.x + brickRect.width) {
								ballDirX = -ballDirX;
							} else {
								ballDirY = -ballDirY;
							}
							
							break A;
						}
					}
				}
			}
			
			ballPosX += ballDirX;
			ballPosY += ballDirY;
			
			if (ballPosX < 0) {
				ballDirX = -ballDirX;
			}
			if (ballPosY < 0) {
				ballDirY = -ballDirY;
			}
			if (ballPosX > 670) {
				ballDirX = -ballDirX;
			}
		}

		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerx >= 600) {
				playerx = 600;
			} else {
				// move right
				gameOver = true;
				playerx += 20;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerx < 10) {
				playerx = 10;
			} else {
				// move left
				gameOver = true;
				playerx -= 20;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!gameOver) {
				gameOver = true;
				
				int max = 600;
				int min = 200;
				int random_intX2 = (int) (Math.random() * (max - min + 1) + min);
				int random_intY2 = (int) (Math.random() * (max - min + 1) + min);
				
				ballPosX = random_intX2;
				ballPosY = random_intY2;
				
				int a = -1;
				int b = 1;
				int randomDir = new Random().nextBoolean() ? a : b;
				
				ballDirX = randomDir;
				ballDirY = -2;
				playerx = 310;
				score = 0;
				totalBricks += 10;
				int x = 3;
				int y = 7;
				map = new MapGen(x, y);
				
				x++;
				y++;
				
				repaint();
				
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
}
