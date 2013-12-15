package com.mbp1;

import java.net.URLEncoder;

import json.Singleton;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DodajPrzychod extends Activity {

	int BudzetID; 
	String BudzetNazwa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodaj_wydatek);
		
		BudzetID = getIntent().getExtras().getInt("budzetID");
		BudzetNazwa = getIntent().getExtras().getString("budzetNazwa");
		
		TextView title = (TextView)this.findViewById(R.id.titleDodajWydatek);
		title.setText("Dodaj przychód");
		
		
		final AutoCompleteTextView boxName = (AutoCompleteTextView)this.findViewById(R.id.autoCompleteDodajWydatekNazwa);
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
        			Singleton.getInstance().ws.AddIncome(BudzetID, name, highDec);
        				Toast.makeText(DodajPrzychod.this, "Dodano przychód: "+nameDec,Toast.LENGTH_LONG).show();
        				Bundle bundle = new Bundle();
                		bundle.putInt("budzetID",BudzetID);
                		bundle.putString("budzetNazwa",BudzetNazwa);
                		Intent seeStats = new Intent(DodajPrzychod.this, Budzet.class);
                		seeStats.putExtras(bundle);
                		startActivity(seeStats);
        		} catch (Exception e) {
        			e.printStackTrace();
        			} 
        		}
        	});	
        
        Button btnOCR = (Button)this.findViewById(R.id.buttonSkorzystajZOCR);
        btnOCR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
	            Intent intent = DodajPrzychod.this.getPackageManager().getLaunchIntentForPackage("edu.sfsu.cs.orange.ocr");
	            if (intent != null)
	            {
	                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                startActivity(intent);
	            }
	            else
	            {
	                intent = new Intent(Intent.ACTION_VIEW);
	                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                intent.setData(Uri.parse("market://details?id="+"edu.sfsu.cs.orange.ocr"));
	                startActivity(intent);
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
