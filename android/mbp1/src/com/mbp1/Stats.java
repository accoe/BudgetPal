package com.mbp1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import json.Singleton;
import charts.Chart;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Stats extends Activity {

	WebView przegladarka;
	int BudzetID;
	String BudzetNazwa;
	String chart1;
	String chart2;
	int rok = Calendar.getInstance().get(Calendar.YEAR);
	int miesiac = Calendar.getInstance().get(Calendar.MONTH)+1;
	int wybranyMiesiac = miesiac;
	int wybranyRok = rok;
	
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		BudzetID = getIntent().getExtras().getInt("budzetID"); 
		BudzetNazwa = getIntent().getExtras().getString("budzetNazwa");
		final String[] nazwyMiesiecy = {"styczeñ","luty","marzec","kwiecieñ","maj","czerwiec",
				"lipiec","sierpieñ","wrzesieñ","paŸdziernik","listopad","grudzieñ"};
		
		przegladarka = (WebView) findViewById(R.id.webView);
		przegladarka.getSettings().setJavaScriptEnabled(true);
		
		final TextView nazwaMiesiaca = (TextView) findViewById(R.id.textStatsMiesiac);
		final Button b1 = (Button) findViewById(R.id.button1);
		final Button b2 = (Button) findViewById(R.id.button2);
		b1.setText(Html.fromHtml("&raquo;"));
		b2.setText(Html.fromHtml("&laquo;"));
		
		final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
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
		
		nazwaMiesiaca.setText(nazwyMiesiecy[wybranyMiesiac-1]+" "+wybranyRok);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                if (String.valueOf(spinner.getSelectedItem()).equals("wydatki")) {
                	nazwaMiesiaca.setText(nazwyMiesiecy[wybranyMiesiac-1]+" "+wybranyRok);
        			chart1 = chart.ExpensesPieChart(BudzetID,rok,miesiac);
        			przegladarka.loadData(chart1, "text/html", "UTF-8");

        			b2.setOnClickListener(new OnClickListener() {
        	        	@Override
        	        	public void onClick(View v) {
        	        		wybranyMiesiac--;
        	        		if (wybranyMiesiac < 1) {
        	        			wybranyRok--; wybranyMiesiac = 12;}
        	        		Log.e("b1",wybranyMiesiac+"");
        	        		nazwaMiesiaca.setText(nazwyMiesiecy[wybranyMiesiac-1]+" "+wybranyRok);
        	        		if (wybranyMiesiac == miesiac+1 && wybranyRok == rok) {
        	        			Toast.makeText(Stats.this, "B³êdne polecenie", Toast.LENGTH_SHORT).show();
        	        			b2.setEnabled(false);
        	        		}
        	        		chart1 = chart.ExpensesPieChart(BudzetID,wybranyRok,wybranyMiesiac);
        	    			przegladarka.loadData(chart1, "text/html", "UTF-8");
        	        	}
        	        });
        		
        			b1.setOnClickListener(new OnClickListener() {
        	        	@Override
        	        	public void onClick(View v) {
        	        		wybranyMiesiac++;
        	        		if (wybranyMiesiac > 12) {
        	        			wybranyRok++; wybranyMiesiac = 1;} 
        	        		nazwaMiesiaca.setText(nazwyMiesiecy[wybranyMiesiac-1]+" "+wybranyRok);
        	        		if (wybranyMiesiac == miesiac+1 && wybranyRok == rok) {
        	        			Toast.makeText(Stats.this, "B³êdne polecenie", Toast.LENGTH_SHORT).show();
        	        			b1.setEnabled(false);
        	        		}
        	        		else {
        		        		String chart2 = chart.ExpensesPieChart(BudzetID,wybranyRok,wybranyMiesiac);
        		    			przegladarka.loadData(chart2, "text/html", "UTF-8");
        	    			}
        	        	}
        	        });	
        			
        		}
        		else {
        			nazwaMiesiaca.setText(nazwyMiesiecy[wybranyMiesiac-1]+" "+wybranyRok);
        			chart1 = chart.IncomesPieChart(BudzetID,rok,miesiac);
        			przegladarka.loadData(chart1, "text/html", "UTF-8");

        			b2.setOnClickListener(new OnClickListener() {
        	        	@Override
        	        	public void onClick(View v) {
        	        		wybranyMiesiac--;
        	        		if (wybranyMiesiac < 1) {
        	        			wybranyRok--; wybranyMiesiac = 12;}
        	        		nazwaMiesiaca.setText(nazwyMiesiecy[wybranyMiesiac-1]+" "+wybranyRok);
        	        		if (wybranyMiesiac == miesiac+1 && wybranyRok == rok) {
        	        			Toast.makeText(Stats.this, "B³êdne polecenie", Toast.LENGTH_SHORT).show();
        	        			b2.setEnabled(false);
        	        		}
        	        		chart1 = chart.IncomesPieChart(BudzetID,wybranyRok,wybranyMiesiac);
        	    			przegladarka.loadData(chart1, "text/html", "UTF-8");
        	        	}
        	        });
        		
        			b1.setOnClickListener(new OnClickListener() {
        	        	@Override
        	        	public void onClick(View v) {
        	        		wybranyMiesiac++;
        	        		if (wybranyMiesiac > 12) {
        	        			wybranyRok++; wybranyMiesiac = 1;} 
        	        		nazwaMiesiaca.setText(nazwyMiesiecy[wybranyMiesiac-1]+" "+wybranyRok);
        	        		if (wybranyMiesiac == miesiac+1 && wybranyRok == rok) {
        	        			Toast.makeText(Stats.this, "B³êdne polecenie", Toast.LENGTH_SHORT).show();
        	        			b1.setEnabled(false);
        	        		}
        	        		else {
        		        		String chart2 = chart.IncomesPieChart(BudzetID,wybranyRok,wybranyMiesiac);
        		    			przegladarka.loadData(chart2, "text/html", "UTF-8");
        	    			}
        	        	}
        			});
        		}
            }
 
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
		
	}
	
	public int przesun(int co, int oIle) {
		co += oIle;
		return co;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

}
