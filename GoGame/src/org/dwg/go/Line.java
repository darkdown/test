package org.dwg.go;

public class Line {

	private Point lineStart;
	private Point lineEnd;

	public Line(Point lineStart, Point lineEnd) {
		this.lineStart = lineStart;
		this.lineEnd = lineEnd;
	}

	public Line() {
	}

	public Point getLineStart() {
		return lineStart;
	}

	public void setLineStart(Point lineStart) {
		this.lineStart = lineStart;
	}

	public Point getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(Point lineEnd) {
		this.lineEnd = lineEnd;
	}

	@Override
	public String toString() {
		return this.lineStart+"-"+this.lineEnd;
	}
}
