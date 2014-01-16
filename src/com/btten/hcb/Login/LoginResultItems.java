package com.btten.hcb.Login;

import org.json.JSONObject;


import com.btten.base.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class LoginResultItems extends BaseJsonItem{
	private static String TAG="LoginResultItems";
	
	public LoginResultItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		LoginResultItems items=this;
		item = new LoginResultItem();
		try {
			items.status = result.getInt("STATUS");
			items.info = result.getString("INFO");
			// 有数据
			if (items.status == 1) {
				CommonConvert convert=new CommonConvert(result);
				item.username = convert.getString("USERNAME");
				item.userid = convert.getString("USERID");
				item.phone = convert.getString("PHONE");
				item.status = convert.getInt("STATUS");
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