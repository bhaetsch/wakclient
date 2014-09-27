package de.techspread.wakclient.dateiablage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import de.techspread.wakclient.common.Einstellungen;
import de.techspread.wakclient.common.WebClient;

/**
 * Dateiablage-Tab
 * 
 * @author Patrick Gotthard
 */
public class DateiablageTab extends JPanel {

	private static final long		serialVersionUID	= 7883827810287428227L;
	private DateiablageTab			dateiablage;
	private Einstellungen			einstellungen;
	private WebClient				client;
	private DownloadTabelleModel	model;
	private JPanel					pnlSync;
	private JLabel					verzeichnis;
	protected JButton				auswaehlen;
	protected JButton				starten;
	private JPanel					pnlDownloads;
	private JScrollPane				scrollPane;
	private JTable					table;

	/**
	 * Tab erzeugen
	 * 
	 * @param einstellungen
	 * @param client
	 */
	public DateiablageTab(Einstellungen einstellungen, WebClient client) {

		// Referenzen
		this.dateiablage = this;
		this.einstellungen = einstellungen;
		this.client = client;

		// GridBagLayout
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.rowWeights = new double[] { 0.0, 1.0 };
		gbl_panel.columnWeights = new double[] { 1.0 };
		setLayout(gbl_panel);

		// Sync Panel
		pnlSync = new JPanel();
		pnlSync.setBorder(new TitledBorder(null, "Verzeichnis auswählen und starten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlSync = new GridBagConstraints();
		gbc_pnlSync.fill = GridBagConstraints.BOTH;
		gbc_pnlSync.insets = new Insets(10, 10, 10, 10);
		gbc_pnlSync.gridx = 0;
		gbc_pnlSync.gridy = 0;
		add(pnlSync, gbc_pnlSync);

		// GridBagLayout Sync Panel
		GridBagLayout gbl_pnlSync = new GridBagLayout();
		gbl_pnlSync.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_pnlSync.rowHeights = new int[] { 0, 0 };
		gbl_pnlSync.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlSync.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlSync.setLayout(gbl_pnlSync);

		// Verzeichnis
		verzeichnis = new JLabel(einstellungen.getVerzeichnis());
		GridBagConstraints gbc_verzeichnis = new GridBagConstraints();
		gbc_verzeichnis.anchor = GridBagConstraints.WEST;
		gbc_verzeichnis.insets = new Insets(5, 10, 10, 10);
		gbc_verzeichnis.gridx = 0;
		gbc_verzeichnis.gridy = 0;
		pnlSync.add(verzeichnis, gbc_verzeichnis);

		// Auswählen
		auswaehlen = new JButton("auswählen");
		auswaehlen.addActionListener(new AuswaehlenActionListener());
		GridBagConstraints gbc_auswaehlen = new GridBagConstraints();
		gbc_auswaehlen.insets = new Insets(5, 0, 10, 0);
		gbc_auswaehlen.gridx = 1;
		gbc_auswaehlen.gridy = 0;
		pnlSync.add(auswaehlen, gbc_auswaehlen);

		// Synchronisieren
		starten = new JButton("starten");
		starten.addActionListener(new SynchronisierenActionListener());
		GridBagConstraints gbc_starten = new GridBagConstraints();
		gbc_starten.insets = new Insets(5, 0, 10, 10);
		gbc_starten.gridx = 2;
		gbc_starten.gridy = 0;
		pnlSync.add(starten, gbc_starten);

		pnlDownloads = new JPanel();
		pnlDownloads.setBorder(new TitledBorder(null, "Neue Dateien", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlDownloads = new GridBagConstraints();
		gbc_pnlDownloads.insets = new Insets(0, 10, 10, 10);
		gbc_pnlDownloads.fill = GridBagConstraints.BOTH;
		gbc_pnlDownloads.gridx = 0;
		gbc_pnlDownloads.gridy = 1;
		add(pnlDownloads, gbc_pnlDownloads);
		GridBagLayout gbl_pnlDownloads = new GridBagLayout();
		gbl_pnlDownloads.columnWidths = new int[] { 0, 0 };
		gbl_pnlDownloads.rowHeights = new int[] { 0, 0 };
		gbl_pnlDownloads.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_pnlDownloads.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlDownloads.setLayout(gbl_pnlDownloads);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(5, 10, 10, 10);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		pnlDownloads.add(scrollPane, gbc_scrollPane);

		// Model für die Tabelle
		model = new DownloadTabelleModel();
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));
			}
		});

		table = new JTable(model);
		table.setShowVerticalLines(false);
		table.getColumnModel().getColumn(1).setMaxWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		FortschrittsBalken balken = new FortschrittsBalken();
		table.setDefaultRenderer(JProgressBar.class, balken);
		scrollPane.setViewportView(table);
	}

	/**
	 * Aktion für den auswählen-Knopf
	 * 
	 * @author Patrick Gotthard
	 */
	private class AuswaehlenActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int state = jfc.showOpenDialog(null);
			if (state == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile();
				String pfad = file.getAbsolutePath() + File.separator;
				verzeichnis.setText(pfad);
				einstellungen.setVerzeichnis(pfad);
			}
		}
	}

	/**
	 * Aktion für den synchronisieren-Knopf
	 * 
	 * @author Patrick Gotthard
	 */
	private class SynchronisierenActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (new File(einstellungen.getVerzeichnis()).exists()) {
				new Synchronisierer(dateiablage, model, einstellungen, client);
			} else {
				JOptionPane.showMessageDialog(null, "Der angegebene Ordner existiert nicht");
			}
		}
	}
}
