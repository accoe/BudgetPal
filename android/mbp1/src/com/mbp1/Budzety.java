package com.mbp1;

import java.util.ArrayList;
import java.util.List;

import json.Budgets;
import json.Singleton;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Budzety extends Activity {

	Menu item;
	List<String> listaNazwBudzetow;
	List<String> listaOpisowBudzetow;
	List<Integer> listaIDBudzetow;	
	Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_budzety);

		final ListView listBudgets = (ListView) findViewById(R.id.listBudgets);
		listaNazwBudzetow = new ArrayList<String>();
        listaOpisowBudzetow = new ArrayList<String>();
        listaIDBudzetow = new ArrayList<Integer>();
		try {
			Budgets budgets = Singleton.getInstance().ws.GetBudgets();
			if (budgets != null) {
				for (int i=0;i<budgets.count; i++) {
					listaNazwBudzetow.add(budgets.budgets.get(i).nazwa);
					listaOpisowBudzetow.add(budgets.budgets.get(i).opis);
					listaIDBudzetow.add(budgets.budgets.get(i).ID_Budzetu);
				}
				
				ListAdapter adapter = new ListAdapter (Budzety.this, listaNazwBudzetow, listaOpisowBudzetow);
				
				/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                    android.R.layout.simple_list_item_1, android.R.id.text1, listaNazwBudzetow);*/
				listBudgets.setAdapter(adapter);
				registerForContextMenu(listBudgets);
				
				listBudgets.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			        @Override
			        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
		        		bundle = new Bundle();
		        		bundle.putInt("budzetID",listaIDBudzetow.get(position));
		        		bundle.putString("budzetNazwa",listaNazwBudzetow.get(position));
		        		Intent seeBudget = new Intent(Budzety.this, Budzet.class);
		        		seeBudget.putExtras(bundle);
		        		startActivity(seeBudget);
			        }
			      });
			}
			else {
				Toast.makeText(Budzety.this, "Nie masz zadnego budzetu",Toast.LENGTH_LONG).show();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		 Button btnAddBudget = (Button)this.findViewById(R.id.buttonAddBudget);
		 btnAddBudget.setOnClickListener(new OnClickListener() {
	        	@Override
	        	public void onClick(View v) {
	        		Intent myIntent = new Intent(Budzety.this, DodajBudzet.class);
	        		Budzety.this.startActivity(myIntent); 
	        		}
	        	});
	}

	@Override 
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.setHeaderTitle("Opcje bud¿etu");  
            menu.add(0, v.getId(), 0, "edytuj");  
            menu.add(0, v.getId(), 0, "usuñ");
    } 
	
	@Override  
    public boolean onContextItemSelected(MenuItem item) {  
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int intIndexSelected = info.position;
		if(item.getTitle()=="edytuj") {
    		bundle = new Bundle();
    		bundle.putInt("budzetID",listaIDBudzetow.get(intIndexSelected));
    		bundle.putString("budzetNazwa",listaNazwBudzetow.get(intIndexSelected));
    		bundle.putString("budzetOpis",listaOpisowBudzetow.get(intIndexSelected));
    		Intent seeBudget = new Intent(Budzety.this, EdytujBudzet.class);
    		seeBudget.putExtras(bundle);
    		startActivity(seeBudget);
		} else if(item.getTitle()=="usuñ") {
			try {
				Singleton.getInstance().ws.DeleteBudget(listaIDBudzetow.get(intIndexSelected));
				Toast.makeText(Budzety.this, "Usuniêto bud¿et: "+ listaNazwBudzetow.get(intIndexSelected),Toast.LENGTH_LONG).show();
				Intent myIntent = new Intent(Budzety.this, Budzety.class);
				Budzety.this.startActivity(myIntent);     			
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return false;
			}
		return true;                     
      }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
        case R.id.menu_logout: {
        	try {
				if (Singleton.getInstance().ws.Logout() == true) {
					Toast.makeText(Budzety.this, "Wylogowano", Toast.LENGTH_SHORT).show();
					Intent myIntent = new Intent(Budzety.this, MainActivity.class);
					Budzety.this.startActivity(myIntent);  
				}
				else Toast.makeText(Budzety.this, "Problem z wylogowaniem", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
            return true; 
            }
 
        case R.id.menu_preferences:
            Toast.makeText(Budzety.this, "Ustawienia", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_refresh: {
        	Intent myIntent = new Intent(Budzety.this, Budzety.class);
			Budzety.this.startActivity(myIntent); 
            return true;
        } 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    

}
