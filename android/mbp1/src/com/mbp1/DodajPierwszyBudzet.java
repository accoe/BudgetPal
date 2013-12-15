package com.mbp1;

import json.Singleton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DodajPierwszyBudzet extends SherlockActivity {

	EditText boxName;
	EditText boxDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodaj_pierwszy_budzet);
		TextView wstep = (TextView) findViewById(R.id.textDNBPierwszy);
		wstep.setText(Html
				.fromHtml("Wygl¹da na to, ¿e nie masz jeszcze utworzonego ¿adnego bud¿etu.<hr>Dodaj go teraz i zacznij planowaæ swoje wydatki!"));
		boxName = (EditText) findViewById(R.id.editNazwaBudzetu);
		boxDescription = (EditText) findViewById(R.id.editOpisBudzetu);
		Button btn = (Button) findViewById(R.id.buttonDNB);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Singleton.getInstance().ws.AddBudget(boxName.getText()
							.toString(), boxDescription.getText().toString());
					Intent myIntent = new Intent(DodajPierwszyBudzet.this,
							Portfel.class);
					DodajPierwszyBudzet.this.startActivity(myIntent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu sub = menu.addSubMenu("Menu");
		sub.add(1, 1, 1, "O aplikacji");
		sub.add(1, 2, 1, "Wyloguj");
		sub.getItem().setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 2) {
			try {
				if (Singleton.getInstance().ws.Logout() == true) {
					Toast.makeText(DodajPierwszyBudzet.this, "Wylogowano",
							Toast.LENGTH_SHORT).show();
					Intent myIntent = new Intent(DodajPierwszyBudzet.this,
							MainActivity.class);
					DodajPierwszyBudzet.this.startActivity(myIntent);
				} else
					Toast.makeText(DodajPierwszyBudzet.this,
							"Problem z wylogowaniem", Toast.LENGTH_SHORT)
							.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} else if (item.getItemId() == 1) {
			Intent myIntent = new Intent(DodajPierwszyBudzet.this, About.class);
			DodajPierwszyBudzet.this.startActivity(myIntent);
			return false;
		}
		return true;
	}

}
