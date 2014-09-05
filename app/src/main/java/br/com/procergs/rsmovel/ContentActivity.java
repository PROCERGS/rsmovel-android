package br.com.procergs.rsmovel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import br.gov.rs.procergs.rsmovel.R;

@SuppressLint("SetJavaScriptEnabled")
public class ContentActivity extends Activity {

	WebView wv = null;
	String url = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);

		setContentView(R.layout.activity_content);
		final Activity MyActivity = this;

		getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

		Intent intent = getIntent();

		Bundle extras = intent.getExtras();
		url = (String) extras.get("url");

		final String section = (String) extras.get("item");
		setTitle(section);

		wv = (WebView) findViewById(R.id.wv_content);
		wv.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int progress) {
				MyActivity.setTitle("Carregando... " + section);
				MyActivity.setProgress(progress);

				if (progress == 100)
					MyActivity.setTitle(section);
			}
		});
		
		//wv.setWebChromeClient(new MyWebChromeClient(wv, url));
		wv.setWebViewClient(new MyWebViewClient());
		wv.getSettings().setJavaScriptEnabled(true);

		CookieSyncManager.createInstance(wv.getContext());
		CookieSyncManager.getInstance().startSync();

		wv.loadUrl(url);
	}

	@Override
	protected void onResume() {
		super.onResume();
		CookieSyncManager.getInstance().stopSync();
	}

	@Override
	protected void onPause() {
		super.onPause();
		CookieSyncManager.getInstance().sync();
	}

	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			if (url.startsWith("tel:")) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
				startActivity(intent);
			} else if (url.startsWith("http:") || url.startsWith("https:")) {
				if (url.endsWith(".pdf")) {
					Log.d("ITEM", url);
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
				}else{
					view.loadUrl(url);
				}
			}else{
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);
			}

			return true;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
			wv.goBack();
			return super.onKeyDown(keyCode, event);
		} else {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_close:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
