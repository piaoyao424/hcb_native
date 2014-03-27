package com.btten.hcb.shoppingRecord;

import com.btten.hcbvip.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShoppingRecordsListAdapter extends BaseAdapter {
	private Context context;
	private ShoppingRecordsListItem[] items;

	public ShoppingRecordsListAdapter(Context context,
			ShoppingRecordsListItem[] items) {
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
			convertView = inflater.inflate(R.layout.shopping_record_listitem,
					null);
			vHolder.tv_date = (TextView) convertView
					.findViewById(R.id.shopping_record_listitem_date);
			vHolder.tv_money = (TextView) convertView
					.findViewById(R.id.shopping_record_listitem_money);
			vHolder.tv_count = (TextView) convertView
					.findViewById(R.id.shopping_record_listitem_count);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.tv_date.setText(items[position].date);
		vHolder.tv_money.setText(items[position].money);
		vHolder.tv_count.setText(items[position].count);
		final int i = position;
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						ShoppingDetailActivity.class);
				intent.putExtra("KEY_ID", items[i].id);
				context.startActivity(intent);
			}
		});

		return convertView;
	}

	class ViewHolder {
		public TextView tv_date;
		public TextView tv_money;
		public TextView tv_count;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
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
}
