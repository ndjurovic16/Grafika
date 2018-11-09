package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Date;
import java.util.Random;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;
import model.Attack;
import model.Blood;
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
	
	private boolean enemy1Drawn=false;
	private boolean enemy2Drawn=false;
	private boolean enemy3Drawn=false;
	private boolean enemy4Drawn=false;
	
	private boolean inBattle=false;
	private boolean attackAnimation=false;
	private boolean toBattle=false;
	private boolean toMap=false;
	
	private boolean positionMatcher1=false;
	private boolean positionMatcher2=false;
	private boolean positionMatcher3=false;
	private boolean positionMatcher4=false;
	
	
	private boolean bossEnabled=false;
	
	private Enemy boss;
	private Point bossPosition=new Point(500, 375);
	private boolean bossMatcher=false;
	
	private Point playerPosition=new Point(500, 375);
	private BufferedImage battlescreen;
	private BufferedImage playerBattleImage;
	
	private BufferedImage background;
	private Blood[] drops = new Blood[20];
	private boolean bossDrawn=false;

	public GameWindow(String title, int sizeX, int sizeY) {
		super(title, sizeX, sizeY);
		// TODO Auto-generated constructor stub
		setUpdateRate(60);
		map=new Map();
		player=new Player(100, "Coka Master Baker", 0);
		player.getAttacks().add(new Attack(100, 100));
		// Set player Sprite array, (0,1,2) UP (3,4,5) LEFT (6,7,8) RIGHT (9,10,11) DOWN
		/* Generate Enemies 
		 * 1 -> Earth elemental
		 * 2 -> Water elemental
		 * 3 -> Fire elemental
		 * 4 -> Air elemental
		 * enemy1=new Enemy(100,"");
		 */
		enemy1=new Enemy(100,"Earth Elemental",new Attack(50, 100),35);
		enemyPosition1=new Point(995, 467);
		enemy1.setColor(Color.YELLOW);
		enemy1.setSprite(Util.loadImage("resources/earth.png")); //TODO: Add sprite
		enemy2=new Enemy(100,"Water Elemental",new Attack(50, 50),25);
		enemyPosition2=new Point(421, 718);
		enemy2.setColor(Color.BLUE);
		enemy2.setSprite(Util.loadImage("resources/water.png")); //TODO: Add sprite
		enemy3=new Enemy(80,"Fire Elemental",new Attack(85, 25),0);
		enemyPosition3=new Point(8, 485);
		enemy3.setColor(Color.ORANGE);
		enemy3.setSprite(Util.loadImage("resources/fire.png")); //TODO: Add sprite
		enemy4=new Enemy(100,"Air Elemental",new Attack(40, 50),15);
		enemyPosition4=new Point(461, 100);
		enemy4.setColor(Color.GREEN);
		enemy4.setSprite(Util.loadImage("resources/air.png")); //TODO: Add sprite
		
		boss=new Enemy(100, "Hot Naked Dude", new Attack(30, 100), 10);
		BufferedImage[] bosses = Util.cutTiles1D(3, 4, Util.loadImage("resources/boss.png"));
		boss.setSprite(bosses[0]);
		boss.setColor(Color.RED);
		
		battlescreen=Util.loadImage("resources/borba.png");
		background=Util.loadImage("resources/background.png");
		//for(int i=0;i<drops.length;i++) drops[i]=new Blood(495, 356, 10, 300, 50);
		
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
		System.out.println(arg0+"/"+arg1);
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
		
		arg0.drawImage(background, -200+(camX/6), -200+(camY/6), null);
		
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
		System.out.println(camX+"/"+camY);
		if(camX>=730 && !enemy1Defeated){
			if(!positionMatcher1){enemyPosition1.y-=camY-465; positionMatcher1=true;}
			arg0.drawImage(enemy1.getSprite(), enemyPosition1.x, enemyPosition1.y, null);
			enemy1Drawn=true;
			
			}
		if(camX<730){enemy1Drawn=false; enemyPosition1=new Point(995, 467); positionMatcher1=false;}
		if(camY>=1080 && !enemy2Defeated) {
			if(!positionMatcher2){enemyPosition2.x-=camX+390; positionMatcher2=true;}
			arg0.drawImage(enemy2.getSprite(), enemyPosition2.x, enemyPosition2.y, null); 
			enemy2Drawn=true;
			}
		if(camY<1080){enemy2Drawn=false; enemyPosition2=new Point(421, 718); positionMatcher2=false;}
		if(camX<=-1695 && !enemy3Defeated) {
			if(!positionMatcher3){enemyPosition3.y-=camY-495; positionMatcher3=true;}
			arg0.drawImage(enemy3.getSprite(), enemyPosition3.x, enemyPosition3.y, null);
			enemy3Drawn=true;
		}
		if(camX>-1695){enemy3Drawn=false; enemyPosition3=new Point(8, 485); positionMatcher3=false;}
		if(camY<=105 && !enemy4Defeated) {
			if(!positionMatcher4){enemyPosition4.x-=camX+400; positionMatcher4=true;}
			arg0.drawImage(enemy4.getSprite(), enemyPosition4.x, enemyPosition4.y, null);
			enemy4Drawn=true;
		}
		if(camY>105){enemy4Drawn=false; enemyPosition4=new Point(470, 29); positionMatcher4=false;}
		
		
		
		/* BOSS */
		if(bossEnabled){
			if(camX>=730){
				if(!bossMatcher){bossPosition.y-=camY-465; bossMatcher=true;}
				arg0.drawImage(boss.getSprite(), bossPosition.x, bossPosition.y, null);
				bossDrawn=true;
				
				}
			if(camX<730){bossDrawn=false; bossPosition=new Point(995, 467); bossMatcher=false;}
		}
	}
		
		else{
			//Battle Screen
			arg0.drawImage(battlescreen, 0, 0, null);
			arg0.drawImage(player.getSprites().get(9), 89, 478, null);
			arg0.drawImage(currentEnemy.getSprite(), 495, 156, null);
			/*if(attackAnimation){
				arg0.setColor(currentEnemy.getColor());
				
				for(Blood b : drops)
				{
					if(b.life <= 0) continue;
					
					arg0.drawLine((int)(b.posX - b.dX), (int)(b.posY - b.dY), (int)b.posX, (int)b.posY);
				}
			}*/
		}
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(bossEnabled){
			WritableRaster raster=background.getRaster();
			int[] k=new int[3];
			for (int i = 0; i < 1920; i++) {
				for(int j=0; j<1080;j+=3) {
					k=raster.getPixel(i, j, k);
					k[2]+=10;
					raster.setPixel(i, j, k);
				}
			}
		}
		if(enemy1Defeated && enemy2Defeated && enemy3Defeated && enemy4Defeated) bossEnabled=true;
	if(!inBattle){
		if(isKeyDown(KeyEvent.VK_LEFT)) {
			camX -= 15;
			if(enemy1Drawn) enemyPosition1.x+=15;
			if(enemy2Drawn) enemyPosition2.x+=15;
			if(enemy3Drawn) enemyPosition3.x+=15;
			if(enemy4Drawn) enemyPosition4.x+=15;
			if(bossDrawn) bossPosition.x+=15;
			playerPosition.x-=3;
			//Set this shit on a timer so that it doesn't spaz out
			if(player.getSprites().indexOf(player.getCurrentSprite())==3) player.setCurrentSprite(player.getSprites().get(4));
			else if(player.getSprites().indexOf(player.getCurrentSprite())==4) player.setCurrentSprite(player.getSprites().get(5));
			else player.setCurrentSprite(player.getSprites().get(3));
			
		}
		if(isKeyDown(KeyEvent.VK_RIGHT)){
			camX += 15;
			if(enemy1Drawn) enemyPosition1.x-=15;
			if(enemy2Drawn) enemyPosition2.x-=15;
			if(enemy3Drawn) enemyPosition3.x-=15;
			if(enemy4Drawn) enemyPosition4.x-=15;
			if(bossDrawn) bossPosition.x-=15;
			playerPosition.x+=3;
			//Set this shit on a timer so that it doesn't spaz out
			if(player.getSprites().indexOf(player.getCurrentSprite())==6) player.setCurrentSprite(player.getSprites().get(7));
			else if(player.getSprites().indexOf(player.getCurrentSprite())==7) player.setCurrentSprite(player.getSprites().get(8));
			else player.setCurrentSprite(player.getSprites().get(6));
			
		}
		if(isKeyDown(KeyEvent.VK_UP)) {
			camY -= 15;
			if(enemy1Drawn) enemyPosition1.y+=15;
			if(enemy2Drawn) enemyPosition2.y+=15;
			if(enemy3Drawn) enemyPosition3.y+=15;
			if(enemy4Drawn) enemyPosition4.y+=15;
			if(bossDrawn) bossPosition.y+=15;
			playerPosition.y-=3;
			//Set this shit on a timer so that it doesn't spaz out
			if(player.getSprites().indexOf(player.getCurrentSprite())==9) player.setCurrentSprite(player.getSprites().get(10));
			else if(player.getSprites().indexOf(player.getCurrentSprite())==10) player.setCurrentSprite(player.getSprites().get(9));
			else player.setCurrentSprite(player.getSprites().get(9));
			
		}
		if(isKeyDown(KeyEvent.VK_DOWN)) {
			camY += 15;
			if(enemy1Drawn) enemyPosition1.y-=15;
			if(enemy2Drawn) enemyPosition2.y-=15;
			if(enemy3Drawn) enemyPosition3.y-=15;
			if(enemy4Drawn) enemyPosition4.y-=15;
			if(bossDrawn) bossPosition.y-=15;
			playerPosition.y+=3;
			//Set this shit on a timer so that it doesn't spaz out
			if(player.getSprites().indexOf(player.getCurrentSprite())==0) player.setCurrentSprite(player.getSprites().get(1));
			else if(player.getSprites().indexOf(player.getCurrentSprite())==1) player.setCurrentSprite(player.getSprites().get(2));
			else player.setCurrentSprite(player.getSprites().get(0));
		}
		if(isKeyDown(KeyEvent.VK_SPACE)){
			if(bossEnabled){
				if(Math.abs(playerPosition.x-bossPosition.x)<100 && Math.abs(playerPosition.y-bossPosition.y)<100){
					inBattle=true;
					currentEnemy=boss;
				}
			}
				else{
					if(!enemy1Defeated)
					if(Math.abs(playerPosition.x-enemyPosition1.x)<100 && Math.abs(playerPosition.y-enemyPosition1.y)<100){
						inBattle=true;
						currentEnemy=enemy1;
				}
					if(!enemy2Defeated)
					if(Math.abs(playerPosition.x-enemyPosition2.x)<100 && Math.abs(playerPosition.y-enemyPosition2.y)<100){
						inBattle=true;
						currentEnemy=enemy2;
			}
					if(!enemy3Defeated)
					if(Math.abs(playerPosition.x-enemyPosition3.x)<100 && Math.abs(playerPosition.y-enemyPosition3.y)<100){
						inBattle=true;
						currentEnemy=enemy3;
		}
					if(!enemy4Defeated)
					if(Math.abs(playerPosition.x-enemyPosition4.x)<100 && Math.abs(playerPosition.y-enemyPosition4.y)<100){
						inBattle=true;
						currentEnemy=enemy4;
					}
				}
	
		
		
		
	}
	}
	else{
		if(isKeyDown(KeyEvent.VK_ENTER)){
			player.doAttack(player.getAttacks().get(0), currentEnemy);
			attackAnimation=true;
			
			if(currentEnemy.getHP()<=0) {inBattle=false; if(currentEnemy==enemy1) enemy1Defeated=true; if(currentEnemy==enemy2) enemy2Defeated=true; 
			if(currentEnemy==enemy3) enemy3Defeated=true; if(currentEnemy==enemy4) enemy4Defeated=true; if(currentEnemy==boss) bossEnabled=false;}
			else currentEnemy.doAttack(currentEnemy.getAttacks().get(0), player);
			System.out.println(currentEnemy.getHP());
			long startTime = System.currentTimeMillis();
			long elapsedTime = 0L;

			while (elapsedTime < 1000) {
			    elapsedTime = (new Date()).getTime() - startTime;
			}
		}
	}
	}
	
	// Battle controls **
	// Loud noises
	}


