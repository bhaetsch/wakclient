package de.techspread.wakclient.notenabfrage;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import de.techspread.wakclient.common.Einstellungen;

/**
 * Modell für die Semester-ComboBox
 * 
 * @author Patrick
 */
public class SemesterComboBoxModel implements ComboBoxModel {

	private ModulListe		liste;
	private Einstellungen	einstellungen;
	private Object			selected	= null;

	/**
	 * Konstruktor
	 * 
	 * @param Modulliste
	 */
	public SemesterComboBoxModel(ModulListe liste, Einstellungen einstellungen) {
		this.liste = liste;
		this.einstellungen = einstellungen;
	}

	/**
	 * Markierten Eintrag auslesen
	 */
	@Override
	public Object getSelectedItem() {
		if (selected == null) {
			return getElementAt(einstellungen.getSemester() - 1);
		} else {
			return selected;
		}
	}

	/**
	 * Markierten Eintrag setzen
	 */
	@Override
	public void setSelectedItem(Object arg0) {
		selected = arg0;
	}

	/**
	 * Eintrag auslesen
	 */
	@Override
	public Object getElementAt(int index) {
		return "Semester " + (index + 1);
	}

	/**
	 * Anzahl Einträge auslesen
	 */
	@Override
	public int getSize() {
		return liste.semester();
	}

	@Override
	public void addListDataListener(ListDataListener arg0) {}

	@Override
	public void removeListDataListener(ListDataListener arg0) {}
}
