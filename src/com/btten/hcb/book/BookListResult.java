package com.btten.hcb.book;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.network.UrlFactory;
import com.btten.tools.CommonConvert;

public class BookListResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public BookListItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				items = new BookListItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					BookListItem temp = new BookListItem();
					temp.title = "《" + convert.getString("F1_4416") + "》";
					temp.id = convert.getString("AID");
					temp.image = UrlFactory.rootUrl_short
							+ convert.getString("F2_4416");
					temp.author = "作者：" + convert.getString("F4_4416");
					temp.synopsis = convert.getString("F6_4416");
					items[i] = temp;
				}
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
