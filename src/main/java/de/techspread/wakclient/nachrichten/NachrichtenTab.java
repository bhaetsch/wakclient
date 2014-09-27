package de.techspread.wakclient.nachrichten;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.text.html.HTMLDocument;

import org.apache.http.client.ClientProtocolException;

import de.techspread.wakclient.common.WebClient;

/**
 * Nachrichten-Tab
 * @author Patrick Gotthard
 *
 */
public class NachrichtenTab extends JPanel {

	private static final long serialVersionUID = -5006474219495455771L;
	private WebClient client;
	private NachrichtenListe liste;
	private NachrichtenTabelleModel model;
	private JSplitPane splitNachrichten;
	private JScrollPane scrollNachrichten;
	private JTable nachrichten;
	private JScrollPane scrollNachricht;
	private JEditorPane nachricht;

	/**
	 * Tab erzeugen
	 * @param client
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public NachrichtenTab(WebClient client) throws ClientProtocolException, NoSuchAlgorithmException, IOException {
		
		this.client = client;
		
		// Objekte erzeugen
		liste = new NachrichtenListe(client);
		model = new NachrichtenTabelleModel(liste);

		// GridBagLayout
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{1.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		setLayout(gridBagLayout);

		// Splitpane
		splitNachrichten = new JSplitPane();
		splitNachrichten.setDividerLocation(200);
		splitNachrichten.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GridBagConstraints gbc_splitNachrichten = new GridBagConstraints();
		gbc_splitNachrichten.insets = new Insets(10, 10, 10, 10);
		gbc_splitNachrichten.fill = GridBagConstraints.BOTH;
		gbc_splitNachrichten.gridx = 0;
		gbc_splitNachrichten.gridy = 0;
		add(splitNachrichten, gbc_splitNachrichten);

		// Scroll Nachrichten
		scrollNachrichten = new JScrollPane();
		splitNachrichten.setLeftComponent(scrollNachrichten);

		// Nachrichten
		nachrichten = new JTable(model);		
		nachrichten.setShowVerticalLines(false);
		nachrichten.getColumnModel().getColumn(0).setMinWidth(150);
		nachrichten.getColumnModel().getColumn(0).setMaxWidth(150);
		nachrichten.getColumnModel().getColumn(1).setMinWidth(150);		
		nachrichten.getColumnModel().getColumn(1).setMaxWidth(150);
		nachrichten.setRowHeight(25);

		// Scroll Nachricht
		scrollNachrichten.setViewportView(nachrichten);
		scrollNachricht = new JScrollPane();
		splitNachrichten.setRightComponent(scrollNachricht);

		// Nachricht
		nachricht = new JEditorPane();
		nachricht.setContentType("text/html");
		String body = "body {font-family: Arial, Helvetica, Verdana; font-size: 9px; }";
		((HTMLDocument) nachricht.getDocument()).getStyleSheet().addRule(body);
		scrollNachricht.setViewportView(nachricht);

		// Listener hinzufügen
		nachrichten.addMouseListener(new MausAuswahlAdapter());
		nachrichten.addKeyListener(new TastaturAuswahlAdapter());
		nachrichten.addKeyListener(new TastaturEntfAdapter());
	}
	
	/**
	 * Einzelne Nachricht per Mausklick ausgewählt
	 */
	class MausAuswahlAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent arg0) {
			zeigeNachricht();
		}
	}
	
	/**
	 * Nachricht per Pfeiltaste ausgewählt
	 */
	class TastaturAuswahlAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_UP || arg0.getKeyCode() == KeyEvent.VK_DOWN) {
				zeigeNachricht();
			}
		}
	}
	
	/**
	 * Entfernen gedrückt
	 */
	class TastaturEntfAdapter extends KeyAdapter {		
		public void keyReleased(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_DELETE) {
				loescheNachrichten();
			}
		}
	}

	/**
	 * Nachricht anzeigen
	 */
	private void zeigeNachricht() {
		new Thread() {
			@Override
			public void run() {
				nachricht.setText("Lade Nachricht...");
				int index = nachrichten.getSelectedRow();
				try {
					nachricht.setText(liste.getNachricht(index));
				} catch(Exception e) {
					nachricht.setText("Fehler beim Laden der Nachricht");
				}
			}
		}.start();
	}
	
	/**
	 * Nachricht löschen
	 */
	private void loescheNachrichten() {
		
		// Ausgewählte Zeilen auslesen
		int[] zeilen = nachrichten.getSelectedRows();
		
		// Prüfen, ob wirklich Zeilen ausgewählt sind
		if (zeilen.length > 0) {
			
			// Nachrichten der entsprechenden Zeilen auslesen
			ArrayList<Nachricht> messages = new ArrayList<Nachricht>();
			for (int zeile : zeilen) {
				messages.add(liste.get(zeile));
			}
			
			// Dialog vorbereiten
			String frage;
			if (messages.size() == 1) {
				frage = "Möchtest du die Nachricht \"" + messages.get(0).getBetreff() + "\" wirklich löschen?";
			} else {
				frage = "Möchtest du die " + messages.size() + " Nachrichten wirklich löschen?";
			}
			
			// Dialog anzeigen
			int answear = JOptionPane.showConfirmDialog(null, frage, "Wirklich löschen?", JOptionPane.YES_NO_OPTION);
			
			// Ja, es soll gelöscht werden
			if (answear == JOptionPane.YES_OPTION) {
				
				// Einträge aus Tabelle entfernen
				for (Nachricht nachricht : messages) {
					model.delete(nachricht);
				}
				
				// Nachrichten im Hintergrund löschen
				new LoeschThread(messages);
				
				// Zur nächsten Nachricht springen
				if(model.getRowCount() != 0) {
					int auswahl = zeilen[0];
					if(auswahl > model.getRowCount()) {
						auswahl--;
					}
					nachrichten.setRowSelectionInterval(auswahl, auswahl);							
					zeigeNachricht();
				}
			}
		}
	}
	
	/**
	 * Thread zum Löschen von Nachrichten
	 */
	class LoeschThread extends Thread {
		
		private ArrayList<Nachricht> nachrichten;
		
		public LoeschThread(ArrayList<Nachricht> nachrichten) {
			this.nachrichten = nachrichten;
			start();
		}
		
		public void run() {
			for (Nachricht nachricht : nachrichten) {
				try {
					client.get(nachricht.getLoeschUrl());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
