package com.btten.hcb.publicNotice;

import com.btten.base.ListItemAdapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class PublicNoticeListAdapter extends ListItemAdapter {
	View view = null;
	Activity context = null;

	public PublicNoticeListAdapter(Activity context) {
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
			Intent intent = new Intent(context, PublicNoticeInfoActivity.class);
			intent.putExtra("KEY_GGID",
					((PublicNoticeListItem) items[index]).id);
			context.startActivity(intent);
		}
	}
}
