package com.btten.hcb.exchange;

import com.btten.hcbvip.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExchangeRecordListAdapter extends BaseAdapter {
	public ExchangeRecordListItem[] items;
	private Context context;

	public ExchangeRecordListAdapter(Context context,
			ExchangeRecordListItem[] items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder vHolder;
		LayoutInflater inflater = null;

		if (convertView == null) {
			vHolder = new ViewHolder();
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.carknowledge_list_item,
					null);
			vHolder.tv_title = (TextView) convertView
					.findViewById(R.id.exchange_record_item_title);
			vHolder.tv_date = (TextView) convertView
					.findViewById(R.id.exchange_record_item_date);
			vHolder.tv_name = (TextView) convertView
					.findViewById(R.id.exchange_record_item_name);
			vHolder.tv_jmsname = (TextView) convertView
					.findViewById(R.id.exchange_record_item_jmsName);
			vHolder.tv_point = (TextView) convertView
					.findViewById(R.id.exchange_record_item_point);
			vHolder.tv_phone = (TextView) convertView
					.findViewById(R.id.exchange_record_item_phone);
			vHolder.tv_addr = (TextView) convertView
					.findViewById(R.id.exchange_record_item_addr);
			vHolder.linear = (LinearLayout) convertView
					.findViewById(R.id.exchange_record_item_linear);
			vHolder.relative = (RelativeLayout) convertView
					.findViewById(R.id.exchange_record_item_relative);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.tv_title.setText(items[position].title);
		vHolder.tv_date.setText(items[position].date);
		vHolder.tv_name.setText(items[position].name);
		vHolder.tv_jmsname.setText(items[position].jmsname);
		vHolder.tv_point.setText(items[position].point);
		vHolder.tv_phone.setText(items[position].phone);
		vHolder.tv_addr.setText(items[position].addr);
		vHolder.relative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (vHolder.linear.getVisibility() == View.VISIBLE) {
					vHolder.linear.setVisibility(View.GONE);
				} else {
					vHolder.linear.setVisibility(View.VISIBLE);
				}
			}
		});
		return convertView;
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
		public TextView tv_title;
		public TextView tv_date;
		public TextView tv_name;
		public TextView tv_point;
		public TextView tv_jmsname;
		public TextView tv_addr;
		public TextView tv_phone;
		public LinearLayout linear;
		public RelativeLayout relative;
	}
}
