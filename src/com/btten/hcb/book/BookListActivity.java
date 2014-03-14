package com.btten.hcb.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BookListActivity extends BaseActivity {

	private ListView lv;
	private TextView txtAuthor, txtTitle, txtSynopsis;
	private ImageView imageView;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_list_activity);
		setCurrentTitle("书推荐");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		txtAuthor = (TextView) findViewById(R.id.book_list_author);
		txtTitle = (TextView) findViewById(R.id.book_list_title);
		txtSynopsis = (TextView) findViewById(R.id.book_list_synopsis);
		imageView = (ImageView) findViewById(R.id.book_list_image);
		button = (Button) findViewById(R.id.book_list_button);

		lv = (ListView) findViewById(R.id.booklist_activity_lv);
		new BookListScene().doScene(callBack);
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			BookListResult item = (BookListResult) data;
			final BookListItem[] items = item.items;
			// 取最新的数据为今天推荐
			txtAuthor.setText(items[0].author);
			txtTitle.setText(items[0].title);
			txtSynopsis.setText(items[0].synopsis);
			ImageLoader.getInstance().displayImage(items[0].image, imageView);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(BookListActivity.this,
							BookInfoActivity.class);
					intent.putExtra("KEY_ID", items[0].id);
					startActivity(intent);
				}
			});
			// 拿掉第一个数据
			for (int i = 0; i < items.length; i++) {
				items[i] = items[i + 1];
			}

			items[items.length - 1] = null;

			BookListAdapter adapter = new BookListAdapter(
					BookListActivity.this, item.items);
			lv.setAdapter(adapter);
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
