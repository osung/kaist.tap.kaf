package kaist.tap.kaf.views;

public class Component {
	protected String mName;
	protected String mPosition;
	protected String mColor;
	protected String mLineThickness;
	protected String mLineStyle;
	protected String mPortList;
	protected String mPortAvailability;
	public String getEndPosition() {
		return mEndPosition;
	}
	public void setendPosition(String endPosition) {
		mEndPosition = endPosition;
	}

	protected String mEndPosition;

	
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public String getPosition() {
		return mPosition;
	}
	public void setPosition(String position) {
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
