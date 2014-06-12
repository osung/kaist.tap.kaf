package kaist.tap.kaf.component;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

public class Text extends Rectangle {

	protected String text;
	protected Color fontColor;
	protected int fontSize;
	protected int fontStyle;

	public Text() {
		setName("Text");
		text = new String();
		fontColor = SWTResourceManager.getColor(SWT.COLOR_BLACK);
	}

	public Text(int x, int y, int w, int h, String str) {
		setName("Text");
		position.x = x;
		position.y = y;
		width = w;
		height = h;
		endPosition.x = x + w;
		endPosition.y = y + h;
		text = str;
		fontColor = SWTResourceManager.getColor(SWT.COLOR_BLACK);
		fontSize = 20;
		fontStyle = SWT.NORMAL;
	}

	public void setText(String str) {
		text = str;
	}

	public String getText() {
		return text;
	}

	public void setFontColor(int color) {
		fontColor = SWTResourceManager.getColor(color);
	}

	public void setFontColor(RGB color) {
		fontColor = SWTResourceManager.getColor(color);
	}

	public void setFontColor(Color color) {
		fontColor = color;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontSize(int size) {
		fontSize = size;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontStyle(int style) {
		fontStyle = style;
	}

	public int getFontStyle() {
		return fontStyle;
	}

	public void draw(GC gc) {
		gc.setForeground(getColor());
		gc.setBackground(getFillColor());
		gc.setLineWidth(lineThickness);
		gc.setLineStyle(lineStyle);
		gc.drawRectangle(position.x, position.y, width, height);
		if (fill == true)
			gc.fillRectangle(position.x + 1, position.y + 1, width - 1,
					height - 1);

		FontData[] fd = gc.getFont().getFontData();
		fd[0].setHeight(fontSize);
		fd[0].setStyle(fontStyle);
		gc.setFont(new Font(gc.getDevice(), fd[0]));

		Point p = gc.stringExtent(text);

		gc.setForeground(fontColor);
		gc.drawText(text, (int) (position.x + (width - p.x) * 0.5),
				(int) (position.y + (height - p.y) * 0.5));

		if (selectMode == SelectMode.SELECTED) {
			gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			// draw control
			gc.fillRectangle(position.x - contSize, position.y - contSize,
					contSize * 2, contSize * 2);
			gc.fillRectangle(endPosition.x - contSize,
					endPosition.y - contSize, contSize * 2, contSize * 2);
			gc.fillRectangle(position.x - contSize, endPosition.y - contSize,
					contSize * 2, contSize * 2);
			gc.fillRectangle(endPosition.x - contSize, position.y - contSize,
					contSize * 2, contSize * 2);
		}
	}

	public Text clone() {
		Text text = new Text(position.x, position.y, width, height, this.text);
		text.setColor(color);
		text.setDrawn(drawn);
		text.setLineStyle(lineStyle);
		text.setLineThickness(lineThickness);
		text.setFill(this.getFill());
		text.setFillColor(this.getFillColor());
		text.setText(this.text);
		text.setFontColor(this.getFontColor());
		text.setFontSize(this.getFontSize());
		text.setFontStyle(this.getFontStyle());

		return text;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		TextPropertyDescriptor nameDiscriptor = new TextPropertyDescriptor(
				"Name", "Type");
		TextPropertyDescriptor posxDiscriptor = new TextPropertyDescriptor(
				"Position_X", "X");
		posxDiscriptor.setCategory("Position");
		TextPropertyDescriptor posyDiscriptor = new TextPropertyDescriptor(
				"Position_Y", "Y");
		posyDiscriptor.setCategory("Position");
		ColorPropertyDescriptor lcDiscriptor = new ColorPropertyDescriptor(
				"Color", "Color");
		lcDiscriptor.setCategory("Line");
		TextPropertyDescriptor ltDiscriptor = new TextPropertyDescriptor(
				"Line_Thickness", "Thickness");
		ltDiscriptor.setCategory("Line");
		String[] lsvalues = { "Solid", "Dot", "Dash", "Dashdot", "Dashdotdot" };
		ComboBoxPropertyDescriptor lsDiscriptor = new ComboBoxPropertyDescriptor(
				"Line_Style", "Line Style", lsvalues);
		lsDiscriptor.setCategory("Line");
		ColorPropertyDescriptor pcDiscriptor = new ColorPropertyDescriptor(
				"FillColor", "Color");
		pcDiscriptor.setCategory("Polygon");
		String[] fillvalues = { "On", "Off" };
		ComboBoxPropertyDescriptor pfDiscriptor = new ComboBoxPropertyDescriptor(
				"Fill", "Fill", fillvalues);
		pfDiscriptor.setCategory("Polygon");
		TextPropertyDescriptor tsDiscriptor = new TextPropertyDescriptor(
				"Text", "String");
		tsDiscriptor.setCategory("Text");
		ColorPropertyDescriptor tcDiscriptor = new ColorPropertyDescriptor(
				"FontColor", "Color");
		tcDiscriptor.setCategory("Text");
		TextPropertyDescriptor tsiDiscriptor = new TextPropertyDescriptor(
				"FontSize", "Size");
		tsiDiscriptor.setCategory("Text");
		String[] fsvalues = { "Normal", "Bold", "Italic", "Bold Italic" };
		ComboBoxPropertyDescriptor fsDiscriptor = new ComboBoxPropertyDescriptor(
				"FontStyle", "Style", fsvalues);
		fsDiscriptor.setCategory("Text");

		return new IPropertyDescriptor[] { nameDiscriptor, posxDiscriptor,
				posyDiscriptor, lcDiscriptor, ltDiscriptor, lsDiscriptor,
				pcDiscriptor, pfDiscriptor, tsDiscriptor, tcDiscriptor,
				tsiDiscriptor, fsDiscriptor };
	}

	@Override
	public Object getPropertyValue(Object id) {
		if ("Text".equals(id))
			return text;
		else if ("FontColor".equals(id))
			return fontColor.getRGB();
		else if ("FontSize".equals(id))
			return Integer.toString(fontSize);
		else if ("FontStyle".equals(id)) {
			switch (fontStyle) {
			case SWT.NORMAL:
				return 0;
			case SWT.BOLD:
				return 1;
			case SWT.ITALIC:
				return 2;
			case SWT.ITALIC | SWT.BOLD:
				return 3;
			default:
				return 0;
			}
		} else
			return super.getPropertyValue(id);
	}

	public void setPropertyValue(Object id, Object value) {
		if ("Text".equals(id))
			text = (String) value;
		else if ("FontColor".equals(id)) {
			setFontColor((RGB) value);
		} else if ("FontSize".equals(id)) {
			fontSize = Integer.parseInt((String) value);
		} else if ("FontStyle".equals(id)) {
			switch ((int) value) {
			case 0:
				fontStyle = SWT.NORMAL;
				break;
			case 1:
				fontStyle = SWT.BOLD;
				break;
			case 2:
				fontStyle = SWT.ITALIC;
				break;
			case 3:
				fontStyle = SWT.ITALIC | SWT.BOLD;
				break;
			}
		} else
			super.setPropertyValue(id, value);
	}
}
