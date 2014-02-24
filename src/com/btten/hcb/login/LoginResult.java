package com.btten.hcb.login;

import org.json.JSONObject;


import com.btten.hcb.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class LoginResult extends BaseJsonItem{
	private static String TAG="LoginResult";
	
	public LoginItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		item = new LoginItem();
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			// 有数据
			if (this.status == 1) {
				CommonConvert convert=new CommonConvert(result);
				item.userid = convert.getString("ID");
				item.status = convert.getInt("STATUS");
			}
		} catch (Exception e) {
			this.status=-1;
			this.info=e.toString();
			HcbAPP.getInstance();
			HcbAPP.ReportError(TAG+"error:\n"+e.toString()+"\nresult:\n"+result.toString());
			Log.Exception(TAG, e);
			return false;
		}
		return true;
	}

}
