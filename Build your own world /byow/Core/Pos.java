package byow.Core;

public class Pos {
	public int x;
	public int y;
	
	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Pos plus(int dx, int dy) {
		return new Pos(x +dx, y +dy);
	}
	
	public String toString() {
		return "x: " + this.x + ", y:" + this.y;

	}
}
