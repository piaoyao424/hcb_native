package com.btten.vincenttools.selectImgDialog;

import java.util.ArrayList;
import java.util.List;

import com.btten.hcbvip.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class SelectImgDialogListAdapter extends BaseAdapter {
	private List<ImgDirectoryObject> items;
	private Activity context;

	public SelectImgDialogListAdapter(Activity context) {
		this.context = context;
		items = new ArrayList<ImgDirectoryObject>();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public void setItems(List<ImgDirectoryObject> items) {
		this.items = items;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = context.getLayoutInflater().inflate(
					R.layout.select_img_dialog_list_item, null);
			holder.photo = (ImageView) convertView
					.findViewById(R.id.select_img_dialog_list_image);
			holder.title = (TextView) convertView
					.findViewById(R.id.select_img_dialog_list_title);
			holder.num = (TextView) convertView
					.findViewById(R.id.select_img_dialog_list_num);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ImgDirectoryObject item = items.get(position);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(item.lstFile.get(0), options); // 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 200);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		bitmap = BitmapFactory.decodeFile(item.lstFile.get(0), options);
		holder.photo.setImageBitmap(bitmap);

		holder.title
				.setText(item.Path.substring(item.Path.lastIndexOf("/") + 1));
		holder.num.setText("(" + item.fileCum + ")");
		holder.object = item;

		return convertView;
	}

	public class ViewHolder {
		ImgDirectoryObject object;
		ImageView photo;
		TextView title;
		TextView num;
	}
}