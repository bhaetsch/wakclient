package de.techspread.wakclient.anmeldung;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import de.techspread.wakclient.common.Einstellungen;
import de.techspread.wakclient.common.MainFrame;
import de.techspread.wakclient.common.WebClient;

/** Tabreiter Anmeldung. */
public class AnmeldungTab extends JPanel {

	/** ID für die Serialisierung. */
	private static final long	serialVersionUID	= -433525275674478989L;

	/** Hauptfenster. */
	private MainFrame			frame;

	/** Einstellungen. */
	private Einstellungen		einstellungen;

	/** Webclient. */
	private WebClient			client;
	
	/** E-Mail Beschriftung. */
	private JLabel				lblEmail;
	
	/** E-Mail Eingabefeld. */
	private JTextField			email;
	
	/** Passwort Beschriftung. */
	private JLabel				lblPasswort;
	
	/** Passwort Eingabefeld. */
	private JPasswordField		passwort;
	
	/** Statusanzeige. */
	private JLabel				status;
	
	/** Anmeldebutton. */
	private JButton				anmelden;
	
	/** Checkbox zur aktivierung eines Proxy-Servers. */
	private JCheckBox			proxy;
	
	/** Proxy Server Beschriftung. */
	private JLabel				lblServer;
	
	/** Proxy Port Beschriftung. */
	private JLabel				lblPort;
	
	/** Proxy Server Eingabefeld. */
	private JTextField			server;
	
	/** Proxy Port Eingabefeld. */
	private JTextField			port;

	/**
	 * Tabreiter Anmeldung erzeugen.
	 * @param aFrame Hauptfenster
	 * @param einstellungen Einstellungen
	 * @param client Webclient
	 */
	public AnmeldungTab(final MainFrame aFrame, final Einstellungen einstellungen, final WebClient client) {

		// Referenzen
		this.frame = aFrame;
		this.einstellungen = einstellungen;
		this.client = client;

		// GridBagLayout
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[] { 0.0, 0.0 };
		setLayout(gbl_panel);

		// Beschriftung E-Mail
		lblEmail = new JLabel("E-Mail:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 200, 10, 0);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 0;
		add(lblEmail, gbc_lblEmail);

		// E-Mail
		email = new JTextField();
		email.setText(einstellungen.getEmail());
		GridBagConstraints gbc_email = new GridBagConstraints();
		gbc_email.weightx = 1.0;
		gbc_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_email.gridwidth = 2;
		gbc_email.insets = new Insets(0, 200, 10, 200);
		gbc_email.gridx = 0;
		gbc_email.gridy = 1;
		add(email, gbc_email);

		// Beschriftung Passwort
		lblPasswort = new JLabel("Passwort:");
		GridBagConstraints gbc_lblPasswort = new GridBagConstraints();
		gbc_lblPasswort.anchor = GridBagConstraints.WEST;
		gbc_lblPasswort.insets = new Insets(0, 200, 10, 0);
		gbc_lblPasswort.gridx = 0;
		gbc_lblPasswort.gridy = 2;
		add(lblPasswort, gbc_lblPasswort);

		// Passwort
		passwort = new JPasswordField();
		passwort.setText(einstellungen.getPasswort());
		GridBagConstraints gbc_passwort = new GridBagConstraints();
		gbc_passwort.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwort.gridwidth = 2;
		gbc_passwort.insets = new Insets(0, 200, 10, 200);
		gbc_passwort.gridx = 0;
		gbc_passwort.gridy = 3;
		add(passwort, gbc_passwort);

		// Status
		status = new JLabel();
		GridBagConstraints gbc_status = new GridBagConstraints();
		gbc_status.fill = GridBagConstraints.HORIZONTAL;
		gbc_status.insets = new Insets(0, 200, 10, 10);
		gbc_status.gridx = 0;
		gbc_status.gridy = 4;
		add(status, gbc_status);

		// Anmelden
		anmelden = new JButton("anmelden");
		anmelden.addActionListener(new AnmeldenActionListener());
		GridBagConstraints gbc_anmelden = new GridBagConstraints();
		gbc_anmelden.anchor = GridBagConstraints.EAST;
		gbc_anmelden.insets = new Insets(0, 0, 10, 200);
		gbc_anmelden.gridx = 1;
		gbc_anmelden.gridy = 4;
		add(anmelden, gbc_anmelden);

		// Proxy Checkbox
		proxy = new JCheckBox("Proxy benutzen");
		proxy.addActionListener(new ProxyActionListener());
		proxy.setSelected(einstellungen.isProxyBenutzen());
		GridBagConstraints gbc_proxy = new GridBagConstraints();
		gbc_proxy.fill = GridBagConstraints.HORIZONTAL;
		gbc_proxy.gridwidth = 2;
		gbc_proxy.insets = new Insets(0, 200, 10, 0);
		gbc_proxy.gridx = 0;
		gbc_proxy.gridy = 5;
		add(proxy, gbc_proxy);

		// Beschriftung Server
		lblServer = new JLabel("Server:");
		lblServer.setVisible(einstellungen.isProxyBenutzen());
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.anchor = GridBagConstraints.WEST;
		gbc_lblServer.insets = new Insets(0, 200, 10, 10);
		gbc_lblServer.gridx = 0;
		gbc_lblServer.gridy = 6;
		add(lblServer, gbc_lblServer);

		// Beschriftung Port
		lblPort = new JLabel("Port:");
		lblPort.setVisible(einstellungen.isProxyBenutzen());
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.WEST;
		gbc_lblPort.insets = new Insets(0, 0, 10, 0);
		gbc_lblPort.gridx = 1;
		gbc_lblPort.gridy = 6;
		add(lblPort, gbc_lblPort);

		// Server
		server = new JTextField();
		server.setText(einstellungen.getServer());
		server.setVisible(einstellungen.isProxyBenutzen());
		GridBagConstraints gbc_server = new GridBagConstraints();
		gbc_server.weightx = 1.0;
		gbc_server.fill = GridBagConstraints.HORIZONTAL;
		gbc_server.insets = new Insets(0, 200, 0, 10);
		gbc_server.gridx = 0;
		gbc_server.gridy = 7;
		add(server, gbc_server);

		// Port
		port = new JTextField();
		port.setText("8080");
		port.setVisible(einstellungen.isProxyBenutzen());
		GridBagConstraints gbc_port = new GridBagConstraints();
		gbc_port.insets = new Insets(0, 0, 0, 200);
		gbc_port.fill = GridBagConstraints.HORIZONTAL;
		gbc_port.gridx = 1;
		gbc_port.gridy = 7;
		add(port, gbc_port);
	}

	/**
	 * Swing Komponenten aktivieren und deaktivieren.
	 * @param enable Komponenten einblenden
	 */
	private void enableComponents(final boolean enable) {
		email.setEnabled(enable);
		passwort.setEnabled(enable);
		anmelden.setEnabled(enable);
		proxy.setEnabled(enable);
		server.setEditable(enable);
		port.setEnabled(enable);
	}

	/** Betätigen des Anmelde-Buttons. */
	private class AnmeldenActionListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent arg0) {
			new Thread() {
				@Override
				public void run() {
					enableComponents(false);
					einstellungen.setEmail(email.getText());
					einstellungen.setPasswort(String.valueOf(passwort.getPassword()));
					einstellungen.setProxyBenutzen(proxy.isSelected());
					einstellungen.setServer(server.getText());
					einstellungen.setPort(port.getText());
					try {
						status.setIcon(new ImageIcon(this.getClass().getResource("/loading.gif")));
						status.setText("Anmeldung läuft...");
						if (client.auth()) {
							status.setText("Hole Benutzerdaten");
							frame.showComponents();
							status.setIcon(new ImageIcon(this.getClass().getResource("/smile.png")));
							status.setText("Erfolgreich eingeloggt");
						} else {
							status.setIcon(new ImageIcon(this.getClass().getResource("/error.png")));
							status.setText("Anmeldung fehlgeschlagen");
							enableComponents(true);
						}
					} catch (Exception e) {
						e.printStackTrace();
						enableComponents(true);
					}
				}
			}.start();
		}
	}

	/** Aktivierung des Proxy-Servers. */
	private class ProxyActionListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent arg0) {
			boolean useProxy = proxy.isSelected();
			einstellungen.setProxyBenutzen(useProxy);
			lblServer.setVisible(useProxy);
			server.setVisible(useProxy);
			lblPort.setVisible(useProxy);
			port.setVisible(useProxy);
		}
	}
}
