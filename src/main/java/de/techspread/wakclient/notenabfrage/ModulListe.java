package de.techspread.wakclient.notenabfrage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import de.techspread.regex.RegexParser;
import de.techspread.wakclient.common.WebClient;

/**
 * Modul-Liste
 * @author Patrick Gotthard
 *
 */
public class ModulListe extends ArrayList<Modul> {

	private static final long serialVersionUID = 4608922057521066680L;
	private WebClient client;

	/**
	 * Konstruktor
	 * @param client
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public ModulListe(WebClient client) throws ClientProtocolException, NoSuchAlgorithmException, IOException {
		this.client = client;
		parseModule();
	}

	/**
	 * Module auslesen
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private void parseModule() throws ClientProtocolException, NoSuchAlgorithmException, IOException {
		ArrayList<String[]> module = RegexParser.find(client.getNotenSeite(), "<tr .*?>.*?<td .*?>(.?)</td>.*?<td>(.*?)</td>.*?<td .*?>.*?</td>.*?<td .*?>(.*?)</td>.*?<td .*?>(.*?)</td>.*?<td .*?>(.*?)</td>.*?<td .*?>(.*?)</td>.*?</tr>");
		for(String[] modul : module) {
			String semester = modul[0];
			String name = modul[1];
			String credits = modul[2];
			String note1 = modul[3].replace(",", ".");
			String note2 = modul[4].replace(",", ".");
			String note3 = modul[5].replace(",", ".");
			if (note1.equals("ext.")) {
				note1 = "0.0";
			}
			if (note2.equals("-")) {
				note2 = "0.0";
				note3 = "0.0";
			} else if (note3.equals("-")) {
				note3 = "0.0";
			}
			Modul temp = new Modul();
			temp.setSemester(Integer.parseInt(semester));
			temp.setName(name);
			temp.setCredits(Integer.parseInt(credits));
			temp.setNote1(Double.parseDouble(note1));
			temp.setNote2(Double.parseDouble(note2));
			temp.setNote3(Double.parseDouble(note3));
			add(temp);
		}
	}

	/**
	 * Semesteranzahl auslesen
	 * @return
	 */
	public int semester() {
		int semester = 0;
		int anzahl = 0;
		for(Modul modul : this) {
			if(modul.getSemester() != semester) {
				anzahl++;
				semester = modul.getSemester();
			}
		}
		return anzahl;
	}

	/**
	 * Anzahl Module des Semesters auslesen
	 * @param Semester
	 * @return Anzahl Module des Semesters
	 */
	public int module(int semester) {
		int anzahl = 0;
		for(Modul modul : this) {
			if(modul.getSemester() == semester) {
				anzahl++;
			}
		}
		return anzahl;
	}

	/**
	 * Erhaltene Credits des Semesters auslesen
	 * @param Semester
	 * @return Erhaltene Credits des Semesters
	 */
	public int credits(int semester) {
		int anzahl = 0;
		for(Modul modul : this) {
			if(modul.getSemester() == semester) {
				anzahl += modul.getCredits();
			}
		}
		return anzahl;
	}

	/**
	 * Note zur Berechnung des Durchschnitts auslesen
	 * @param Modul
	 * @return Note zur Berechnung des Durchschnitts
	 */
	private double relevanteNote(Modul modul) {
		if(modul.getNote3() != 0) {
			return modul.getNote3();
		} else if(modul.getNote2() != 0) {
			return modul.getNote2();
		} else {
			return modul.getNote1();
		}
	}

	/**
	 * Semester-Durchschnitt berechnen
	 * @param Semester
	 * @return Semester-Durchschnitt
	 */
	public String durchschnitt(int semester) {
		double durchschnitt = 0.0;
		for(Modul modul : this) {
			if(modul.getSemester() == semester) {
				durchschnitt += modul.getCredits() * relevanteNote(modul);
			}
		}
		durchschnitt /= credits(semester);
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(durchschnitt);
	}

	/**
	 * Module eines Semesters auslesen
	 * @param Semester
	 * @return Module des Semesters
	 */
	public ArrayList<Modul> noten(int semester) {
		ArrayList<Modul> module = new ArrayList<Modul>();
		for(Modul note : this) {
			if(note.getSemester() == semester) {
				module.add(note);
			}
		}
		return module;
	}

	/**
	 * Anzahl Module berechnen
	 * @return Anzahl Module
	 */
	public String module() {
		return String.valueOf(size());
	}

	/**
	 * Anzahl Credits berechnen
	 * @return Anzahlk Credits
	 */
	public String credits() {
		int summe = 0;
		for(Modul modul : this) {
			summe += modul.getCredits();
		}
		return String.valueOf(summe);
	}

	/**
	 * Gesamtdurchschnitt berechnen
	 * @return Gesamtdurchschnitt
	 */
	public String durchschnitt() {
		double durchschnitt = 0.0;
		for(Modul modul : this) {
			durchschnitt += modul.getCredits() * relevanteNote(modul);
		}
		durchschnitt /= Integer.parseInt(credits());
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(durchschnitt);
	}
}
