package com.btten.hcb.account;

import org.json.JSONObject;

import com.btten.hcb.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.Log;

public class MyAccountResult extends BaseJsonItem {
	private static String TAG = "MyConsumeResult";
	public MyAccountItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			item = new MyAccountItem();
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				item.level = result.getString("LEVEL");
				item.hbValue = result.getString("HBVALUE");
				item.name = result.getString("NAME");
				item.pointValue = result.getString("POINTVALUE");
			}

		} catch (Exception e) {
			this.status = -1;
			this.info = e.toString();
			HcbAPP.getInstance();
			HcbAPP.ReportError(TAG + "error:\n" + e.toString() + "\nresult:\n"
					+ result.toString());
			Log.Exception(TAG, e);
			return false;
		}
		return true;
	}

}
