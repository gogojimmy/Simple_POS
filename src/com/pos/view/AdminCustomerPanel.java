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
import com.pos.bean.Customer;
import com.pos.bean.User;
import com.pos.dao.FindCustomerDao;
import com.pos.dao.FindUserDao;
import com.pos.dao.InsertCustomerDao;

public class AdminCustomerPanel extends JPanel {
	private User admin;
	private Properties locale;
	private JTable dataTable;
	private CustomerTableModel model;
	private List<Customer> list;
	private Vector<Customer> customers;

	/**
	 * Create the panel.
	 */
	public AdminCustomerPanel(User admin, Properties locale) {
		this.admin = admin;
		this.locale = locale;
		setLayout(null);
		
		JButton addButton = new JButton(locale.getProperty("Add"));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addActionPerformed(e);
			}
		});
		addButton.setBounds(470, 96, 154, 51);
		add(addButton);
		
		JButton updateButton = new JButton(locale.getProperty("Update"));
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateActionPerformed(e);
			}
		});
		updateButton.setBounds(470, 33, 154, 51);
		add(updateButton);
		
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
		
		dataTable = new JTable();
		scrollPane.setViewportView(dataTable);
		
		JButton editButton = new JButton(locale.getProperty("Edit"));
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editActionPerformed(e);
			}
		});
		editButton.setBounds(470, 153, 154, 51);
		add(editButton);

	}
	
	private void updateActionPerformed(ActionEvent e) {
		FindCustomerDao findCustomerDao = new FindCustomerDao();
		FindUserDao findUserDao = new FindUserDao();
		Customer customer = new Customer();
		User user = new User();
		String createdBy = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD HH:mm:ss E");
		this.customers = new Vector<Customer>();
		this.model = new CustomerTableModel(customers);
		dataTable.setModel(model);
		list = findCustomerDao.getAllCustomers();

		for (Customer c : list) {
			customer.setId(c.getId());
			customer.setName(c.getName());
			customer.setCreatedAt(c.getCreatedAt());
			customer.setCreatedBy(c.getCreatedBy());

			user.setId(customer.getCreatedBy());
			user = findUserDao.getUserById(user);
			
			if(user.getUsername()!= null)
				createdBy = user.getUsername();
			else
				createdBy = locale.getProperty("deleted user");

			model.addRow(customer.getId(), customer.getName(),
					sdf.format(customer.getCreatedAt()), createdBy);
		}
		dataTable.updateUI();
		ColumnsAutoSizer.sizeColumnsToFit(dataTable);
	}

	private void addActionPerformed(ActionEvent e) {
		FindCustomerDao findCustomerDao = new FindCustomerDao();
		Customer customer = new Customer();
		boolean ok = true;
		String name;
		do {
			name = JOptionPane.showInputDialog(null,
					locale.getProperty("Please input the name"));
			customer.setName(name);
			if (findCustomerDao.findCustomerByName(name).getId() > 0) {
				ok = false;
				JOptionPane.showMessageDialog(null,
						locale.getProperty("Duplicate name"));
			} else {
				ok = true;
			}
		} while (!ok);

		
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(
				now.getTime());

		customer.setName(name);
		customer.setCreatedAt(currentTimestamp);
		customer.setCreatedBy(admin.getId());

		boolean bool = false;
		try {
			InsertCustomerDao id = new InsertCustomerDao();
			bool = id.setCustomerInfoToDBbean(customer);
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
		String name;
		Customer customer = new Customer();
		customer.setId((Integer) dataTable.getModel().getValueAt(
				dataTable.getSelectedRow(), 0));
		FindCustomerDao findCustomerDao = new FindCustomerDao();
		InsertCustomerDao insertCustomerDao = new InsertCustomerDao();

		customer = findCustomerDao.getCustomerById(customer);

		do {
			name = JOptionPane.showInputDialog(null,
					locale.getProperty("Please input the name"));
			customer.setName(name);
			if (findCustomerDao.findCustomerByName(name).getId() > 0) {
				ok = false;
				JOptionPane.showMessageDialog(null,
						locale.getProperty("Duplicate name"));
			} else {
				ok = true;
			}
		} while (!ok);
		

		try {
			insertCustomerDao.updatecCustomerName(customer);
			JOptionPane.showMessageDialog(null,
					locale.getProperty("update customer success"));
			updateActionPerformed(e);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					locale.getProperty("update customer faild"));
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
			FindCustomerDao findCustomerDao = new FindCustomerDao();
			try {
				findCustomerDao.delCustomerInfo((Integer) dataTable.getModel()
						.getValueAt(dataTable.getSelectedRow(), 0));
				JOptionPane.showMessageDialog(null,
						locale.getProperty("Delete success"));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null,
						locale.getProperty("Delete faild"));
			}
		}
	}

	class CustomerTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		public String[] colNames = { "ID", locale.getProperty("name"),
				locale.getProperty("created_at"),
				locale.getProperty("created_by") };

		public Class[] colTypes = { Integer.class, String.class, String.class,
				String.class };

		Vector dataVector;

		public void addRow(int id, String name, String createdAt,
				String createdBy) {
			Vector v = new Vector(4);
			v.add(0, new Integer(id));
			v.add(1, name);
			v.add(2, createdAt);
			v.add(3, createdBy);
			dataVector.add(v);
		}

		public CustomerTableModel(Vector dataVector) {
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
