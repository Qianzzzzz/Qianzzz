package byow.Core;

import java.util.LinkedList;
import java.util.Random;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Rect {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Rect(Random rand, int x1, int x2, int y1, int y2) {
		this.x = RandomUtils.uniform(rand, x1, x2);
		this.y = RandomUtils.uniform(rand, y1, y2);
	}
	
	public void genWH(Random rand, int WIDTH, int HEIGHT, int RANGE) {
		width = RandomUtils.uniform(rand, 2, RANGE*2-2);//The total of Length, width no exceed 2 range. But not <2.
		height = RandomUtils.uniform(rand, 2, RANGE*2 - width);
        if(x+width > WIDTH-1) {
        	width = WIDTH-1-x;
        }
        
        if(y+height > HEIGHT-1) {
        	height = HEIGHT-1-y;
        }
	}
	
	public int getRightX() {
		return x+width;
	}
	
	public int getLeftX() {
		return x;
	}
	
	public int getY1() {
		return y;
	}
	
	public int getY2() {
		return y+height;
	}
	
	public void fillWorld(TETile[][] finalWorldFrame) {
		for(int xi=x; xi<x+width; xi++) {
        	for(int yi=y; yi< y + height;yi++) {
        		finalWorldFrame[xi][yi] = Tileset.FLOOR;
        	}
        }
	}
	
	public void connectTo(Rect other, Random rand, TETile[][] finalWorldFrame) {
		int cx, cy;
		if(getRightX() <= other.getLeftX()) {
			cy = RandomUtils.uniform(rand, y, y+height);
			for(int xi = getRightX(); xi< other.getLeftX();xi++) {
				finalWorldFrame[xi][cy] = Tileset.FLOOR;
			}
			cx = other.getLeftX();
		}else if(getY1() > other.getY2()) {
			cy = getY1();
			cx = RandomUtils.uniform(rand, other.getLeftX(), getRightX());
		}else if(getY2() < other.getY1()) {
			cy = getY2();
			cx = RandomUtils.uniform(rand, other.getLeftX(), getRightX());
		}else {
			return;
		}
		
		if(cy<other.getY1()) {
			for(int yi = cy; yi < other.getY1();yi++) {
				finalWorldFrame[cx][yi] = Tileset.FLOOR;
			}
		}else if(cy > other.getY2()) {
			for(int yi = cy; yi >= other.getY2();yi--) {
				finalWorldFrame[cx][yi] = Tileset.FLOOR;
			}
		}
		
	}
	
	public LinkedList<Pos> getAdjWall(TETile[][] finalWorldFrame) {
		int wx = finalWorldFrame.length;
		int wy = finalWorldFrame[0].length;
		
		LinkedList<Pos> posList = new LinkedList<Pos>();
		// return the position list of adjacent wall
		for(int xi=getLeftX(); xi<getRightX(); xi++) {
			if (getY1()-1 >=  0 && getY1()-1 <wy &&
					finalWorldFrame[xi][getY1()-1].equals(Tileset.WALL)) {
				posList.add(new Pos(xi, getY1()-1));
			}
			if (getY2() >=  0 && getY2() <wy && 
					finalWorldFrame[xi][getY2()].equals(Tileset.WALL)) {
				posList.add(new Pos(xi, getY2()));
			}
		}
		
		for(int yi=getY1(); yi<getY2(); yi++) {
			if (getLeftX()-1 >=  0 && getLeftX()-1 <wx &&
					finalWorldFrame[getLeftX()-1][yi].equals(Tileset.WALL)) {
				posList.add(new Pos(getLeftX()-1, yi));
			}
			if (getRightX() >=  0 && getRightX() <wx &&
					finalWorldFrame[getRightX()][yi].equals(Tileset.WALL)) {
				posList.add(new Pos(getRightX(), yi));
			}
		}
		
		return posList;
	}
	
	public Pos genRandomPos(Random rand) {
		int px = RandomUtils.uniform(rand, getLeftX(), getRightX());
		int py = RandomUtils.uniform(rand, getY1(), getY2());
		return new Pos(px, py);	
	}
	
	public String toString() {
		return "Rect: x: "+ x + ", y: " + y + ". width= " + width + ", height: " + height;
	}
}
