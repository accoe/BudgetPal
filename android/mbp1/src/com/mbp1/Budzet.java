package com.mbp1;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import json.Activities;
import json.Singleton;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Budzet extends Activity {
	
	List<String> listaRodzajowAktywnosci;
	List<Integer> listaIDAktywnosci;
	List<String> listaNazwAktywnosci;
	List<Double> listaKwotAktywnosci;
	List<String> listaDatAktywnosci;
	int BudzetID;
	ListView listActiv;
	String BudzetNazwa;
	int skip = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_budzet);
		
		BudzetID = getIntent().getExtras().getInt("budzetID"); 
		BudzetNazwa = getIntent().getExtras().getString("budzetNazwa");
		
		// bilans
		double bilans = 0;
		NumberFormat formatter = new DecimalFormat("#0.00");
		try {
			bilans = Singleton.getInstance().ws.GetBudgetBilans(BudzetID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		TextView title = (TextView) findViewById(R.id.textAktywnosc);
		title.setText(Html.fromHtml("<b>Aktywnoœæ</b>"));
		
		TextView nazwaBudzetu = (TextView) findViewById(R.id.textBudzetNazwaBudzetu);
		nazwaBudzetu.setText(BudzetNazwa);
		
		TextView bilansBudzetu = (TextView) findViewById(R.id.textBudzetBilans);
		if (bilans > 0)
			bilansBudzetu.setText(Html.fromHtml("Saldo: <font color='#55AB3D'><b>"+formatter.format(bilans)+" z³</b></font>"));
		else 
			bilansBudzetu.setText(Html.fromHtml("Saldo: <font color='#D40000'><b>"+formatter.format(bilans)+" z³</b></font>"));
		
		// aktywnosc
		listActiv = (ListView) findViewById(R.id.listAktywnosci);
		listaRodzajowAktywnosci = new ArrayList<String>();
		listaIDAktywnosci = new ArrayList<Integer>();
		listaNazwAktywnosci = new ArrayList<String>();
		listaKwotAktywnosci = new ArrayList<Double>();
		listaDatAktywnosci = new ArrayList<String>();
		
		try {
			Activities activities = Singleton.getInstance().ws.GetRecentActivities(BudzetID,3);
			if (activities != null) {
				for (int i=0;i<activities.count; i++) {
					listaRodzajowAktywnosci.add(activities.activities.get(i).rodzaj);
					listaIDAktywnosci.add(activities.activities.get(i).ID_Zdarzenia);
					listaNazwAktywnosci.add(activities.activities.get(i).nazwa);
					listaKwotAktywnosci.add(activities.activities.get(i).kwota);
					listaDatAktywnosci.add(activities.activities.get(i).data);
				}
				ListActivity adapter = new ListActivity (Budzet.this, listaNazwAktywnosci, listaKwotAktywnosci, listaDatAktywnosci, listaRodzajowAktywnosci);
				listActiv.setAdapter(adapter);
			} else Toast.makeText(Budzet.this, "Brak aktywnoœci dla tego bud¿etu",Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ImageButton przychod = (ImageButton) findViewById(R.id.imageAdd);
		przychod.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Bundle bundle = new Bundle();
        		bundle.putInt("budzetID",BudzetID);
        		bundle.putString("budzetNazwa",BudzetNazwa);
        		Intent seeStats = new Intent(Budzet.this, DodajPrzychod.class);
        		seeStats.putExtras(bundle);
        		startActivity(seeStats);
        	}
        });
		
		ImageButton wydatek = (ImageButton) findViewById(R.id.imageRemove);
		wydatek.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Bundle bundle = new Bundle();
        		bundle.putInt("budzetID",BudzetID);
        		bundle.putString("budzetNazwa",BudzetNazwa);
        		Intent seeStats = new Intent(Budzet.this, DodajWydatek.class);
        		seeStats.putExtras(bundle);
        		startActivity(seeStats);
        	}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu_budzet, menu);
        return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
        case R.id.menu_logout: {
        	try {
				if (Singleton.getInstance().ws.Logout() == true) {
					Toast.makeText(Budzet.this, "Wylogowano", Toast.LENGTH_SHORT).show();
					Intent myIntent = new Intent(Budzet.this, MainActivity.class);
					Budzet.this.startActivity(myIntent);  
				}
				else Toast.makeText(Budzet.this, "Problem z wylogowaniem", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
            return true; 
        }
        case R.id.menu_preferences:
            Toast.makeText(Budzet.this, "Ustawienia", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_stats: {
        	Bundle bundle = new Bundle();
    		bundle.putInt("budzetID",BudzetID);
    		Intent seeStats = new Intent(Budzet.this, Stats.class);
    		seeStats.putExtras(bundle);
    		startActivity(seeStats);
            return true;
        }
        case R.id.menu_refresh: {
        	try {
        		listaRodzajowAktywnosci.clear();
        		listaIDAktywnosci.clear();
        		listaNazwAktywnosci.clear();
        		listaKwotAktywnosci.clear();
        		listaDatAktywnosci.clear();
    			Activities activities = Singleton.getInstance().ws.GetRecentActivities(BudzetID,3,0);
    			if (activities != null) {
    				for (int i=0;i<activities.count; i++) {
    					listaRodzajowAktywnosci.add(activities.activities.get(i).rodzaj);
    					listaIDAktywnosci.add(activities.activities.get(i).ID_Zdarzenia);
    					listaNazwAktywnosci.add(activities.activities.get(i).nazwa);
    					listaKwotAktywnosci.add(activities.activities.get(i).kwota);
    					listaDatAktywnosci.add(activities.activities.get(i).data);
    				}
    				ListActivity adapter = new ListActivity (Budzet.this, listaNazwAktywnosci, listaKwotAktywnosci, listaDatAktywnosci, listaRodzajowAktywnosci);
    				listActiv.setAdapter(adapter);
    			} else Toast.makeText(Budzet.this, "Brak aktywnoœci dla tego bud¿etu",Toast.LENGTH_LONG).show();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		double bilans = 0;
    		NumberFormat formatter = new DecimalFormat("#0.00");
    		try {
    			bilans = Singleton.getInstance().ws.GetBudgetBilans(BudzetID);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		Log.e("ID",""+bilans);
    		TextView bilansBudzetu = (TextView) findViewById(R.id.textBudzetBilans);
    		if (bilans > 0)
    			bilansBudzetu.setText(Html.fromHtml("Saldo: <font color='#55AB3D'><b>"+formatter.format(bilans)+" z³</b></font>"));
    		else 
    			bilansBudzetu.setText(Html.fromHtml("Saldo: <font color='#D40000'><b>"+formatter.format(bilans)+" z³</b></font>"));
            return true;
        }
        case R.id.menu_loadmore: {
        	try {
    			Activities activities = Singleton.getInstance().ws.GetRecentActivities(BudzetID,3,skip);
    			if (activities != null) {
    				for (int i=0;i<activities.count; i++) {
    					listaRodzajowAktywnosci.add(activities.activities.get(i).rodzaj);
    					listaIDAktywnosci.add(activities.activities.get(i).ID_Zdarzenia);
    					listaNazwAktywnosci.add(activities.activities.get(i).nazwa);
    					listaKwotAktywnosci.add(activities.activities.get(i).kwota);
    					listaDatAktywnosci.add(activities.activities.get(i).data);
    					skip += 3;
    				}
    				ListActivity adapter = new ListActivity (Budzet.this, listaNazwAktywnosci, listaKwotAktywnosci, listaDatAktywnosci, listaRodzajowAktywnosci);
    				listActiv.setAdapter(adapter);
    			} else Toast.makeText(Budzet.this, "Nie mo¿na by³o pobraæ aktywnoœci",Toast.LENGTH_LONG).show();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
            return true;
        } 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
}
