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
import android.widget.TextView;
import android.widget.Toast;

public class EdytujBudzet extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodaj_budzet);
		
		TextView title = (TextView)this.findViewById(R.id.titleAddNewBudget);
		title.setText("Edytuj bud¿et");
		
		final int BudzetID = getIntent().getExtras().getInt("budzetID");
		String BudzetNazwa = getIntent().getExtras().getString("budzetNazwa");
		String BudzetOpis = getIntent().getExtras().getString("budzetOpis");
		
		final EditText boxName = (EditText)this.findViewById(R.id.editAddNewBudgetName);
		boxName.setText(BudzetNazwa);
		final EditText boxDescription = (EditText)this.findViewById(R.id.editAddNewBudgetDescription);
		boxDescription.setText(BudzetOpis);
		
		
        Button btnAdd = (Button)this.findViewById(R.id.buttonAddNewBudgetAdd);
        btnAdd.setText("Zapisz zmiany");
        btnAdd.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		try {
        			String name = URLEncoder.encode(boxName.getText().toString(),"UTF-8");
        			String nameDec = boxName.getText().toString();
        			String description = URLEncoder.encode(boxDescription.getText().toString(),"UTF-8");        			
        			Singleton.getInstance().ws.UpdateBudget(BudzetID, name, description);
        				Toast.makeText(EdytujBudzet.this, "Zaktualizowano bud¿et: "+nameDec,Toast.LENGTH_LONG).show();
        				Intent myIntent = new Intent(EdytujBudzet.this, Budzety.class);
        				EdytujBudzet.this.startActivity(myIntent);
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
