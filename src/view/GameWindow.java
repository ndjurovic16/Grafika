package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;
import model.Attack;
import model.Enemy;
import model.Player;
import model.Tile;

public class GameWindow extends GameFrame{
	
	private static final int TILE_W = 128;
	private static final int TILE_H = 64;
	
	private Point mousePoint = new Point();
	private Point mouseWorld = new Point();
	private Point zeroPoint = new Point(0, 0);
	
	private int mapW = 30;
	private int mapH = 30;
	
	private int camX = 0;
	private int camY = 0;
	
	private int selX = 0;
	private int selY = 0;
	
	private Map map;
	private Player player;
	private Enemy currentEnemy;
	
	private Enemy enemy1;
	private Enemy enemy2;
	private Enemy enemy3;
	private Enemy enemy4;
	
	private Point enemyPosition1;
	private Point enemyPosition2;
	private Point enemyPosition3;
	private Point enemyPosition4;
	
	private boolean enemy1Defeated=false;
	private boolean enemy2Defeated=false;
	private boolean enemy3Defeated=false;
	private boolean enemy4Defeated=false;
	
	private boolean inBattle=false;
	private boolean attackAnimation=false;
	private boolean toBattle=false;
	private boolean toMap=false;
	
	
	private boolean bossEnabled=false;
	
	private Enemy boss;
	private Point bossPosition=new Point(500, 375);
	
	private Point playerPosition=new Point(500, 375);

	public GameWindow(String title, int sizeX, int sizeY) {
		super(title, sizeX, sizeY);
		// TODO Auto-generated constructor stub
		setUpdateRate(60);
		map=new Map();
		player=new Player(100, "Coka Master Baker", 0);
		// Set player Sprite array, (0,1,2) UP (3,4,5) LEFT (6,7,8) RIGHT (9,10,11) DOWN
		/* Generate Enemies 
		 * 1 -> Earth elemental
		 * 2 -> Water elemental
		 * 3 -> Fire elemental
		 * 4 -> Air elemental
		 * enemy1=new Enemy(100,"");
		 */
		enemy1=new Enemy(100,"Earth Elemental",new Attack(50, 100),35);
		enemy1.setSprite(null); //TODO: Add sprite
		enemy2=new Enemy(100,"Water Elemental",new Attack(50, 50),25);
		enemy2.setSprite(null); //TODO: Add sprite
		enemy3=new Enemy(80,"Fire Elemental",new Attack(85, 25),0);
		enemy3.setSprite(null); //TODO: Add sprite
		enemy4=new Enemy(100,"Air Elemental",new Attack(40, 50),15);
		enemy4.setSprite(null); //TODO: Add sprite
		
		startThread();
	}

	
	private static final long serialVersionUID = 1L;

	@Override
	public void handleKeyDown(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleKeyUp(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseDown(int arg0, int arg1, GFMouseButton arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseMove(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseUp(int arg0, int arg1, GFMouseButton arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleWindowDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleWindowInit() {
		// TODO Auto-generated method stub
		
	}
	private void unprojectFromScreen(Point scrPt, Point worldPt)
	{
		int mX = scrPt.x + camX;
		int mY = scrPt.y + camY;
		worldPt.y = (mY * 2 - mX) / 2;
		worldPt.x = mX + worldPt.y;
	}
	
	private void projectToScreen(Point worldPt, Point scrPt)
	{
		scrPt.x = (worldPt.x - worldPt.y) / 1 - camX;
		scrPt.y = (worldPt.x + worldPt.y) / 2 - camY;
	}

	@Override
	public void render(Graphics2D arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		if(!inBattle){
		Point startWorld = new Point();
		
		Point tileView = new Point();
		Point tileWorld = new Point();
		
		unprojectFromScreen(zeroPoint, startWorld);
		
		int x0 = startWorld.x / TILE_H - 2;
		int y0 = startWorld.y / TILE_H - 1;
		int x1 = arg1 / TILE_W + 4;
		int y1 = arg2 / TILE_H * 2 + 5;
		
		for(int y = 0; y <= y1; ++y)
		{
			for(int x = 0; x <= x1; ++x)
			{
				int X = x0 + x + y/2;
				int Y = y0 + y/2 - x + (y % 2);
				
				if(X < 0 || Y < 0 || X >= mapW || Y >= mapH) continue;
				
				tileWorld.x = X * TILE_H;
				tileWorld.y = Y * TILE_H;
				
				projectToScreen(tileWorld, tileView);
				
				arg0.drawImage(map.getImage(map.getTileMatrix()[X][Y]), tileView.x - TILE_W / 2, tileView.y, null);
				//System.out.println(map.getImage(map.getTileMatrix()[X][Y]));
				
			}
		}
		arg0.drawImage(player.getCurrentSprite(), playerPosition.x, playerPosition.y, null);
		if(!enemy1Defeated) arg0.drawImage(enemy1.getSprite(), enemyPosition1.x, enemyPosition1.y, null);
		if(!enemy2Defeated) arg0.drawImage(enemy2.getSprite(), enemyPosition2.x, enemyPosition2.y, null);
		if(!enemy3Defeated) arg0.drawImage(enemy3.getSprite(), enemyPosition3.x, enemyPosition3.y, null);
		if(!enemy4Defeated) arg0.drawImage(enemy4.getSprite(), enemyPosition4.x, enemyPosition4.y, null);
		}
		else{
			//Battle Screen
		}
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	if(!inBattle){
		if(isKeyDown(KeyEvent.VK_LEFT)) {
			camX -= 10;
			playerPosition.x-=10;
			//Set this shit on a timer so that it doesn't spaz out
			/*if(player.getSprites().indexOf(player.getCurrentSprite())==3) player.setCurrentSprite(player.getSprites().get(4));
			else if(player.getSprites().indexOf(player.getCurrentSprite())==4) player.setCurrentSprite(player.getSprites().get(5));
			else player.setCurrentSprite(player.getSprites().get(3));
			*/
		}
		if(isKeyDown(KeyEvent.VK_RIGHT)){
			camX += 10;
			playerPosition.x+=10;
			//Set this shit on a timer so that it doesn't spaz out
			/*if(player.getSprites().indexOf(player.getCurrentSprite())==6) player.setCurrentSprite(player.getSprites().get(7));
			else if(player.getSprites().indexOf(player.getCurrentSprite())==7) player.setCurrentSprite(player.getSprites().get(8));
			else player.setCurrentSprite(player.getSprites().get(6));
			*/
		}
		if(isKeyDown(KeyEvent.VK_UP)) {
			camY -= 10;
			playerPosition.y-=10;
			//Set this shit on a timer so that it doesn't spaz out
			/*if(player.getSprites().indexOf(player.getCurrentSprite())==0) player.setCurrentSprite(player.getSprites().get(1));
			else if(player.getSprites().indexOf(player.getCurrentSprite())==1) player.setCurrentSprite(player.getSprites().get(2));
			else player.setCurrentSprite(player.getSprites().get(0));
			*/
		}
		if(isKeyDown(KeyEvent.VK_DOWN)) {
			camY += 10;
			playerPosition.y+=10;
			//Set this shit on a timer so that it doesn't spaz out
			/*if(player.getSprites().indexOf(player.getCurrentSprite())==9) player.setCurrentSprite(player.getSprites().get(10));
			else if(player.getSprites().indexOf(player.getCurrentSprite())==10) player.setCurrentSprite(player.getSprites().get(11));
			else player.setCurrentSprite(player.getSprites().get(9));
			*/
		}
		if(isKeyDown(KeyEvent.VK_SPACE)){
			if(bossEnabled){
				if(Math.abs(playerPosition.x-bossPosition.x)<10 && Math.abs(playerPosition.y-bossPosition.y)<10){
					inBattle=true;
					currentEnemy=boss;
				}
			}
				else{
					if(!enemy1Defeated)
					if(Math.abs(playerPosition.x-enemyPosition1.x)<10 && Math.abs(playerPosition.y-enemyPosition1.y)<10){
						inBattle=true;
						currentEnemy=enemy1;
				}
					if(enemy2Defeated)
					if(Math.abs(playerPosition.x-enemyPosition2.x)<10 && Math.abs(playerPosition.y-enemyPosition2.y)<10){
						inBattle=true;
						currentEnemy=enemy2;
			}
					if(enemy3Defeated)
					if(Math.abs(playerPosition.x-enemyPosition3.x)<10 && Math.abs(playerPosition.y-enemyPosition3.y)<10){
						inBattle=true;
						currentEnemy=enemy3;
		}
					if(enemy4Defeated)
					if(Math.abs(playerPosition.x-enemyPosition4.x)<10 && Math.abs(playerPosition.y-enemyPosition4.y)<10){
						inBattle=true;
						currentEnemy=enemy4;
					}
				}
	
		mousePoint.x = getMouseX();
		mousePoint.y = getMouseY();
		
		unprojectFromScreen(mousePoint, mouseWorld);
		
		selX = mouseWorld.x / TILE_H;
		selY = mouseWorld.y / TILE_H;
		
		if(selX < 0) selX = 0;
		if(selY < 0) selY = 0;
		if(selX >= mapW) selX = mapW - 1;
		if(selY >= mapH) selY = mapH - 1;
	}
	else{
		if(isKeyDown(KeyEvent.VK_ENTER)){
			player.doAttack(player.getAttacks().get(0), currentEnemy);
			if(currentEnemy.getHP()<=0) inBattle=false;
			else currentEnemy.doAttack(currentEnemy.getAttacks().get(0), player);
		}
	}
	}
	
	// Battle controls **
	// Loud noises
	}

}
