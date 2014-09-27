package de.techspread.wakclient.benutzerinformationen;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;

import de.techspread.regex.RegexParser;
import de.techspread.wakclient.common.WebClient;

/**
 * Auslesen von Benutzerinformationen
 * @author Patrick Gotthard
 *
 */
public class BenutzerInformationen {
	
	private WebClient client;

	/**
	 * Konstruktur
	 * @param WebClient
	 */
	public BenutzerInformationen(WebClient client) {
		this.client = client;
	}
	
	/**
	 * Benutzername auslesen
	 * @return Benutzername
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String getBenutzername() throws ClientProtocolException, IOException {
		String name = RegexParser.find(client.getUebersicht(), "<b>Hallo&nbsp;(.*?)</b>").get(0)[0];
		return name.replaceAll("&nbsp;", " ").replace("!", "");
	}	
	
	/**
	 * Studiengang auslesen
	 * @return Studiengang
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public String getStudiengang() throws ClientProtocolException, IOException, NoSuchAlgorithmException {
		String group = getStudiengruppe();
		if (group.contains("WINF")) {
			return "Wirtschaftsinformatik";
		} else if (group.contains("WING")) {
			return "Wirtschaftsingenieurwesen";
		} else {
			return "Betriebswirtschaft";
		}
	}
	
	/**
	 * Studiengruppe auslesen
	 * @return Studiengruppe
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public String getStudiengruppe() throws ClientProtocolException, IOException, NoSuchAlgorithmException {
		return RegexParser.find(client.getDateiablage(), "<a.*?>(BA.*?)</a>").get(0)[0];
	}
	
	/**
	 * Matrikelnummer auslesen
	 * @return Matrikelnummer
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public String getMatrikelNummer() throws ClientProtocolException, IOException, NoSuchAlgorithmException {
		return RegexParser.find(client.getNotenSeite(), "<td>Studierendennummer: (.*?) </td>").get(0)[0];
	}	
}
