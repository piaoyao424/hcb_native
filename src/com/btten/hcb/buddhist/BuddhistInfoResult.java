package com.btten.hcb.buddhist;

import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class BuddhistInfoResult extends BaseJsonItem {
	BuddhistListItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			if (this.status == 1) {
				CommonConvert convert = new CommonConvert(result);
				item = new BuddhistListItem();
				item.content = convert.getString("F3_4230");
				item.date = convert.getString("F4_4230");
				item.title = convert.getString("F2_4230");
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
