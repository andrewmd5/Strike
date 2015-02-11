package net.codeusa.strike.settings;

public class Settings {
	private static String MPC_URL = "";

	public static String getMPCServer() {
		return MPC_URL;
	}

	public static void setMPCServer(final String mPC_URL) {
		MPC_URL = mPC_URL;
	}

	public static String getTorrentClient() {
		return TORRENT_URL;
	}

	public static void setTorrentClient(final String torrentURL) {
		TORRENT_URL = torrentURL;
	}

	private static String TORRENT_URL = "";
}
