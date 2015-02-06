package net.codeusa.strike;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.nio.charset.Charset;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import net.codeusa.strike.utils.Utils;

import com.alee.laf.WebLookAndFeel;

public class Main {
	private static JTextField mpcfield;
	private static JTextField delugeField;
	private static String MPC_IP;
	private static String DELUGE_IP;


	private static void initAndShowGUI() {
		// This method is invoked on Swing thread
		final JFrame frmStrikeDesktop = new JFrame("Strike Desktop");
		frmStrikeDesktop.setTitle("Strike Desktop");
		frmStrikeDesktop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frmStrikeDesktop.getContentPane().setLayout(null);
		final JFXPanel fxPanel = new JFXPanel();
		frmStrikeDesktop.getContentPane().add(fxPanel);
		frmStrikeDesktop.setVisible(true);
		fxPanel.setSize(new Dimension(399, 676));
		fxPanel.setLocation(new Point(0, 43));

		frmStrikeDesktop.getContentPane().setPreferredSize(
				new Dimension(399, 709));

		final JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 409, 43);
		frmStrikeDesktop.getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		final JLabel lblMpcIp = new JLabel("MPC IP");
		panel.add(lblMpcIp);

		mpcfield = new JTextField();
		mpcfield.setText(MPC_IP);
		mpcfield.setColumns(10);
		panel.add(mpcfield);

		final JSeparator separator = new JSeparator();
		panel.add(separator);

		final JLabel lblDelugeIp = new JLabel("DELUGE IP");
		panel.add(lblDelugeIp);

		delugeField = new JTextField();
		delugeField.setText(DELUGE_IP);
		delugeField.setColumns(10);
		panel.add(delugeField);

		final JButton saveButton = new JButton("Save");
		saveButton.addActionListener(arg0 -> {

			MPC_IP = mpcfield.getText();

			DELUGE_IP = delugeField.getText();
			Utils.writeFile(MPC_IP + "," + DELUGE_IP, "settings.csv");
		});
		panel.add(saveButton);
		frmStrikeDesktop.pack();

		Platform.runLater(() -> initFX(fxPanel));
	}

	private static void displaySettings() {
		final JTextField field1 = new JTextField("");
		final JTextField field2 = new JTextField("");
		final JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("MPC IP:"));
		panel.add(field1);
		panel.add(new JLabel("Deluge IP:"));
		panel.add(field2);
		final int result = JOptionPane.showConfirmDialog(null, panel, "Test",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			MPC_IP = field1.getText();

			DELUGE_IP = field2.getText();
			Utils.writeFile(MPC_IP + "," + DELUGE_IP, "settings.csv");

		} else {
			System.exit(1); // we need settings afterall
		}
	}

	/* Creates a WebView and fires up our mpc root */
	private static void initFX(final JFXPanel fxPanel) {
		final Group group = new Group();
		final Scene scene = new Scene(group);
		fxPanel.setScene(scene);

		final WebView webView = new WebView();

		group.getChildren().add(webView);
		webView.setMinSize(399, 709);
		webView.setMaxSize(399, 709);

		// Obtain the webEngine to navigate
		final WebEngine webEngine = webView.getEngine();
		webEngine.load(MPC_IP);
	}

	/* Start application */
	public static void main(final String[] args) {
		WebLookAndFeel.install();
		final String settings = Utils.readFile("settings.csv",
				Charset.defaultCharset());
		if (settings.equals("null")) { // don't want it to be actually null
			displaySettings();
		} else {
			final String[] urls = settings.split(",");
			MPC_IP = urls[0];
			DELUGE_IP = urls[1];
		}
		SwingUtilities.invokeLater(() -> initAndShowGUI());
	}
}