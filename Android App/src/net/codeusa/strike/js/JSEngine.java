package net.codeusa.strike.js;

import net.codeusa.strike.StrikeActivity;
import net.codeusa.strike.services.NotficationService;
import net.codeusa.strike.settings.Settings;
import net.codeusa.strike.utils.Utils;
import android.webkit.JavascriptInterface;

public class JSEngine {
	
	NotficationService notfication = new NotficationService();
	

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

	@JavascriptInterface
	public String checkStatus(final String host) {
		return Utils.isUP(host) == true ? "true" : "false";
	}
	
	@JavascriptInterface
	public void play() {
		Settings.getClient().play();
	}
	
	@JavascriptInterface
	public void pause() {
		Settings.getClient().pause();
	}

	@JavascriptInterface
	public void prev() {
		Settings.getClient().previous();
	}
	
	@JavascriptInterface
	public void next() {
		Settings.getClient().next();
	}
	
	@JavascriptInterface
	public void mute() {
		Settings.getClient().mute();
	}
	
	@JavascriptInterface
	public void volumeUp() {
		Settings.getClient().volumeUp();
	}
	
	@JavascriptInterface
	public void volumeDown() {
		Settings.getClient().volumeDown();
	}
	
	@JavascriptInterface
	public void seek(int seconds) {
		Settings.getClient().seek(seconds);
	}
	
	@JavascriptInterface
	public void startNotfication() {

		this.notfication.notficationUpdate();

	}

	@JavascriptInterface
	public void removeNotfication() {

	}
}