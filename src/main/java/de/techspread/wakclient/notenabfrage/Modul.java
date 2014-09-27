package de.techspread.wakclient.notenabfrage;

/** Modul. */
public class Modul {

	/** Semester. */
	private int semester;
	
	/** Modulname. */
	private String name;
	
	/** Anzahl Credits. */
	private int credits;
	
	/** Note des ersten Versuchs. */
	private double note1;
	
	/*** Note des zweiten Versuchs. */
	private double note2;
	
	/** Note des dritten Versuchs. */
	private double note3;

	/**
	 * Semester auslesen.
	 * @return Semester
	 */
	public final int getSemester() {
		return semester;
	}

	/**
	 * Semester festlegen.
	 * @param semester Semester
	 */
	public final void setSemester(final int semester) {
		this.semester = semester;
	}

	/**
	 * Modulname auslesen.
	 * @return Modulname
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Modulname setzen.
	 * @param name Modulname
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * Credits auslesen. 
	 * @return Credits
	 */
	public final int getCredits() {
		return credits;
	}

	/**
	 * Credits festlegen.
	 * @param credits Credits
	 */
	public final void setCredits(final int credits) {
		this.credits = credits;
	}

	/**
	 * Note des ersten Versuchs auslesen.
	 * @return Note des ersten Versuchs
	 */
	public final double getNote1() {
		return note1;
	}

	/**
	 * Note des ersten Versuchs festlegen.
	 * @param note Note des ersten Versuchs
	 */
	public final void setNote1(final double note) {
		this.note1 = note;
	}

	/**
	 * Note des zweiten Versuchs auslesen.
	 * @return Note des zweiten Versuchs
	 */
	public final double getNote2() {
		return note2;
	}

	/**
	 * Note des zweiten Versuchs festlegen.
	 * @param note Note des zweiten Versuchs
	 */
	public final void setNote2(final double note) {
		this.note2 = note;
	}

	/**
	 * Note des dritten Versuchs auslesen.
	 * @return Note des dritten Versuchs
	 */
	public final double getNote3() {
		return note3;
	}

	/**
	 * Note des dritten Versuchs festlegen.
	 * @param note Note des dritten Versuchs
	 */
	public final void setNote3(final double note) {
		this.note3 = note;
	}
}
