package model;

public class Gloves extends Item{

	public Gloves(String name, String buff) {
		super(name, buff);
		// TODO Auto-generated constructor stub
	}
	private int AccuracyUp;
	public int getAccuracyUp() {
		return AccuracyUp;
	}
	public void setAccuracyUp(int accuracyUp) {
		AccuracyUp = accuracyUp;
	}
	
}
