package kaist.tap.kaf.component;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;

public class Port {
	protected Point position;
	protected Vector<Line> connections;
	protected final int width = 10;
	protected final int halfWidth = (int) (width * 0.5);
	
	public Port() {
		position = new Point(0, 0);
		connections = new Vector<Line>();
	}
	
	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
	}
	
	public Point getPosition() {
		return position;
	}
	
	
	public void move(int x, int y) {
		for (int i = 0; i < connections.size(); ++i) {
			Line line = connections.get(i);
			Point spos = line.getPosition();
			Point epos = line.getEndPosition();
			
			if (spos.x == position.x && spos.y == position.y) {
				spos.x += x; spos.y += y;
			}
			else {
				epos.x += x; epos.y += y;
			}
		}
		
		position.x += x;
		position.y += y;
	}
	
	public Line getConnection(int idx) {
		if (idx >= connections.size()) return null;
		
		return connections.get(idx);
	}
	
	public void addConnection(Line line) {
		connections.add(line);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getConnectionSize() {
		return connections.size();
	}
	
	public boolean contains(int x, int y) {
		if (x >= position.x-halfWidth && x <= position.x+halfWidth && 
			y >= position.y-halfWidth && y <= position.y+halfWidth) {
			return true;
		}
		else return false;
	}
	
	public void draw(GC gc) {
		gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		gc.fillRectangle(position.x-halfWidth, position.y-halfWidth, width, width);
		gc.drawRectangle(position.x-halfWidth, position.y-halfWidth, width, width);
	}
}
