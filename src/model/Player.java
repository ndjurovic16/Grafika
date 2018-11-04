package model;

import java.util.ArrayList;
import java.util.Random;

public class Player {
	private int HP;
	private String name;
	private ArrayList<Attack> attacks;
	private ArrayList<Item> items;
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
	public Player(int hP, String name, int defence) {
		super();
		HP = hP;
		this.name = name;
		this.defence = defence;
		items=new ArrayList<>();
		attacks=new ArrayList<>();
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
