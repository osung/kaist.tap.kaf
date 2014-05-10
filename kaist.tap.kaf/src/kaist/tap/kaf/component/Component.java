package kaist.tap.kaf.component;



public class Component {

	public class Point {
		public int x;
		public int y;
		
		public Point() {
			x = y = 0;
		}
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	protected String mName;
	protected Point mPosition;
	protected String mColor;
	protected String mLineThickness;
	protected String mLineStyle;
	protected String mPortList;
	protected String mPortAvailability;
	protected Point mEndPosition;
	
	public Point getEndPosition() {
		return mEndPosition;
	}
	
	public void setEndPosition(Point endPosition) {
		mEndPosition = endPosition;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	public Point getPosition() {
		return mPosition;
	}
	public void setPosition(Point position) {
		mPosition = position;
	}
	public String getColor() {
		return mColor;
	}
	public void setColor(String color) {
		mColor = color;
	}
	public String getLineThickness() {
		return mLineThickness;
	}
	public void setLineThickness(String lineThickness) {
		mLineThickness = lineThickness;
	}
	public String getLineStyle() {
		return mLineStyle;
	}
	public void setLineStyle(String lineStyle) {
		mLineStyle = lineStyle;
	}
	public String getPortList() {
		return mPortList;
	}
	public void setPortList(String portList) {
		mPortList = portList;
	}
	public String getPortAvailability() {
		return mPortAvailability;
	}
}
