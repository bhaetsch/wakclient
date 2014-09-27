package de.techspread.wakclient.dateiablage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;

import de.techspread.wakclient.common.WebClient;

/**
 * Download-Klasse
 * 
 * @author Patrick Gotthard
 */
public class Download extends Observable implements Runnable {

	private HttpEntity	entity;
	private String		relativerPfad;
	private String		ziel;

	private long		groesse			= 0;
	private long		heruntergeladen	= 0;

	/**
	 * Konstruktor
	 * 
	 * @param client
	 * @param url
	 * @param ziel
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Download(WebClient client, String url, String relativerPfad, String ziel) throws ClientProtocolException, IOException {
		entity = client.getEntity(url);
		this.relativerPfad = relativerPfad;
		this.ziel = ziel;
		groesse = entity.getContentLength();
	}

	/**
	 * Download-Fortschritt auslesen
	 * 
	 * @return Download-Fortschritt
	 */
	public int getFortschritt() {
		return (int) (heruntergeladen / (groesse / 100));
	}

	/**
	 * Relativen Pfad auslesen
	 * 
	 * @return Relativer Pfad
	 */
	public String getRelativerPfad() {
		return relativerPfad;
	}

	/**
	 * Download starten
	 */
	public void start() {
		new Thread(this).start();
	}

	/**
	 * Download-Thread
	 */
	@Override
	public void run() {
		try {
			BufferedInputStream input = new BufferedInputStream(entity.getContent());
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(ziel));
			byte[] puffer = new byte[2048];
			int gelesen;
			while ((gelesen = input.read(puffer)) != -1) {
				output.write(puffer, 0, gelesen);
				heruntergeladen += gelesen;
				setChanged();
				notifyObservers();
			}
			output.flush();
			output.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
