package de.techspread.wakclient.dateiablage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;

/**
 * Model für die Download-Tabelle
 * 
 * @author Patrick Gotthard
 */
public class DownloadTabelleModel extends AbstractTableModel implements Observer {

	private static final long	serialVersionUID	= -6595099773055969257L;
	private ArrayList<Download>	downloads			= new ArrayList<Download>();
	private String[]			titel				= { "Datei", "Fortschritt" };
	@SuppressWarnings("rawtypes")
	private Class[]				typen				= { String.class, JProgressBar.class };

	/**
	 * Anzahl der Spalten auslesen
	 */
	@Override
	public int getColumnCount() {
		return titel.length;
	}

	/**
	 * Titel der Spalte auslesen
	 */
	@Override
	public String getColumnName(int col) {
		return titel[col];
	}

	/**
	 * Typ der Spalte auslesen
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class getColumnClass(int spalte) {
		return typen[spalte];
	}

	/**
	 * Anzahl der Zeilen auslesen
	 */
	@Override
	public int getRowCount() {
		return downloads.size();
	}

	/**
	 * Zelleninhalt auslesen
	 */
	@Override
	public Object getValueAt(int reihe, int spalte) {
		Download download = downloads.get(reihe);
		switch (spalte) {
			case 0:
				return download.getRelativerPfad();
			case 1:
				return download.getFortschritt();
		}
		return null;
	}

	/**
	 * Download hinzufügen
	 * 
	 * @param Download
	 */
	public void addDownload(Download download) {
		download.addObserver(this);
		downloads.add(download);
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
		download.start();
	}

	/**
	 * Download auslesen
	 * 
	 * @param Reihe
	 * @return Download
	 */
	public Download getDownload(int reihe) {
		return downloads.get(reihe);
	}

	/**
	 * Benachrichtigung von Beobachteten Objekten Aktualisierung der Tabelle
	 */
	@Override
	public void update(Observable o, Object arg) {
		int index = downloads.indexOf(o);
		fireTableRowsUpdated(index, index);
	}
}