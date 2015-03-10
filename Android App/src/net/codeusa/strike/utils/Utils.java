package net.codeusa.strike.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import net.codeusa.strike.StrikeActivity;
import net.codeusa.strike.settings.Settings;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Environment;
import android.util.Log;

public class Utils {
	AudioManager mAudioManager;
	ComponentName mMediaButtonReceiverComponent;

	public static boolean isSdReadable() {

		boolean mExternalStorageAvailable = false;
		try {
			final String state = Environment.getExternalStorageState();

			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// We can read and write the media
				mExternalStorageAvailable = true;
				Log.i("isSdReadable", "External storage card is readable.");
			} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				// We can only read the media
				Log.i("isSdReadable", "External storage card is readable.");
				mExternalStorageAvailable = true;
			} else {
				// Something else is wrong. It may be one of many other
				// states, but all we need to know is we can neither read nor
				// write
				mExternalStorageAvailable = false;
			}
		} catch (final Exception ex) {

		}
		return mExternalStorageAvailable;
	}

	public static void writeFileInternalStorage(final String data,
			final Context context, final String fileName) {

		final String filename = fileName;

		FileOutputStream outputStream;

		try {
			outputStream = context.openFileOutput(filename,
					Context.MODE_PRIVATE);
			outputStream.write(data.getBytes());
			outputStream.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean isUP(final String host) {
		try {
			final URL url = new URL(host);
			InputStream i = null;

			try {
				i = url.openStream();
			} catch (final UnknownHostException ex) {
				return false;
			} catch (final IOException e) {
				return false;
			}

			if (i != null) {
				return true;
			}

		} catch (final MalformedURLException e) {
			return false;
		}

		return false;
	}

	public static void readSettings(final Context context) {
		try {
			final FileInputStream fis = context.getApplicationContext()
					.openFileInput("strikeconfig.txt");
			final InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			final BufferedReader bufferedReader = new BufferedReader(isr);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				final String[] settings = line.split(",");
				System.out.println(settings.length);
				Settings.setMPCServer(settings[0]);
				Settings.setTorrentClient(settings[1]);
				
				Settings.setBrowserPath(settings[2]);
				
			}

		} catch (final FileNotFoundException e) {
			Settings.setMPCServer("file:///android_asset/settings.html");
			Settings.setTorrentClient("file:///android_asset/settings.html");
		} catch (final UnsupportedEncodingException e) {

		} catch (final IOException e) {

		}
	}

	public static Drawable drawableFromUrl(final String url) throws IOException {
		Bitmap x;

		final HttpURLConnection connection = (HttpURLConnection) new URL(url)
		.openConnection();
		connection.connect();
		final InputStream input = connection.getInputStream();

		x = BitmapFactory.decodeStream(input);
		return new BitmapDrawable(StrikeActivity.activity.getResources(), x);
	}
}
