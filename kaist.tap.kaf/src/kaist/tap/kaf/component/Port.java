package kaist.tap.kaf.component;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jdom2.Element;

public class Port {
	protected Point position;
	protected Vector<Line> connections;
	protected final int width = 12;
	protected final int halfWidth = (int) (width * 0.5);
	protected Component comp;

	public Port(Component c) {
		position = new Point(0, 0);
		connections = new Vector<Line>();
		comp = c;
	}

	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
	}

	public Point getPosition() {
		return position;
	}

	public void move(int x, int y) {
		position.x += x;
		position.y += y;

		updateConnection();
	}

	public Line getConnection(int idx) {
		if (idx >= connections.size())
			return null;

		return connections.get(idx);
	}

	public void addConnection(Line line) {
		connections.add(line);
	}

	public void removeConnection(Line line) {
		connections.remove(line);
	}

	public int getWidth() {
		return width;
	}

	public int getConnectionSize() {
		return connections.size();
	}

	public boolean contains(int x, int y) {
		if (x >= position.x - halfWidth && x <= position.x + halfWidth
				&& y >= position.y - halfWidth && y <= position.y + halfWidth) {
			return true;
		} else
			return false;
	}

	public void draw(GC gc) {
		gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		gc.setLineStyle(SWT.LINE_SOLID);
		gc.setLineWidth(0);
		gc.fillRectangle(position.x - halfWidth, position.y - halfWidth, width,
				width);
		gc.drawRectangle(position.x - halfWidth, position.y - halfWidth, width,
				width);
	}

	public void updateConnection() {
		for (int i = 0; i < connections.size(); ++i) {
			Line line = connections.get(i);
			if (line.getStartComponent() == comp) {
				line.setPosition(position.x, position.y);
			} else {
				line.setEndPosition(position.x, position.y);
			}
		}
	}

	public Element getPortXMLElement(int id) {
		Element pel = new Element("PORT");
		pel.setAttribute("id", Integer.toString(id));

		Element pos = new Element("POSITION");
		Element posx = new Element("X");
		Element posy = new Element("Y");
		posx.setText(Integer.toString(position.x));
		posy.setText(Integer.toString(position.y));
		pos.addContent(posx);
		pos.addContent(posy);
		pel.addContent(pos);

		return pel;
	}
}
