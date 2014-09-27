package de.techspread.wakclient.notenabfrage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import org.apache.http.client.ClientProtocolException;

import de.techspread.wakclient.common.Einstellungen;
import de.techspread.wakclient.common.WebClient;

/**
 * Notenabfrage-Tab
 * 
 * @author Patrick Gotthard
 */
public class NotenAbfrageTab extends JPanel {

	private static final long	serialVersionUID	= 8721314219961752125L;
	private Einstellungen		einstellungen;
	private ModulListe			liste;
	private NotenTabelleModel	tabelle;
	private JPanel				pnlSemesterAuswahl;
	private JPanel				pnlStatistikSemester;
	private JPanel				pnlNoten;
	private JComboBox			comboSemesterAuswahl;
	private JLabel				lblModuleSemester;
	private JLabel				moduleSemester;
	private JLabel				lblCreditsSemester;
	private JLabel				creditsSemester;
	private JLabel				lblDurchschnittSemester;
	private JLabel				durchschnittSemester;
	private JPanel				pnlStatistikGesamt;
	private JLabel				lblModuleGesamt;
	private JLabel				moduleGesamt;
	private JLabel				lblCreditsGesamt;
	private JLabel				creditsGesamt;
	private JLabel				lblDurchschnittGesamt;
	private JLabel				durchschnittGesamt;
	private JScrollPane			scrollPane;
	private JTable				table;

	/**
	 * Tab erzeugen
	 * 
	 * @param einstellungen
	 * @param client
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public NotenAbfrageTab(Einstellungen einstellungen, WebClient client) throws ClientProtocolException, NoSuchAlgorithmException, IOException {
		this.einstellungen = einstellungen;
		liste = new ModulListe(client);
		tabelle = new NotenTabelleModel(liste, einstellungen);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0 };
		setLayout(gridBagLayout);
		
		boolean abrufbar = true;
		try {
			liste.get(0);
		} catch(Exception e) {
			abrufbar = false;
		}		
		
		if(abrufbar) {	
			pnlSemesterAuswahl = new JPanel();
			pnlSemesterAuswahl.setBorder(new TitledBorder(null, "Semester auswählen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	
			GridBagConstraints gbc_pnlSemesterAuswahl = new GridBagConstraints();
			gbc_pnlSemesterAuswahl.insets = new Insets(10, 10, 10, 10);
			gbc_pnlSemesterAuswahl.anchor = GridBagConstraints.NORTH;
			gbc_pnlSemesterAuswahl.fill = GridBagConstraints.HORIZONTAL;
			gbc_pnlSemesterAuswahl.gridx = 0;
			gbc_pnlSemesterAuswahl.gridy = 0;
			add(pnlSemesterAuswahl, gbc_pnlSemesterAuswahl);
	
			GridBagLayout gbl_pnlSemesterAuswahl = new GridBagLayout();
			gbl_pnlSemesterAuswahl.columnWidths = new int[] { 0, 0 };
			gbl_pnlSemesterAuswahl.rowHeights = new int[] { 0, 0 };
			gbl_pnlSemesterAuswahl.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_pnlSemesterAuswahl.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			pnlSemesterAuswahl.setLayout(gbl_pnlSemesterAuswahl);
	
			comboSemesterAuswahl = new JComboBox(new SemesterComboBoxModel(liste, einstellungen));
			comboSemesterAuswahl.addActionListener(new ComboSemesterAuswahlActionListener());
			GridBagConstraints gbc_comboSemesterAuswahl = new GridBagConstraints();
			gbc_comboSemesterAuswahl.insets = new Insets(5, 10, 10, 10);
			gbc_comboSemesterAuswahl.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboSemesterAuswahl.gridx = 0;
			gbc_comboSemesterAuswahl.gridy = 0;
			pnlSemesterAuswahl.add(comboSemesterAuswahl, gbc_comboSemesterAuswahl);
	
			pnlNoten = new JPanel();
			pnlNoten.setBorder(new TitledBorder(null, "Noten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_pnlNoten = new GridBagConstraints();
			gbc_pnlNoten.gridheight = 3;
			gbc_pnlNoten.insets = new Insets(10, 0, 10, 10);
			gbc_pnlNoten.fill = GridBagConstraints.BOTH;
			gbc_pnlNoten.gridx = 1;
			gbc_pnlNoten.gridy = 0;
			add(pnlNoten, gbc_pnlNoten);
	
			GridBagLayout gbl_pnlNoten = new GridBagLayout();
			gbl_pnlNoten.columnWidths = new int[] { 0, 0 };
			gbl_pnlNoten.rowHeights = new int[] { 0, 0 };
			gbl_pnlNoten.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_pnlNoten.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
			pnlNoten.setLayout(gbl_pnlNoten);
	
			scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.insets = new Insets(5, 10, 10, 10);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			pnlNoten.add(scrollPane, gbc_scrollPane);
	
			table = new JTable(tabelle);
			table.setShowVerticalLines(false);
			table.getColumnModel().getColumn(1).setMaxWidth(50);
			table.getColumnModel().getColumn(2).setMaxWidth(50);
			table.getColumnModel().getColumn(3).setMaxWidth(50);
			table.getColumnModel().getColumn(4).setMaxWidth(50);
			table.setRowHeight(25);
			scrollPane.setViewportView(table);
			pnlStatistikSemester = new JPanel();
			pnlStatistikSemester.setBorder(new TitledBorder(null, "Statistik Semester", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_pnlStatistikSemester = new GridBagConstraints();
			gbc_pnlStatistikSemester.anchor = GridBagConstraints.NORTH;
			gbc_pnlStatistikSemester.insets = new Insets(0, 10, 10, 10);
			gbc_pnlStatistikSemester.fill = GridBagConstraints.HORIZONTAL;
			gbc_pnlStatistikSemester.gridx = 0;
			gbc_pnlStatistikSemester.gridy = 1;
			add(pnlStatistikSemester, gbc_pnlStatistikSemester);
	
			GridBagLayout gbl_pnlStatistikSemester = new GridBagLayout();
			pnlStatistikSemester.setLayout(gbl_pnlStatistikSemester);
			lblModuleSemester = new JLabel("Anzahl Module:");
			GridBagConstraints gbc_lblModuleSemester = new GridBagConstraints();
			gbc_lblModuleSemester.anchor = GridBagConstraints.WEST;
			gbc_lblModuleSemester.insets = new Insets(5, 10, 10, 10);
			gbc_lblModuleSemester.gridx = 0;
			gbc_lblModuleSemester.gridy = 0;
			pnlStatistikSemester.add(lblModuleSemester, gbc_lblModuleSemester);
	
			moduleSemester = new JLabel(String.valueOf(liste.module(einstellungen.getSemester())));
			GridBagConstraints gbc_moduleSemester = new GridBagConstraints();
			gbc_moduleSemester.anchor = GridBagConstraints.WEST;
			gbc_moduleSemester.insets = new Insets(5, 0, 10, 10);
			gbc_moduleSemester.gridx = 1;
			gbc_moduleSemester.gridy = 0;
			pnlStatistikSemester.add(moduleSemester, gbc_moduleSemester);
	
			lblCreditsSemester = new JLabel("Erhaltene Credits:");
			GridBagConstraints gbc_lblCreditsSemester = new GridBagConstraints();
			gbc_lblCreditsSemester.anchor = GridBagConstraints.WEST;
			gbc_lblCreditsSemester.insets = new Insets(0, 10, 10, 10);
			gbc_lblCreditsSemester.gridx = 0;
			gbc_lblCreditsSemester.gridy = 1;
			pnlStatistikSemester.add(lblCreditsSemester, gbc_lblCreditsSemester);
	
			creditsSemester = new JLabel(String.valueOf(liste.credits(einstellungen.getSemester())));
			GridBagConstraints gbc_creditsSemester = new GridBagConstraints();
			gbc_creditsSemester.anchor = GridBagConstraints.WEST;
			gbc_creditsSemester.insets = new Insets(0, 0, 10, 10);
			gbc_creditsSemester.gridx = 1;
			gbc_creditsSemester.gridy = 1;
			pnlStatistikSemester.add(creditsSemester, gbc_creditsSemester);
	
			lblDurchschnittSemester = new JLabel("Durchschnitt:");
			GridBagConstraints gbc_lblDurchschnittSemester = new GridBagConstraints();
			gbc_lblDurchschnittSemester.anchor = GridBagConstraints.WEST;
			gbc_lblDurchschnittSemester.insets = new Insets(0, 10, 10, 10);
			gbc_lblDurchschnittSemester.gridx = 0;
			gbc_lblDurchschnittSemester.gridy = 2;
			pnlStatistikSemester.add(lblDurchschnittSemester, gbc_lblDurchschnittSemester);
	
			durchschnittSemester = new JLabel(String.valueOf(liste.durchschnitt(einstellungen.getSemester())));
			GridBagConstraints gbc_durchschnittSemester = new GridBagConstraints();
			gbc_durchschnittSemester.anchor = GridBagConstraints.WEST;
			gbc_durchschnittSemester.insets = new Insets(0, 0, 10, 10);
			gbc_durchschnittSemester.gridx = 1;
			gbc_durchschnittSemester.gridy = 2;
			pnlStatistikSemester.add(durchschnittSemester, gbc_durchschnittSemester);
	
			pnlStatistikGesamt = new JPanel();
			pnlStatistikGesamt.setBorder(new TitledBorder(null, "Statistik gesamt", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_pnlStatistikGesamt = new GridBagConstraints();
			gbc_pnlStatistikGesamt.anchor = GridBagConstraints.NORTH;
			gbc_pnlStatistikGesamt.insets = new Insets(0, 10, 10, 10);
			gbc_pnlStatistikGesamt.fill = GridBagConstraints.HORIZONTAL;
			gbc_pnlStatistikGesamt.gridx = 0;
			gbc_pnlStatistikGesamt.gridy = 2;
			add(pnlStatistikGesamt, gbc_pnlStatistikGesamt);
	
			GridBagLayout gbl_pnlStatistikGesamt = new GridBagLayout();
			gbl_pnlStatistikGesamt.columnWidths = new int[] { 0, 0, 0 };
			gbl_pnlStatistikGesamt.rowHeights = new int[] { 0, 0, 0, 0 };
			gbl_pnlStatistikGesamt.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
			gbl_pnlStatistikGesamt.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
			pnlStatistikGesamt.setLayout(gbl_pnlStatistikGesamt);
	
			lblModuleGesamt = new JLabel("Anzahl Module:");
			GridBagConstraints gbc_lblModuleGesamt = new GridBagConstraints();
			gbc_lblModuleGesamt.anchor = GridBagConstraints.WEST;
			gbc_lblModuleGesamt.insets = new Insets(5, 10, 10, 10);
			gbc_lblModuleGesamt.gridx = 0;
			gbc_lblModuleGesamt.gridy = 0;
			pnlStatistikGesamt.add(lblModuleGesamt, gbc_lblModuleGesamt);
	
			moduleGesamt = new JLabel(liste.module());
			GridBagConstraints gbc_moduleGesamt = new GridBagConstraints();
			gbc_moduleGesamt.anchor = GridBagConstraints.WEST;
			gbc_moduleGesamt.insets = new Insets(5, 0, 10, 10);
			gbc_moduleGesamt.gridx = 1;
			gbc_moduleGesamt.gridy = 0;
			pnlStatistikGesamt.add(moduleGesamt, gbc_moduleGesamt);
	
			lblCreditsGesamt = new JLabel("Erhaltene Credits:");
			GridBagConstraints gbc_lblCreditsGesamt = new GridBagConstraints();
			gbc_lblCreditsGesamt.anchor = GridBagConstraints.WEST;
			gbc_lblCreditsGesamt.insets = new Insets(0, 10, 10, 10);
			gbc_lblCreditsGesamt.gridx = 0;
			gbc_lblCreditsGesamt.gridy = 1;
			pnlStatistikGesamt.add(lblCreditsGesamt, gbc_lblCreditsGesamt);
	
			creditsGesamt = new JLabel(liste.credits());
			GridBagConstraints gbc_creditsGesamt = new GridBagConstraints();
			gbc_creditsGesamt.anchor = GridBagConstraints.WEST;
			gbc_creditsGesamt.insets = new Insets(0, 0, 10, 10);
			gbc_creditsGesamt.gridx = 1;
			gbc_creditsGesamt.gridy = 1;
			pnlStatistikGesamt.add(creditsGesamt, gbc_creditsGesamt);
	
			lblDurchschnittGesamt = new JLabel("Durchschnitt:");
			GridBagConstraints gbc_lblDurchschnittGesamt = new GridBagConstraints();
			gbc_lblDurchschnittGesamt.anchor = GridBagConstraints.WEST;
			gbc_lblDurchschnittGesamt.insets = new Insets(0, 10, 10, 10);
			gbc_lblDurchschnittGesamt.gridx = 0;
			gbc_lblDurchschnittGesamt.gridy = 2;
			pnlStatistikGesamt.add(lblDurchschnittGesamt, gbc_lblDurchschnittGesamt);
	
			durchschnittGesamt = new JLabel(liste.durchschnitt());
			GridBagConstraints gbc_durchschnittGesamt = new GridBagConstraints();
			gbc_durchschnittGesamt.insets = new Insets(0, 0, 10, 10);
			gbc_durchschnittGesamt.anchor = GridBagConstraints.WEST;
			gbc_durchschnittGesamt.gridx = 1;
			gbc_durchschnittGesamt.gridy = 2;
			pnlStatistikGesamt.add(durchschnittGesamt, gbc_durchschnittGesamt);
		} else {
			add(new JLabel("Noten nicht abrufbar"));
		}
	}

	/**
	 * Semester per Combobox auswählen
	 */
	private class ComboSemesterAuswahlActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int semester = comboSemesterAuswahl.getSelectedIndex() + 1;
			einstellungen.setSemester(semester);
			tabelle.setSelectedSemester(semester);
			moduleSemester.setText(String.valueOf(liste.module(semester)));
			creditsSemester.setText(String.valueOf(liste.credits(semester)));
			durchschnittSemester.setText(liste.durchschnitt(semester));
		}
	}
}
