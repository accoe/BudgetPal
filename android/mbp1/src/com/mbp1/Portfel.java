package com.mbp1;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import charts.Chart;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import json.Activities;
import json.Budgets;
import json.Expenses;
import json.Incomes;
import json.Singleton;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Portfel extends SherlockActivity implements ActionBar.TabListener {

	Menu appMenu;
	List<String> listaNazw;
	List<String> listaDat;
	List<Double> listaKwot;
	List<String> listaRodzajow;
	List<Integer> listaID;
	Bundle bundle;
	int budzetID = 1;
	String budzetNazwa = "Mój bud¿et";
	ListView listBudgets;
	TextView element;
	WebView przegladarka;
	int rok = Calendar.getInstance().get(Calendar.YEAR);
	int miesiac = Calendar.getInstance().get(Calendar.MONTH) + 1;
	int wybranyMiesiac = miesiac;
	int wybranyRok = rok;
	String chart1;
	TextView nazwaMiesiaca;
	Button b1;
	Button b2;
	Spinner spinner;
	List<String> list;
	Chart chart;
	double bilans = 0;
	NumberFormat formatter;
	TextView bilansBudzetu;
	public static final String DANE_BUDZETU = "DANE_BUDZETU";
	int currentTab = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_portfel);

		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab tab1 = getSupportActionBar().newTab()
				.setIcon(R.drawable.ic_aktywnosc).setTabListener(this)
				.setTag("aktywnosc");
		getSupportActionBar().addTab(tab1);
		ActionBar.Tab tab2 = getSupportActionBar().newTab()
				.setIcon(R.drawable.ic_wydatki).setTabListener(this)
				.setTag("wydatki");
		getSupportActionBar().addTab(tab2);
		ActionBar.Tab tab3 = getSupportActionBar().newTab()
				.setIcon(R.drawable.ic_przychody).setTabListener(this)
				.setTag("przychody");
		getSupportActionBar().addTab(tab3);
		ActionBar.Tab tab4 = getSupportActionBar().newTab()
				.setIcon(R.drawable.ic_stats).setTabListener(this)
				.setTag("stats");
		getSupportActionBar().addTab(tab4);

		// budzetID = getIntent().getExtras().getInt("budzetID");
		// budzetNazwa = getIntent().getExtras().getString("budzetNazwa");

		element = (TextView) findViewById(R.id.textWybierzBudzet2);
		listBudgets = (ListView) findViewById(R.id.listBudgets);
		list = new ArrayList<String>();
		formatter = new DecimalFormat("#0.00");
		bilansBudzetu = (TextView) findViewById(R.id.textBilans);
		b1 = (Button) findViewById(R.id.btnZalozKonto);
		b2 = (Button) findViewById(R.id.calc8);
		spinner = (Spinner) findViewById(R.id.spinner1);
		przegladarka = (WebView) findViewById(R.id.webView);
		list = new ArrayList<String>();
		listaNazw = new ArrayList<String>();
		listaDat = new ArrayList<String>();
		listaKwot = new ArrayList<Double>();
		listaRodzajow = new ArrayList<String>();
		nazwaMiesiaca = (TextView) findViewById(R.id.textStatsMiesiac);
		wczytajAktywnosc();
		listaID = new ArrayList<Integer>();
	}

	public void wczytajWydatki() {
		element.setText(Html.fromHtml("<b>Wydatki</b>"));
		listBudgets.setVisibility(View.VISIBLE);
		bilansBudzetu.setVisibility(View.GONE);
		przegladarka.setVisibility(View.GONE);
		nazwaMiesiaca.setVisibility(View.GONE);
		b1.setVisibility(View.GONE);
		b2.setVisibility(View.GONE);
		spinner.setVisibility(View.GONE);
		listaNazw.clear();
		listaDat.clear();
		listaKwot.clear();
		listaRodzajow.clear();
		try {
			Expenses expenses = Singleton.getInstance().ws.GetExpenses(
					budzetID, 15);
			if (expenses != null) {
				for (int i = 0; i < expenses.count; i++) {
					listaNazw.add(expenses.expenses.get(i).nazwa);
					listaDat.add(expenses.expenses.get(i).data);
					listaKwot.add(expenses.expenses.get(i).kwota);
					listaRodzajow.add("wydatek");
				}
				ListActivity adapter = new ListActivity(Portfel.this,
						listaNazw, listaKwot, listaDat, listaRodzajow);
				listBudgets.setAdapter(adapter);
			} else {
				Toast.makeText(Portfel.this, "Nie masz ¿adnych wydatków",
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void wczytajPrzychody() {
		element.setText(Html.fromHtml("<b>Przychody</b>"));
		listBudgets.setVisibility(View.VISIBLE);
		bilansBudzetu.setVisibility(View.GONE);
		przegladarka.setVisibility(View.GONE);
		nazwaMiesiaca.setVisibility(View.GONE);
		b1.setVisibility(View.GONE);
		b2.setVisibility(View.GONE);
		spinner.setVisibility(View.GONE);
		listaNazw.clear();
		listaDat.clear();
		listaKwot.clear();
		listaRodzajow.clear();
		try {
			Incomes incomes = Singleton.getInstance().ws.GetIncomes(budzetID,
					15);
			if (incomes != null) {
				for (int i = 0; i < incomes.count; i++) {
					listaNazw.add(incomes.incomes.get(i).nazwa);
					listaDat.add(incomes.incomes.get(i).data);
					listaKwot.add(incomes.incomes.get(i).kwota);
					listaRodzajow.add("przychod");
				}
				ListActivity adapter = new ListActivity(Portfel.this,
						listaNazw, listaKwot, listaDat, listaRodzajow);
				listBudgets.setAdapter(adapter);
			} else {
				Toast.makeText(Portfel.this, "Nie masz ¿adnych przychodów",
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void wczytajStats() {
		element.setText(Html.fromHtml("<b>Statystyki</b>"));
		listBudgets.setVisibility(View.GONE);
		przegladarka.setVisibility(View.VISIBLE);
		b1.setVisibility(View.VISIBLE);
		b2.setVisibility(View.VISIBLE);
		spinner.setVisibility(View.VISIBLE);
		bilansBudzetu.setVisibility(View.GONE);
		nazwaMiesiaca.setVisibility(View.VISIBLE);

		final String[] nazwyMiesiecy = { "styczeñ", "luty", "marzec",
				"kwiecieñ", "maj", "czerwiec", "lipiec", "sierpieñ",
				"wrzesieñ", "paŸdziernik", "listopad", "grudzieñ" };

		przegladarka.getSettings().setJavaScriptEnabled(true);

		b1.setText(Html.fromHtml("&raquo;"));
		b2.setText(Html.fromHtml("&laquo;"));

		list.clear();
		list.add("wydatki");
		list.add("przychody");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		// Wykresy
		chart = new Chart(Singleton.getInstance().ws);
		chart.properties.setSize(270, 270);
		// Ko³owe
		nazwaMiesiaca.setText(nazwyMiesiecy[wybranyMiesiac - 1] + " "
				+ wybranyRok);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (String.valueOf(spinner.getSelectedItem()).equals("wydatki")) {

					// wydatki
					if (wybranyMiesiac == miesiac && wybranyRok == rok) {
						b1.setVisibility(View.GONE);
					}
					nazwaMiesiaca.setText(nazwyMiesiecy[wybranyMiesiac - 1]
							+ " " + wybranyRok);
					chart1 = chart.ExpensesPieChart(budzetID, wybranyRok,
							wybranyMiesiac);
					if (chart.notEmpty) {
						przegladarka.loadData(chart1, "text/html", "UTF-8");
					} else {
						przegladarka.loadData("Brak wydatk&#243;w.",
								"text/html", "UTF-8");
					}

					// wydatki - miesiac wstecz

					b2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							b1.setVisibility(View.VISIBLE);
							--wybranyMiesiac;
							if (wybranyMiesiac < 1) {
								wybranyRok--;
								wybranyMiesiac = 12;
							}
							nazwaMiesiaca
									.setText(nazwyMiesiecy[wybranyMiesiac - 1]
											+ " " + wybranyRok);
							chart1 = chart.ExpensesPieChart(budzetID,
									wybranyRok, wybranyMiesiac);
							if (chart.notEmpty) {
								przegladarka.loadData(chart1, "text/html",
										"UTF-8");
							} else {
								przegladarka.loadData("Brak wydatk&#243;w.",
										"text/html", "UTF-8");
							}
						}
					});

					// wydatki - miesiac do przodu

					b1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							wybranyMiesiac++;
							if (wybranyMiesiac == miesiac && wybranyRok == rok) {
								b1.setVisibility(View.GONE);
							}
							if (wybranyMiesiac > 12) {
								wybranyRok++;
								wybranyMiesiac = 1;
							}
							nazwaMiesiaca
									.setText(nazwyMiesiecy[wybranyMiesiac - 1]
											+ " " + wybranyRok);
							chart1 = chart.ExpensesPieChart(budzetID,
									wybranyRok, wybranyMiesiac);
							if (chart.notEmpty) {
								przegladarka.loadData(chart1, "text/html",
										"UTF-8");
							} else {
								przegladarka.loadData("Brak wydatk&#243;w.",
										"text/html", "UTF-8");
							}
						}
					});

				} else {
					// przychody
					if (wybranyMiesiac == miesiac && wybranyRok == rok) {
						b1.setVisibility(View.GONE);
					}
					nazwaMiesiaca.setText(nazwyMiesiecy[wybranyMiesiac - 1]
							+ " " + wybranyRok);
					chart1 = chart.IncomesPieChart(budzetID, wybranyRok,
							wybranyMiesiac);
					if (chart.notEmpty) {
						przegladarka.loadData(chart1, "text/html", "UTF-8");
					} else {
						przegladarka.loadData("Brak przychod&#243;w.",
								"text/html", "UTF-8");
					}

					// przychody - miesiac wstecz

					b2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							b1.setVisibility(View.VISIBLE);
							--wybranyMiesiac;
							if (wybranyMiesiac < 1) {
								wybranyRok--;
								wybranyMiesiac = 12;
							}
							nazwaMiesiaca
									.setText(nazwyMiesiecy[wybranyMiesiac - 1]
											+ " " + wybranyRok);
							chart1 = chart.IncomesPieChart(budzetID,
									wybranyRok, wybranyMiesiac);
							if (chart.notEmpty) {
								przegladarka.loadData(chart1, "text/html",
										"UTF-8");
							} else {
								przegladarka.loadData("Brak przychod&#243;w.",
										"text/html", "UTF-8");
							}
						}
					});

					// przychody - miesiac do przodu

					b1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							wybranyMiesiac++;
							if (wybranyMiesiac == miesiac && wybranyRok == rok) {
								b1.setVisibility(View.GONE);
							}
							if (wybranyMiesiac > 12) {
								wybranyRok++;
								wybranyMiesiac = 1;
							}
							nazwaMiesiaca
									.setText(nazwyMiesiecy[wybranyMiesiac - 1]
											+ " " + wybranyRok);
							chart1 = chart.IncomesPieChart(budzetID,
									wybranyRok, wybranyMiesiac);
							if (chart.notEmpty) {
								przegladarka.loadData(chart1, "text/html",
										"UTF-8");
							} else {
								przegladarka.loadData("Brak przychod&#243;w.",
										"text/html", "UTF-8");
							}
						}
					});

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}

	public void wczytajAktywnosc() {
		element.setText(Html.fromHtml("<b>" + budzetNazwa + "</b>"));
		listBudgets.setVisibility(View.VISIBLE);
		bilansBudzetu.setVisibility(View.VISIBLE);
		przegladarka.setVisibility(View.GONE);
		nazwaMiesiaca.setVisibility(View.GONE);
		b1.setVisibility(View.GONE);
		b2.setVisibility(View.GONE);
		spinner.setVisibility(View.GONE);
		listaNazw.clear();
		listaDat.clear();
		listaKwot.clear();
		listaRodzajow.clear();

		try {
			bilans = Singleton.getInstance().ws.GetBudgetBilans(budzetID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bilans > 0)
			bilansBudzetu.setText(Html
					.fromHtml("Saldo: <font color='#55AB3D'><b>"
							+ formatter.format(bilans) + " z³</b></font>"));
		else
			bilansBudzetu.setText(Html
					.fromHtml("Saldo: <font color='#D40000'><b>"
							+ formatter.format(bilans) + " z³</b></font>"));

		try {
			Activities activities = Singleton.getInstance().ws
					.GetRecentActivities(budzetID, 15);
			if (activities != null) {
				for (int i = 0; i < activities.count; i++) {
					listaNazw.add(activities.activities.get(i).nazwa);
					listaDat.add(activities.activities.get(i).data);
					listaKwot.add(activities.activities.get(i).kwota);
					listaRodzajow.add(activities.activities.get(i).rodzaj);
				}
				ListActivity adapter = new ListActivity(Portfel.this,
						listaNazw, listaKwot, listaDat, listaRodzajow);
				listBudgets.setAdapter(adapter);
			} else {
				Toast.makeText(Portfel.this,
						"Brak aktywnoœci dla tego bud¿etu", Toast.LENGTH_LONG)
						.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		listaNazw.clear();
		listaID.clear();
		try {
			Budgets budgets = Singleton.getInstance().ws.GetBudgets();
			if (budgets != null) {
				for (int i = 0; i < budgets.count; i++) {
					listaNazw.add(budgets.budgets.get(i).nazwa);
					listaID.add(budgets.budgets.get(i).ID_Budzetu);
				}
			} else {
				Toast.makeText(Portfel.this, "Brak bud¿etów", Toast.LENGTH_LONG)
						.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < listaID.size(); i++) {
			if (item.getTitle().equals(listaNazw.get(i))) {
				Toast.makeText(Portfel.this,
						"Zmieniono bud¿et na: " + listaNazw.get(i),
						Toast.LENGTH_LONG).show();
				budzetID = listaID.get(i);
				budzetNazwa = listaNazw.get(i);
			} else if (item.getTitle().equals("Dodaj nowy...")) {
				Intent myIntent = new Intent(Portfel.this, DodajBudzet.class);
				this.startActivity(myIntent);
			}
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		appMenu = menu;
		menu.add(0, 7, 0, "Powiadomienia").setIcon(R.drawable.ic_powiadomienie)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		SubMenu sub = menu.addSubMenu("Menu");
		sub.add(1, 4, 1, "Limity");
		try {
			Budgets budgets = Singleton.getInstance().ws.GetBudgets();
			if (budgets != null)
				sub.add(1, 5, 1, "Zmieñ bud¿et");
		} catch (Exception e) {
			e.printStackTrace();
		}
		sub.add(1, 4, 1, "O aplikacji");
		sub.add(1, 3, 1, "Wyloguj");
		sub.getItem().setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		if (currentTab == 1) {
			menu.add(2, 8, 2, "Dodaj wydatek").setIcon(R.drawable.ic_dodaj)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		} else if (currentTab == 2) {
			menu.add(2, 6, 2, "Dodaj przychód").setIcon(R.drawable.ic_dodaj)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		} else if (currentTab == 3) {
			menu.add(2, 6, 2, "Zmieñ typ wykresu").setIcon(R.drawable.ic_stats)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 5) {
			registerForContextMenu(getWindow().getDecorView().findViewById(
					android.R.id.content));
			openContextMenu(getWindow().getDecorView().findViewById(
					android.R.id.content));
			return false;
		} else if (item.getItemId() == 4) {
		} else if (item.getItemId() == 3) {
			try {
				if (Singleton.getInstance().ws.Logout() == true) {
					Toast.makeText(Portfel.this, "Wylogowano",
							Toast.LENGTH_SHORT).show();
					Intent myIntent = new Intent(Portfel.this,
							MainActivity.class);
					Portfel.this.startActivity(myIntent);
				} else
					Toast.makeText(Portfel.this, "Problem z wylogowaniem",
							Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else if (item.getItemId() == 6) {
			Intent intent = Portfel.this.getPackageManager()
					.getLaunchIntentForPackage("edu.sfsu.cs.orange.ocr");
			if (intent != null) {
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} else {
				intent = new Intent(Intent.ACTION_VIEW);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setData(Uri.parse("market://details?id="
						+ "edu.sfsu.cs.orange.ocr"));
				startActivity(intent);
			}
			return false;
		} else if (item.getItemId() == 8) {
			Intent myIntent = new Intent(Portfel.this, Tab1.class);
			Portfel.this.startActivity(myIntent);
			return false;
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Wybierz bud¿et");
		menu.clear();
		listaNazw.clear();
		listaID.clear();
		try {
			Budgets budgets = Singleton.getInstance().ws.GetBudgets();
			if (budgets != null) {
				for (int i = 0; i < budgets.count; i++) {
					menu.add(budgets.budgets.get(i).ID_Budzetu, v.getId(), 0,
							budgets.budgets.get(i).nazwa);
					listaNazw.add(budgets.budgets.get(i).nazwa);
					listaID.add(budgets.budgets.get(i).ID_Budzetu);
				}
				menu.add(-1, v.getId(), 0, "Dodaj nowy...");
			} else {
				Toast.makeText(Portfel.this, "Brak bud¿etów", Toast.LENGTH_LONG)
						.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		switch (tab.getPosition()) {
		case 1: {
			wczytajWydatki();
			changeMenu(1);
			break;
		}
		case 2: {
			wczytajPrzychody();
			changeMenu(2);
			break;
		}
		case 3: {
			wczytajStats();
			changeMenu(3);
			break;
		}
		case 0: {
			try {
				wczytajAktywnosc();
				changeMenu(0);
				break;
			} catch (Exception e) {
			}
		}
		}
	}

	public void changeMenu(int change) {
		currentTab = change;
		appMenu.clear();
		onCreateOptionsMenu(appMenu);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
	}

}
