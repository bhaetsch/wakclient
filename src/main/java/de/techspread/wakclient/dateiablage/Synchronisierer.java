package de.techspread.wakclient.dateiablage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.apache.http.client.ClientProtocolException;

import de.techspread.regex.RegexParser;
import de.techspread.wakclient.common.Einstellungen;
import de.techspread.wakclient.common.WebClient;

/**
 * Klasse zur Synchronisation der Plattform
 * 
 * @author Patrick Gotthard
 */
public class Synchronisierer extends Thread {

	private DateiablageTab			dateiablage;
	private DownloadTabelleModel	tabelle;
	private Einstellungen			einstellungen;
	private WebClient				client;

	/**
	 * Konstruktur
	 * 
	 * @param tabelle
	 * @param einstellungen
	 * @param client
	 */
	public Synchronisierer(DateiablageTab dateiablage, DownloadTabelleModel tabelle, Einstellungen einstellungen, WebClient client) {
		this.dateiablage = dateiablage;
		this.tabelle = tabelle;
		this.einstellungen = einstellungen;
		this.client = client;
		start();
	}

	/**
	 * Startet Synchronisation
	 */
	@Override
	public void run() {
		client.setActiveSync(true);
		einstieg();
		client.setActiveSync(false);
	}

	/**
	 * Einstiegspunkte auslesen und durchgehen
	 */
	private void einstieg() {
		dateiablage.auswaehlen.setEnabled(false);
		dateiablage.starten.setEnabled(false);
		dateiablage.starten.setIcon(new ImageIcon(this.getClass().getResource("/loading.gif")));
		dateiablage.starten.setText("synchronisiert");
		try {
			ArrayList<String[]> einstiegsPunkte = RegexParser.find(client.getDateiablage(), "<a href=\"(c_dateiablage.html.{2}no_cache=1.{1}mountpoint=[0-9]+)\" class=\'filelink\'>(.*?)</a>");
			for (String[] einstiegsPunkt : einstiegsPunkte) {
				String ordner = einstiegsPunkt[1].replace('/', '-');
				File verzeichnis = new File(einstellungen.getVerzeichnis() + ordner);
				if (!verzeichnis.exists()) {
					verzeichnis.mkdir();
				}
				synchronize(ordner + File.separator, "/" + einstiegsPunkt[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		dateiablage.starten.setText("starten");
		dateiablage.starten.setIcon(null);
		dateiablage.starten.setEnabled(true);
		dateiablage.auswaehlen.setEnabled(true);

	}

	/**
	 * Ordner und Dateien abgleichen
	 * 
	 * @param node
	 * @param url
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private void synchronize(String node, String url) throws ClientProtocolException, IOException {
		String relevant = RegexParser.find(client.get(url), "(<table id=\"contenttable\".*?</table>)").get(0)[0];
		ArrayList<String[]> links = RegexParser.find(relevant, "<a href=\"(.*?dir=(.*?)&.*?)\".*?>(.*?)</a>");
		for (String[] link : links) {
			if (!link[2].contains("<img")) {
				if (link[0].contains("filename")) {
					String relativerPfad = node + link[1] + File.separator + link[2];
					File datei = new File(einstellungen.getVerzeichnis() + relativerPfad);
					if (!datei.exists()) {
						tabelle.addDownload(new Download(client, "/" + link[0], relativerPfad, datei.getAbsolutePath()));
					}
				} else {
					File ordner = new File(einstellungen.getVerzeichnis() + node + link[1]);
					if (!ordner.exists()) {
						ordner.mkdir();
					}
					synchronize(node, "/" + link[0]);
				}
			}
		}
	}
}