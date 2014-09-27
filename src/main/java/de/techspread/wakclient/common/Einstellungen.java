package de.techspread.wakclient.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import de.techspread.crypt.Base64;

/**
 * Persistierung der Einstellungen
 * 
 * @author Patrick Gotthard
 */
public class Einstellungen {

	private String		heimatVerzeichnis	= System.getProperty("user.home") + File.separator;
	private File		konfigurationsDatei	= new File(heimatVerzeichnis + ".wakclient.properties");
	private Properties	einstellungen		= new Properties();

	/**
	 * Standard-Konstruktor
	 */
	public Einstellungen() {
		laden();
	}

	/**
	 * Einstellungen aus Datei laden
	 */
	public void laden() {
		try {
			if (konfigurationsDatei.exists()) {
				einstellungen.loadFromXML(new FileInputStream(konfigurationsDatei));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Abspeichern der Einstellungen
	 */
	public void speichern() {
		try {
			einstellungen.storeToXML(new FileOutputStream(konfigurationsDatei), "1.0");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * E-Mail auslesen
	 * 
	 * @return E-Mail
	 */
	public String getEmail() {
		return einstellungen.getProperty("email", "@berufsakademie-sh.de");
	}

	/**
	 * E-Mail setzen
	 * 
	 * @param E
	 *            -Mail
	 */
	public void setEmail(String email) {
		einstellungen.setProperty("email", email);
		speichern();
	}

	/**
	 * Passwort auslesen und entschlüsseln
	 * 
	 * @return Passwort
	 */
	public String getPasswort() {
		String password = einstellungen.getProperty("password", "");
		if (!password.isEmpty()) {
			password = Base64.decode(password);
		}
		return password;
	}

	/**
	 * Passwort verschlüsselt abspeichern
	 * 
	 * @param Passwort
	 */
	public void setPasswort(String password) {
		einstellungen.setProperty("password", Base64.encode(password));
		speichern();
	}

	/**
	 * Auslesen ob der Proxy benutzt werden soll
	 * 
	 * @return
	 */
	public boolean isProxyBenutzen() {
		return Boolean.parseBoolean(einstellungen.getProperty("useproxy", "false"));
	}

	/**
	 * Setzen, ob der Proxy benutzt werden soll
	 * 
	 * @param useProxy
	 */
	public void setProxyBenutzen(boolean useProxy) {
		einstellungen.setProperty("useproxy", String.valueOf(useProxy));
		speichern();
	}

	/**
	 * Proxy-Server auslesen
	 * 
	 * @return
	 */
	public String getServer() {
		return einstellungen.getProperty("proxyurl", "");
	}

	/**
	 * Proxy-Server setzen
	 * 
	 * @param proxyurl
	 */
	public void setServer(String proxyurl) {
		einstellungen.setProperty("proxyurl", proxyurl);
		speichern();
	}

	/**
	 * Proxy-Port auselsen
	 * 
	 * @return
	 */
	public int getPort() {
		try {
			return Integer.parseInt(einstellungen.getProperty("proxyport", "8080"));
		} catch (NumberFormatException e) {
			setPort("8080");
			return 8080;
		}
	}

	/**
	 * Proxy-Port setzen
	 * 
	 * @param proxyport
	 */
	public void setPort(String proxyport) {
		einstellungen.setProperty("proxyport", proxyport);
		speichern();
	}

	/**
	 * Zuletzt gewähltes Semester auslesen
	 * 
	 * @return
	 */
	public int getSemester() {
		try {
			return Integer.parseInt(einstellungen.getProperty("lastusedsemester", "1"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * Zuletzt gewähltes Semester setzen
	 * 
	 * @param lastUsedSemester
	 */
	public void setSemester(int lastUsedSemester) {
		einstellungen.setProperty("lastusedsemester", String.valueOf(lastUsedSemester));
		speichern();
	}

	/**
	 * Synchronisations-Verzeichnis auslesen
	 * 
	 * @return Synchronisations-Verzeichnis
	 */
	public String getVerzeichnis() {
		return einstellungen.getProperty("syncpath", "");
	}

	/**
	 * Synchronisations-Verzeichnis setzen
	 * 
	 * @param Synchronisations
	 *            -Verzeichnis
	 */
	public void setVerzeichnis(String syncPath) {
		einstellungen.setProperty("syncpath", syncPath);
		speichern();
	}
}