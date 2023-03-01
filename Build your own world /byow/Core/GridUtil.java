package byow.Core;

import byow.TileEngine.TETile;


public class GridUtil {
	public static boolean checkEqual(Pos p, TETile tile, TETile[][] world) {
		return checkEqual(p.x, p.y, tile, world);
	}
	
	public static boolean checkEqual(int x, int y, TETile tile, TETile[][] world) {
		int width = world.length;
		int height = world[0].length;
		if(x<0 || x >= width) {
    		return false;
    	}
    	if(y<0 || y >= height) {
    		return false;
    	}
    	
    	if(tile.equals(world[x][y]) ) {
    		return true;
    	}
    	return false;
    }

}
