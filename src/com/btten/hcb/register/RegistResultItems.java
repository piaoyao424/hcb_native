package com.btten.hcb.register;

import org.json.JSONObject;

import com.btten.hcb.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class RegistResultItems extends BaseJsonItem{
	private static String TAG="RegistResultItems";
	
	public RegistResultItem item;
	
	@Override
	public boolean CreateFromJson(JSONObject result) {
		RegistResultItems items = this;
		item = new RegistResultItem();
		try {
			items.status = result.getInt("status");
			items.info = result.getString("info");
			// 有数据
			if (items.status == 1) {
				CommonConvert convert=new CommonConvert(result);
				item.username = result.getString("username");
				item.userid = result.getString("userid");
				item.status=convert.getInt("status");
			}
		} catch (Exception e) {
			items.status=-1;
			items.info=e.toString();
			HcbAPP.getInstance().ReportError(TAG+"error:\n"+e.toString()+"\nresult:\n"+result.toString());
			Log.Exception(TAG, e);
			return false;
		}
		return true;
	}

}
