package com.btten.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.btten.msgcenter.MsgCenter;
import com.btten.tools.Log;
import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.analytics.MobclickAgent;

public class HcbAPP extends Application {
	static HcbAPP mainApp;
	static BMapManager mMapManager = null;

	public HcbAPP() {
		mainApp = this;
		MsgCenter.getInstance();
		Log.setOutputPath(Log.SDCARD_LOG_PATH, Log.SYS_LOG, 0);
	}

	public void init() {
		try {
			// 初始化地图
			mMapManager = new BMapManager(this);
			System.out.println("创建管理器完毕");
			// mMapManager.init("7a9f319132726cc4a02b7089636374bc",
			// new myMKGeneralListener());
			mMapManager.init("DmFGGSGzxVcVse0wRjrmSC4n",
					new myMKGeneralListener());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private class myMKGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(mainApp.getBaseContext(), "网络不通", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onGetPermissionState(int arg0) {
			// TODO Auto-generated method stub
			String str = " ";
			if (arg0 == 300) {
				str = "无法建立与百度地图服务端的连接!";
			}
			if (arg0 == 200) {
				str = "百度地图服务端数据错误，无法解析认证服务端返回数据!";
			}
			Toast.makeText(mainApp.getBaseContext(), "授权异常" + str,
					Toast.LENGTH_SHORT).show();
		}
	}

	public static HcbAPP getInstance() {
		return mainApp;
	}

	public int GetVersionCode() {
		String pName = "com.btten.hcb";
		int versionCode = 0;
		try {
			PackageInfo pinfo = this.getPackageManager().getPackageInfo(pName,
					PackageManager.GET_CONFIGURATIONS);
			versionCode = pinfo.versionCode;
		} catch (Exception e) {
		}
		return versionCode;
	}

	public String GetVersion() {
		String pName = "com.btten.hcb";
		String versionName = null;
		try {
			PackageInfo pinfo = this.getPackageManager().getPackageInfo(pName,
					PackageManager.GET_CONFIGURATIONS);
			versionName = pinfo.versionName;
		} catch (Exception e) {
		}
		return versionName;
	}

	public String GetVersionStr() {
		return "" + GetVersionCode();
	}

	public String GetAnonymousName() {
		return "未命名";
	}

	public int GetDpType() {
		Activity top = BaseActivity.GetTopActivity();
		if (top == null)
			return 1;

		DisplayMetrics dm = new DisplayMetrics();
		top.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		screenWidth /= 3;
		if (screenWidth > 200)
			return 2;
		if (screenWidth > 150)
			return 1;
		return 0;
	}

	// 上报错误
	public static void ReportError(String error) {
		Activity topactivity = BaseActivity.GetTopActivity();
		if (topactivity == null)
			return;
		MobclickAgent.reportError(topactivity, error);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "hcb/Cache");

		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPoolSize(4)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// .memoryCacheSize(20000000) // 20 Mb
				.memoryCache(new FIFOLimitedMemoryCache(5 * 1024 * 1024))
				.discCache(
						new TotalSizeLimitedDiscCache(cacheDir,
								new Md5FileNameGenerator(), 20 * 1024 * 1024))
				// 能缓�?0M文件
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator());

		if (Log.IsDebug()) {
			builder.enableLogging();
		}

		ImageLoaderConfiguration config = builder.build();
		ImageLoader.getInstance().init(config);

	}

	private class RepairInfo {
		Date startTime;
		Date endTime;
		String info;

		public RepairInfo() {

		}

		/*
		 * <repair><starttime>2012-01-09 02:03:04</starttime> endtime>2012-01-09
		 * 02:03:04</endtime> <info>维修信息</info> </repair>
		 */
		public Boolean Parase(String repairInfo) {
			final Map<String, String> values = com.btten.tools.Util.parseXml(
					repairInfo, "repair", null);
			if (values == null)
				return false;
			String strStartTime = values.get(".repair.starttime");
			String strEndTime = values.get(".repair.endtime");
			info = values.get(".repair.info");
			if (info == null || info.length() <= 0)
				return false;

			startTime = getTime(strStartTime);
			endTime = getTime(strEndTime);

			if (startTime == null || endTime == null)
				return false;
			return true;
		}

		// 是否在维�?
		public Boolean IsInTime() {
			Date now = new Date();
			if (startTime == null || endTime == null)
				return false;

			if (now.getTime() >= startTime.getTime()
					&& now.getTime() <= endTime.getTime())
				return true;
			return false;
		}

		@SuppressLint("SimpleDateFormat")
		private Date getTime(String str) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = format.parse(str);
			} catch (Exception e) {
				return null;
			}
			return date;
		}
	}

	public static String GetSystemVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

}
