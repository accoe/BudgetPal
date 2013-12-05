package charts;

public class ChartsProperties {

	public int sizeX;
	public int sizeY;
	public int labelSizeX;
	public int labelSizeY;
	public boolean scalable;
	public String type;
	public boolean horizontal;

	public ChartsProperties() {
		this.sizeX = 200;
		this.sizeY = 200;
		this.labelSizeX = 150;
		this.labelSizeY = this.sizeY;
		this.scalable = true;
		this.type = "Pie";
		this.horizontal = false;
	}

	public void setSize(int x, int y){
		this.sizeX = x;
		this.sizeY = y;
		this.labelSizeX = 150;
		this.labelSizeY = y;
	}
	
	public String getScalable() {
		return (this.scalable ? "yes" : "no");
	}

	public String Horizontal() {
		if (this.horizontal)
			return "<div>";
		else
			return "<div style=\"width:" + sizeX + "px;\">";

	}

}
