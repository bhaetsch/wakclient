package de.techspread.wakclient.common;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutWindow {

	private JFrame frmberWakclient;
	private JLabel label;
	private JLabel lblProgramm;
	private JLabel lblPatrickGotthard;
	private JLabel lblEmail;
	private JLabel lblPatricktechspreadde;
	private JLabel lblIcq;
	private JLabel lblPatricktechspreadde_2;
	private JLabel lblMsn;
	private JLabel lblPatricktechspreadde_1;
	private JLabel lblSkype;
	private JLabel lblPgotthard;
	private JLabel lblTwitter;
	private JLabel lblPg;

	public AboutWindow() {
		initialize();
	}

	private void initialize() {
		frmberWakclient = new JFrame();
		frmberWakclient.setResizable(false);
		frmberWakclient.setTitle("Über WAKClient");		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{1.0, 1.0};
		frmberWakclient.getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(30, 30, 10, 30);
		gbc_label.gridwidth = 2;
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		frmberWakclient.getContentPane().add(getLabel(), gbc_label);
		GridBagConstraints gbc_lblProgramm = new GridBagConstraints();
		gbc_lblProgramm.insets = new Insets(0, 0, 10, 30);
		gbc_lblProgramm.anchor = GridBagConstraints.EAST;
		gbc_lblProgramm.gridx = 0;
		gbc_lblProgramm.gridy = 1;
		frmberWakclient.getContentPane().add(getLblProgramm(), gbc_lblProgramm);
		GridBagConstraints gbc_lblPatrickGotthard = new GridBagConstraints();
		gbc_lblPatrickGotthard.insets = new Insets(0, 0, 10, 0);
		gbc_lblPatrickGotthard.anchor = GridBagConstraints.WEST;
		gbc_lblPatrickGotthard.gridx = 1;
		gbc_lblPatrickGotthard.gridy = 1;
		frmberWakclient.getContentPane().add(getLblPatrickGotthard(), gbc_lblPatrickGotthard);
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.insets = new Insets(0, 0, 10, 30);
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 2;
		frmberWakclient.getContentPane().add(getLblEmail(), gbc_lblEmail);
		GridBagConstraints gbc_lblPatricktechspreadde = new GridBagConstraints();
		gbc_lblPatricktechspreadde.insets = new Insets(0, 0, 10, 0);
		gbc_lblPatricktechspreadde.anchor = GridBagConstraints.WEST;
		gbc_lblPatricktechspreadde.gridx = 1;
		gbc_lblPatricktechspreadde.gridy = 2;
		frmberWakclient.getContentPane().add(getLblPatricktechspreadde(), gbc_lblPatricktechspreadde);
		GridBagConstraints gbc_lblIcq = new GridBagConstraints();
		gbc_lblIcq.insets = new Insets(0, 0, 10, 30);
		gbc_lblIcq.anchor = GridBagConstraints.EAST;
		gbc_lblIcq.gridx = 0;
		gbc_lblIcq.gridy = 3;
		frmberWakclient.getContentPane().add(getLblIcq(), gbc_lblIcq);
		GridBagConstraints gbc_lblPatricktechspreadde_2 = new GridBagConstraints();
		gbc_lblPatricktechspreadde_2.insets = new Insets(0, 0, 10, 0);
		gbc_lblPatricktechspreadde_2.anchor = GridBagConstraints.WEST;
		gbc_lblPatricktechspreadde_2.gridx = 1;
		gbc_lblPatricktechspreadde_2.gridy = 3;
		frmberWakclient.getContentPane().add(getLblPatricktechspreadde_2(), gbc_lblPatricktechspreadde_2);
		GridBagConstraints gbc_lblMsn = new GridBagConstraints();
		gbc_lblMsn.insets = new Insets(0, 0, 10, 30);
		gbc_lblMsn.anchor = GridBagConstraints.EAST;
		gbc_lblMsn.gridx = 0;
		gbc_lblMsn.gridy = 4;
		frmberWakclient.getContentPane().add(getLblMsn(), gbc_lblMsn);
		GridBagConstraints gbc_lblPatricktechspreadde_1 = new GridBagConstraints();
		gbc_lblPatricktechspreadde_1.insets = new Insets(0, 0, 10, 0);
		gbc_lblPatricktechspreadde_1.anchor = GridBagConstraints.WEST;
		gbc_lblPatricktechspreadde_1.gridx = 1;
		gbc_lblPatricktechspreadde_1.gridy = 4;
		frmberWakclient.getContentPane().add(getLblPatricktechspreadde_1(), gbc_lblPatricktechspreadde_1);
		GridBagConstraints gbc_lblSkype = new GridBagConstraints();
		gbc_lblSkype.insets = new Insets(0, 0, 10, 30);
		gbc_lblSkype.anchor = GridBagConstraints.EAST;
		gbc_lblSkype.gridx = 0;
		gbc_lblSkype.gridy = 5;
		frmberWakclient.getContentPane().add(getLblSkype(), gbc_lblSkype);
		GridBagConstraints gbc_lblPgotthard = new GridBagConstraints();
		gbc_lblPgotthard.insets = new Insets(0, 0, 10, 10);
		gbc_lblPgotthard.anchor = GridBagConstraints.WEST;
		gbc_lblPgotthard.gridx = 1;
		gbc_lblPgotthard.gridy = 5;
		frmberWakclient.getContentPane().add(getLblPgotthard(), gbc_lblPgotthard);
		GridBagConstraints gbc_lblTwitter = new GridBagConstraints();
		gbc_lblTwitter.insets = new Insets(0, 0, 30, 30);
		gbc_lblTwitter.anchor = GridBagConstraints.EAST;
		gbc_lblTwitter.gridx = 0;
		gbc_lblTwitter.gridy = 6;
		frmberWakclient.getContentPane().add(getLblTwitter(), gbc_lblTwitter);
		GridBagConstraints gbc_lblPg = new GridBagConstraints();
		gbc_lblPg.insets = new Insets(0, 0, 30, 0);
		gbc_lblPg.anchor = GridBagConstraints.WEST;
		gbc_lblPg.gridx = 1;
		gbc_lblPg.gridy = 6;
		frmberWakclient.getContentPane().add(getLblPg(), gbc_lblPg);
		frmberWakclient.pack();
		frmberWakclient.setLocationRelativeTo(null);
		frmberWakclient.setVisible(true);
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel(new ImageIcon(this.getClass().getResource("/wakclient-splash.png")));
		}
		return label;
	}
	private JLabel getLblProgramm() {
		if (lblProgramm == null) {
			lblProgramm = new JLabel("Autor:");
		}
		return lblProgramm;
	}
	private JLabel getLblPatrickGotthard() {
		if (lblPatrickGotthard == null) {
			lblPatrickGotthard = new JLabel("Patrick Gotthard");
		}
		return lblPatrickGotthard;
	}
	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("E-Mail:");
		}
		return lblEmail;
	}
	private JLabel getLblPatricktechspreadde() {
		if (lblPatricktechspreadde == null) {
			lblPatricktechspreadde = new JLabel("patrick@techspread.de");
		}
		return lblPatricktechspreadde;
	}
	private JLabel getLblIcq() {
		if (lblIcq == null) {
			lblIcq = new JLabel("MSN:");
		}
		return lblIcq;
	}
	private JLabel getLblPatricktechspreadde_2() {
		if (lblPatricktechspreadde_2 == null) {
			lblPatricktechspreadde_2 = new JLabel("patrick@techspread.de");
		}
		return lblPatricktechspreadde_2;
	}
	private JLabel getLblMsn() {
		if (lblMsn == null) {
			lblMsn = new JLabel("ICQ:");
		}
		return lblMsn;
	}
	private JLabel getLblPatricktechspreadde_1() {
		if (lblPatricktechspreadde_1 == null) {
			lblPatricktechspreadde_1 = new JLabel("215389992");
		}
		return lblPatricktechspreadde_1;
	}
	private JLabel getLblSkype() {
		if (lblSkype == null) {
			lblSkype = new JLabel("Skype:");
		}
		return lblSkype;
	}
	private JLabel getLblPgotthard() {
		if (lblPgotthard == null) {
			lblPgotthard = new JLabel("pgotthard");
		}
		return lblPgotthard;
	}
	private JLabel getLblTwitter() {
		if (lblTwitter == null) {
			lblTwitter = new JLabel("Twitter:");
		}
		return lblTwitter;
	}
	private JLabel getLblPg() {
		if (lblPg == null) {
			lblPg = new JLabel("techspread");
		}
		return lblPg;
	}
}
