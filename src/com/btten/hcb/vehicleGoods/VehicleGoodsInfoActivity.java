package com.btten.hcb.vehicleGoods;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;

public class VehicleGoodsInfoActivity extends BaseActivity {

	private TextView txtTitle, txtPrice, txtDiscount, txtType, txtContent;
	private ImageView imageView1, imageView2, imageView3;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_info_activity);
		setCurrentTitle("车产品详情");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {
		txtType = (TextView) findViewById(R.id.book_list_author);
		txtTitle = (TextView) findViewById(R.id.book_list_title);
		txtContent = (TextView) findViewById(R.id.book_list_synopsis);
		txtPrice = (TextView) findViewById(R.id.book_info_price);
		txtDiscount = (TextView) findViewById(R.id.book_info_excerpt);

		imageView1 = (ImageView) findViewById(R.id.book_list_image);
		imageView2 = (ImageView) findViewById(R.id.book_list_image);
		imageView3 = (ImageView) findViewById(R.id.book_list_image);
		button = (Button) findViewById(R.id.vehicle_life_item_txt);

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
			txtContent.setText(ToDBC(items.item.content));
			txtPrice.setText(items.item.price);
			txtDiscount.setText(items.item.discount);

			ImageLoader.getInstance().displayImage(items.item.image1,
					imageView1);
			ImageLoader.getInstance().displayImage(items.item.image2,
					imageView2);
			ImageLoader.getInstance().displayImage(items.item.image3,
					imageView3);
			HideProgress();
			return;
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	/***
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	private static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
