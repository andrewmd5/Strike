package net.codeusa.strike;

import net.codeusa.strike.js.JSEngine;
import net.codeusa.strike.services.NotficationService;
import net.codeusa.strike.settings.Settings;
import net.codeusa.strike.utils.Utils;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class StrikeActivity extends ActionBarActivity {

	public static StrikeActivity activity;
	public WebView globalView;
	NotficationService notification;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		/*
		 * requestWindowFeature(Window.FEATURE_ACTION_BAR |
		 * Window.FEATURE_ACTION_MODE_OVERLAY);
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_strike);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new StrikeFragment()).commit();
			getSupportActionBar().hide();
		} else {
			getSupportActionBar().hide();
			// ((WebView)findViewById(R.id.webview)).restoreState(savedInstanceState);
		}

		activity = this;

		Utils.readSettings(getApplicationContext());

		this.notification = new NotficationService();

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
		// remoteView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
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
				final Context context = view.getContext();
				final CharSequence text = description + " \\ " + errorCode
						+ " \\ " + failingUrl;
				final AlertDialog.Builder alert = new AlertDialog.Builder(
						context);
				// https://github.com/Codeusa/Strike/issues/new
				alert.setTitle("Report this on Github");
				alert.setMessage(text);

				// Set an EditText view to get user input
				final EditText input = new EditText(context);
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int whichButton) {
								view.getContext()
										.startActivity(
												new Intent(
														Intent.ACTION_VIEW,
														Uri.parse("https://github.com/Codeusa/Strike/issues/new")));
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int whichButton) {
								// Canceled.
							}
						});

				alert.show();
				view.loadUrl("file:///android_asset/landing.html");
			}

			@Override
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {

				if (url.contains(Settings.getMPCServer())
						|| url.contains(Settings.getTorrentClient())) {
					view.loadUrl(url);
					return false;
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

				Settings.setURL(url);
				return false;

			}

		});

		// The URL that webview is loading
		final JSEngine jsEngine = new JSEngine();
		remoteView.addJavascriptInterface(jsEngine, "strike");
		remoteView.getSettings().setDomStorageEnabled(true);
		remoteView
				.loadUrl(Settings.getLastURL() == null ? "file:///android_asset/landing.html"
						: Settings.getLastURL());

	}

	@Override
	public void onBackPressed() {
		notification.setStopNotfications(true);
		 NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		 mNotificationManager.cancelAll();
		android.os.Process.killProcess(android.os.Process.myPid());
		return;
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

			final WebView webView = (WebView) rootView
					.findViewById(R.id.webview);
			loadView(webView);

			return rootView;
		}
	}
}
