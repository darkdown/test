package org.dwg.go;

public class Point {

	private Player player;
	private int x;
	private int y;

	public Point(Player player, int x, int y) {
		this.player = player;
		this.x = x;
		this.y = y;
	}

	enum Player {
		B, W
	}

	public Point() {
	}
	
	@Override
	public String toString() {
		return "["+this.player+":("+this.x+","+this.y+")]";
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
