package com.btten.hcb.rescue;

import com.btten.hcbvip.R;
import com.btten.vincenttools.CallTelephone;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RescueListAdapter extends BaseAdapter {
	Activity context = null;
	RescueListItem[] items;

	public RescueListAdapter(Activity context, RescueListItem[] items) {
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
			convertView = inflater.inflate(R.layout.rescue_list_item, null);
			vHolder.txtName = (TextView) convertView
					.findViewById(R.id.rescue_list_item_name);
			vHolder.txtPhone = (TextView) convertView
					.findViewById(R.id.rescue_list_item_phone);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txtName.setText(items[position].name);
		vHolder.txtPhone.setText("24小时热线：" + items[position].phone);
		convertView.setOnClickListener(new adapterListner(position));
		return convertView;
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

	public class ViewHolder {
		TextView txtName, txtPhone;
	}

	class adapterListner implements OnClickListener {
		int index;

		public adapterListner(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			new CallTelephone(context, items[index].phone, items[index].name)
					.call();
		}
	}
}
