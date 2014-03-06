package com.btten.hcb.homeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class TitleNoticeResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public TitleNoticeItem[] item = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1 && !result.isNull("DATA")) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				item = new TitleNoticeItem[length];
				for (int i = 0; i < length; i++) {
					
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					TitleNoticeItem temp = new TitleNoticeItem();
					
					temp.title = convert.getString("F2_4230");
					temp.id = convert.getString("F1_4230");
					item[i] = temp;
				}
			}
		} catch (Exception ex) {
			this.status = -1;
			this.info = ex.toString();
			Log.d("tag", info);
			return false;
		}
		return true;
	}

}
