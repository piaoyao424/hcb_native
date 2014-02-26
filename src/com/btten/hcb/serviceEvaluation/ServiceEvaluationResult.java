package com.btten.hcb.serviceEvaluation;

import org.json.JSONArray;
import org.json.JSONObject;
import com.btten.hcb.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class ServiceEvaluationResult extends BaseJsonItem {
	private static String TAG = "LoginResult";
	public ServiceEvaluationItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			// 有数据
			if (status == 1 ) {

				JSONArray jsonArray = result.getJSONArray("DATA");
				int lenth = jsonArray.length();
				items = new ServiceEvaluationItem[lenth];

				for (int i = 0; i < lenth; ++i) {
					ServiceEvaluationItem temp = new ServiceEvaluationItem();
					CommonConvert convert = new CommonConvert(
							jsonArray.getJSONObject(i));

					temp.VipName = convert.getString("F1_4006");
					temp.content = convert.getString("F4_4223");
					temp.star = convert.getInt("F5_4223");
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
