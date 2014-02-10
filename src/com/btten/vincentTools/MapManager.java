package com.btten.vincentTools;

import java.util.ArrayList;
import java.util.List;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKOfflineMap;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.btten.hcb.HcbAPP;
import com.btten.hcbvip.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.Toast;

public class MapManager {
	private BMapManager mMapManager = null;
	private MapController mMapController = null;
	private Activity active = null;
	// 缓存的坐标信息
	private Context context = null;
	private MapView mapview = null;
	// 显示当前位置
	private MyLocationOverlay myLocationOverlay = null;
	private LocationData locData = null;
	// 搜索成员
	private MKSearch mMKSearch = null;
	public BDLocation userGps = null;
	private String[] jmsinfo_split_str = new String[255];
	// 标记加盟商初始化是否成功
	public boolean isOK = false;
	// 离线地图
	MKOfflineMap mOffline = null;

	/** Called when the activity is first created. */
	public MapManager(final Context context) {
		this.context = context;
		try {
			mMapManager = HcbAPP.getBMapManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BMapManager initMap(MapView mMapView, BDLocation blocation) {
		try {
			this.mapview = mMapView;

			locData = new LocationData();
			locData.latitude = blocation.getLatitude();
			locData.longitude = blocation.getLongitude();

			userGps = blocation;
			// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
			mMapController = mapview.getController();
			// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
			// GeoPoint point = new GeoPoint((int) (30.499086 * 1E6),
			// (int) (114.347261 * 1E6));
			GeoPoint point = new GeoPoint(
					(int) (blocation.getLatitude() * 1E6),
					(int) (blocation.getLongitude() * 1E6));
			// 设置地图中心点
			mMapController.setCenter(point);
			// 设置地图zoom级别
			mMapController.setZoom(14);
			// 路线规划
			mMKSearch = new MKSearch();
			mMKSearch.init(mMapManager, new MySearchListener());

/*	百度太恶心了，只能下载单独格式的离线包，不能共用百度地图的离线包，
 * 	为了减小APK的大小，放弃离线功能		
 * // 写在onCreate函数里
			mOffline = new MKOfflineMap();
			// offline 实始化方法用更改。
			mOffline.init(mMapController, new MKOfflineMapListener() {
				@Override
				public void onGetOfflineMapState(int type, int state) {
					switch (type) {
					case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
						MKOLUpdateElement update = mOffline
								.getUpdateInfo(state);
					}
						break;
					case MKOfflineMap.TYPE_NEW_OFFLINE:
						Log.d("OfflineDemo",
								String.format("add offlinemap num:%d", state));
						break;
					case MKOfflineMap.TYPE_VER_UPDATE:
						Log.d("OfflineDemo",
								String.format("new offlinemap ver"));
						break;
					}
				}
			});
			int num = mOffline.scan();
			System.out.println(String.format("已安装%d个离线包", num));
*/
			return mMapManager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void showlocation(BDLocation location) {
		// 请使用百度定位SDK获取位置信息，要在SDK中显示一个位置，需要使用百度经纬度坐标（bd09ll）
		myLocationOverlay = new MyLocationOverlay(mapview);
		if (location != null) {
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			locData.direction = 2.0f;
			myLocationOverlay.setData(locData);
			mapview.getOverlays().add(myLocationOverlay);
			mapview.refresh();
			mapview.getController().animateTo(
					new GeoPoint((int) (locData.latitude * 1e6),
							(int) (locData.longitude * 1e6)));
		}
	}

	public boolean initJMS(String str[], Activity active) {
		isOK = false;
		this.active = active;
		this.jmsinfo_split_str = str;
		int jms_count = jmsinfo_split_str.length / 4;

		Drawable mark = context.getResources().getDrawable(R.drawable.map_ico);
		try {
			// 一个加盟商就显示导航
			if (jms_count == 1) {
				final LocationData location = new LocationData();
				location.latitude = Float.valueOf(jmsinfo_split_str[3]);
				location.longitude = Float.valueOf(jmsinfo_split_str[2]);

				// 延迟加载导航信息,直接加载可能导致地图初始化未完成，导航请求已经发出，无法收到导航信息
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						getDriverRoute(location);
						System.out.println("线程跑起来了");
					};
				}, 1000);

			} else if (jms_count > 1) {
				// 将IteminizedOverlay添加到MapView中
				mapview.getOverlays().clear();
				OverlayTest itemOverlay = new OverlayTest(mark, mapview);
				mapview.getOverlays().add(itemOverlay);

				List<OverlayItem> listmap = new ArrayList<OverlayItem>();
				for (int i = 0; i < jms_count; i++) {
					int y = i * 4;
					GeoPoint p1 = new GeoPoint(
							(int) (Float.valueOf(jmsinfo_split_str[y + 3]) * 1E6),
							(int) (Float.valueOf(jmsinfo_split_str[y + 2]) * 1E6));
					OverlayItem item = new OverlayItem(p1,
							jmsinfo_split_str[y], jmsinfo_split_str[y + 1]);
					listmap.add(item);
				}
				itemOverlay.addItem(listmap);
				isOK = true;
			}
			showlocation(userGps);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return isOK;
	}

	class OverlayTest extends ItemizedOverlay<OverlayItem> {
		// 用MapView构造ItemizedOverlay
		public OverlayTest(Drawable mark, MapView mapView) {
			super(mark, mapView);
		}

		protected boolean onTap(int index) {
			// 在此处理item点击事件
//			System.out.println("item onTap: " + index);
//			if (myLocation != null) {
//				int i = myLocation.getLocationClient().requestLocation();
//				switch (i) {
//				case 0:
//					System.out.println("正常");
//					break;
//				case 1:
//					System.out.println("服务未启动");
//					break;
//				case 2:
//					System.out.println("没有监听函数");
//					break;
//				case 6:
//					System.out.println("请求间隔过短。 前后两次请求定位时间间隔不能小于1000ms");
//					break;
//				default:
//					break;
//				}
			//pop demo
//			//创建pop对象，注册点击事件监听接口
//			PopupOverlay pop = new PopupOverlay(mMapView,new PopupClickListener() {                
//			        @Override
//			        public void onClickedPopup(int index) {
//			                //在此处理pop点击事件，index为点击区域索引,点击区域最多可有三个
//			        }
//			});
//			/**  准备pop弹窗资源，根据实际情况更改
//			 *  弹出包含三张图片的窗口，可以传入三张图片、两张图片、一张图片。
//			 *  弹出的窗口，会根据图片的传入顺序，组合成一张图片显示.
//			 *  点击到不同的图片上时，回调函数会返回当前点击到的图片索引index
//			 */
//			Bitmap[] bmps = new Bitmap[3];
//			try {
//			bmps[0] = BitmapFactory.decodeStream(getAssets().open("marker1.png"));
//			     bmps[1] = BitmapFactory.decodeStream(getAssets().open("marker2.png"));
//			bmps[2] = BitmapFactory.decodeStream(getAssets().open("marker3.png"));
//			} catch (IOException e) {
//			         e.printStackTrace();
//			}
//			//弹窗弹出位置
//			GeoPoint ptTAM = new GeoPoint((int)(39.915 * 1E6), (int) (116.404 * 1E6));
//			//弹出pop,隐藏pop
//			pop.showPopup(bmps, ptTAM, 32);
//			//隐藏弹窗
//			//  pop.hidePop();
			return true;
		}

		public boolean onTap(GeoPoint pt, MapView mapView) {
			// 在此处理MapView的点击事件，当返回 true时
			super.onTap(pt, mapView);
			return false;
		}
	}

	public class MySearchListener implements MKSearchListener {
		@Override
		public void onGetAddrResult(MKAddrInfo result, int iError) {
			// 返回地址信息搜索结果
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result,
				int iError) {
			// 返回驾乘路线搜索结果
			if (iError != 0 || result == null) {
				Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
				return;
			}
			RouteOverlay routeOverlay = new RouteOverlay(active, mapview);
			// 此处仅展示一个方案
			routeOverlay.setData(result.getPlan(0).getRoute(0));
			mapview.getOverlays().add(routeOverlay);
			mapview.refresh();
			mapview.getController().animateTo(result.getStart().pt);
			System.out.println("收到导航信息");
			isOK = true;
		}

		@Override
		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
			// 返回poi搜索结果
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result,
				int iError) {
			// 返回公交搜索结果
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result,
				int iError) {
			// 返回步行路线搜索结果
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			// 返回公交车详情信息搜索结果
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// 返回poi相信信息搜索的结果

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// 返回联想词信息搜索结果

		}
	}

	public void getDriverRoute(LocationData location2) {

		if (active == null || location2 == null) {
			return;
		}
		MKPlanNode start = new MKPlanNode();
		start.pt = new GeoPoint((int) (locData.latitude * 1E6),
				(int) (locData.longitude * 1E6));
		MKPlanNode end = new MKPlanNode();
		end.pt = new GeoPoint((int) (location2.latitude * 1E6),
				(int) (location2.longitude * 1E6));
		// 设置驾车路线搜索策略，距离最短
		mMKSearch.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);
		int i = mMKSearch.drivingSearch(null, start, null, end);
		if (i == -1) {
			System.out.println(locData.latitude + "," + locData.longitude
					+ "请求导航失败!");
		} else if (i == 0) {
			System.out.println(locData.latitude + "," + locData.longitude
					+ "请求导航成功!");
		}
	}

	public void Pause() {
		if (mMapManager != null) {
			mMapManager.stop();
		}
	}

	public void Resume() {
		if (mMapManager != null) {
			mMapManager.start();
		}
	}
}