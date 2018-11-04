package model;

public class Attack {
	private int DMG;
	private int accuracy;
	public int getDMG() {
		return DMG;
	}
	public void setDMG(int dMG) {
		DMG = dMG;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public Attack(int dMG, int accuracy) {
		super();
		DMG = dMG;
		this.accuracy = accuracy;
	}
	

}
