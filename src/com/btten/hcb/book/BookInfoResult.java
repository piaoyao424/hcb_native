package com.btten.hcb.book;

import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.network.UrlFactory;

public class BookInfoResult extends BaseJsonItem {
	BookListItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			item = new BookListItem();
			item.title = result.getString("F1_4416");
			item.id = result.getString("AID");
			item.image = UrlFactory.rootUrl_short + result.getString("F2_4416");
			item.author = result.getString("F7_4416");

		} catch (Exception ex) {
			this.status = -1;
			this.info = ex.toString();
			Log.d("gwjtag", info);
			return false;
		}
		return true;
	}

}
