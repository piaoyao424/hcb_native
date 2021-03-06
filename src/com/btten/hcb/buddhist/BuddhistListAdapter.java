package com.btten.hcb.buddhist;

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

public class BuddhistListAdapter extends BaseAdapter {
	View view = null;
	Activity context = null;
	private BuddhistListItem[] items;

	public BuddhistListAdapter(Activity context, BuddhistListItem[] items) {
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
			convertView = inflater.inflate(R.layout.buddhist_list_item, null);
			vHolder.txtTitle = (TextView) convertView
					.findViewById(R.id.buddhist_list_item_title);
			vHolder.txtContent = (TextView) convertView
					.findViewById(R.id.buddhist_list_item_content);
			vHolder.txtDate = (TextView) convertView
					.findViewById(R.id.buddhist_list_item_date);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txtTitle.setText(items[position].title);
		vHolder.txtContent.setText(items[position].content);
		vHolder.txtDate.setText(items[position].date);

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
			Intent intent = new Intent(context, BuddhistInfoActivity.class);
			intent.putExtra("KEY_ID", items[index].id);
			context.startActivity(intent);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	class ViewHolder {
		TextView txtTitle, txtContent, txtDate;
	}
}
