package com.mbp1;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import json.Budgets;
import json.Singleton;
import android.os.Bundle;
//import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
import android.view.View;
//import android.view.View.OnClickListener;
import android.widget.AdapterView;
//import android.widget.Button;
import android.widget.ListView;
//import android.widget.TextView;
import android.widget.Toast;

public class Budzety extends SherlockActivity implements ActionBar.TabListener {

	Menu item;
	List<String> listaNazwBudzetow;
	List<String> listaOpisowBudzetow;
	List<Integer> listaIDBudzetow;
	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_budzety);

		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab tab1 = getSupportActionBar().newTab();
		tab1.setIcon(R.drawable.ic_aktywnosc);
		tab1.setTag("aktywnosc");
		tab1.setTabListener(this);
		ActionBar.Tab tab2 = getSupportActionBar().newTab();
		getSupportActionBar().addTab(tab1);
		tab2.setIcon(R.drawable.ic_wydatki);
		tab2.setTabListener(this);
		tab2.setTag("wydatki");
		ActionBar.Tab tab3 = getSupportActionBar().newTab();
		getSupportActionBar().addTab(tab2);
		tab3.setIcon(R.drawable.ic_przychody);
		tab3.setTabListener(this);
		tab3.setTag("przychody");
		getSupportActionBar().addTab(tab3);
		ActionBar.Tab tab4 = getSupportActionBar().newTab();
		tab4.setIcon(R.drawable.ic_stats);
		tab4.setTabListener(this);
		tab4.setTag("stats");
		getSupportActionBar().addTab(tab4);
	}
	
	public void wczytajBudzety() {
		final ListView listBudgets = (ListView) findViewById(R.id.listBudgets);
		listaNazwBudzetow = new ArrayList<String>();
		listaOpisowBudzetow = new ArrayList<String>();
		listaIDBudzetow = new ArrayList<Integer>();
		try {
			Budgets budgets = Singleton.getInstance().ws.GetBudgets();
			if (budgets != null) {
				for (int i = 0; i < budgets.count; i++) {
					listaNazwBudzetow.add(budgets.budgets.get(i).nazwa);
					listaOpisowBudzetow.add(budgets.budgets.get(i).opis);
					listaIDBudzetow.add(budgets.budgets.get(i).ID_Budzetu);
				}

				ListAdapter adapter = new ListAdapter(Budzety.this,
						listaNazwBudzetow, listaOpisowBudzetow);

				listBudgets.setAdapter(adapter);
				registerForContextMenu(listBudgets);

				listBudgets
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									final View view, int position, long id) {
								/*bundle = new Bundle();
								bundle.putInt("budzetID",
										listaIDBudzetow.get(position));
								bundle.putString("budzetNazwa",
										listaNazwBudzetow.get(position));*/
								Intent seeBudget = new Intent(Budzety.this,
										Budzet.class);
								seeBudget.putExtras(bundle);
								startActivity(seeBudget);
							}
						});
			} else {
				Toast.makeText(Budzety.this, "Nie masz zadnego budzetu",
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int intIndexSelected = info.position;
		if (item.getTitle() == "edytuj") {
			bundle = new Bundle();
			bundle.putInt("budzetID", listaIDBudzetow.get(intIndexSelected));
			bundle.putString("budzetNazwa",
					listaNazwBudzetow.get(intIndexSelected));
			bundle.putString("budzetOpis",
					listaOpisowBudzetow.get(intIndexSelected));
			Intent seeBudget = new Intent(Budzety.this, EdytujBudzet.class);
			seeBudget.putExtras(bundle);
			startActivity(seeBudget);
		} else if (item.getTitle() == "usuñ") {
			try {
				Singleton.getInstance().ws.DeleteBudget(listaIDBudzetow
						.get(intIndexSelected));
				Toast.makeText(
						Budzety.this,
						"Usuniêto bud¿et: "
								+ listaNazwBudzetow.get(intIndexSelected),
						Toast.LENGTH_LONG).show();
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
		SubMenu sub = menu.addSubMenu("Menu");
		sub.add(0, 1, 0, "Odœwie¿");
		sub.add(0, 4, 0, "Statystyki");
		sub.add(0, 5, 0, "Zmieñ bud¿et");
		sub.add(0, 2, 0, "Ustawienia");
		sub.add(0, 3, 0, "Wyloguj");
		sub.getItem().setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		menu.add(1, 6, 1, "Dodaj").setIcon(R.drawable.ic_dodaj)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 5) {
			bundle = new Bundle();
			bundle.putInt("budzetID", 1);
			bundle.putString("budzetNazwa", "Nazwa");
			Intent seeBudget = new Intent(Budzety.this, Budzety.class);
			seeBudget.putExtras(bundle);
			startActivity(seeBudget);
			return false;
		}
		if (item.getItemId() == 4) {
			bundle = new Bundle();
			bundle.putInt("budzetID", 1);
			bundle.putString("budzetNazwa", "Nazwa");
			Intent seeBudget = new Intent(Budzety.this, Stats.class);
			seeBudget.putExtras(bundle);
			startActivity(seeBudget);
			return false;
		}
		if (item.getItemId() == 1) {
			Intent myIntent = new Intent(Budzety.this, Budzety.class);
			Budzety.this.startActivity(myIntent);
			Toast.makeText(this, "Odœwie¿y³em", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (item.getItemId() == 3) {
			try {
				if (Singleton.getInstance().ws.Logout() == true) {
					Toast.makeText(Budzety.this, "Wylogowano",
							Toast.LENGTH_SHORT).show();
					Intent myIntent = new Intent(Budzety.this,
							MainActivity.class);
					Budzety.this.startActivity(myIntent);
				} else
					Toast.makeText(Budzety.this, "Problem z wylogowaniem",
							Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (item.getItemId() == 6) {
			Intent myIntent = new Intent(Budzety.this, DodajBudzet.class);
			Budzety.this.startActivity(myIntent);
			return false;
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Opcje bud¿etu");
		menu.add(0, v.getId(), 0, "edytuj");
		menu.add(0, v.getId(), 0, "usuñ");
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		// Toast.makeText(Budzety.this, "cos ", Toast.LENGTH_LONG).show();
		if (tab.getTag() == "przychody") {
			wczytajBudzety();
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
	}

}
