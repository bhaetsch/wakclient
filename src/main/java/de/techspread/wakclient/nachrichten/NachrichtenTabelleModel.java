package de.techspread.wakclient.nachrichten;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.table.AbstractTableModel;

import org.apache.http.client.ClientProtocolException;

/**
 * Model für die Nachrichten-Tabelle
 * @author Patrick Gotthard
 *
 */
public class NachrichtenTabelleModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private NachrichtenListe liste;
	private String[] titel = {"Datum", "Von", "Betreff"};

	/**
	 * Konstruktor
	 * @param liste
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public NachrichtenTabelleModel(NachrichtenListe liste) throws ClientProtocolException, IOException, NoSuchAlgorithmException {
		this.liste = liste;
	}

	/**
	 * Spalten-Anzahl auslesen
	 */
	@Override
	public int getColumnCount() {
		return titel.length;
	}

	/**
	 * Titel der Spalte auslesen
	 */
	@Override
	public String getColumnName(int spalte) {
		return titel[spalte];
	}

	/**
	 * Anzahl der Zelen auslesen
	 */
	@Override
	public int getRowCount() {
		return liste.size();
	}

	/**
	 * Zelleninhalt auslesen
	 */
	@Override
	public String getValueAt(int reihe, int spalte) {
		if(spalte == 0) {
			return liste.get(reihe).getDatum();
		} else if(spalte == 1) {
			return liste.get(reihe).getAbsender();
		} else {
			return liste.get(reihe).getBetreff();
		}
	}

	/**
	 * Nachricht aus Tabelle löschen
	 * @param message
	 */
	public void delete(Nachricht message) {
		liste.remove(message);
		fireTableDataChanged();
	}
}