package view;

import java.awt.image.BufferedImage;
import java.util.Random;

import model.Tile;
import rafgfxlib.Util;

public class Map {

   int [][] tileMatrix;
   BufferedImage tileSet[];
   public Map()
    {  
	   tileSet = new BufferedImage[6];
	   tileMatrix = new int[30][30];
		for(int i = 0 ; i<30 ; i++)
			for(int j = 0 ; j<30; j++)
			{
				if(i<10)
				{
					if(j<10)
						tileMatrix[i][j]=0;
					else if(j>=10 && j<20)
						tileMatrix[i][j]=4;
					else
						tileMatrix[i][j]=1;
				}
				else if(i>=10 && i<20)
				{
					tileMatrix[i][j]=4;
				}
				else
				{
					if(j<10)
						tileMatrix[i][j]=2;
					else if(j>=10 && j<20)
						tileMatrix[i][j]=4;
					else
						tileMatrix[i][j]=3;
				}
			}
         tileMatrix[14][14]=5;
         
         
        	tileSet[0]= Util.loadImage("resources/Grassy1.png");
        	System.out.println(tileSet[0]);
        	tileSet[1]= Util.loadImage("resources/Red3.png");
        	System.out.println(tileSet[1]);
        	tileSet[2]= Util.loadImage("resources/YellowGrass1.png");
        	System.out.println(tileSet[2]);
        	tileSet[3]= Util.loadImage("resources/Blue4.png");
        	System.out.println(tileSet[3]);
        	tileSet[4]= Util.loadImage("resources/DeepStone3.png");
        	System.out.println(tileSet[4]);
        	tileSet[5]= Util.loadImage("resources/ChocolateCakePink4.png");
        	System.out.println(tileSet[5]);
        	
         
   }
   public int[][] getTileMatrix() {
	   return tileMatrix;
   }
   public void setTileMatrix(int[][] tileMatrix) {
	   this.tileMatrix = tileMatrix;
   }
   public BufferedImage getImage(int i)
   {
	   return tileSet[i];
   }
}
