package com.mbp1;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListNotifications extends BaseAdapter {
	private Activity activity;
	private List<String> list_nazwa;
	private List<String> list_data;
	private List<Integer> list_mark;
	private List<String> list_typ;
	private static LayoutInflater inflater = null;

	public ListNotifications(Activity a, List<String> Powiadomienie,
			List<Integer> Mark, List<String> Data, List<String> Typ) {
		this.activity = a;
		this.list_nazwa = Powiadomienie;
		this.list_mark = Mark;
		this.list_data = Data;
		this.list_typ = Typ;
		ListNotifications.inflater = (LayoutInflater) activity
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
	}

	@SuppressLint("ResourceAsColor")
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		try {
			if (convertView == null) {
				vi = inflater.inflate(R.layout.aktywnosc_item_list, null);
				holder = new ViewHolder();
				holder.text_nazwa = (TextView) vi
						.findViewById(R.id.textAktywnoscNazwa);
				holder.text_data = (TextView) vi
						.findViewById(R.id.textAktywnoscData);
				vi.setTag(holder);
			} else
				holder = (ViewHolder) vi.getTag();

			if (this.list_mark.get(position) == 0) {
				holder.text_nazwa.setText(Html.fromHtml("<b>"
						+ this.list_nazwa.get(position) + "</b>"));
				vi.setBackgroundColor(Color.LTGRAY);
			} else {
				holder.text_nazwa.setText(this.list_nazwa.get(position));
				vi.setBackgroundColor(Color.TRANSPARENT);
			}
			holder.text_data.setText(this.list_data.get(position));
		} catch (Exception e) {
		}
		return vi;
	}

	public int getCount() {
		return list_nazwa.size();
	}
}
