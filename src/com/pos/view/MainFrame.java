package com.pos.view;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import com.pos.bean.User;

// 管理系統主介面
public class MainFrame extends JFrame {
	private User user;
	private Properties locale;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User user = new User();
					MainFrame frame = new MainFrame(user);
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
	public MainFrame(User user) {
		locale = new Properties();
		try {
			locale.load(new FileInputStream("Locale/zh_TW_zh_TW.locale"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.user = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel userlabel = new JLabel(locale.getProperty("user"));
		userlabel.setBounds(448, 23, 92, 16);
		contentPane.add(userlabel);
		
		JLabel usernameLabel = new JLabel(user.getUsername());
		usernameLabel.setBounds(563, 23, 117, 16);
		contentPane.add(usernameLabel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 48, 688, 474);
		contentPane.add(tabbedPane);
		
		JPanel inputPanel = new InputPanel(user, locale);
		tabbedPane.add(locale.getProperty("input"),inputPanel);
		
		JPanel outputPanel = new OutputPanel(user, locale);
		tabbedPane.add(locale.getProperty("output"), outputPanel);
		
		JPanel serialNoQueryPanel = new SerialNoQueryPanel(user, locale);
		tabbedPane.add(locale.getProperty("serialNoQuery"), serialNoQueryPanel);
		
		JPanel boughtPanel = new BoughtListPanel(user, locale);
		tabbedPane.add(locale.getProperty("cutomer history"), boughtPanel);
		
		JPanel turnoverPanel = new TurnoverPanel(user, locale);
		tabbedPane.add(locale.getProperty("turnover"), turnoverPanel);
		
		JPanel repoPanel = new StockPanel(user, locale);
		tabbedPane.add(locale.getProperty("repoquery"), repoPanel);
		
		setTitle(locale.getProperty("AOTC System"));
		setResizable(false);
	}
}
