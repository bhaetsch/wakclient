package de.techspread.wakclient.nachrichten;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import de.techspread.regex.RegexParser;
import de.techspread.wakclient.common.WebClient;

/**
 * Liste der Nachrichten
 * @author Patrick Gotthard
 *
 */
public class NachrichtenListe extends ArrayList<Nachricht> {

	private static final long serialVersionUID = 59070074634746730L;
	private WebClient client;

	/**
	 * Konstruktor
	 * @param client
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public NachrichtenListe(WebClient client) throws ClientProtocolException, NoSuchAlgorithmException, IOException {
		this.client = client;
		parseNachrichten();
	}

	/**
	 * Nachrichten auslesen
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private void parseNachrichten() throws ClientProtocolException, NoSuchAlgorithmException, IOException {
		ArrayList<String[]> nachrichten = RegexParser.find(client.getNachrichten(), "single&msg_uid=(.*?)\">(.*?)<.*?<td>(.*?)</td>.*?<td>(.*?)&.*?delete&msg_uid=(.*?)\">");
		for(String[] nachricht : nachrichten) {
			Nachricht temp = new Nachricht();
			temp.setLeseUrl(nachricht[0]);
			temp.setBetreff(nachricht[1]);
			temp.setDatum(nachricht[2]);
			temp.setAbsender(nachricht[3]);
			temp.setLoeschUrl(nachricht[4]);
			add(temp);
		}
	}

	/**
	 * Nachricht auslesen
	 * @param nummer
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String getNachricht(int nummer) throws ClientProtocolException, IOException {
		Nachricht nachricht = get(nummer);
		if(nachricht.getText() == null) {
			nachricht.setText(RegexParser.find(client.get(nachricht.getLeseUrl()), "Nachricht:.+?<td>(.*?)</td>").get(0)[0]);
		}
		return nachricht.getText();
	}
}