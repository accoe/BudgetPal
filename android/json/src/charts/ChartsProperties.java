package charts;

public class ChartsProperties {
	
	public int sizeX;
	public int sizeY;
	public boolean scalable;
	public String type;
	
	public ChartsProperties() {
		this.sizeX = 200;
		this.sizeY = 200;
		this.scalable = true;
		this.type = "Pie";
	}
	
	
	public String getScalable(){
		return (this.scalable ? "yes" : "no");
	}

	
}
