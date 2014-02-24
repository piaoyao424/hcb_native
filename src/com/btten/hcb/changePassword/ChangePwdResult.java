package com.btten.hcb.changePassword;

import org.json.JSONObject;

import com.btten.hcb.HcbAPP;
import com.btten.hcb.login.LoginItem;
import com.btten.model.BaseJsonItem;
import com.btten.tools.Log;

public class ChangePwdResult extends BaseJsonItem {
	private static String TAG = "ChangePwdResult";

	public LoginItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		item = new LoginItem();
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
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
