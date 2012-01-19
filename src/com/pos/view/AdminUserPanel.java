package com.pos.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.jimmy.tool.ColumnsAutoSizer;
import com.pos.bean.User;
import com.pos.dao.FindUserDao;
import com.pos.dao.InsertUserDao;

public class AdminUserPanel extends JPanel {
	private User user;
	private Properties locale;
	private JTable dataTable;
	private UserTableModel model;
	private List<User> list;
	private Vector<User> users;

	/**
	 * Create the panel.
	 */
	public AdminUserPanel(User user, Properties locale) {
		this.user = user;
		this.locale = locale;
		setLayout(null);

		JButton addButton = new JButton(locale.getProperty("Add"));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addActionPerformed(e);
			}
		});
		addButton.setBounds(470, 90, 154, 51);
		add(addButton);

		JButton editButton = new JButton(locale.getProperty("change password"));
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editActionPerformed(e);
			}
		});
		editButton.setBounds(470, 153, 154, 51);
		add(editButton);

		JButton delButton = new JButton(locale.getProperty("Delete"));
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delActionPerformed(e);
			}
		});
		delButton.setBounds(470, 216, 154, 51);
		add(delButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 31, 444, 236);
		add(scrollPane);

		users = new Vector<User>();
		model = new UserTableModel(users);
		dataTable = new JTable(model);
		scrollPane.setViewportView(dataTable);

		JButton updateButton = new JButton(locale.getProperty("Update"));
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateActionPerformed(e);
			}
		});
		updateButton.setBounds(470, 31, 154, 51);
		add(updateButton);

	}

	private void updateActionPerformed(ActionEvent e) {
		FindUserDao userDao = new FindUserDao();
		User user = new User();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD HH:mm:ss E");
		this.users = new Vector<User>();
		this.model = new UserTableModel(users);
		dataTable.setModel(model);
		list = userDao.getAllUsers();
		String isAdmin = "";

		for (User u : list) {
			user.setId(u.getId());
			user.setUsername(u.getUsername());
			user.setPassword(u.getPassword());
			user.setLastLogin(u.getLastLogin());
			user.setAdmin(u.isAdmin());

			if (user.isAdmin()) {
				isAdmin = "Yes";
			} else {
				isAdmin = "No";
			}

			model.addRow(user.getId(), user.getUsername(),
					sdf.format(user.getLastLogin()), isAdmin);
		}
		dataTable.updateUI();
		ColumnsAutoSizer.sizeColumnsToFit(dataTable);
	}

	private void addActionPerformed(ActionEvent e) {
		FindUserDao findUserDao = new FindUserDao();
		User user = new User();
		boolean ok = true;
		boolean admin = false;
		String username, password, confirm, isAdmin;
		do {
			username = JOptionPane.showInputDialog(null,
					locale.getProperty("Please input the username"));
			user.setUsername(username);
			if (findUserDao.getUserByUsername(user).getId() > 0) {
				ok = false;
				JOptionPane.showMessageDialog(null,
						locale.getProperty("Duplicate username"));
			} else {
				ok = true;
			}
		} while (!ok);

		do {
			password = JOptionPane.showInputDialog(null,
					locale.getProperty("Please input the password"));
			confirm = JOptionPane.showInputDialog(null,
					locale.getProperty("Please input again"));
			if (!password.equals(confirm)) {
				ok = false;
				JOptionPane.showMessageDialog(null,
						locale.getProperty("Confirm error"));
			} else {
				ok = true;
			}
		} while (!ok);

		do {
			isAdmin = JOptionPane
					.showInputDialog(null,
							locale.getProperty("input yes or no"),
							locale.getProperty("isAdmin"),
							JOptionPane.QUESTION_MESSAGE);

			if (isAdmin.toLowerCase().equals("yes")) {
				ok = true;
				admin = true;
			} else if (isAdmin.toLowerCase().equals("no")) {
				ok = true;
				admin = false;
			} else {
				ok = false;
				JOptionPane.showMessageDialog(null,
						locale.getProperty("You can only input yes or no"));
			}
		} while (!ok);

		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(
				now.getTime());

		user.setUsername(username);
		user.setPassword(password);
		user.setAdmin(admin);
		user.setLogin(false);
		user.setCreatedAt(currentTimestamp);
		user.setLastLogin(currentTimestamp);

		boolean bool = false;
		try {
			InsertUserDao id = new InsertUserDao();
			bool = id.setUserInfoToDBbean(user);
			JOptionPane
					.showMessageDialog(null, locale.getProperty("Insert OK"));
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					locale.getProperty("Insert faild"));
			JOptionPane.showMessageDialog(null, e);
		}
		dataTable.updateUI();
		ColumnsAutoSizer.sizeColumnsToFit(dataTable);
	}

	private void editActionPerformed(ActionEvent e) {
		boolean ok = true;
		User user = new User();
		user.setId((Integer) dataTable.getModel().getValueAt(
				dataTable.getSelectedRow(), 0));
		FindUserDao findUserDao = new FindUserDao();
		InsertUserDao InsertUserDao = new InsertUserDao();
		String password, confirm;
		user = findUserDao.getUserById(user);

		do {
			password = JOptionPane.showInputDialog(null,
					locale.getProperty("Please input new password"));
			confirm = JOptionPane.showInputDialog(null,
					locale.getProperty("Please input again"));
			if (!password.equals(confirm)) {
				ok = false;
				JOptionPane.showMessageDialog(null,
						locale.getProperty("Confirm error"));
			} else {
				ok = true;
			}
		} while (!ok);

		try {
			user.setPassword(password);
			InsertUserDao.updateUserPassWord(user);
			JOptionPane.showMessageDialog(null,
					locale.getProperty("update password success"));
			updateActionPerformed(e);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					locale.getProperty("update password faild"));
			JOptionPane.showMessageDialog(null, e1);
		}
		dataTable.updateUI();
		ColumnsAutoSizer.sizeColumnsToFit(dataTable);
	}

	private void delActionPerformed(ActionEvent e) {
		int sure = JOptionPane.showConfirmDialog(null,
				locale.getProperty("Sure to delete?"),
				locale.getProperty("Sure to delete?"),
				JOptionPane.OK_CANCEL_OPTION);

		if (sure == JOptionPane.OK_OPTION) {
			FindUserDao findUserDao = new FindUserDao();
			try {
				findUserDao.delUserInfo((Integer) dataTable.getModel()
						.getValueAt(dataTable.getSelectedRow(), 0));
				JOptionPane.showMessageDialog(null,
						locale.getProperty("Delete success"));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null,
						locale.getProperty("Delete faild"));
			}
		}
	}

	class UserTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		public String[] colNames = { "ID", locale.getProperty("username"),
				locale.getProperty("last login"),
				locale.getProperty("is admin") };

		public Class[] colTypes = { Integer.class, String.class, String.class,
				String.class };

		Vector dataVector;

		public void addRow(int id, String username, String lastLogin,
				String isAdmin) {
			Vector v = new Vector(4);
			v.add(0, new Integer(id));
			v.add(1, username);
			v.add(2, lastLogin);
			v.add(3, isAdmin);
			dataVector.add(v);
		}

		public UserTableModel(Vector dataVector) {
			super();
			this.dataVector = dataVector;
		}

		public String getColumnName(int col) {
			return colNames[col];
		}

		public int getColumnCount() {
			return colNames.length;
		}

		public int getRowCount() {
			return dataVector.size();
		}

		public void removeRow(int row) {
			dataVector.remove(row);
		}

		public void removeRows(int row, int count) {
			for (int i = 0; i < count; i++) {
				if (dataVector.size() > row) {
					dataVector.remove(row);
				}
			}
		}

		public Object getValueAt(int row, int col) {
			return ((Vector) dataVector.get(row)).get(col);
		}

		public Class getColumnClass(int col) {
			if (this.getValueAt(0, col).getClass() == null) {
				return String.class;
			} else {
				return getValueAt(0, col).getClass();
			}
		}
	}
}
