package com.btten.vincenttools.selectImgDialog;

import com.btten.hcbvip.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SelectImgDialogGridItem extends RelativeLayout {

	private Context mContext;
	private ImageView mImgView = null;
	private ImageView mSelcetView = null;

	public SelectImgDialogGridItem(Context context) {
		this(context, null, 0);
	}

	public SelectImgDialogGridItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SelectImgDialogGridItem(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		LayoutInflater.from(mContext).inflate(
				R.layout.select_img_dialog_grid_item, this);
		mImgView = (ImageView) findViewById(R.id.myopendialog_img);
		mSelcetView = (ImageView) findViewById(R.id.select);
	}

	@SuppressLint("NewApi")
	public void setChecked(boolean checked) {
		mSelcetView.setVisibility(checked ? View.VISIBLE : View.GONE);
	}

	@SuppressLint("NewApi")
	public void setImg(Bitmap bitmap) {
		if (mImgView != null) {
			mImgView.setImageBitmap(bitmap);
		}
	}

}
