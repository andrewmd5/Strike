package net.codeusa.strike.js;

import net.codeusa.strike.StrikeActivity;
import net.codeusa.strike.settings.Settings;
import net.codeusa.strike.utils.Utils;
import android.webkit.JavascriptInterface;

public class JSEngine {

	@JavascriptInterface
	public void saveSettings(final String jsResult) {

		Utils.writeFileInternalStorage(jsResult,
				StrikeActivity.activity.getApplicationContext(),
				"strikeconfig.txt");
		Utils.readSettings(StrikeActivity.activity.getApplicationContext());
	}

	@JavascriptInterface
	public String getTorrentClient() {

		return Settings.getTorrentClient();
	}

	@JavascriptInterface
	public String getMPCServer() {

		return Settings.getMPCServer();
	}
}