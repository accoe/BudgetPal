package com.mbp1;

import json.Singleton;
import charts.Chart;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class BarChart extends Activity {

	WebView przegladarka;
	int BudzetID;
	String BudzetNazwa;
	
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_chart);
		
		BudzetID = getIntent().getExtras().getInt("budzetID"); 
		BudzetNazwa = getIntent().getExtras().getString("budzetNazwa");
		
		przegladarka = (WebView) findViewById(R.id.webView);
		przegladarka.getSettings().setJavaScriptEnabled(true);
				
		// Wykresy
		Chart chart = new Chart(Singleton.getInstance().ws);
		chart.properties.setSize(270,270);
		// Ko³owe
		final String chart1 = chart.ExpensesPieChart(BudzetID,2013,12);
		final String chart2 = chart.IncomesPieChart(BudzetID,2013,11);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		b1.setText("wydatki");
		b2.setText("przychody");
		final TextView legenda = (TextView) findViewById(R.id.textLegendaWykres);
		
		b1.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        			przegladarka.loadData(chart1, "text/html", "UTF-8");
        			legenda.setText(Html.fromHtml("<font color='#2C3E50'>&#9632; </font>jedzenie<br /><font color='#D35400'>&#9632; </font>dom<br />"));
        	}
        });
	
		b2.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        			przegladarka.loadData(chart2, "text/html", "UTF-8");
        	}
        });	
				 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bar_chart, menu);
		return true;
	}

}
