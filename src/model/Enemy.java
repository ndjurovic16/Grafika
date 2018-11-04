package model;

import java.util.ArrayList;
import java.util.Random;

public class Enemy {
	private int HP;
	private String name;
	private ArrayList<Attack> attacks;
	private int defence;
	public Enemy() {
		HP=100;
		name="untitled";
		attacks=new ArrayList<>();
		attacks.add(new Attack(100,100));
		defence=0;
	}
	public Enemy(int hP, String name, ArrayList<Attack> attacks, int defence) {
		super();
		HP = hP;
		this.name = name;
		this.attacks = attacks;
		this.defence = defence;
	}
	public void doAttack(Attack attack,Player player){
		Random r=new Random();
		if(attack.getAccuracy()==100){
			if(attack.getDMG()>player.getDefence())
				player.setHP(player.getHP()-(attack.getDMG()-player.getDefence()));
		}
		else{
			if(r.nextInt(100)<attack.getAccuracy()){
				if(attack.getDMG()>player.getDefence())
					player.setHP(player.getHP()-(attack.getDMG()-player.getDefence()));
			}
		}
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	
	
}
