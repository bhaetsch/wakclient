package de.techspread.wakclient.benutzerinformationen;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.http.client.ClientProtocolException;

import de.techspread.wakclient.common.WebClient;

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Benutzerinformationen-Tab
 * 
 * @author Patrick Gotthard
 */
public class BenutzerInformationenTab extends JPanel {

	private static final long	serialVersionUID	= 5607277995068140478L;
	private JLabel				lblBenutzername;
	private JLabel				benutzername;
	private JLabel				lblStudiengang;
	private JLabel				studiengang;
	private JLabel				lblStudiengruppe;
	private JLabel				studiengruppe;
	private JLabel				lblMatrikelnummer;
	private JLabel				matrikelnummer;
	private JButton btnKopiereMatrikelnummer;

	/**
	 * Panel erzeugen
	 * 
	 * @param client
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public BenutzerInformationenTab(WebClient client) throws ClientProtocolException, NoSuchAlgorithmException, IOException {

		BenutzerInformationen parser = new BenutzerInformationen(client);

		// GridBagLayout
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] {};
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		setLayout(gridBagLayout);

		// Beschriftung Benutzername
		lblBenutzername = new JLabel("Benutzername:");
		GridBagConstraints gbc_lblBenutzername = new GridBagConstraints();
		gbc_lblBenutzername.anchor = GridBagConstraints.WEST;
		gbc_lblBenutzername.insets = new Insets(0, 0, 30, 30);
		gbc_lblBenutzername.gridx = 0;
		gbc_lblBenutzername.gridy = 0;
		add(lblBenutzername, gbc_lblBenutzername);

		// Benutzername
		benutzername = new JLabel(parser.getBenutzername());
		GridBagConstraints gbc_benutzername = new GridBagConstraints();
		gbc_benutzername.gridwidth = 2;
		gbc_benutzername.anchor = GridBagConstraints.WEST;
		gbc_benutzername.insets = new Insets(0, 0, 30, 10);
		gbc_benutzername.gridx = 1;
		gbc_benutzername.gridy = 0;
		add(benutzername, gbc_benutzername);

		// Beschriftung Studiengang
		lblStudiengang = new JLabel("Studiengang:");
		GridBagConstraints gbc_lblStudiengang = new GridBagConstraints();
		gbc_lblStudiengang.anchor = GridBagConstraints.WEST;
		gbc_lblStudiengang.insets = new Insets(0, 0, 30, 30);
		gbc_lblStudiengang.gridx = 0;
		gbc_lblStudiengang.gridy = 1;
		add(lblStudiengang, gbc_lblStudiengang);

		// Studiengang
		studiengang = new JLabel(parser.getStudiengang());
		GridBagConstraints gbc_studiengang = new GridBagConstraints();
		gbc_studiengang.gridwidth = 2;
		gbc_studiengang.anchor = GridBagConstraints.WEST;
		gbc_studiengang.insets = new Insets(0, 0, 30, 10);
		gbc_studiengang.gridx = 1;
		gbc_studiengang.gridy = 1;
		add(studiengang, gbc_studiengang);

		// Beschriftung Studiengruppe
		lblStudiengruppe = new JLabel("Studiengruppe:");
		GridBagConstraints gbc_lblStudiengruppe = new GridBagConstraints();
		gbc_lblStudiengruppe.anchor = GridBagConstraints.WEST;
		gbc_lblStudiengruppe.insets = new Insets(0, 0, 30, 30);
		gbc_lblStudiengruppe.gridx = 0;
		gbc_lblStudiengruppe.gridy = 2;
		add(lblStudiengruppe, gbc_lblStudiengruppe);

		// Studiengruppe
		studiengruppe = new JLabel(parser.getStudiengruppe());
		GridBagConstraints gbc_studiengruppe = new GridBagConstraints();
		gbc_studiengruppe.gridwidth = 2;
		gbc_studiengruppe.anchor = GridBagConstraints.WEST;
		gbc_studiengruppe.insets = new Insets(0, 0, 30, 10);
		gbc_studiengruppe.gridx = 1;
		gbc_studiengruppe.gridy = 2;
		add(studiengruppe, gbc_studiengruppe);

		// Beschriftung Matrikelnummer
		lblMatrikelnummer = new JLabel("Matrikelnummer:");
		GridBagConstraints gbc_lblMatrikelnummer = new GridBagConstraints();
		gbc_lblMatrikelnummer.anchor = GridBagConstraints.WEST;
		gbc_lblMatrikelnummer.insets = new Insets(0, 0, 0, 30);
		gbc_lblMatrikelnummer.gridx = 0;
		gbc_lblMatrikelnummer.gridy = 3;
		add(lblMatrikelnummer, gbc_lblMatrikelnummer);

		// Matrikelnummer
		try {
			matrikelnummer = new JLabel(parser.getMatrikelNummer());
		} catch(Exception e) {
			matrikelnummer = new JLabel("nicht abrufbar");
		}		
		GridBagConstraints gbc_matrikelnummer = new GridBagConstraints();
		gbc_matrikelnummer.insets = new Insets(0, 0, 0, 10);
		gbc_matrikelnummer.anchor = GridBagConstraints.WEST;
		gbc_matrikelnummer.gridx = 1;
		gbc_matrikelnummer.gridy = 3;
		add(matrikelnummer, gbc_matrikelnummer);
		
		if(!matrikelnummer.getText().equals("nicht abrufbar")) {
			btnKopiereMatrikelnummer = new JButton();
			btnKopiereMatrikelnummer.addActionListener(new KopiereMatrikelnummerListener());
			btnKopiereMatrikelnummer.setIcon(new ImageIcon(this.getClass().getResource("/copy.png")));
			GridBagConstraints gbc_btnKopiereMatrikelnummer = new GridBagConstraints();
			gbc_btnKopiereMatrikelnummer.gridx = 2;
			gbc_btnKopiereMatrikelnummer.gridy = 3;
			add(btnKopiereMatrikelnummer, gbc_btnKopiereMatrikelnummer);
		}		
	}
	
	/**
	 * Matrikelnumemr in die Zwischenablage kopieren
	 */
	private class KopiereMatrikelnummerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(matrikelnummer.getText()), null);
			JOptionPane.showMessageDialog( null, "Matrikelnummer wurde in die Zwischenablage kopiert");
		}
	}
}
