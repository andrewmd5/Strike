package net.codeusa.strike.settings;

public class Settings {
	private static String MPC_URL = "";

	public static String getMPC_URL() {
		return MPC_URL;
	}

	public static void setMPC_URL(final String mPC_URL) {
		MPC_URL = mPC_URL;
	}

	public static String getDELUGE_URL() {
		return DELUGE_URL;
	}

	public static void setDELUGE_URL(final String dELUGE_URL) {
		DELUGE_URL = dELUGE_URL;
	}

	private static String DELUGE_URL = "";
}
