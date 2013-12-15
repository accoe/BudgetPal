package com.mbp1;

import java.net.URLEncoder;

import json.Singleton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ZalozKonto extends SherlockActivity {

	EditText boxLogin;
	EditText boxPass1;
	EditText boxPass2;
	EditText boxMail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zaloz_konto);

		boxLogin = (EditText) findViewById(R.id.editRegisterLogin);
		boxPass1 = (EditText) findViewById(R.id.editRegisterPass1);
		boxPass2 = (EditText) findViewById(R.id.editRegisterPass2);
		boxMail = (EditText) findViewById(R.id.editRegisterMail);

		Button btnRegister = (Button) this.findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String login = URLEncoder.encode(boxLogin.getText()
							.toString(), "UTF-8");
					String pass1 = URLEncoder.encode(boxPass1.getText()
							.toString(), "UTF-8");
					String pass2 = URLEncoder.encode(boxPass2.getText()
							.toString(), "UTF-8");
					String mail = URLEncoder.encode(boxMail.getText()
							.toString(), "UTF-8");
					if (pass1.equals(pass2)) {
						if (Singleton.getInstance().ws.Register(login, pass1,
								mail)) {
							Toast.makeText(ZalozKonto.this,
									"Konto zosta³o utworzone",
									Toast.LENGTH_LONG).show();
							Bundle bundle = new Bundle();
							bundle.putString("login", login);
							bundle.putString("haslo", pass1);
							Intent seeLogin = new Intent(ZalozKonto.this,
									MainActivity.class);
							seeLogin.putExtras(bundle);
							startActivity(seeLogin);
						}
					} else {
						Toast.makeText(ZalozKonto.this, "Has³a s¹ ró¿ne",
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(ZalozKonto.this,
							"Konto nie zosta³o za³o¿one", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu sub = menu.addSubMenu("Menu");
		sub.add(1, 1, 1, "O aplikacji");
		sub.add(1, 2, 1, "FAQ");
		sub.getItem().setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 2) {
			Intent myIntent = new Intent(ZalozKonto.this, FAQ.class);
			ZalozKonto.this.startActivity(myIntent);
			return false;
		} else if (item.getItemId() == 1) {
			Intent myIntent = new Intent(ZalozKonto.this, About.class);
			ZalozKonto.this.startActivity(myIntent);
			return false;
		}
		return true;
	}
}
