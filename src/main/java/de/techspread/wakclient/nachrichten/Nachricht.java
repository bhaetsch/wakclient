package de.techspread.wakclient.nachrichten;

/**
 * Speicherung der Nachrichten
 * @author Patrick Gotthard
 *
 */
public class Nachricht {

	private String datum;
	private String absender;
	private String betreff;
	private String text;
	private String leseUrl;
	private String loeschUrl;

	/**
	 * Datum auslesen
	 * @return Datum
	 */
	public String getDatum() {
		return datum;
	}

	/**
	 * Datum setzen
	 * @param Datum
	 */
	public void setDatum(String datum) {
		this.datum = datum;
	}

	/**
	 * Absender auslesen
	 * @return Absender
	 */
	public String getAbsender() {
		return absender;
	}

	/**
	 * Absender setzen
	 * @param Absender
	 */
	public void setAbsender(String absender) {
		this.absender = absender;
	}

	/**
	 * Betreff auslesen
	 * @return Betreff
	 */
	public String getBetreff() {
		return betreff;
	}

	/**
	 * Betreff setzen
	 * @param Betreff
	 */
	public void setBetreff(String betreff) {
		this.betreff = betreff;
	}

	/**
	 * Nachrichten-Text auslesen
	 * @return Nachricht-Text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Nachrichten-Text setzen
	 * @param Nachrichten-Text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * URL der Nachricht auslesen
	 * @return Nachricht-URL
	 */
	public String getLeseUrl() {
		return leseUrl;
	}

	/**
	 * URL der Nachricht setzen
	 * @param Nachricht-URL
	 */
	public void setLeseUrl(String leseUrl) {
		this.leseUrl = "/c_email.html?&action=getviewmessagessingle&msg_uid=" + leseUrl;
	}

	/**
	 * URL zum Löschen der Nachricht auslesen
	 * @return URL zum Löschen der Nachricht
	 */
	public String getLoeschUrl() {
		return loeschUrl;
	}

	/**
	 * URL zum Löschen der Nachricht setzen
	 * @param URL zum Löschen der Nachricht
	 */
	public void setLoeschUrl(String loeschUrl) {
		this.loeschUrl = "/c_email.html?&action=getviewmessagesdelete&msg_uid=" + loeschUrl;
	}
}
