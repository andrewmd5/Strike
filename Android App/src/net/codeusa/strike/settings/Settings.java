package net.codeusa.strike.settings;

import net.codeusa.strike.mediaplayer.clients.MediaClient;

public class Settings {
	private static String LAST_URL;
	private static String MPC_URL = "";
	private static String BROWSER_PATH = "";
	public static MediaClient client;

	public static MediaClient getClient() {
		return client;
	}

	public static void setClient(final MediaClient client) {
		Settings.client = client;
	}

	public static String getMPCServer() {
		return MPC_URL;
	}

	public static void setMPCServer(final String mPC_URL) {
		MPC_URL = mPC_URL;
	}

	public static String getTorrentClient() {
		return TORRENT_URL;
	}
public static String getBrowserPath() {
	return BROWSER_PATH;
}

public static void setBrowserPath(String path) {
	BROWSER_PATH = path;
}
	public static void setTorrentClient(final String torrentURL) {
		TORRENT_URL = torrentURL;
	}

	public static String getLastURL() {
		return LAST_URL;
	}

	public static void setURL(final String url) {
		LAST_URL = url;
	}
	

	private static String TORRENT_URL = "";
}
