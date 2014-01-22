package com.btten.hcb.search;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import com.btten.base.ListItemBase;
import com.btten.hcbvip.R;

public class SearchResultItem extends ListItemBase {
	public String jname;
	public String jid;
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
	public void initView(View view) {

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
		txtView[0].setText(scope);
		// 原价
		txtView[0].setText("￥" + oldPrice);
		// 折扣价
		txtView[0].setText("￥" + newPrice);
		// 地区
		txtView[0].setText(areaName);
		// 距离
		txtView[0].setText(distance + "公里");
	}
}
