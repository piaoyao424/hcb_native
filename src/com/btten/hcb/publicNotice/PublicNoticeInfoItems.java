package com.btten.hcb.publicNotice;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class PublicNoticeInfoItems extends BaseJsonItem {
	private static String TAG = "MyGGContentItemsItems";
	private JSONArray jsonArray = null;
	PublicNoticeInfoItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		
		item = new PublicNoticeInfoItem();
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			
			if (this.status == 1) {
				CommonConvert convert = new CommonConvert(result);
				item.content = convert.getString("F3_4230");
			}
		} catch (Exception ex) {
			this.status = -1;
			this.info = ex.toString();
			Log.d("gwjtag", info);
			return false;
		}
		return true;
	}

}
