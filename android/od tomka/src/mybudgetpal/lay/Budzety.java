package mybudgetpal.lay;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;

import json.Budgets;
import json.Session;
import json.SessionManager;
import json.Utils;
import mybudgetpal.lay.R;
import json.WebService;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Budzety extends Activity {

	SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_budzety);
		
		final ListView listBudgets = (ListView) findViewById(R.id.listView1);
		final ArrayList<String> listaNazwBudzetow = new ArrayList<String>();
        final ArrayList<String> listaOpisowBudzetow = new ArrayList<String>();
		session = new SessionManager(getApplicationContext());   
		WebService ws = new WebService();
		try {

			Budgets budgets = ws.GetBudgets();
			if (budgets != null) {
				Toast.makeText(Budzety.this, "Twoje budzety",Toast.LENGTH_LONG).show();   
				for (int i=0;i<budgets.count; i++) {
					listaNazwBudzetow.add(budgets.budgets.get(i).nazwa);
					listaOpisowBudzetow.add(budgets.budgets.get(i).nazwa);
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                    android.R.layout.two_line_list_item, android.R.id.text1,listaNazwBudzetow);
				listBudgets.setAdapter(adapter);
			}
			else
				Toast.makeText(Budzety.this, "Nie masz zadnego budzetu",Toast.LENGTH_LONG).show();   
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.budzety, menu);
		return true;
	}

}
