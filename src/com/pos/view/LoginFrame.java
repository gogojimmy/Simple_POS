package com.pos.view;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.pos.bean.User;
import com.pos.dao.FindUserDao;
import javax.swing.JPasswordField;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private Properties locale;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		locale = new Properties();
		try {
			locale.load(new FileInputStream("Locale/zh_TW_zh_TW.locale"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel userNameLabel = new JLabel(locale.getProperty("username"));
		userNameLabel.setBounds(60, 73, 119, 16);
		contentPane.add(userNameLabel);

		JLabel passwordLabel = new JLabel(locale.getProperty("password"));
		passwordLabel.setBounds(60, 134, 119, 16);
		contentPane.add(passwordLabel);

		usernameField = new JTextField();
		usernameField.setBounds(191, 67, 224, 28);
		contentPane.add(usernameField);
		usernameField.setColumns(10);

		JButton loginButton = new JButton(locale.getProperty("login"));
		loginButton.setBounds(79, 192, 132, 48);
		loginButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				enterButtonActionPerformed(evt);
			}
		});
		contentPane.add(loginButton);

		JButton resetButton = new JButton(locale.getProperty("reset"));
		resetButton.setBounds(264, 192, 132, 48);
		resetButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});
		contentPane.add(resetButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(191, 134, 224, 28);
		contentPane.add(passwordField);
	}

	private void enterButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if (usernameField.getText().equals("")) {
			JOptionPane.showMessageDialog(this,
					locale.getProperty("username can't be blank"),
					locale.getProperty("username can't be blank"),
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (passwordField.getText().equals("")) {
			JOptionPane.showMessageDialog(this,
					locale.getProperty("password can't be blank"),
					locale.getProperty("password can't be blank"),
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		FindUserDao findDao = new FindUserDao();
		User user = new User();
		user.setUsername(usernameField.getText());
		user.setPassword(passwordField.getText());
		User currentUser = findDao.getUser(user);
		if (currentUser.getId() != 0) {
			if (!currentUser.isAdmin()) {
				MainFrame mainFrame = new MainFrame(currentUser);
				mainFrame.setVisible(true);
				mainFrame.setTitle(locale.getProperty("POS System"));
			} else {
				AdminFrame adminFrame = new AdminFrame(currentUser);
				adminFrame.setVisible(true);
				adminFrame.setTitle(locale.getProperty("Admin Panel"));
			}
			this.dispose();
		} else if (currentUser.getId() == 0) {
			JOptionPane.showMessageDialog(this,
					locale.getProperty("Wrong username or password"),
					locale.getProperty("Warning"), JOptionPane.WARNING_MESSAGE);
		}
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		usernameField.setText("");
		passwordField.setText("");
	}
}
