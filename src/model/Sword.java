package model;

public class Sword extends Item{
	private int DMGup;
	public Sword(String name, String buff) {
		super(name, buff);
		
	}
	public int getDMGup() {
		return DMGup;
	}
	public void setDMGup(int dMGup) {
		DMGup = dMGup;
	}
	

}
