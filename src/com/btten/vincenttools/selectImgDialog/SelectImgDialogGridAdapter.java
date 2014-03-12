package com.btten.vincenttools.selectImgDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.AbsListView.LayoutParams;

public class SelectImgDialogGridAdapter extends BaseAdapter {
	private List<String> list;
	private Context mContext;
	private Map<Integer, Boolean> checkedImg = new HashMap<Integer, Boolean>();

	public SelectImgDialogGridAdapter(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	// 返回选中的图片路径
	@SuppressLint("NewApi")
	public String[] getImgList() {
		if (checkedImg.size() < 1) {
			return null;
		}
		List<String> Imglist = new ArrayList<String>();

		for (int i = 0; i < checkedImg.size(); i++) {
			if (checkedImg.get(i)) {
				Imglist.add(list.get(i));
			}
		}
		return Imglist.toArray(new String[0]);
	}

	public void setItem(List<String> list) {
		this.list = list;
		for (int i = 0; i < list.size(); i++) {
			checkedImg.put(i, false);
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SelectImgDialogGridItem item;
		if (convertView == null) {
			item = new SelectImgDialogGridItem(mContext);
			item.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		} else {
			item = (SelectImgDialogGridItem) convertView;
		}

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(list.get(position), options); // 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 200);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		bitmap = BitmapFactory.decodeFile(list.get(position), options);
		item.setImg(bitmap);

		item.setChecked(checkedImg.get(position) == null ? false : checkedImg
				.get(position));

		return item;
	}

	public boolean setCheced(int position) {
		checkedImg.put(position, !checkedImg.get(position));
		return checkedImg.get(position);

	}
}
