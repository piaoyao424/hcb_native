package com.btten.hcb.Gonggao;

import com.btten.base.ListItemAdapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class NoticeListAdapter extends ListItemAdapter {
	View view = null;
	Activity context = null;

	public NoticeListAdapter(Activity context) {
		super(context);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i("position = " + position,
				"parent.getchildposition" + parent.getChildCount());
		if (convertView == null) {
			convertView = inflater.inflate(items[position].getItemLayout(),
					null);
		}
		items[position].initView(convertView);
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
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, NoticeInfoActivity.class);
			intent.putExtra("KEY_GGID", ((NoticeListItem) items[index]).id);
			intent.putExtra("KEY_DATE", ((NoticeListItem) items[index]).date);
			intent.putExtra("KEY_TITLE", ((NoticeListItem) items[index]).title);
			context.startActivity(intent);
		}
	}
}
