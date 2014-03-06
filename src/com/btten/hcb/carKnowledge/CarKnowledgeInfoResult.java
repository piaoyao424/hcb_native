package com.btten.hcb.carKnowledge;

import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;

public class CarKnowledgeInfoResult extends BaseJsonItem {
	CarKnowledgeListItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			if (this.status == 1) {
				item = new CarKnowledgeListItem();
				item.title = result.getString("F2_4230");
				item.id = result.getString("F1_4230");
				item.image = result.getString("F4_4230");
				item.content = result.getString("F4_4230");
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
