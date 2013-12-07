package com.mbp1;

import json.Singleton;
import com.mbp1.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	protected MyApplication app ; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        app = (MyApplication)getApplication();
            
        final EditText boxLogin = (EditText)this.findViewById(R.id.editTextLogin);
        
        final EditText boxPassword = (EditText)this.findViewById(R.id.editTextPassword);
        
        Button btnLogin = (Button)this.findViewById(R.id.buttonLogIn);
        btnLogin.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// Logowanie
        		final String login = boxLogin.getText().toString();
        		final String haslo = boxPassword.getText().toString();
        		Singleton.initInstance();
        		try {
        			if(Singleton.getInstance().ws.Login(login, haslo)) {
        				Toast.makeText(MainActivity.this, "Zalogowano",Toast.LENGTH_LONG).show();
        				Intent myIntent = new Intent(MainActivity.this, Budzety.class);
        				MainActivity.this.startActivity(myIntent); 
        				MainActivity.this.finish();
        			}
        			else
        				Toast.makeText(MainActivity.this, "B³êdny login lub has³o!",Toast.LENGTH_LONG).show();        			
        		} catch (Exception e) {
        			e.printStackTrace();
        			} 
        		}
        	});
    }
}