package com.btten.hcb.serviceEvaluation;

import com.btten.base.ListItemAdapter;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class ServiceEvaluationListAdapter extends ListItemAdapter {
	View view = null;
	Activity context = null;

	public ServiceEvaluationListAdapter(Activity context) {
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
		items[position].initView(convertView, context);
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
		 
		}
	}
}
