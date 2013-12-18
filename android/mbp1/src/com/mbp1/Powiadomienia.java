package com.mbp1;

import java.util.ArrayList;
import java.util.List;

import json.Notifications;
import json.Singleton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Powiadomienia extends SherlockActivity {

	Menu appMenu;
	List<String> listaNazw;
	List<Integer> listaID;
	List<String> listaDat;
	List<Integer> listaMark;
	List<String> listaTypow;
	int budzetID = 1;
	String budzetNazwa = "Mój bud¿et";
	ListView listNotifications;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_powiadomienia);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		listaNazw = new ArrayList<String>();
		listaID = new ArrayList<Integer>();
		listaTypow = new ArrayList<String>();
		listaMark = new ArrayList<Integer>();
		listaDat = new ArrayList<String>();
		listNotifications = (ListView) findViewById(R.id.listPowiadomienia);
		wczytajZawartosc();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		appMenu = menu;
		menu.add(0, 10, 0, "Powiadomienia")
				.setIcon(R.drawable.ic_notification)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		SubMenu sub = menu.addSubMenu("Menu");
		sub.add(1, 7, 1, "Planowanie");
		sub.add(1, 6, 1, "Limity");
		sub.add(1, 5, 1, "Opcje bud¿etu");
		sub.add(1, 4, 1, "Zmieñ bud¿et");
		sub.add(1, 3, 1, "FAQ");
		sub.add(1, 2, 1, "O aplikacji");
		sub.add(1, 1, 1, "Wyloguj");
		sub.getItem().setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}
	
	public void wczytajZawartosc() {
		try {
			Notifications notifications = Singleton.getInstance().ws
					.GetNotifications(true);
			if (notifications != null) {
				for (int i = 0; i < notifications.count; i++) {
					listaNazw.add(notifications.notifications.get(i).tekst);
					listaDat.add(notifications.notifications.get(i).data);
					listaMark
							.add(notifications.notifications.get(i).przeczytane);
					listaTypow.add(notifications.notifications.get(i).typ);
					listaID.add(notifications.notifications.get(i).ID_Powiadomienia);
				}
				ListNotifications adapter = new ListNotifications(
						Powiadomienia.this, listaNazw, listaMark, listaDat,
						listaTypow);
				listNotifications.setAdapter(adapter);
				listNotifications
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									final View view, int position, long id) {
								if (listaMark.get(position) == 0) {
									try {
										Singleton.getInstance().ws
												.MarkNotificationAsRead(listaID
														.get(position));
										Intent seeBudget = new Intent(
												Powiadomienia.this,
												Powiadomienia.class);
										startActivity(seeBudget);
										Toast.makeText(
												Powiadomienia.this,
												"Powiadomienie oznaczone jako przeczytane",
												Toast.LENGTH_SHORT).show();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						});
			} else {
				Toast.makeText(Powiadomienia.this,
						"Brak powiadomieñ dla tego bud¿etu", Toast.LENGTH_LONG)
						.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 10) {
			powiadomienia();
		} else if (item.getItemId() == 7) {
			planowanie();
		} else if (item.getItemId() == 6) {
			limity();
		} else if (item.getItemId() == 5) {
			opcjeBudzetu();
		} else if (item.getItemId() == 4) {
			registerForContextMenu(getWindow().getDecorView().findViewById(
					android.R.id.content));
			openContextMenu(getWindow().getDecorView().findViewById(
					android.R.id.content));
		} else if (item.getItemId() == 3) {
			faq();
		} else if (item.getItemId() == 2) {
			about();
		} else if (item.getItemId() == 1) {
			wyloguj();
		}
		return true;
	}

	public void wyloguj() {
		try {
			if (Singleton.getInstance().ws.Logout() == true) {
				Toast.makeText(Powiadomienia.this, "Wylogowano",
						Toast.LENGTH_SHORT).show();
				Intent myIntent = new Intent(Powiadomienia.this,
						MainActivity.class);
				Powiadomienia.this.startActivity(myIntent);
			} else
				Toast.makeText(Powiadomienia.this, "Problem z wylogowaniem",
						Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void about() {
		Intent myIntent = new Intent(Powiadomienia.this, About.class);
		Powiadomienia.this.startActivity(myIntent);
	}

	public void faq() {
		Intent myIntent = new Intent(Powiadomienia.this, FAQ.class);
		Powiadomienia.this.startActivity(myIntent);
	}

	public void opcjeBudzetu() {
		Intent myIntent = new Intent(Powiadomienia.this, FAQ.class);
		Powiadomienia.this.startActivity(myIntent);
	}

	public void limity() {
		Intent myIntent = new Intent(Powiadomienia.this, FAQ.class);
		Powiadomienia.this.startActivity(myIntent);
	}

	public void planowanie() {
		Intent myIntent = new Intent(Powiadomienia.this, FAQ.class);
		Powiadomienia.this.startActivity(myIntent);
	}

	public void powiadomienia() {
		Intent myIntent = new Intent(Powiadomienia.this, Powiadomienia.class);
		finish();
		Powiadomienia.this.startActivity(myIntent);
	}

}
