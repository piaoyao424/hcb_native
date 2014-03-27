package com.btten.hcb.search;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import com.btten.base.ListItemBase;
import com.btten.hcbvip.R;

public class SearchResultItem extends ListItemBase {
	public String jname;
	public String jid;
	public Double gps_la;
	public Double gps_lo;
	public int star;
	public String oldPrice;
	public String newPrice;
	public String distance;
	public String areaName;
	public String scope;
	public int status;

	public TextView[] txtView = new TextView[6];
	public int[] txt_id = { R.id.sreachresult_txt_jname,
			R.id.sreachresult_txt_scope, R.id.sreachresult_txt_oldprice,
			R.id.sreachresult_txt_newprice, R.id.sreachresult_txt_areaname,
			R.id.sreachresult_txt_distance };
	public RatingBar ratingStar = null;

	public SearchResultItem() {
		layoutId = R.layout.search_result_item;
	}

	@Override
	public void initView(View view, Context context) {

		for (int i = 0; i < 6; i++) {
			TextView tmptxtView = (TextView) view.findViewById(txt_id[i]);
			txtView[i] = tmptxtView;
		}

		ratingStar = (RatingBar) view.findViewById(R.id.sreachresult_ratingbar);

		// 加盟商名称
		txtView[0].setText(jname);
		// 评分
		ratingStar.setRating(star);
		// 经营范围
		txtView[1].setText(scope);
		// 原价
		txtView[2].getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		txtView[2].setText("￥" + oldPrice);
		// 折扣价
		txtView[3].setText("￥" + newPrice);
		// 地区
		txtView[4].setText(areaName);
		// 距离
		txtView[5].setText(distance + "公里");
	}
}
