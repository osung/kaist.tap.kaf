package kaist.tap.kaf.component;

public class Color {
	public int r;
	public int g;
	public int b;
	
	public Color() {
		r = g = b = 0;
	}
	
	public Color(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Color(String color) {
		switch (color) {
		case "red" :
			this.r = 255;
			this.g = this.b = 0;
			break;
		case "green" :
			this.r = this.b = 0;
			this.g = 255;
			break;
		case "blue" :
			this.r = this.g = 0;
			this.b = 255;
			break;
		case "white" :
			this.r = this.g = this.b = 255;
			break;
		case "black" :
			this.r = this.g = this.b = 0;
			break;
		case "yellow" :
			this.r = this.g = 255;
			this.b = 0;
		}
	}
}