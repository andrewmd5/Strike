package net.codeusa.strike.mediaplayer.clients.mpc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.codeusa.strike.mediaplayer.clients.MediaClient;
import net.codeusa.strike.settings.Settings;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MediaPlayerClassicClient extends MediaClient {

	private static int PLAY_PAUSE = 889;
	private static int NEXT = 920;
	private static int PREV = 921;

	private String cachedTitle = "";
	private Bitmap cachedScreenGrab;
	private String[] statusContent;
	private String title;
	private String currentDuration;
	private String totalDuration;
	private String playBackStatus;

	@Override
	public void play() {
		sendCommand(PLAY_PAUSE);

	}

	@Override
	public void pause() {
		sendCommand(PLAY_PAUSE);

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void next() {
		sendCommand(NEXT);

	}

	@Override
	public void previous() {
		sendCommand(PREV);

	}


	public void sendCommand(int id) {
		final String url = Settings.getMPCServer()
				+ "/command.html?wm_command=" + String.valueOf(id);
		HttpResponse response = null;

		try {
			// Create http client object to send request to server
			final HttpClient client = new DefaultHttpClient();
			// Create URL string

			// Create Request to server and get response
			final HttpGet httpget = new HttpGet();
			httpget.setURI(new URI(url));
			response = client.execute(httpget);
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		} catch (final ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (final IOException e) {
			// TODO Auto-generated catch block
		}

	}

	public void setStatusContent() {
		String mpcStatus = getMPCStatus();
		int ignore_mpc = mpcStatus.lastIndexOf(" - Media Player Classic");
		 if( ignore_mpc != -1 ) {
			mpcStatus = mpcStatus.replace(" - Media Player Classic", "");
		}
		Pattern pattern = Pattern.compile("OnStatus\\(\"(.*?)\"\\)");
		Matcher matcher = pattern.matcher(getMPCStatus());
		if (matcher.find()) {
			statusContent = matcher.group(1).replaceAll("\"", "").split(",");
			title = statusContent[0];
			currentDuration = statusContent[3];
			totalDuration = statusContent[5];
			playBackStatus = statusContent[1];
		}	
	}
	

	public String getTitle() {
		return title;
	}

	public String getPlayBackStatus() {	
		return playBackStatus;
	}

	public int getPlaybackDrawable() {
		final String status = getPlayBackStatus().trim();
		if (status.equals("Playing")) {
			return android.R.drawable.ic_media_pause;
		} else if (status.equals("Paused") || status.equals("Stopped")) {
			return android.R.drawable.ic_media_play;
		}
		return android.R.drawable.ic_media_ff;
	}

	public String getFormattedNotfication() {
		final String text = currentDuration + "/" + totalDuration + " - "
				+ playBackStatus;
		return text;
	}

	public Bitmap getScreenGrab(final String title) {
		if (title.equals(this.cachedTitle)) {

			return this.cachedScreenGrab;
		}
		Bitmap bm = null;
		try {
			final URL aURL = new URL(Settings.getMPCServer() + "/snapshot.jpg");
			final URLConnection conn = aURL.openConnection();
			conn.connect();
			final InputStream is = conn.getInputStream();
			final BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (final IOException e) {

		}
		this.cachedTitle = title;
		this.cachedScreenGrab = bm;
		return bm;

	}

	private String getMPCStatus()  {
		try {
			final URL url = new URL(Settings.getMPCServer() + "/status.html");
			final DefaultHttpClient client = new DefaultHttpClient();
			final HttpGet request = new HttpGet(url.toURI());
			final HttpResponse response = client.execute(request);

			Reader reader = null;
			try {
				reader = new InputStreamReader(response.getEntity().getContent());

				final StringBuffer sb = new StringBuffer();
				{
					int read;
					final char[] cbuf = new char[1024];
					while ((read = reader.read(cbuf)) != -1) {
						sb.append(cbuf, 0, read);
					}
				}

				return sb.toString();

			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalStateException | URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
