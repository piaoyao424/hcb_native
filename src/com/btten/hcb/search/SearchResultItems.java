package com.btten.hcb.search;

import org.json.JSONArray;
import org.json.JSONObject;
import com.btten.base.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class SearchResultItems extends BaseJsonItem {
	private static String TAG = "LoginResultItems";
	// 0区域请求1商品列表2加盟商列表
	private int Flag = 0;
	public SearchResultItem_area[] areaItems = null;
	public SearchResultItem_saleslist[] saleslist = null;
	public SearchResultItem[] items = null;

	// 识别不同的请求类型
	public SearchResultItems(int Flag) {
		this.Flag = Flag;
	}

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			switch (Flag) {
			// 0区域请求
			case 0:
				this.status = result.getInt("STATUS");
				this.info = result.getString("INFO");

				// 有数据
				if (status == 1 && !result.isNull("DATA")) {
					JSONArray jsonArray = result.getJSONArray("DATA");
					int lenth = jsonArray.length();
					areaItems = new SearchResultItem_area[lenth];

					for (int i = 0; i < lenth; ++i) {
						SearchResultItem_area temp = new SearchResultItem_area();
						CommonConvert convert = new CommonConvert(
								jsonArray.getJSONObject(i));

						temp.areaID = convert.getString("QYID");
						temp.areaName = convert.getString("NAME");
						temp.areaUpID = convert.getString("UPID");
						 
						areaItems[i] = temp;
					}
				}
				break;
			// 1商品列表
			case 1:

				this.status = result.getInt("STATUS");
				this.info = result.getString("INFO");

				// 有数据
				if (status == 1 && !result.isNull("DATA")) {
					JSONArray jsonArray = result.getJSONArray("DATA");
					int lenth = jsonArray.length();
					saleslist = new SearchResultItem_saleslist[lenth];

					for (int i = 0; i < lenth; ++i) {
						SearchResultItem_saleslist temp = new SearchResultItem_saleslist();
						CommonConvert convert = new CommonConvert(
								jsonArray.getJSONObject(i));

						temp.itemID = convert.getString("ITEMID");
						temp.itemName = convert.getString("NAME");
						temp.itemUpID = convert.getString("UPID");
						temp.status = convert.getInt("STATUS");
						saleslist[i] = temp;
					}
				}
				break;
			// 2加盟商查询列表
			case 2:
				this.status = result.getInt("STATUS");
				this.info = result.getString("INFO");

				// 有数据
				if (status == 1 && !result.isNull("DATA")) {
					JSONArray jsonArray = result.getJSONArray("DATA");
					int lenth = jsonArray.length();
					items = new SearchResultItem[lenth];

					for (int i = 0; i < lenth; ++i) {
						SearchResultItem temp = new SearchResultItem();
						CommonConvert convert = new CommonConvert(
								jsonArray.getJSONObject(i));

						temp.jid = convert.getString("JID");
						temp.jname = convert.getString("JNAME");
						temp.newPrice = convert.getString("NEWPRICE");
						temp.oldPrice = convert.getString("OLDPRICE");
						temp.scope = convert.getString("SCOPE");
						temp.distance = Double.valueOf(convert.getString("DISTANCE"));
						temp.star = Integer.valueOf(convert.getString("STAR"));
						temp.status = convert.getInt("STATUS");
						
						items[i] = temp;
					}
				}
				break;
			default:
				throw new Exception("未识别的参数结构！");
			}

		} catch (Exception e) {
			this.status = -1;
			this.info = e.toString();
			HcbAPP.getInstance();
			HcbAPP.ReportError(TAG + "error:\n" + e.toString() + "\nresult:\n"
					+ result.toString());
			Log.Exception(TAG, e);
			return false;
		}
		return true;
	}

}
