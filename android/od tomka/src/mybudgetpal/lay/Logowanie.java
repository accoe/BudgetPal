package mybudgetpal.lay;

import json.SessionManager;

import org.apache.http.impl.client.DefaultHttpClient;
import json.WebService;
import mybudgetpal.lay.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class Logowanie extends Activity {
	public static DefaultHttpClient httpClient = new DefaultHttpClient();
	SessionManager session;
	
	DefaultHttpClient httpclient;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        final EditText boxLogin = (EditText)this.findViewById(R.id.textBoxLogin);
        
        final EditText boxPassword = (EditText)this.findViewById(R.id.textBoxPassword);
        
        Button btnLogin = (Button)this.findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// Logowanie
        		final String login = boxLogin.getText().toString();
        		final String haslo = boxPassword.getText().toString();
                final WebService ws = new WebService();
                session = new SessionManager(getApplicationContext());      
        		try {
        			if(ws.Login(login, haslo)) {
        				Toast.makeText(Logowanie.this, "Zalogowano",Toast.LENGTH_LONG).show();
        				Intent myIntent = new Intent(Logowanie.this, Budzety.class);
        				Logowanie.this.startActivity(myIntent); 
        				MySingleton.initInstance();
        			}
        			else
        				Toast.makeText(Logowanie.this, "B³êdny login lub has³o!",Toast.LENGTH_LONG).show();        			
        		} catch (Exception e) {
        			e.printStackTrace();
        			} 
        		}
        	}); 
        }
    }