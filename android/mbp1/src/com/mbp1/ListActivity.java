package com.mbp1;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListActivity extends BaseAdapter {
	private Activity activity;
	private List<String> list_nazwa;
	private List<String> list_data;
	private List<Double> list_koszt;
	private List<String> list_rodzaj;
	private static LayoutInflater inflater = null;
	NumberFormat formatter = new DecimalFormat("#0.00");

	public ListActivity(Activity a, List<String> Aktywnosc, List<Double> Koszt,
			List<String> Data, List<String> Rodzaj) {
		this.activity = a;
		this.list_nazwa = Aktywnosc;
		this.list_koszt = Koszt;
		this.list_data = Data;
		this.list_rodzaj = Rodzaj;
		ListActivity.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView text_nazwa;
		public TextView text_data;
		public TextView text_koszt;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.aktywnosc_item_list, null);
			holder = new ViewHolder();
			holder.text_nazwa = (TextView) vi
					.findViewById(R.id.textAktywnoscNazwa);
			holder.text_data = (TextView) vi
					.findViewById(R.id.textAktywnoscData);
			holder.text_koszt = (TextView) vi
					.findViewById(R.id.textAktywnoscKwota);
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		holder.text_nazwa.setText(this.list_nazwa.get(position));
		holder.text_data.setText(this.list_data.get(position));
		if (this.list_rodzaj.get(position).equals("wydatek")) {
			//#D40000
			holder.text_koszt.setText(Html
					.fromHtml("<font color='#ffffff'><b>- "
							+ formatter.format(this.list_koszt.get(position))
							+ " z³</b></font>"));
			holder.text_koszt.setBackgroundColor(Color.RED);
		} else {
			//#55AB3D
			holder.text_koszt.setText(Html
					.fromHtml("<font color='#ffffff'><b>+ "
							+ formatter.format(this.list_koszt.get(position))
							+ " z³</b></font>"));
			holder.text_koszt.setBackgroundColor(Color.GREEN);
		}

		return vi;
	}

	public int getCount() {
		return list_nazwa.size();
	}
}
