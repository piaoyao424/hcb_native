package com.btten.hcb.vehicleLife;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;

public class VehicleLifeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_life_activity);
		initView();
	}

	public void initView() {
		setCurrentTitle("人车生活");
		setBackKeyListner(true);
		GridView gridView = (GridView) findViewById(R.id.vehicle_life_gridview);

		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemText", "书推荐");
		lstImageItem.add(map);

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemText", "车知识");
		lstImageItem.add(map1);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ItemText", "车产品");
		lstImageItem.add(map2);

		SimpleAdapter sAdapter = new SimpleAdapter(this, lstImageItem,
				R.layout.vehicle_life_item, new String[] { "ItemText" },
				new int[] { R.id.vehicle_life_item_txt });
		gridView.setAdapter(sAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					Toast.makeText(VehicleLifeActivity.this, "0",
							Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(VehicleLifeActivity.this,  "1",
							Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(VehicleLifeActivity.this,  "2",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
