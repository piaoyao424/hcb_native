package com.btten.vincenttools.selectImgDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.btten.hcbvip.R;
import com.btten.vincenttools.selectImgDialog.SelectImgDialogListAdapter.ViewHolder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SelectDirListDialog extends Activity {
	// 文件夹路径
	private List<ImgDirectoryObject> lstDirectory = new ArrayList<ImgDirectoryObject>();
	private String[] filterStr = { "cache", "Cache", "thumb", "fonts",
			"Tencent" };
	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_dir_dialog);
		initView();
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.selectdirdialog_img_list);
		// 遍历文件
		new Thread(new Runnable() {

			@Override
			public void run() {

				GetImgFiles(Environment.getExternalStorageDirectory()
						.getAbsolutePath());
				handler.sendEmptyMessage(0);
			}
		}).start();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				SelectImgDialogListAdapter adapter = new SelectImgDialogListAdapter(
						SelectDirListDialog.this);
				adapter.setItems(lstDirectory);
				mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						ViewHolder vHolder;
						vHolder = (ViewHolder) view.getTag();

						Intent intent = SelectDirListDialog.this.getIntent();
						intent.putExtra("KEY_DIR", vHolder.object);
						SelectDirListDialog.this.setResult(1, intent);
						SelectDirListDialog.this.finish();
					}
				});
				
				mListView.setAdapter(adapter);
				break;
			default:
				break;
			}

		};
	};

	// 遍历文件夹，查找文件
	private void GetImgFiles(String Path) // 搜索目录，扩展名，是否进入子文件夹
	{
		File[] files = new File(Path).listFiles();

		ImgDirectoryObject imgDirectory = new ImgDirectoryObject();
		int isImg = 0;
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isFile()) {
				isImg = 0;
				// 判断扩展名
				if (f.getPath()
						.substring(f.getPath().length() - ".jpg".length())
						.equals(".jpg")) {
					isImg = 1;
				} else if (f.getPath()
						.substring(f.getPath().length() - ".png".length())
						.equals(".png")) {
					isImg = 1;
				}
				// 图片文件
				if (isImg == 1) {
					imgDirectory.Path = Path;
					imgDirectory.fileCum += 1;
					imgDirectory.lstFile.add(f.getPath());
				}
			} else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) // 忽略点文件（隐藏文件/文件夹）
			{
				for (int j = 0; j < filterStr.length; j++) {

					// 找到类似字串
					if (f.getPath().indexOf(filterStr[j]) > -1) {
						break;
					}
					// 未找到类似字串
					if (j == filterStr.length - 1) {
						GetImgFiles(f.getPath());
					}
				}
			}
		}

		if (imgDirectory.fileCum > 0) {
			lstDirectory.add(imgDirectory);
		}
	}
}