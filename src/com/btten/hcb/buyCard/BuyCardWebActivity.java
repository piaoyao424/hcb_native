package com.btten.hcb.buyCard;

import java.net.URLEncoder;
import com.btten.base.BaseActivity;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.hcbvip.R;
import com.btten.network.UrlFactory;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

@SuppressLint({ "SetJavaScriptEnabled", "SdCardPath" })
public class BuyCardWebActivity extends BaseActivity {
	private WebView wv_shouye = null;
	private ProgressDialog pd_shouye_pd = null;
	private final static int UI_PDSHOW = 0;
	private final static int UI_PDHIDE = 1;
	private BuyCardItem item;
	private int itemNum;

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_card_web_activity);
		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		init();// 执行初始化函数
		// 能联网,载入网页
		if (info != null && info.isAvailable()) {

			String url = UrlFactory.javaRoot + "/Alipay/doSubmit?";
			url = url + "vid=" + VIPInfoManager.getInstance().getUserid();
			url = url + "&itemname=" + URLEncoder.encode(item.name);
			url = url + "&itemid=" + item.id;
			url = url + "&itemprice=" + item.price;
			url = url + "&itemvalue=" + item.value;
			url = url + "&itemnum=" + itemNum;
			System.out.println(url);
			loadWeburl(wv_shouye, url);
		} else {
			wv_shouye.loadUrl("file:///android_asset/content_error.html");
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case UI_PDSHOW:
					pd_shouye_pd.show();// 显示进度对话框
					break;
				case UI_PDHIDE:
					pd_shouye_pd.hide();
					// 隐藏进度对话框，不可使用dismiss()、cancel(),否则再次调用show()时，显示的对话框小圆圈不会动。
					break;
				}
			}
			super.handleMessage(msg);
		}
	};

	public void init() {// 初始化
		Bundle bundle = getIntent().getExtras();
		item = new BuyCardItem();
		item.id = bundle.getString("KEY_ID");
		item.name = bundle.getString("KEY_NAME");
		item.price = bundle.getDouble("KEY_PRICE");
		item.value = bundle.getDouble("KEY_VALUE");
		itemNum = bundle.getInt("KEY_NUM");

		iniWebview();
	}

	private void iniWebview() {
		wv_shouye = (WebView) findViewById(R.id.buy_card_webview);
		WebSettings webset = wv_shouye.getSettings();
		// 可用JS
		webset.setJavaScriptEnabled(true);
		// 设置js接口
		wv_shouye.addJavascriptInterface(new JsCommunication(this),
				"JsCommunication");
		CookieManager.getInstance().setAcceptCookie(true);

		wv_shouye.setWebViewClient(new WebViewClient() {
			// 重写点击动作,用webview载入
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				handler.sendEmptyMessage(UI_PDSHOW);
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				handler.sendEmptyMessage(UI_PDHIDE);
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				handler.sendEmptyMessage(UI_PDHIDE);

				// 检测网络状态
				ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = cManager.getActiveNetworkInfo();
				if (info != null && info.isAvailable()) {
					view.loadUrl("file:///android_asset/content_error.html");
					Alert(description);
				} else {
					// 不能联网
					Toast toast = Toast.makeText(getBaseContext(), "网络中断",
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					view.loadUrl("file:///android_asset/content_error.html");
				}
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});

		wv_shouye.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
				if (progress == 100) {
					handler.sendEmptyMessage(UI_PDHIDE);// 如果全部载入,隐藏进度对话框
				}
				super.onProgressChanged(view, progress);
			}

			@Override
			public void onExceededDatabaseQuota(String url,
					String databaseIdentifier, long quota,
					long estimatedDatabaseSize, long totalQuota,
					WebStorage.QuotaUpdater quotaUpdater) {
				quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
			}
		});

		pd_shouye_pd = new ProgressDialog(BuyCardWebActivity.this);
		pd_shouye_pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd_shouye_pd.setMessage("数据载入中，请稍候！");
	}

	public void loadWeburl(final WebView view, final String url) {
		new Thread() {
			public void run() {
				view.loadUrl(url);// 载入网页
			}
		}.start();
	}

	// 软件隐藏到后台
	public void Backward() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		startActivity(intent);
	}

	class JsCommunication {
		Context content;

		public JsCommunication(Context content) {
			this.content = content;
		}

		public void goback(int status) {
			switch (status) {
			case 0:
				// 支付成功
				BacktoActivity("支付成功！", 1);
				break;
			case 1:
				// 支付失败
				BacktoActivity("支付失败！", 0);
				finish();
				break;
			default:
				break;
			}
		}

	}

	// 回到前一个activity
	private void BacktoActivity(String info, int status) {
		Intent intent = BuyCardWebActivity.this.getIntent();
		intent.putExtra("KEY_INFO", info);
		intent.putExtra("KEY_STATUS", status);
		setResult(1, intent);
		finish();
	}

	@Override
	protected void onDestroy() {
		wv_shouye.destroy();
		super.onDestroy();
	}

	@Override
	public void initDate() {

	}
}