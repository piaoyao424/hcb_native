package com.btten.hcb.tools.areaInfo;

import com.btten.hcbvip.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProvinceListAdapter extends BaseAdapter {
	View view = null;
	Activity context = null;
	private ProvinceListItem[] items;

	public ProvinceListAdapter(Activity context, ProvinceListItem[] items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder;
		LayoutInflater inflater = null;

		if (convertView == null) {
			vHolder = new ViewHolder();
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.province_list_item, null);
			vHolder.txt_name = (TextView) convertView
					.findViewById(R.id.province_list_item_name);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txt_name.setText(items[position].name);
		vHolder.id = items[position].id;
		vHolder.name = items[position].name;
		return convertView;
	}

	static class ViewHolder {
		String id;
		String name;
		TextView txt_name;
	}

	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
