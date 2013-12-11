package charts;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import json.BarCharts;

public class Chart {
	private json.WebService ws;
	private String html;
	public ChartsProperties properties;
	private String[] colors = { "#2C3E50", "#D35400", "#27AE60", "#bdc3c7",
			"#95A5A6", "#2980B9", "#E67E22", "#1ABC9C", "#7F8C8D", "#34495E",
			"#F1C40F", "#2ECC71", "#3498DB", "#E74C3C", "#F39C12", "#16A085",
			"#C0392B", "#ECF0F1", "#8E44AD", "#9B59B6", "#BDC3C7 " };
	private String data;
	private String legend;
	public boolean notEmpty;
		
	
	public Chart(json.WebService ws) {
		this.notEmpty = true;
		this.ws = ws;
		this.properties = new ChartsProperties();
		this.html = "";
		this.data = "";
		this.legend = "";
	}

	public String IncomesPieChart(int budgetId, int year, int month) {
		try {
			json.PieCharts pc = ws.GetIncomesPieChart(budgetId, year + "-"
					+ month + "-10");
			this.CreateData(pc);
			this.CreateChart();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.html;
	}

	public String ExpensesPieChart(int budgetId, int year, int month) {
		try {
			json.PieCharts pc = ws.GetExpensesPieChart(budgetId, year + "-"
					+ month + "-10");
			this.CreateData(pc);
			this.CreateChart();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.html;
	}

	public String IncomesCategoryChart(int budgetId, int months,
			String[] categoryNames) {
		try {
			List<BarCharts> listOfBarCharts = new ArrayList<BarCharts>();
			for (int i = 0; i < categoryNames.length; i++) {
				listOfBarCharts.add(ws.GetIncomesCategoryChart(budgetId,
						months, categoryNames[i]));
			}
			this.CreateData(listOfBarCharts);
			this.CreateChart();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.html;
	}

	public String ExpenseCategoryChart(int budgetId, int months,
			String[] categoryNames) {
		try {
			List<BarCharts> listOfBarCharts = new ArrayList<BarCharts>();
			for (int i = 0; i < categoryNames.length; i++) {
				listOfBarCharts.add(ws.GetExpenseCategoryChart(budgetId,
						months, categoryNames[i]));
			}
			this.CreateData(listOfBarCharts);
			this.CreateChart();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.html;
	}

	private void CreateData(json.PieCharts Pie) {
		String data = "";
		String legend = "";
		int added = 0;
		
		if (Pie == null)
			this.notEmpty = false;
		else
		for (int i = 0; i < Pie.count; i++) {
			if (Pie.PieChart.get(i).suma > 0) {
				String color = colors[added % colors.length];
				data += "{value: " + Pie.PieChart.get(i).suma + ", color: \""
						+ color + "\"}" + (i == Pie.count - 1 ? "" : ",");
				legend += "<div style=\"width:160px;display:inline-block;\">";
				legend += "<font color=\""+color+"\">&#9632; </font>"+Pie.PieChart.get(i).kategoria+" <font size=\"-1em\">("+Pie.PieChart.get(i).suma+" PLN)</font>";
				legend += "</div>";
				added++;
			}
		}
		this.legend = legend;
		this.data = "[" + data + "]";
	}

	private void CreateData(List<json.BarCharts> Bars) {
		if (this.properties.type != "Line")
			this.properties.type = "Bar";
		String data = "";
		String labels = "";
		List<String> categories = new ArrayList<String>();

		if (Bars == null)
			this.notEmpty = false;
		else
		for (int i = 0; i < Bars.size(); i++) {
			String data_row = "";
			for (int j = 0; j < Bars.get(i).count; j++) {
				String category = Bars.get(i).BarChart.get(j).kategoria;
				if (categories.indexOf(category) == -1)
					categories.add(category);
				double suma = Bars.get(i).BarChart.get(j).suma;
				if (i == 0) {
					String etykieta = Bars.get(i).BarChart.get(j).month + " "
							+ Bars.get(i).BarChart.get(j).year;
					labels += "\"" + etykieta + "\""
							+ (j == Bars.get(i).count - 1 ? "" : ",");
				}
				data_row += suma + (j == Bars.get(i).count - 1 ? "" : ",");
			}
			data_row = "data : [" + data_row + "]";
			data += "{fillColor : \"" + colors[i % colors.length]
					+ "\"," + data_row
					+ "}" + (i == Bars.size() - 1 ? "" : ",");
		}
		String legend = "";
		for (int i = 0; i < categories.size(); i++) {
			legend += "<div style=\"width:100px;display:inline-block;\">";
			legend += "<font color=\""+colors[i % colors.length]+"\">&#9632; </font>"+categories.get(i);
			legend += "</div>";
		}
		this.legend = legend;
		labels = "labels : [" + labels + "],";
		data = "datasets : [" + data + "]";
		this.data = "{" + labels + data + "}";
		
	}

	private void CreateChart() {
		this.html = "<!doctype html>"
				+ "<html>"
				+ "	<head>"
				+ "		<title>Radar Chart</title>"
				+ "		<script src=\"http://mybudgetpal.com/chart/Chart.min.js\"></script>"
				+ "		<meta name = \"viewport\" content = \"initial-scale = 1, user-scalable = "
				+ this.properties.getScalable()
				+ "\">"
				+ "		<style>"
				+ "			canvas{"
				+ "			}"
				+ "		</style>"
				+ "	</head>"
				+ "	<body>"
				+ "		<canvas id=\"canvas\" height=\""
				+ this.properties.sizeY
				+ "\" width=\""
				+ this.properties.sizeX
				+ "\"></canvas>"
				+ "	<script>"
				+ "		var Data = "
				+ this.data
				+ ";"
				+ "	var my = new Chart(document.getElementById(\"canvas\").getContext(\"2d\"))."
				+ properties.type
				+ "(Data);"
				+ "	</script>"
				+ "<div style=\"width:"+this.properties.sizeX+"px;\">" //usun jak chcesz miec legende w innym webview
				+ this.legend
				+ "</div>"
				+ "</body></html>";

	}

	public String getChart(){
		return this.html;
	}
	
	public String getLegend(){
		return this.legend;
	}
	
	
	public void SaveChartToFile(String filename) {
		try {
			PrintWriter out = new PrintWriter(filename + ".html");
			out.println(this.html);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
