package com.mbp1;

import json.Singleton;
import com.mbp1.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

		boxLogin = (EditText) this.findViewById(R.id.editTextLogin);
		boxPassword = (EditText) this.findViewById(R.id.editTextPassword);
		SharedPreferences settings = getSharedPreferences(DANE_LOGOWANIA, 0);
		String name = settings.getString(LOGIN, "");
		String password = settings.getString(PASSWORD, "");
		boxLogin.setText(name);
		boxPassword.setText(password);
		checkBox = (CheckBox) findViewById(R.id.checkBoxZapamietajHaslo);
		;

		Button btnLogin = (Button) this.findViewById(R.id.buttonLogIn);
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Logowanie
				final String login = boxLogin.getText().toString();
				final String haslo = boxPassword.getText().toString();
				Singleton.initInstance();
				try {
					if (Singleton.getInstance().ws.Login(login, haslo)) {
						Toast.makeText(MainActivity.this, "Zalogowano",
								Toast.LENGTH_LONG).show();
						Intent myIntent = new Intent(MainActivity.this,
								Budzety.class);
						MainActivity.this.startActivity(myIntent);
						MainActivity.this.finish();
					} else
						Toast.makeText(MainActivity.this,
								"B³êdny login lub has³o!", Toast.LENGTH_LONG)
								.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void onStop() {
		super.onStop();

		if (checkBox.isChecked()) {
			SharedPreferences settings = getSharedPreferences(DANE_LOGOWANIA, 0);
			settings.edit().putString(LOGIN, boxLogin.getText().toString())
					.putString(PASSWORD, boxPassword.getText().toString())
					.commit();
		}
		else {
			SharedPreferences settings = getSharedPreferences(DANE_LOGOWANIA, 0);
			settings.edit().putString(LOGIN, "")
					.putString(PASSWORD, "")
					.commit();
		}
	}
}