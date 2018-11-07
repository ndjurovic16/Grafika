package model;

import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage image;
	public int offsetX = 0;
	public int offsetY = 0;
	public int tileID = 0;
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public int getOffsetX() {
		return offsetX;
	}
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}
	public int getOffsetY() {
		return offsetY;
	}
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	public int getTileID() {
		return tileID;
	}
	public void setTileID(int tileID) {
		this.tileID = tileID;
	}
	public Tile(BufferedImage image, int offsetX, int offsetY, int tileID) {
		super();
		this.image = image;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.tileID = tileID;
	}
	public Tile(BufferedImage image, int tileID){
		super();
		this.image=image;
		this.tileID=tileID;
	}
	
}
