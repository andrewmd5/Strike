package net.codeusa.strike;

import net.codeusa.strike.js.JSEngine;
import net.codeusa.strike.settings.Settings;
import net.codeusa.strike.utils.Utils;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StrikeActivity extends ActionBarActivity {

	public static StrikeActivity activity;
	private WebView globalView;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_strike);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new StrikeFragment()).commit();
		}

		activity = this;
		Utils.readSettings(getApplicationContext());
	}

	@SuppressLint("SetJavaScriptEnabled")
	public static void loadView(final WebView remoteView) {
		activity.globalView = remoteView;

		if (Build.VERSION.SDK_INT >= 19) {
			remoteView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		} else {
			remoteView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		remoteView.clearHistory();
		remoteView.clearFormData();
		remoteView.clearCache(true);
		remoteView.getSettings().setAppCacheEnabled(true);
		remoteView.getSettings().setDatabaseEnabled(true);
		remoteView.setWebChromeClient(new WebChromeClient());
		remoteView.getSettings().setDomStorageEnabled(true);

		remoteView.getSettings().setJavaScriptEnabled(true);
		remoteView.getSettings().setUserAgentString("StrikeDroid/4");
	//	remoteView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		remoteView.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(final View v) {
				return true;
			}
		});

		remoteView.setWebViewClient(new WebViewClient() {
			@Override
			public void onLoadResource(final WebView view, final String url) {

				// Hide the webview while loading
				remoteView.setEnabled(false);

			}

			@Override
			public void onPageFinished(final WebView view, final String url) {

				remoteView.setEnabled(true);

			}

			@Override
			public void onReceivedError(final WebView view,
					final int errorCode, final String description,
					final String failingUrl) {

				final String html = "<html><body bgcolor=\"#09343E\"><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
						+ "<tr>"
						+ "<td><div align=\"center\"><font color=\"white\" size=\"20pt\">Error Loading, Please Try Again.</font></div></td>"
						+ "</tr>" + "</table><html><body>";

				final String base64 = android.util.Base64.encodeToString(
						html.getBytes(), android.util.Base64.DEFAULT);
				view.loadData(base64, "text/html; charset=utf-8", "base64");
			}

			@Override
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {
				if (url.contains(Settings.getMPCServer())
						|| url.contains(Settings.getTorrentClient())) {
					view.loadUrl(url);
					return true;
				} else if (url.contains("andrew.im")) {

					view.getContext().startActivity(
							new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					return true;
				} else if (url.contains("github")) {
					view.getContext().startActivity(
							new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					return true;

				} else if (url.contains("paypal")) {
					view.getContext().startActivity(
							new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					return true;
				} else if (url.contains("facebook")) {

					return false;
				}
				return false;

			}

		});

		// The URL that webview is loading
		final JSEngine jsEngine = new JSEngine();
		remoteView.addJavascriptInterface(jsEngine, "strike");
		remoteView.getSettings().setDomStorageEnabled(true);
		remoteView.loadUrl("file:///android_asset/landing.html");

	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.strike, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		final int id = item.getItemId();
		if (id == R.id.action_donate) {
			this.globalView
					.getContext()
					.startActivity(
							new Intent(
									Intent.ACTION_VIEW,
									Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JJQGYGF7EW56U")));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class StrikeFragment extends Fragment {

		public StrikeFragment() {
		}

		@Override
		public View onCreateView(final LayoutInflater inflater,
				final ViewGroup container, final Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_strike,
					container, false);

			if (savedInstanceState != null) {
				((WebView) rootView.findViewById(R.id.webview))
				.restoreState(savedInstanceState);
			}

			final WebView webView = (WebView) rootView
					.findViewById(R.id.webview);
			loadView(webView);
			return rootView;
		}
	}
}
