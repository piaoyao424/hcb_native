package com.btten.vincenttools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

public class CallTelephone {
	private Context context;
	private String telephone;

	public CallTelephone(final Context context, final String telephone) {
		this.context = context;
		this.telephone = telephone;
	}

	public void call() {
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				new AlertDialog.Builder(context)
						.setTitle("提示")
						.setMessage("拨打电话给 " + telephone)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										context.startActivity(new Intent(
												Intent.ACTION_CALL, Uri
														.parse("tel:"
																+ telephone)));
										dialog.dismiss();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								}).show();
			}
		});
	}
}
