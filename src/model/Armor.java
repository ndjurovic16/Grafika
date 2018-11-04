package model;

public class Armor extends Item{
	private int DefenceUp;
	public Armor(String name, String buff) {
		super(name, buff);
		// TODO Auto-generated constructor stub
	}
	public int getDefenceUp() {
		return DefenceUp;
	}
	public void setDefenceUp(int defenceUp) {
		DefenceUp = defenceUp;
	}
	
}
