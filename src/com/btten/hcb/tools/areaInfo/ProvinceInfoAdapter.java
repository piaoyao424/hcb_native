package com.btten.hcb.tools.areaInfo;

import com.btten.hcbvip.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProvinceInfoAdapter extends BaseAdapter {
	View view = null;
	Activity context = null;
	private ProvinceListItem[] items;

	public ProvinceInfoAdapter(Activity context, ProvinceListItem[] items) {
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
		vHolder.id = items[position].id;
		vHolder.txt_name.setText(items[position].name);
		convertView.setOnClickListener(new adapterListner(position));
		return convertView;
	}

	class adapterListner implements OnClickListener {
		int index;

		public adapterListner(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, ProvinceInfoActivity.class);
			intent.putExtra("KEY_areaID", items[index].id);
			context.startActivity(intent);
		}
	}

	class ViewHolder {
		String id;
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
