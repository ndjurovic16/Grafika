package model;

public class Trinket extends Item{

	public Trinket(String name, String buff) {
		super(name, buff);
		// TODO Auto-generated constructor stub
	}
	private int HPup;
	public int getHPup() {
		return HPup;
	}
	public void setHPup(int hPup) {
		HPup = hPup;
	}
	
}
