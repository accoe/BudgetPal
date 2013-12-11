package com.mbp1;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Stats extends Activity {

	WebView przegladarka;
	int BudzetID;
	String BudzetNazwa;
	
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		BudzetID = getIntent().getExtras().getInt("budzetID"); 
		BudzetNazwa = getIntent().getExtras().getString("budzetNazwa");
		
		przegladarka = (WebView) findViewById(R.id.webView);
		przegladarka.getSettings().setJavaScriptEnabled(true);
				
			
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		b1.setText(Html.fromHtml("&raquo;"));
		b2.setText(Html.fromHtml("&laquo;"));
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		list.add("wydatki");
		list.add("przychody");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		
		// Wykresy
		final Chart chart = new Chart(Singleton.getInstance().ws);
		chart.properties.setSize(270,270);
		// Ko³owe
		final String chart1;
		if (String.valueOf(spinner.getSelectedItem()).equals("wydatki")) {
			chart1 = chart.ExpensesPieChart(BudzetID,2013,12);
			przegladarka.loadData(chart1, "text/html", "UTF-8");
		}
		else {
			chart1 = chart.IncomesPieChart(BudzetID,2013,12);
			przegladarka.loadData(chart1, "text/html", "UTF-8");
		}
		
		
		
		b1.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		String chart2 = chart.ExpensesPieChart(BudzetID,2013,12);
    			przegladarka.loadData(chart2, "text/html", "UTF-8");
        	}
        });
	
		b2.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		String chart2 = chart.ExpensesPieChart(BudzetID,2013,11);
    			przegladarka.loadData(chart2, "text/html", "UTF-8");
        	}
        });	
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

}
