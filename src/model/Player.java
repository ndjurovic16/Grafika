package model;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import rafgfxlib.Util;

public class Player {
	private int HP;
	private String name;
	private ArrayList<Attack> attacks;
	private ArrayList<Item> items;
	private ArrayList<BufferedImage> sprites;
	private BufferedImage currentSprite;
	private int defence;
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public ArrayList<Attack> getAttacks() {
		return attacks;
	}
	public void setAttacks(ArrayList<Attack> attacks) {
		this.attacks = attacks;
	}
	public int getDefence() {
		return defence;
	}
	public void setDefence(int defence) {
		this.defence = defence;
	}
	
	public ArrayList<BufferedImage> getSprites() {
		return sprites;
	}
	public void setSprites(ArrayList<BufferedImage> sprites) {
		this.sprites = sprites;
		currentSprite=sprites.get(0);
	}
	
	public BufferedImage getCurrentSprite() {
		return currentSprite;
	}
	public void setCurrentSprite(BufferedImage currentSprite) {
		this.currentSprite = currentSprite;
	}
	public Player(int hP, String name, int defence) {
		super();
		HP = hP;
		this.name = name;
		this.defence = defence;
		items=new ArrayList<>();
		attacks=new ArrayList<>();
		sprites=new ArrayList<>();
		BufferedImage[] spriteQueue = Util.cutTiles1D(3, 4, Util.loadImage("resources/sofija.png"));
		for(int i=0;i<spriteQueue.length-1;i++) sprites.add(spriteQueue[i]);
		currentSprite=sprites.get(0);
	}
	public void doAttack(Attack attack, Enemy enemy){
		Random r=new Random();
		if(attack.getAccuracy()==100){
			if(attack.getDMG()>enemy.getDefence())
				enemy.setHP(enemy.getHP()-(attack.getDMG()-enemy.getDefence()));
		}
		else{
			if(r.nextInt(100)<attack.getAccuracy()){
				if(attack.getDMG()>enemy.getDefence())
					enemy.setHP(enemy.getHP()-(attack.getDMG()-enemy.getDefence()));
			}
		}
	}
}
