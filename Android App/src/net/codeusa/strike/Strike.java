package net.codeusa.strike;

import java.util.HashMap;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class Strike extends Application {

	// The following line should be changed to include the correct property id.
	private static final String PROPERTY_ID = "XX-XXXXXX-1";

	public static int GENERAL_TRACKER = 0;

	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg:
		// roll-up tracking.
		ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a
		// company.
	}

	public HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	public Strike() {
		super();
	}

	public synchronized Tracker getTracker(final TrackerName trackerId) {
		if (!this.mTrackers.containsKey(trackerId)) {
			final GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			final Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
					.newTracker(PROPERTY_ID)
					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
							.newTracker(R.xml.global_tracker) : analytics
							.newTracker(R.xml.ecommerce_tracker);
			this.mTrackers.put(trackerId, t);

		}
		return this.mTrackers.get(trackerId);
	}
}