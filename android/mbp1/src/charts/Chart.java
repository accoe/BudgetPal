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

	public Chart(json.WebService ws) {
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
		String legend = "var canvas = document.getElementById('legend'); var context = canvas.getContext('2d');";
		int added = 0;
		int s = 20;
		int p = 10;
		int fs = 13;
		int row = 0;
		int col = 0;
		for (int i = 0; i < Pie.count; i++) {
			if (Pie.PieChart.get(i).suma > 0) {
				String color = colors[added % colors.length];
				data += "{value: " + Pie.PieChart.get(i).suma + ", color: \""
						+ color + "\"}" + (i == Pie.count - 1 ? "" : ",");
				legend += "context.fillStyle = \"" + color + "\";";

				int y = p + row * (s + p);
				int x = p;
				if (this.properties.horizontal) {

					if (y > this.properties.labelSizeY) {
						this.properties.labelSizeX *= 2;
						x += 140;
						row = 0;
						y = p + row * (s + p);
					}
				} else {
					if (row > 4) {
						col++;
						row = 0;
					}
					x = p + col * 150;
					y = p + row * (s + p);

					this.properties.labelSizeX = 150 * (col + 1);
					if (this.properties.sizeX < this.properties.labelSizeX)
						this.properties.sizeX = this.properties.labelSizeX;
					this.properties.labelSizeY = y + p + s;
				}
				
				legend += "context.fillRect(" + x + "," + y + "," + s + "," + s
						+ ");";
				legend += "context.fillStyle = \"black\";";
				legend += "context.font = \"" + fs + "px Arial\";";
				legend += "context.fillText(\"" + Pie.PieChart.get(i).kategoria
						+ "\", " + (1.5 * p + s + x) + ", " + (y + fs + 1)
						+ ");";
				row++;
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
		int s = 20;
		int p = 10;
		int fs = 13;
		int row = 0;
		int col = 0;

		List<String> categories = new ArrayList<String>();

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
		String legend = "var canvas = document.getElementById('legend'); var context = canvas.getContext('2d');";
		for (int i = 0; i < categories.size(); i++) {
			int y = p + row * (s + p);
			int x = p;
			if (this.properties.horizontal) {

				if (y > this.properties.labelSizeY) {
					this.properties.labelSizeX *= 2;
					x += 140;
					row = 0;
					y = p + row * (s + p);
				}
			} else {
				if (row > 4) {
					col++;
					row = 0;
				}
				x = p + col * 150;
				y = p + row * (s + p);
				
				this.properties.labelSizeX = 150 * (col + 1);
				if (this.properties.sizeX < this.properties.labelSizeX)
					this.properties.sizeX = this.properties.labelSizeX;
				this.properties.labelSizeY = y + p + s;
			}
			legend += "context.fillStyle = \"" + colors[i % colors.length]
					+ "\";";
			legend += "context.fillRect(" + x + "," + y + "," + s + "," + s
					+ ");";
			legend += "context.fillStyle = \"black\";";
			legend += "context.font = \"" + fs + "px Arial\";";
			legend += "context.fillText(\"" + categories.get(i) + "\", "
					+ (1.5 * p + s + x) + ", " + (y + fs + 1) + ");";
			row++;
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
				+ this.properties.Horizontal()
				+ "		<canvas id=\"canvas\" height=\""
				+ this.properties.sizeY
				+ "\" width=\""
				+ this.properties.sizeX
				+ "\"></canvas>"
				+ "	<canvas id=\"legend\" height=\""
				+ this.properties.labelSizeY
				+ "\" width=\""
				+ this.properties.labelSizeX
				+ "\"></canvas>"
				+ "	<script>"
				+ "		var Data = "
				+ this.data
				+ ";"
				+ "	var my = new Chart(document.getElementById(\"canvas\").getContext(\"2d\"))."
				+ properties.type
				+ "(Data);"
				+ this.legend
				+ "	</script>"
				+ "</div></body></html>";

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
