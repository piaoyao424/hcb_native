package com.btten.hcb.vehicleKnowledge;

import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.network.UrlFactory;

public class CarKnowledgeInfoResult extends BaseJsonItem {
	CarKnowledgeListItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			if (this.status == 1) {
				item = new CarKnowledgeListItem();
				item.title = result.getString("F1_4416");
				item.id = result.getString("AID");
				item.image = UrlFactory.rootUrl_short
						+ result.getString("F2_4416");
				item.content = result.getString("F7_4416");
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
