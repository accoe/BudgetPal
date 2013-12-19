package com.mbp1;

import json.Budgets;
import json.Singleton;
import com.mbp1.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected MyApplication app;
	public static final String DANE_LOGOWANIA = "DANE_LOGOWANIA";
	public static final String LOGIN = "LOGIN";
	public static final String PASSWORD = "PASSWORD";
	private EditText boxLogin;
	private EditText boxPassword;
	CheckBox checkBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		app = (MyApplication) getApplication();

		TextView title = (TextView) findViewById(R.id.textLogowanie);
		title.setText(Html.fromHtml("<b>Logowanie</b>"));

		boxLogin = (EditText) this.findViewById(R.id.editTextLogin);
		boxPassword = (EditText) this.findViewById(R.id.editTextPassword);
		SharedPreferences settings = getSharedPreferences(DANE_LOGOWANIA, 0);
		String name = settings.getString(LOGIN, "");
		String password = settings.getString(PASSWORD, "");
		boxLogin.setText(name);
		boxPassword.setText(password);
		checkBox = (CheckBox) findViewById(R.id.checkBoxZapamietajHaslo);
		checkBox.setChecked(true);

		Button btnLogin = (Button) this.findViewById(R.id.buttonLogIn);
		Button btnRegister = (Button) this.findViewById(R.id.btnZalozKonto);
		try {
			btnLogin.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isOnline()) {
						// Logowanie
						final String login = boxLogin.getText().toString();
						final String haslo = boxPassword.getText().toString();
						Singleton.initInstance();
						try {
							if (Singleton.getInstance().ws.Login(login, haslo)) {
								Toast.makeText(MainActivity.this, "Zalogowano",
										Toast.LENGTH_LONG).show();
								Budgets budgets = Singleton.getInstance().ws
										.GetBudgets();
								if (budgets != null) {
									Intent myIntent = new Intent(
											MainActivity.this, Portfel.class);
									MainActivity.this.startActivity(myIntent);
								} else {
									Intent myIntent = new Intent(
											MainActivity.this,
											DodajPierwszyBudzet.class);
									MainActivity.this.startActivity(myIntent);
								}
								MainActivity.this.finish();
							} else
								Toast.makeText(MainActivity.this,
										"B��dny login lub has�o!",
										Toast.LENGTH_LONG).show();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else
						Toast.makeText(MainActivity.this,
								"Brak po��czenia z internetem.",
								Toast.LENGTH_LONG).show();
				}
			});

			btnRegister.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isOnline()) {
						// Rejestracja
						Intent myIntent = new Intent(MainActivity.this,
								ZalozKonto.class);
						MainActivity.this.startActivity(myIntent);
					} else
						Toast.makeText(MainActivity.this,
								"Brak po��czenia z internetem.",
								Toast.LENGTH_LONG).show();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	protected void onStop() {
		super.onStop();

		if (checkBox.isChecked()) {
			SharedPreferences settings = getSharedPreferences(DANE_LOGOWANIA, 0);
			settings.edit().putString(LOGIN, boxLogin.getText().toString())
					.putString(PASSWORD, boxPassword.getText().toString())
					.commit();
		} else {
			SharedPreferences settings = getSharedPreferences(DANE_LOGOWANIA, 0);
			settings.edit().putString(LOGIN, "").putString(PASSWORD, "")
					.commit();
		}
	}
}