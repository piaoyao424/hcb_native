package com.btten.hcb.book;

import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;

public class BookInfoResult extends BaseJsonItem {
	BookListItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			if (this.status == 1) {
				item = new BookListItem();
				item.title = result.getString("F2_4230");
				item.id = result.getString("F1_4230");
				item.image = result.getString("F4_4230");
				item.author = result.getString("F4_4230");
				item.date = result.getString("F4_4230");
				item.publisher = result.getString("F4_4230");
				item.price = result.getString("F4_4230");
				item.excerpt = result.getString("F4_4230");
				item.synopsis = result.getString("F4_4230");
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
