package demo.main;

public class Grid {

	//20x11
	private int[][] tile;
	public Grid(int width, int height) {
		tile = new int[width][height];
		
		for(int r = 0; r < height; r++) {
			for(int c = 0; c < width; c++) {
				tile[c][r] = 0;
			}
		}
		
	}
	
	public int[][] getTile() {
		return tile;
	}
}
