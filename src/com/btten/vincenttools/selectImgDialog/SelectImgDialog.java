package com.btten.vincenttools.selectImgDialog;

import com.btten.hcbvip.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

@SuppressLint("NewApi")
public class SelectImgDialog extends Activity {

	private GridView mGridView;
	private SelectImgDialogGridAdapter mGridAdapter;
	private Button btSubmit, btCancel, btAlbum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_img_dialog);
		initView();

		Intent intentA = new Intent(SelectImgDialog.this,
				SelectDirListDialog.class);
		startActivityForResult(intentA, 1);
	}

	private void initView() {
		btSubmit = (Button) findViewById(R.id.selectimgdialog_button_submit);
		btSubmit.setOnClickListener(clickListener);
		btCancel = (Button) findViewById(R.id.selectimgdialog_button_cancel);
		btCancel.setOnClickListener(clickListener);
		btAlbum = (Button) findViewById(R.id.select_img_dialog_back);
		btAlbum.setOnClickListener(clickListener);
		mGridView = (GridView) findViewById(R.id.selectimgdialog_gridview);
		// mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
	}

	private void initData(Intent intent) {
		ImgDirectoryObject dir = (ImgDirectoryObject) intent.getExtras().get(
				"KEY_DIR");
		mGridAdapter = new SelectImgDialogGridAdapter(this);
		mGridAdapter.setItem(dir.lstFile);
		mGridView.setAdapter(mGridAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((SelectImgDialogGridItem) view).setChecked(mGridAdapter
						.setCheced(position));
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { // resultCode为回传的标记
		case 1:
//			Bundle b = data.getExtras(); // data为B中回传的Intent
			initData(data);
			break;
		default:
			break;
		}
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = SelectImgDialog.this.getIntent();
			switch (v.getId()) {
			case R.id.selectimgdialog_button_submit:
				String[] imgList = mGridAdapter.getImgList();

				Bundle bundle = new Bundle();
				bundle.putStringArray("KEY_IMGLIST", imgList);
				intent.putExtras(bundle);

				SelectImgDialog.this.setResult(Activity.RESULT_OK, intent);
				finish();
				break;
			case R.id.selectimgdialog_button_cancel:
				finish();
				break;
			case R.id.select_img_dialog_back:
				Intent intentA = new Intent(SelectImgDialog.this,
						SelectDirListDialog.class);
				startActivityForResult(intentA, 1);
				break;
			default:
				break;
			}
		}
	};

}