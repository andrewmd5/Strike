package net.codeusa.strike.js;

import net.codeusa.strike.StrikeActivity;
import net.codeusa.strike.utils.Utils;
import android.webkit.JavascriptInterface;

public class JSEngine {

	@JavascriptInterface
	public void saveSettings(final String jsResult) {

		Utils.writeFileInternalStorage(jsResult,
				StrikeActivity.activity.getApplicationContext(),
				"strikeconfig.txt");
	}
}