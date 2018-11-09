package model;

public class Blood {
	public float posX;
	public float posY;
	public float dX;
	public float dY;
	public int life = 0;
	
	public Blood(float posX, float posY, float dX, float dY, int life) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.dX = dX;
		this.dY = dY;
		this.life = life;
	}
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public float getdX() {
		return dX;
	}
	public void setdX(float dX) {
		this.dX = dX;
	}
	public float getdY() {
		return dY;
	}
	public void setdY(float dY) {
		this.dY = dY;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	
}
