package com.btten.hcb.changePassword;

import org.json.JSONObject;


import com.btten.hcb.HcbAPP;
import com.btten.hcb.login.LoginResultItem;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class ChangePwdResultItems extends BaseJsonItem{
	private static String TAG="ChangePwdResultItems";
	
	public LoginResultItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		ChangePwdResultItems items=this;
		item = new LoginResultItem();
		try {
			items.status = result.getInt("STATUS");
			items.info = result.getString("INFO");
			// 返回值为真。
			if (items.status == 1) {
				CommonConvert convert=new CommonConvert(result);
				item.username = convert.getString("USERNAME");
				item.userid = convert.getString("USERID");
				item.status=convert.getInt("STATUS");
			}
		} catch (Exception e) {
			items.status=-1;
			items.info=e.toString();
			HcbAPP.getInstance();
			HcbAPP.ReportError(TAG+"error:\n"+e.toString()+"\nresult:\n"+result.toString());
			Log.Exception(TAG, e);
			return false;
		}
		return true;
	}

}