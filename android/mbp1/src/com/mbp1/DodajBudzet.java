package com.mbp1;

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

public class DodajBudzet extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodaj_budzet);
		
		final EditText boxName = (EditText)this.findViewById(R.id.editAddNewBudgetName);
		final EditText boxDescription = (EditText)this.findViewById(R.id.editAddNewBudgetDescription);
		
		
        Button btnAdd = (Button)this.findViewById(R.id.buttonAddNewBudgetAdd);
        btnAdd.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		String name = boxName.getText().toString();
        		String description = boxDescription.getText().toString();
        		try {
        			Singleton.getInstance().ws.AddBudget(name, description);
        				Toast.makeText(DodajBudzet.this, "Dodano bud¿et: "+name,Toast.LENGTH_LONG).show();
        				Intent myIntent = new Intent(DodajBudzet.this, Budzety.class);
        				DodajBudzet.this.startActivity(myIntent);     			
        		} catch (Exception e) {
        			e.printStackTrace();
        			} 
        		}
        	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dodaj_budzet, menu);
		return true;
	}

}
