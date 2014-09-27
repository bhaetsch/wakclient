package de.techspread.wakclient.common;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.apache.http.client.ClientProtocolException;

import de.techspread.wakclient.anmeldung.AnmeldungTab;
import de.techspread.wakclient.benutzerinformationen.BenutzerInformationenTab;
import de.techspread.wakclient.dateiablage.DateiablageTab;
import de.techspread.wakclient.nachrichten.NachrichtenTab;
import de.techspread.wakclient.notenabfrage.NotenAbfrageTab;

/**
 * Hauptfenster
 * 
 * @author Patrick Gotthard
 */
public class MainFrame {

	private Einstellungen	einstellungen	= new Einstellungen();
	private WebClient		client			= new WebClient(einstellungen);
	private JFrame			frmWakclient;
	private JTabbedPane		tabbedPane;
	private JMenuBar menuBar;
	private JMenu mnDatei;
	private JMenuItem mntmBeenden;
	private JMenuItem mntmUeber;
	private JMenuItem mntmKontaktAufnehmen;

	/**
	 * Anwendung starten
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmWakclient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Konstruktor
	 */
	public MainFrame() {
		initialize();
		tabbedPane.add("Anmeldung", new AnmeldungTab(this, einstellungen, client));
		frmWakclient.setJMenuBar(getMenuBar());
	}

	/**
	 * Fenster aufbauen
	 */
	private void initialize() {
		frmWakclient = new JFrame();
		frmWakclient.setTitle("WAKClient");
		frmWakclient.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				beenden();
			}
		});
		frmWakclient.setSize(700, 550);
		frmWakclient.setLocationRelativeTo(null);
		frmWakclient.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmWakclient.setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		frmWakclient.getContentPane().setLayout(gridBagLayout);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(10, 10, 10, 10);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frmWakclient.getContentPane().add(tabbedPane, gbc_tabbedPane);
	}

	/**
	 * Restliche Tabs anzeigen
	 * 
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public void showComponents() throws ClientProtocolException, NoSuchAlgorithmException, IOException {
		tabbedPane.add("Benutzerinformationen", new BenutzerInformationenTab(client));
		tabbedPane.add("Nachrichten", new NachrichtenTab(client));
		tabbedPane.add("Dateiablage", new DateiablageTab(einstellungen, client));
		tabbedPane.add("Notenabfrage", new NotenAbfrageTab(einstellungen, client));
	}
	private JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			
			mnDatei = new JMenu("Datei");
			menuBar.add(mnDatei);
			
			mntmUeber = new JMenuItem("Über WAKClient");
			mntmUeber.addActionListener(new MntmUeberActionListener());
			mnDatei.add(mntmUeber);
			
			mntmKontaktAufnehmen = new JMenuItem("Kontakt aufnehmen");
			mntmKontaktAufnehmen.addActionListener(new MntmKontaktAufnehmenActionListener());
			mnDatei.add(mntmKontaktAufnehmen);
			
			mntmBeenden = new JMenuItem("Beenden");
			mntmBeenden.addActionListener(new MntmBeendenActionListener());
			mnDatei.add(mntmBeenden);
		}
		return menuBar;
	}
	
	private void beenden() {
		// Sicherheitsabfrage bei aktiver Synchronisation
		if (client.isActiveSync()) {			
			String frage = "Es wird noch synchronisiert. Wenn du jetzt " +
					"beendest, können unvollständige Dateien entstehen. " +
					"Möchtest du trotzdem beenden?";			
			int answear = JOptionPane.showConfirmDialog(null, frage, "Synchronisation aktiv", JOptionPane.YES_NO_OPTION);			
			if (answear == JOptionPane.YES_OPTION) {
				System.exit(0);
			}			
		} else {
			System.exit(0);
		}
	}
	
	private class MntmBeendenActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			beenden();
		}
	}
	
	private class MntmKontaktAufnehmenActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				Desktop.getDesktop().mail(new URI("mailto:patrick@techspread.de?subject=WAKClient"));
			} catch(Exception e) {
				e.printStackTrace();
			}			
		}
	}
	private class MntmUeberActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new AboutWindow();
		}
	}
}
