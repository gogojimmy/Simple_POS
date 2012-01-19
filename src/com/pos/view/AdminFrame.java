package com.pos.view;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import com.pos.bean.User;

public class AdminFrame extends JFrame {

	private JPanel contentPane;
	private User admin;
	private Properties locale;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User admin = new User();
					AdminFrame frame = new AdminFrame(admin);
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
	public AdminFrame(User admin) {
		this.admin = admin;
		this.locale = new Properties();
		try {
			locale.load(new FileInputStream("Locale/zh_TW_zh_TW.locale"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "load zh_TW_zh_TW.locale error");
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel userLabel = new JLabel(locale.getProperty("user"));
		userLabel.setBounds(456, 17, 79, 16);
		contentPane.add(userLabel);
		
		JLabel usernameLabel = new JLabel(admin.getUsername());
		usernameLabel.setBounds(547, 17, 79, 16);
		contentPane.add(usernameLabel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 45, 658, 327);
		contentPane.add(tabbedPane);
		
		JPanel adminUserPanel = new AdminUserPanel(admin, locale);
		tabbedPane.add(locale.getProperty("Account Manage"), adminUserPanel);
		
		JPanel adminCustomerPanel = new AdminCustomerPanel(admin, locale);
		tabbedPane.add(locale.getProperty("Customer Manage"), adminCustomerPanel);
		
		
	}
}
