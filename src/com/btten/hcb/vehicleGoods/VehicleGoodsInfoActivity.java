package com.btten.hcb.vehicleGoods;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.hcb.branch.BranchListActivity;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;

public class VehicleGoodsInfoActivity extends BaseActivity {

	private TextView txtTitle, txtPrice, txtOldprice, txtDiscount, txtType,
			txtContent;
	private ImageView imageView1;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_goods_info_activity);
		setCurrentTitle("车产品详情");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {
		txtType = (TextView) findViewById(R.id.vehicleGoods_info_class);
		txtTitle = (TextView) findViewById(R.id.vehicleGoods_info_title);
		txtContent = (TextView) findViewById(R.id.vehicleGoods_info_content);
		txtPrice = (TextView) findViewById(R.id.vehicleGoods_info_newprice);
		txtOldprice = (TextView) findViewById(R.id.vehicleGoods_info_oldprice);
		txtDiscount = (TextView) findViewById(R.id.vehicleGoods_info_rate);

		imageView1 = (ImageView) findViewById(R.id.vehicleGoods_info_image);
		button = (Button) findViewById(R.id.vehicleGoods_info_button);

		Bundle bundle = this.getIntent().getExtras();
		new VehicleGoodsInfoScene().doScene(callBack,
				bundle.getString("KEY_ID"));
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			final VehicleGoodsInfoResult items = (VehicleGoodsInfoResult) data;

			txtType.setText(items.item.type);
			txtTitle.setText(items.item.title);
			txtPrice.setText("￥" + items.item.price);
			txtOldprice.setText("￥" + items.item.oldprice);
			txtOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			txtDiscount.setText(items.item.discount + "折");

			txtContent.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
			txtContent.setText(Html.fromHtml(items.item.content));

			ImageLoader.getInstance().displayImage(items.item.image1,
					imageView1);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(VehicleGoodsInfoActivity.this,
							BranchListActivity.class);
					intent.putExtra("KEY_NAME", items.item.title);
					intent.putExtra("KEY_ID", items.item.id);
					startActivity(intent);
				}
			});
			HideProgress();
			return;
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
