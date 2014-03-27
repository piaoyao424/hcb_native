package com.btten.hcb.publicNotice;


import com.btten.hcbvip.R;
import com.btten.base.ListItemBase;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class PublicNoticeListItem extends ListItemBase {
	String title= "",date= "",id = "";
	TextView tv_title, tv_date ;
	
	public PublicNoticeListItem() {
		layoutId = R.layout.publicnotice_list_item;
	}

	@Override
	public void initView(View view, Context context) {
		tv_title=(TextView)view.findViewById(R.id.tvId_gonggao_title);
		tv_date=(TextView)view.findViewById(R.id.tvId_gonggao_date);
		
		tv_title.setText(title);
		tv_date.setText(date);
	}
}
