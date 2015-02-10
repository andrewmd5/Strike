package net.codeusa.strike;

import net.codeusa.strike.js.JSEngine;
import net.codeusa.strike.settings.Settings;
import net.codeusa.strike.utils.Utils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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

// “Always code as if the guy who ends up maintaining your
//code will be a violent psychopath who knows where you live.” - Words I did not live by
//Created by Andrew Sampson
public class StrikeActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private WebView globalView;

	// private ProgressDialog progressDialog;
	public static StrikeActivity activity;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		this.mTitle = getTitle();

		// Set up the drawer.
		this.mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		activity = this;
		Utils.readSettings(getApplicationContext());

	}

	@Override
	public void onNavigationDrawerItemSelected(final int position) {
		// update the main content by replacing fragments
		final FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						RemoteControlFragment.newInstance(position + 1))
				.commit();

	}

	public void onSectionAttached(final int number) {
		switch (number) {
		case 1:
			this.mTitle = "About";
			break;
		case 2:
			this.mTitle = "Settings";
			break;
		case 3:
			this.mTitle = "Remote";
			break;
		}
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
		remoteView.getSettings().setUserAgentString("StrikeDroid");
		remoteView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
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

				final String html = "<html><body bgcolor=\"#B9090B\"><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
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
				if (url.contains(Settings.getMPC_URL())
						|| url.contains(Settings.getDELUGE_URL())) {
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

	public void restoreActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(this.mTitle);

		if (this.globalView != null) {
			// if you rely on toString() on an arbitrary CharSequence, it should
			// work
			// but I don't like taking chances
			final StringBuilder sb = new StringBuilder(this.mTitle.length());
			sb.append(this.mTitle);
			final String title = sb.toString();
			switch (title) {
			case "Remote":
				this.globalView.loadUrl(Settings.getMPC_URL());
				break;
			case "Settings":
				this.globalView.loadUrl("file:///android_asset/settings.html");
				break;
			case "About":
				this.globalView.loadUrl("file:///android_asset/landing.html");
				break;
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		if (!this.mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		final int id = item.getItemId();
		if (id == R.id.action_settings) {

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void finish() {

		this.globalView.clearHistory();
		this.globalView.clearCache(true);
		this.globalView.loadUrl("about:blank");
		this.globalView.pauseTimers(); // new code

		this.globalView.destroy();
		this.globalView = null;

		super.finish();
	}

	private boolean isPackageInstalled(final String packagename,
			final Context context) {
		final PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (final NameNotFoundException e) {
			return false;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		final int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class RemoteControlFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static RemoteControlFragment newInstance(final int sectionNumber) {
			final RemoteControlFragment fragment = new RemoteControlFragment();
			final Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public RemoteControlFragment() {

		}

		@Override
		public View onCreateView(final LayoutInflater inflater,
				final ViewGroup container, final Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_main,
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

		@Override
		public void onAttach(final Activity activity) {
			super.onAttach(activity);
			((StrikeActivity) activity).onSectionAttached(getArguments()
					.getInt(ARG_SECTION_NUMBER));

		}
	}

}
