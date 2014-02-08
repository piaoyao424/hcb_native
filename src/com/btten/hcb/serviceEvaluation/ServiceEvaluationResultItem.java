package com.btten.hcb.serviceEvaluation;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import com.btten.base.ListItemBase;
import com.btten.hcbvip.R;

public class ServiceEvaluationResultItem extends ListItemBase {
	public String content;
	public String VipName;
	public int star;
	public int status;

	public TextView txtViewVipName = null;
	public TextView txtViewContent = null;
	public RatingBar ratingStar = null;

	public ServiceEvaluationResultItem() {
		layoutId = R.layout.evaluation_service_list_item;
	}

	@Override
	public void initView(View view) {

		txtViewVipName = (TextView) view
				.findViewById(R.id.evaluation_service_listitem_vipname);
		txtViewContent = (TextView) view
				.findViewById(R.id.evaluation_service_listitem_content);

		ratingStar = (RatingBar) view
				.findViewById(R.id.evaluation_service_listitem_ratingbar);

		// 评价人名称
		txtViewVipName.setText(VipName);
		// 评分
		ratingStar.setRating(star);
		// 评价内容
		txtViewContent.setText(content);
	}
}
