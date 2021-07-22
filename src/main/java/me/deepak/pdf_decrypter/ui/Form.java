package me.deepak.pdf_decrypter.ui;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang3.StringUtils;

import me.deepak.pdf_decrypter.DecrypterService;

public class Form {

	private JFrame frame;
	private JLabel fileLabel;
	private JPasswordField passwordField;
	private JFileChooser fileChooser;

	private static final String NO_FILE_CHOSEN = "No File Chosen";
	private static final String CHOOSE_FILE = "Choose File ...";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Form window = new Form();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the application.
	 */
	/**
	 * @wbp.parser.entryPoint
	 */
	public Form() {
		initialize();

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		fileLabel = new JLabel(NO_FILE_CHOSEN);
		fileLabel.setBounds(50, 50, 350, 25);
		fileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fileLabel.setToolTipText(fileLabel.getText());
		panel.add(fileLabel);

		JButton chooseButton = new JButton(CHOOSE_FILE);
		chooseButton.addActionListener(event -> {
			fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("Only Pdf Files", "pdf", "Pdf"));
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileLabel.setText(fileChooser.showDialog(frame, CHOOSE_FILE) == JFileChooser.APPROVE_OPTION
					? fileChooser.getSelectedFile().getPath()
					: NO_FILE_CHOSEN);
			fileLabel.setToolTipText(fileLabel.getText());
		});

		chooseButton.setBounds(412, 45, 133, 35);
		panel.add(chooseButton);

		JLabel passwordLabel = new JLabel("Pdf Password");
		passwordLabel.setBounds(100, 122, 175, 25);
		panel.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(225, 122, 314, 30);
		passwordField.setToolTipText("Pdf Password");
		panel.add(passwordField);

		JButton decryptButton = new JButton("Decrypt");
		decryptButton.addActionListener(event -> {
			String password = passwordField.getText();
			if (StringUtils.equals(NO_FILE_CHOSEN, fileLabel.getText()) || StringUtils.isBlank(password)) {
				JOptionPane.showMessageDialog(null, "Please fill required fields!!!");
			} else {
				try {
					DecrypterService.decrypt(fileChooser.getSelectedFile(), password);
					JOptionPane.showMessageDialog(null, "Pdf decrypted successfully!!!");
					fileLabel.setText(NO_FILE_CHOSEN);
					passwordField.setText(null);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Exception occured : " + e);
				}
			}
		});
		decryptButton.setBounds(242, 196, 155, 35);
		panel.add(decryptButton);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(600, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Pdf Decrypter");
	}
}
