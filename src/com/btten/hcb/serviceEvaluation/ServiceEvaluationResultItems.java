package com.btten.hcb.serviceEvaluation;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;

import com.btten.base.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class ServiceEvaluationResultItems extends BaseJsonItem {
	private static String TAG = "LoginResultItems";
	public String jmsName;
	public float star;
	public ServiceEvaluationResultItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.star = result.getInt("STAR");
			this.jmsName = result.getString("JMSNAME");

			// 有数据
			if (status == 1 && !result.isNull("DATA")) {

				this.status = result.getInt("STATUS");
				this.info = result.getString("INFO");

				JSONArray jsonArray = result.getJSONArray("DATA");
				int lenth = jsonArray.length();
				items = new ServiceEvaluationResultItem[lenth];

				for (int i = 0; i < lenth; ++i) {
					ServiceEvaluationResultItem temp = new ServiceEvaluationResultItem();
					CommonConvert convert = new CommonConvert(
							jsonArray.getJSONObject(i));

					temp.VipName = convert.getString("VIPNAME");
					temp.content = convert.getString("CONTENT");
					temp.star = convert.getInt("STAR");
					temp.status = convert.getInt("STATUS");
					items[i] = temp;
				}
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
