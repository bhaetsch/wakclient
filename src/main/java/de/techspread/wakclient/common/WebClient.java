package de.techspread.wakclient.common;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import de.techspread.crypt.MD5;
import de.techspread.regex.RegexParser;

/**
 * Klasse zur Interaktion mit der Webseite
 * 
 * @author Patrick Gotthard
 */
public class WebClient extends de.techspread.webclient.WebClient {

	private Einstellungen	einstellungen;
	private String			cache_dateiablage;
	private String			cache_nachrichten;
	private String			cache_notenseite;
	private String			cache_uebersicht;
	private boolean			activeSync	= false;

	/**
	 * Konstruktor
	 * 
	 * @param Einstellungen
	 */
	public WebClient(Einstellungen einstellungen) {
		this.einstellungen = einstellungen;
	}

	/**
	 * Anmeldung
	 * 
	 * @return Erfolg der Anmeldung
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean auth() throws ClientProtocolException, IOException, NoSuchAlgorithmException {
		if(einstellungen.isProxyBenutzen()) {
			setProxy(einstellungen.getServer(), einstellungen.getPort());
		}		
		String source = get("/30.html");
		String challenge = RegexParser.find(source, "<input type=\"hidden\" name=\"challenge\" value=\"(.{32})\">").get(0)[0];
		String passphrase = MD5.hash(einstellungen.getEmail() + ":" + MD5.hash(einstellungen.getPasswort()) + ":" + challenge);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user", einstellungen.getEmail()));
		params.add(new BasicNameValuePair("pass", passphrase));
		params.add(new BasicNameValuePair("submit", "Anmelden"));
		params.add(new BasicNameValuePair("logintype", "login"));
		params.add(new BasicNameValuePair("pid", "3"));
		params.add(new BasicNameValuePair("redirect_url", ""));
		params.add(new BasicNameValuePair("challenge", challenge));
		source = post("/community-login.html", params);
		return !source.contains("Anmeldefehler");
	}

	/**
	 * Prüfung der Anmeldung
	 * 
	 * @return Anmeldestatus
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean isAuthed() throws ClientProtocolException, IOException {
		String source = get("/c_dateiablage.html");
		return !source.contains("Benutzeranmeldung");
	}

	/**
	 * Dateiablage-Seite auslesen
	 * 
	 * @return Dateiablage-Seite
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public String getDateiablage() throws ClientProtocolException, IOException, NoSuchAlgorithmException {
		if (cache_dateiablage == null) {
			cache_dateiablage = get("/c_dateiablage.html");
			while (cache_dateiablage.contains("Benutzeranmeldung")) {
				auth();
				cache_dateiablage = get("/c_dateiablage.html");
			}
		}
		return cache_dateiablage;
	}

	/**
	 * Nachrichtenseite auslesen
	 * 
	 * @return Nachrichtenseite
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public String getNachrichten() throws ClientProtocolException, IOException, NoSuchAlgorithmException {
		if (cache_nachrichten == null) {
			cache_nachrichten = get("/c_email.html");
			while (cache_nachrichten.contains("Benutzeranmeldung")) {
				auth();
				cache_nachrichten = get("/c_dateiablage.html");
			}
		}
		return cache_nachrichten;
	}

	/**
	 * Notenseite auslesen
	 * 
	 * @return Notenseite
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public String getNotenSeite() throws ClientProtocolException, IOException, NoSuchAlgorithmException {
		if (cache_notenseite == null) {
			do {
				if (!isAuthed()) {
					auth();
				}
				String notenAnmeldung = get("/notenabfrage_bc.html");
				String id = RegexParser.find(notenAnmeldung, "<input type=\"hidden\" name=\"id\" value=\"(.*?)\">").get(0)[0];
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", id));
				params.add(new BasicNameValuePair("Passwort", einstellungen.getPasswort()));
				cache_notenseite = post("", params);
			} while (!cache_notenseite.contains("Logout"));
		}
		return cache_notenseite;
	}

	/**
	 * Übersichts-Seite auslesen
	 * 
	 * @return Übersichts-Seite
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String getUebersicht() throws ClientProtocolException, IOException {
		if (cache_uebersicht == null) {
			cache_uebersicht = get("/c_uebersicht.html");
		}
		return cache_uebersicht;
	}

	/**
	 * Synchronisations-Status angeben
	 * 
	 * @return Synchronisations-Status
	 */
	public boolean isActiveSync() {
		return activeSync;
	}

	/**
	 * Synchronisations-Status setzen
	 * 
	 * @param activeSync
	 */
	public void setActiveSync(boolean activeSync) {
		this.activeSync = activeSync;
	}
}
