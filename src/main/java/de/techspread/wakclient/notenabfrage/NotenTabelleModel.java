package de.techspread.wakclient.notenabfrage;

import javax.swing.table.AbstractTableModel;

import de.techspread.wakclient.common.Einstellungen;

/**
 * Modell für die Notentabelle
 * 
 * @author Patrick Gotthard
 */
public class NotenTabelleModel extends AbstractTableModel {

	private static final long	serialVersionUID	= 1L;
	private ModulListe			liste;
	private String[]			titel				= { "Modul", "Credits", "Note 1", "Note 2", "Note 3" };
	private int					semester;

	/**
	 * Konstruktor
	 * 
	 * @param Modulliste
	 */
	public NotenTabelleModel(ModulListe liste, Einstellungen einstellungen) {
		this.liste = liste;
		semester = einstellungen.getSemester();
	}

	/**
	 * Anzahl Spalten auslesen
	 */
	@Override
	public int getColumnCount() {
		return titel.length;
	}

	/**
	 * Anzahl Zeilen auslesen
	 */
	@Override
	public int getRowCount() {
		return liste.module(semester);
	}

	/**
	 * Zelleninhalt auslesen
	 */
	@Override
	public Object getValueAt(int reihe, int spalte) {
		double note = 0.0;
		if (spalte == 0) {
			return liste.noten(semester).get(reihe).getName();
		} else if (spalte == 1) {
			return liste.noten(semester).get(reihe).getCredits();
		} else if (spalte == 2) {
			note = liste.noten(semester).get(reihe).getNote1();
		} else if (spalte == 3) {
			note = liste.noten(semester).get(reihe).getNote2();
		} else {
			note = liste.noten(semester).get(reihe).getNote3();
		}
		return note == 0.0 ? "-" : note;
	}

	/**
	 * Spaltentitel auslesen
	 */
	@Override
	public String getColumnName(int column) {
		return new String[] { "Modul", "Credits", "Note 1", "Note 2", "Note 3" }[column];
	}

	/**
	 * Gewähltes Semester festlegen
	 * 
	 * @param semester
	 */
	public void setSelectedSemester(int semester) {
		this.semester = semester;
		fireTableDataChanged();
	}
}