package com.btten.hcb.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BookInfoActivity extends BaseActivity {

	private TextView txtAuthor, txtTitle, txtSynopsis, txtDate, txtPublisher,
			txtExcerpt, txtPrice;
	private ImageView imageView;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_info_activity);
		setCurrentTitle("详情");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {

		txtAuthor = (TextView) findViewById(R.id.book_list_author);
		txtTitle = (TextView) findViewById(R.id.book_list_title);
		txtSynopsis = (TextView) findViewById(R.id.book_list_synopsis);
		imageView = (ImageView) findViewById(R.id.book_list_image);
		txtPrice = (TextView) findViewById(R.id.book_info_price);
		txtExcerpt = (TextView) findViewById(R.id.book_info_excerpt);
		txtDate = (TextView) findViewById(R.id.book_info_date);

		layout = (LinearLayout) findViewById(R.id.book_info_linearlayout);

		Bundle bundle = this.getIntent().getExtras();
		new BookInfoScene().doScene(callBack, bundle.getString("KEY_ID"));
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			final BookInfoResult items = (BookInfoResult) data;

			txtAuthor.setText(items.item.author);
			txtTitle.setText(items.item.title);
			txtSynopsis.setText(items.item.synopsis);
			txtPrice.setText(items.item.date);
			txtExcerpt.setText(items.item.excerpt);
			txtDate.setText(items.item.date);
			
			ImageLoader.getInstance().displayImage(items.item.image, imageView);

			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(BookInfoActivity.this,
							BookInfoActivity.class);
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
