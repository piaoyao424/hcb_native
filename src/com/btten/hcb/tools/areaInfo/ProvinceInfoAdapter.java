package com.btten.hcb.tools.areaInfo;

import com.btten.hcbvip.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProvinceInfoAdapter extends BaseAdapter {
	Activity context = null;
	private ProvinceListItem[] items;
	private int type = 0;

	public ProvinceInfoAdapter(Activity context, ProvinceListItem[] items,
			int type) {
		this.context = context;
		this.items = items;
		this.type = type;
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
			vHolder.image = (ImageView) convertView
					.findViewById(R.id.province_list_item_image);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.id = items[position].id;
		vHolder.txt_name.setText(items[position].name);
		vHolder.name = items[position].name;
		if (type > 0) {
			vHolder.image.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		String id;
		String name;
		TextView txt_name;
		ImageView image;
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
