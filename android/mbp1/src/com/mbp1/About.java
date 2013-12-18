package com.mbp1;

import json.Singleton;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import android.os.Bundle;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.Toast;

public class About extends SherlockActivity {

	Menu appMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faq);

		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		WebView faq = (WebView) findViewById(R.id.webFAQ);

		String src = "<!DOCTYPE html><html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><style>	body {		font-family: sans-serif;	}	h1 {		line-height: 1.1em;	}	.feature {		padding: 0 10px;		font-weight: bold;	}	.plus {		font-weight: bold;		padding: 3px 6px;		background: #008f07;		color: #fff;	} </style><body><center><h1>MyBudgetPal.com</h1></center><div class=\"desc\">MyBudgetPal – to aplikacja na urz¹dzenia mobilne z systemem Android umo¿liwiaj¹ca planowanie bud¿etu domowego poprzez definiowanie bud¿etów, dodawanie przychodów i wydatków, okreœlanie zaplanowanych przychodów, definiowanie w³asnych kategorii wydatków, generowanie raportów i wykresów, okreœlanie limitów i powiadomieñ. Aplikacja posiada modu³ OCR do szybkiego dodawania wydatków z paragonów fiskalnych.</div><h3>Wyró¿niaj¹ce nas funkcje</h3><table cellspacing=\"2\"><tr><td class=\"plus\">+</td><td class=\"feature\">Modu³ OCR paragonów</td></tr><tr><td class=\"plus\">+</td><td class=\"feature\">Prostota i intuicyjna obs³uga</td></tr><tr><td class=\"plus\">+</td><td class=\"feature\">Powiadomienia o zmianach na koncie</td></tr><tr><td class=\"plus\">+</td><td class=\"feature\">Zaplanowane wydatki i dochody</td></tr><tr><td class=\"plus\">+</td><td class=\"feature\">Ogromna baza zdefiniowanych produktów</td></tr><tr><td class=\"plus\">+</td><td class=\"feature\">Rozbudowane statystyki</td></tr><tr><td class=\"plus\">+</td><td class=\"feature\">Raporty podsumowuj¹ce stan Twojego bud¿etu</td></tr></table></body></html>";

		faq.loadData(src, "text/html", "UTF-8");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		appMenu = menu;
		menu.add(0, 10, 0, "Powiadomienia").setIcon(R.drawable.ic_notification)
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
		} else if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return true;
	}

	public void wyloguj() {
		try {
			if (Singleton.getInstance().ws.Logout() == true) {
				Toast.makeText(About.this, "Wylogowano", Toast.LENGTH_SHORT)
						.show();
				Intent myIntent = new Intent(About.this, MainActivity.class);
				About.this.startActivity(myIntent);
			} else
				Toast.makeText(About.this, "Problem z wylogowaniem",
						Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void about() {
		Intent myIntent = new Intent(About.this, About.class);
		About.this.startActivity(myIntent);
	}

	public void faq() {
		Intent myIntent = new Intent(About.this, FAQ.class);
		About.this.startActivity(myIntent);
	}

	public void opcjeBudzetu() {
		Intent myIntent = new Intent(About.this, FAQ.class);
		About.this.startActivity(myIntent);
	}

	public void limity() {
		Intent myIntent = new Intent(About.this, FAQ.class);
		About.this.startActivity(myIntent);
	}

	public void planowanie() {
		Intent myIntent = new Intent(About.this, FAQ.class);
		About.this.startActivity(myIntent);
	}

	public void powiadomienia() {
		Intent myIntent = new Intent(About.this, Powiadomienia.class);
		About.this.startActivity(myIntent);
	}
}
