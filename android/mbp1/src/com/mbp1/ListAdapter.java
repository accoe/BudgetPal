package com.mbp1;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	private Activity activity;
	private List<String> list1;
	private List<String> list2;
	private static LayoutInflater inflater = null;

	public ListAdapter(Activity a, List<String> listaNazwBudzetow,
			List<String> listaOpisowBudzetow) {
		this.activity = a;
		this.list1 = listaNazwBudzetow;
		this.list2 = listaOpisowBudzetow;
		ListAdapter.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView text1;
		public TextView text2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.two_items_list, null);
			holder = new ViewHolder();
			holder.text1 = (TextView) vi.findViewById(R.id.textWybierzBudzet);
			holder.text2 = (TextView) vi
					.findViewById(R.id.textEdytujBudzetNazwa);
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		holder.text1.setText(this.list1.get(position));
		holder.text2.setText(this.list2.get(position));

		return vi;
	}

	public int getCount() {
		return list1.size();
	}
}
