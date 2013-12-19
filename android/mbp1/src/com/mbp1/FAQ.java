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

public class FAQ extends SherlockActivity {

	Menu appMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faq);

		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		WebView faq = (WebView) findViewById(R.id.webFAQ);

		String src = "<!DOCTYPE html><html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><style>h1 {line-height: 1.1em;}	h3{font-weight:none;}	.question {padding: 0 10px;font-weight: bold;font-size:1em;}	.answer {font-size:0.9em;padding: 0 10px;}	.Q, .A{font-weight: bold;padding: 3px 6px;text-align:center;background: #004C80;color: #fff;} 	.spacer > td{padding: 5px 0;}	.A{background: #0063A6;}</style><body><center><h1>FAQ</h1><h3>Frequently Asked Questions</3></center><table cellspacing=\"0\"><tr><td class=\"Q\">Q</td><td class=\"question\">Jakiej wersji systemu Android wymaga aplikacja?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Minimalna wersja systemu to 1.5 (SDK 3).</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak mog� za�o�y� konto?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby za�o�y� konto nale�y u�y� przycisku Za�� konto dost�pnego na ekranie startowym aplikacji.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Co nale�y zrobi� by zacz�� korzysta� z aplikacji?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Nale�y pobra� j� ze strony mybudgetpal.com i zainstalowa� na swoim smartfonie/tablecie.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Ile bud�et�w musz� doda�?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Nale�y doda� minimum jeden bud�et, na kt�rym b�d� wykonywane operacje. W ka�dej chwili dzia�ania aplikacji mo�na prze��cza� bud�ety mi�dzy sob�.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak doda� bud�et?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Je�li nie masz jeszcze dodanego �adnego bud�etu, po zalogowaniu zostanie wy�wietlony komunikat i wskaz�wka jak doda� bud�et. Ka�dy kolejny mo�na doda� wybieraj�c z menu Zmie� bud�et i wybieraj�c ostatni� opcj� z listy Dodaj nowy...</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak doda� wydatek?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby doda� wydatek nale�y przej�� do drugiej zak�adki w g�rnym menu � Wydatki, a nast�pnie wybra� przycisk + w menu na dole ekranu.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak doda� przych�d?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby doda� przych�d nale�y przej�� do trzeciej zak�adki w g�rnym menu � Przychody, a nast�pnie wybra� przycisk + w menu na dole ekranu.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak doda� zaplanowany wydatek?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby doda� zaplanowany wydatek nale�y wybra� z menu opcj� 'Planowanie', a nast�pnie  wype�ni� wymagane pola.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak doda� zaplanowany doch�d?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby doda� zaplanowany doch�d nale�y wybra� z menu opcj� 'Planowanie', a nast�pnie wype�ni� wymagane pola.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak doda� limit wydatk�w?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby doda� zaplanowany doch�d nale�y wybra� z menu opcj� 'Limity', a nast�pnie wype�ni� wymagane pola.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak doda� wydatki z paragonu?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby zeskanowa� paragon fiskalny i doda� zawarte na nim produkty do listy swoich wydatk�w, nale�y w zak�adce Wydatki wybra� przycisk Skorzystaj z OCR. U�ytkownik zostanie przeniesiony do aplikacji OCRTest (je�li nie ma jej zainstalowanej na smart fonie/tablecie, nast�pi przeniesienie bezpo�rednio do jej strony w Google Play). Po zeskanowaniu paragonu nale�y wcisn�� przycisk z ikon� aparatu. Wydatek zostanie automatycznie dodany i nast�pi powr�t do aplikacji My Budget Pal.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak zobaczy� wykresy?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby przejrze� statystyki wydatk�w i przychod�w danego bud�etu nale�y przej�� do czwartej zak�adki w g�rnym menu � Statystyki.</td></tr></table></body></html>";

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
		sub.add(1, 5, 1, "Opcje bud�etu");
		sub.add(1, 4, 1, "Zmie� bud�et");
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
				Toast.makeText(FAQ.this, "Wylogowano", Toast.LENGTH_SHORT)
						.show();
				Intent myIntent = new Intent(FAQ.this, MainActivity.class);
				FAQ.this.startActivity(myIntent);
			} else
				Toast.makeText(FAQ.this, "Problem z wylogowaniem",
						Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void about() {
		Intent myIntent = new Intent(FAQ.this, About.class);
		FAQ.this.startActivity(myIntent);
	}

	public void faq() {
		Intent myIntent = new Intent(FAQ.this, FAQ.class);
		FAQ.this.startActivity(myIntent);
	}

	public void opcjeBudzetu() {
		Intent myIntent = new Intent(FAQ.this, FAQ.class);
		FAQ.this.startActivity(myIntent);
	}

	public void limity() {
		Intent myIntent = new Intent(FAQ.this, FAQ.class);
		FAQ.this.startActivity(myIntent);
	}

	public void planowanie() {
		Intent myIntent = new Intent(FAQ.this, FAQ.class);
		FAQ.this.startActivity(myIntent);
	}

	public void powiadomienia() {
		Intent myIntent = new Intent(FAQ.this, Powiadomienia.class);
		FAQ.this.startActivity(myIntent);
	}
}
