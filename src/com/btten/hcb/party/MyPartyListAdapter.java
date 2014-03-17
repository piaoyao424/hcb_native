package com.btten.hcb.party;

import com.btten.hcbvip.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyPartyListAdapter extends BaseAdapter {
	Activity context = null;
	MyPartyListItem[] items;

	public MyPartyListAdapter(Activity context, MyPartyListItem[] items) {
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
			convertView = inflater.inflate(R.layout.my_party_item, null);
			vHolder.txtTitle = (TextView) convertView
					.findViewById(R.id.my_party_item_title);
			vHolder.txtInitiator = (TextView) convertView
					.findViewById(R.id.my_party_item_initiator);
			vHolder.txtAddr = (TextView) convertView
					.findViewById(R.id.my_party_item_addr);
			vHolder.txtDate = (TextView) convertView
					.findViewById(R.id.my_party_item_date);
			vHolder.imageView = (ImageView) convertView
					.findViewById(R.id.my_party_item_image);
			convertView.setTag(vHolder);

		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txtTitle.setText(items[position].title);
		vHolder.txtInitiator.setText(items[position].initiator);
		vHolder.txtAddr.setText(items[position].addr);
		vHolder.txtDate.setText(items[position].startDate + "出发，共"
				+ items[position].totleDate + "天");
		ImageLoader.getInstance().displayImage(items[position].image,
				vHolder.imageView);

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
			Intent intent = new Intent(context, MyPartyInfoActivity.class);
			intent.putExtra("KEY_ID", items[index].id);
			context.startActivity(intent);
		}
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
		TextView txtTitle, txtInitiator, txtAddr, txtDate;
		ImageView imageView;
	}
}
