package org.dwg.go;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class GoView extends JFrame {
	private static final long serialVersionUID = 1L;
	private static GoView instance;
	private List<Point> viewList;
	private Point.Player currentPlayer;
	private List<Line> lineList;
	private int unit = 30;

	public void parse(String goView) {
		String[] tempView1Arr = goView.split("];");
		String[][] tempView2Arr = new String[tempView1Arr.length][2];
		for (int i = 0; i < tempView1Arr.length; i++) {
			tempView2Arr[i] = tempView1Arr[i].split("\\[");
			char[] pointCharArr = tempView2Arr[i][1].toCharArray();
			if ("B".equalsIgnoreCase(tempView2Arr[i][0])) {
				viewList.add(new Point(Point.Player.B, pointCharArr[0] - 'a', pointCharArr[1] - 'a'));
				currentPlayer = Point.Player.W;
			} else if ("W".equalsIgnoreCase(tempView2Arr[i][0])) {
				viewList.add(new Point(Point.Player.W, pointCharArr[0] - 'a', pointCharArr[1] - 'a'));
				currentPlayer = Point.Player.B;
			}
		}
	}

	public static GoView getInstance() {
		if (null == instance) {
			instance = new GoView();
			instance.viewList = new ArrayList<Point>();
			instance.lineList = new ArrayList<Line>();
			instance.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent event) {
					super.mousePressed(event);
					StringBuffer buffer = new StringBuffer();
					if(Point.Player.B.equals(instance.currentPlayer)){
						buffer.append("B[");
						instance.currentPlayer = Point.Player.W;
					}else if(Point.Player.W.equals(instance.currentPlayer)){
						buffer.append("W[");
						instance.currentPlayer = Point.Player.B;
					}
					buffer.append((char)(((event.getX() + instance.unit / 2) / instance.unit - 3) + 'a'));
					buffer.append((char)(((event.getY() + instance.unit / 2) / instance.unit - 3) + 'a'));
					buffer.append("]");
					instance.parse(buffer.toString());
					instance.drawParse();
				}
			});
		}
		return instance;
	}

	public void drawPanel() {
		instance.getGraphics().create();
		instance.getGraphics().setColor(Color.BLACK);
		instance.getGraphics().fillArc(6 * unit - 2, 6 * unit - 2, 6, 6, 0, 360);
		instance.getGraphics().fillArc(6 * unit - 2, 12 * unit - 2, 6, 6, 0, 360);
		instance.getGraphics().fillArc(6 * unit - 2, 18 * unit - 2, 6, 6, 0, 360);
		instance.getGraphics().fillArc(12 * unit - 2, 6 * unit - 2, 6, 6, 0, 360);
		instance.getGraphics().fillArc(12 * unit - 2, 12 * unit - 2, 6, 6, 0, 360);
		instance.getGraphics().fillArc(12 * unit - 2, 18 * unit - 2, 6, 6, 0, 360);
		instance.getGraphics().fillArc(18 * unit - 2, 6 * unit - 2, 6, 6, 0, 360);
		instance.getGraphics().fillArc(18 * unit - 2, 12 * unit - 2, 6, 6, 0, 360);
		instance.getGraphics().fillArc(18 * unit - 2, 18 * unit - 2, 6, 6, 0, 360);
		for (int i = 21; i > 2; i--) {
			instance.getGraphics().drawLine(i * unit, 3 * unit, i * unit, 21 * unit);
			instance.getGraphics().drawLine(3 * unit, i * unit, 21 * unit, i * unit);
		}
	}

	public void drawParse(){
		for (int i = 0; i < viewList.size(); i++) {
			Point tempPoint = viewList.get(i);
			if(Point.Player.W.equals(tempPoint.getPlayer())){
				instance.getGraphics().drawArc((tempPoint.getX() + 3) * unit-unit/4, (tempPoint.getY() + 3) * unit-unit/4, unit / 2, unit / 2, 0, 360);
			}else if(Point.Player.B.equals(tempPoint.getPlayer())){
				instance.getGraphics().fillArc((tempPoint.getX() + 3) * unit-unit/4, (tempPoint.getY() + 3) * unit-unit/4, unit / 2, unit / 2, 0, 360);
			}
		}
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.setColor(Color.ORANGE);
		graphics.fillRect(0, 0, instance.getWidth(), instance.getHeight());
		drawPanel();
		drawParse();
	}
	
	private GoView() {
		super();
		currentPlayer = Point.Player.B;
		setVisible(true);
		setSize(unit * 25, unit * 25);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public List<Point> getViewList() {
		return viewList;
	}

	public void setViewList(List<Point> viewList) {
		this.viewList = viewList;
	}

	public List<Line> getLineList() {
		return lineList;
	}

	public void setLineList(List<Line> lineList) {
		this.lineList = lineList;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public Point.Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Point.Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

}
