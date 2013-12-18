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

		String src = "<!DOCTYPE html><html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><style>h1 {line-height: 1.1em;}	h3{font-weight:none;}	.question {padding: 0 10px;font-weight: bold;font-size:1em;}	.answer {font-size:0.9em;padding: 0 10px;}	.Q, .A{font-weight: bold;padding: 3px 6px;text-align:center;background: #004C80;color: #fff;} 	.spacer > td{padding: 5px 0;}	.A{background: #0063A6;}</style><body><center><h1>FAQ</h1><h3>Frequently Asked Questions</3></center><table cellspacing=\"0\"><tr><td class=\"Q\">Q</td><td class=\"question\">Jakiej wersji systemu Android wymaga aplikacja?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Minimalna wersja systemu to 1.5 (SDK 3).</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak mogê za³o¿yæ konto?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby za³o¿yæ konto nale¿y u¿yæ przycisku Za³ó¿ konto dostêpnego na ekranie startowym aplikacji.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Co nale¿y zrobiæ by zacz¹æ korzystaæ z aplikacji?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Nale¿y pobraæ j¹ ze strony mybudgetpal.com i zainstalowaæ na swoim smartfonie/tablecie.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Ile bud¿etów muszê dodaæ?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Nale¿y dodaæ minimum jeden bud¿et, na którym bêd¹ wykonywane operacje. W ka¿dej chwili dzia³ania aplikacji mo¿na prze³¹czaæ bud¿ety miêdzy sob¹.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak dodaæ bud¿et?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Jeœli nie masz jeszcze dodanego ¿adnego bud¿etu, po zalogowaniu zostanie wyœwietlony komunikat i wskazówka jak dodaæ bud¿et. Ka¿dy kolejny mo¿na dodaæ wybieraj¹c z menu Zmieñ bud¿et i wybieraj¹c ostatni¹ opcjê z listy Dodaj nowy...</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak dodaæ wydatek?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby dodaæ wydatek nale¿y przejœæ do drugiej zak³adki w górnym menu – Wydatki, a nastêpnie wybraæ przycisk + w menu na dole ekranu.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak dodaæ przychód?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby dodaæ przychód nale¿y przejœæ do trzeciej zak³adki w górnym menu – Przychody, a nastêpnie wybraæ przycisk + w menu na dole ekranu.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak dodaæ zaplanowany wydatek?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby dodaæ zaplanowany wydatek nale¿y wybraæ z menu opcjê 'Planowanie', a nastêpnie  wype³niæ wymagane pola.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak dodaæ zaplanowany dochód?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby dodaæ zaplanowany dochód nale¿y wybraæ z menu opcjê 'Planowanie', a nastêpnie wype³niæ wymagane pola.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak dodaæ limit wydatków?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby dodaæ zaplanowany dochód nale¿y wybraæ z menu opcjê 'Limity', a nastêpnie wype³niæ wymagane pola.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak dodaæ wydatki z paragonu?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby zeskanowaæ paragon fiskalny i dodaæ zawarte na nim produkty do listy swoich wydatków, nale¿y w zak³adce Wydatki wybraæ przycisk Skorzystaj z OCR. U¿ytkownik zostanie przeniesiony do aplikacji OCRTest (jeœli nie ma jej zainstalowanej na smart fonie/tablecie, nast¹pi przeniesienie bezpoœrednio do jej strony w Google Play). Po zeskanowaniu paragonu nale¿y wcisn¹æ przycisk z ikon¹ aparatu. Wydatek zostanie automatycznie dodany i nast¹pi powrót do aplikacji My Budget Pal.</td></tr><tr class=\"spacer\"><td></td><td></td></tr><tr><td class=\"Q\">Q</td><td class=\"question\">Jak zobaczyæ wykresy?</td></tr><tr><td class=\"A\">A</td><td class=\"answer\">Aby przejrzeæ statystyki wydatków i przychodów danego bud¿etu nale¿y przejœæ do czwartej zak³adki w górnym menu – Statystyki.</td></tr></table></body></html>";

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
