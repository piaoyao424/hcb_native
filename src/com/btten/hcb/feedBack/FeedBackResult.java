package com.btten.hcb.feedBack;

import org.json.JSONObject;

import com.btten.hcb.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.Log;

public class FeedBackResult extends BaseJsonItem {
	private static String TAG = "MyConsumeResult";
	  
	@Override
	public boolean CreateFromJson(JSONObject result) {
		FeedBackResult items = this;
		try {
			items.status = result.getInt("STATUS");
			items.info = result.getString("INFO");
			
			 
		} catch (Exception e) {
			items.status = -1;
			items.info = e.toString();
			HcbAPP.getInstance();
			HcbAPP.ReportError(
					TAG + "error:\n" + e.toString() + "\nresult:\n"
							+ result.toString());
			Log.Exception(TAG, e);
			return false;
		}
		return true;
	}

}
