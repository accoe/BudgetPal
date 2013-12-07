package com.mbp1;

import java.net.URLEncoder;

import json.Singleton;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DodajWydatek extends Activity {

	int BudzetID; 
	String BudzetNazwa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodaj_wydatek);
		
		BudzetID = getIntent().getExtras().getInt("budzetID");
		BudzetNazwa = getIntent().getExtras().getString("budzetNazwa");
		
		final EditText boxName = (EditText)this.findViewById(R.id.editDodajWydatekNazwa);
		final EditText boxHigh = (EditText)this.findViewById(R.id.editDodajWydatekKwota);
		
        Button btnAdd = (Button)this.findViewById(R.id.buttonDodajWydatek);
        btnAdd.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		try {
        			String name = URLEncoder.encode(boxName.getText().toString(),"UTF-8");
        			String nameDec = boxName.getText().toString();
        			String high = boxHigh.getText().toString();   
        			double highDec = Double.parseDouble(high);
        			Singleton.getInstance().ws.AddExpense(BudzetID, name, highDec);
        				Toast.makeText(DodajWydatek.this, "Dodano wydatek: "+nameDec,Toast.LENGTH_LONG).show();
        				Bundle bundle = new Bundle();
                		bundle.putInt("budzetID",BudzetID);
                		bundle.putString("budzetNazwa",BudzetNazwa);
                		Intent seeStats = new Intent(DodajWydatek.this, Budzet.class);
                		seeStats.putExtras(bundle);
                		startActivity(seeStats);
        		} catch (Exception e) {
        			e.printStackTrace();
        			} 
        		}
        	});		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dodaj_wydatek, menu);
		return true;
	}

}
