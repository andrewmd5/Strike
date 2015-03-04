package net.codeusa.strike.services;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.codeusa.strike.R;
import net.codeusa.strike.StrikeActivity;
import net.codeusa.strike.mediaplayer.clients.mpc.MediaPlayerClassicClient;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class NotficationService {
	private Notification notification;
	private boolean stopNotifications;
	NotificationManager notificationManager;
	MediaPlayerClassicClient client = new MediaPlayerClassicClient();

	public Notification getNotification() {
		return this.notification;
	}

	public void createNotification(final String title, final String duration,
			final Bitmap snapShot) {

		//register intents
		StrikeActivity.activity.registerReceiver(this.receiver,
				new IntentFilter("net.codeusa.strike.PREV_VIDEO"));
		StrikeActivity.activity.registerReceiver(this.receiver,
				new IntentFilter("net.codeusa.strike.NEXT_VIDEO"));
		StrikeActivity.activity.registerReceiver(this.receiver,
				new IntentFilter("net.codeusa.strike.MEDIA_STATE"));
		int playBackIcon = client.getPlaybackDrawable();
		
		final Intent previousIntent = new Intent();
		previousIntent.setAction("net.codeusa.strike.PREV_VIDEO");
		final PendingIntent pendingPrev = PendingIntent.getBroadcast(
				StrikeActivity.activity.getApplicationContext(), 12345,
				previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		final Intent nextIntent = new Intent();
		nextIntent.setAction("net.codeusa.strike.NEXT_VIDEO");
		final PendingIntent pendingNext = PendingIntent.getBroadcast(
				StrikeActivity.activity.getApplicationContext(), 12345,
				nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		final Intent playbackIntent = new Intent();
		playbackIntent.setAction("net.codeusa.strike.MEDIA_STATE");
		final PendingIntent pendingPlayback = PendingIntent.getBroadcast(
				StrikeActivity.activity.getApplicationContext(), 12345,
				playbackIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		

		this.notification = new Notification.Builder(StrikeActivity.activity)
				// Show controls on lock screen even when user hides sensitive
				// content.
				.setOngoing(true)
				.setVisibility(Notification.VISIBILITY_PUBLIC)
				.setSmallIcon(R.drawable.ic_launcher)
				.addAction(android.R.drawable.ic_media_previous, "Previous",
						pendingPrev)
				.addAction(playBackIcon, "Pause",
						pendingPlayback)
				.addAction(android.R.drawable.ic_media_next, "Next",
						pendingNext)
				// Apply the media style template
				.setStyle(
						new Notification.MediaStyle()
								.setShowActionsInCompactView(0, 1, 2)
								.setMediaSession(null)).setContentTitle(title)
				.setContentText(duration).setLargeIcon(snapShot).build();
		
		

		this.notificationManager = (NotificationManager) StrikeActivity.activity
				.getSystemService(Context.NOTIFICATION_SERVICE);

		this.notificationManager.notify(6939, this.notification);

	}

	

	public void notficationUpdate() {
		final ScheduledExecutorService scheduleTaskExecutor = Executors
				.newScheduledThreadPool(1);

		scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				if (isStopNotfications()) {
					scheduleTaskExecutor.shutdown();
				}
				
				
				

				client.setStatusContent();
				final String title = client.getTitle();
				final String text = client.getFormattedNotfication();
				final Bitmap icon = client.getScreenGrab(title);
				
				createNotification(title, text, icon);
				
			}
		}, 0, 500, TimeUnit.MILLISECONDS);
	}

	public boolean isStopNotfications() {
		return this.stopNotifications;
	}

	public void setStopNotfications(final boolean stopNotfications) {
		this.stopNotifications = stopNotfications;
	}

	private class PlayerControlTask extends AsyncTask<String, Integer, Double> {
		@Override
		protected Double doInBackground(final String... params) {
			switch (params[0]) {
			case "prev":
				client.previous();
				break;

			case "next":
				client.next();
				break;

			case "state":
				client.play();
				break;
			}

			return null;
		}
	}

	public BroadcastReceiver receiver = new BroadcastReceiver() {
		// @Override
		@Override
		public void onReceive(final Context context, final Intent intent) {

			final String action = intent.getAction();

			switch (action) {
			case "net.codeusa.strike.PREV_VIDEO":
				new PlayerControlTask().execute("prev");
				break;
			case "net.codeusa.strike.NEXT_VIDEO":
				new PlayerControlTask().execute("next");
				break;
			case "net.codeusa.strike.MEDIA_STATE":
				new PlayerControlTask().execute("state");
				break;
			}

		}
	};
}
