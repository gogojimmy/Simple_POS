package com.pos.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.pos.bean.Customer;
import com.pos.bean.Product;
import com.pos.bean.User;
import com.pos.dao.FindCustomerDao;
import com.pos.dao.FindProductDao;
import com.pos.dao.InsertProductDao;

public class OutputPanel extends JPanel {
	private JTextField outputNoField;
	private JTextField outputDateField;
	private JTextField customerOrderNoField;
	private JTable dataTable;
	private Properties locale;
	private User user;
	private Vector<Product> products;
	private ProductTableModel model;
	private List<Product> list;
	private SimpleDateFormat sdf;
	private boolean isValid = false;
	private JComboBox customerComboBox;
	private JButton serialOutputButton, packageOutputButton;
	private Vector<String> packageList = new Vector<String>(); // 儲存讀取過的Package
																// No，用來驗證使用者輸入的Package
																// No是否輸入過
	private Vector<String> serialList = new Vector<String>(); // 儲存讀取過的Serial
																// No，用來驗證使用者輸入的Serial
																// No是否輸入過

	/**
	 * Create the panel.
	 */
	public OutputPanel(User user, Properties locale) {
		this.user = user;
		this.locale = locale;
		list = new ArrayList<Product>();
		setLayout(null);

		JLabel outputNoLabel = new JLabel(locale.getProperty("outputNo"));
		outputNoLabel.setBounds(20, 21, 82, 16);
		add(outputNoLabel);

		outputNoField = new JTextField();
		outputNoField.setBounds(114, 15, 154, 28);
		add(outputNoField);
		outputNoField.setColumns(10);

		JLabel outputDateLabel = new JLabel(locale.getProperty("outputDate"));
		outputDateLabel.setBounds(20, 61, 82, 16);
		add(outputDateLabel);

		sdf = new SimpleDateFormat("yyyy/MM/DD");
		Date today = new Date();
		outputDateField = new JTextField(sdf.format(today));
		outputDateField.setBounds(114, 55, 154, 28);
		add(outputDateField);
		outputDateField.setColumns(10);

		JLabel customerLabel = new JLabel(locale.getProperty("customer"));
		customerLabel.setBounds(280, 21, 82, 16);
		add(customerLabel);

		JLabel customerOrderNoLabel = new JLabel(
				locale.getProperty("customerOrderNo"));
		customerOrderNoLabel.setBounds(280, 61, 82, 16);
		add(customerOrderNoLabel);

		customerOrderNoField = new JTextField();
		customerOrderNoField.setBounds(374, 55, 183, 28);
		add(customerOrderNoField);
		customerOrderNoField.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 117, 475, 295);
		add(scrollPane_1);

		products = new Vector<Product>();
		model = new ProductTableModel(products);

		dataTable = new JTable(model);
		scrollPane_1.setViewportView(dataTable);

		packageOutputButton = new JButton(locale.getProperty("package output"));
		packageOutputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				packageOutputActionPerformed(evt);
			}
		});
		packageOutputButton.setBounds(507, 117, 154, 67);
		add(packageOutputButton);

		JButton okButton = new JButton(locale.getProperty("OK"));
		okButton.setBounds(507, 345, 154, 67);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				okActionPerformed(evt);
			}
		});
		add(okButton);

		FindCustomerDao dao = new FindCustomerDao();

		customerComboBox = new JComboBox();
		for (Customer customer : dao.getAllCustomers()) {
			customerComboBox.addItem(customer);
		}
		customerComboBox.setBounds(374, 17, 183, 27);
		add(customerComboBox);

		JButton excelButton = new JButton(locale.getProperty("excel"));
		excelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excelOutputActionPerformed(e);
			}
		});
		excelButton.setBounds(507, 266, 154, 67);
		add(excelButton);

		serialOutputButton = new JButton(locale.getProperty("serial output"));
		serialOutputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serialOutputActionPerformed(e);
			}
		});
		serialOutputButton.setBounds(507, 196, 154, 58);
		add(serialOutputButton);

	}

	// 匯出Excel
	private void excelOutputActionPerformed(ActionEvent e) {
		if (list == null || list.size() < 1) {
			JOptionPane.showMessageDialog(dataTable,
					locale.getProperty("no data"), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		isDateValid();
		checkIsValid();
		if (!isValid) {
			JOptionPane.showMessageDialog(this,
					locale.getProperty("You have to fill all fields !"));
			return;
		}

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(locale.getProperty("Choose path"));
		chooser.setDialogType(JFileChooser.DIRECTORIES_ONLY);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		String path = null;

		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().toString();
		} else {
			return;
		}
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File(path + ".xls"));
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);

			// 寫標題
			for (int i = 0; i < dataTable.getModel().getColumnCount(); i++) {
				Label label = new Label(i, 0, dataTable.getModel()
						.getColumnName(i));
				sheet.addCell(label);
			}

			// 寫內容
			for (int i = 0; i < dataTable.getModel().getRowCount(); i++) {
				for (int j = 0; j < dataTable.getModel().getColumnCount(); j++) {
					Label label = new Label(j, i + 1, dataTable.getModel()
							.getValueAt(i, j).toString());
					sheet.addCell(label);
				}
			}

			// 寫入Excel
			workbook.write();
			workbook.close();
			JOptionPane.showMessageDialog(dataTable,
					locale.getProperty("output success"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(dataTable, "Error output Excel file"
					+ e1, "Error", JOptionPane.ERROR_MESSAGE);
		} catch (RowsExceededException e2) {
			JOptionPane.showMessageDialog(dataTable, "Error output Excel file"
					+ e2, "Error", JOptionPane.ERROR_MESSAGE);
		} catch (WriteException e3) {
			JOptionPane.showMessageDialog(dataTable, "Error output Excel file"
					+ e3, "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	// 樣品出貨
	private void serialOutputActionPerformed(ActionEvent evt) {
		String serialNo = null;
		FindProductDao dao = new FindProductDao();
		List<Product> products = new ArrayList<Product>();
		Map<String, Product> map = new HashMap<String, Product>();
		packageOutputButton.setEnabled(false);

		do {
			serialNo = JOptionPane.showInputDialog(locale
					.getProperty("Please input the serial no"));
			if (!serialNo.equals("")) {
				// 若是沒輸入過的packageNo才會處理
				if (serialList.indexOf(serialNo) == -1) {
					serialList.add(serialNo);
					products = dao.getProductBySerialNo(serialNo);
					// 若查無資料則警告
					if (products.size() < 1) {
						JOptionPane.showMessageDialog(dataTable,
								locale.getProperty("No Records"));
						return;
					}

					for (Product product : products) {
						list.add(product);

						if (!map.containsKey(product.getDescription()))
							map.put(product.getDescription(), product);
						else {
							Product p = map.get(product.getDescription());
							int value = p.getQty();
							p.setQty(value + product.getQty());
							map.put(product.getDescription(), p);
						}
					}
					Iterator iterator = map.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry entry = (Entry) iterator.next();
						System.out.println(!((Product) entry.getValue())
								.isShipped());
						if (!((Product) entry.getValue()).isShipped())
							model.addRow(entry.getKey().toString(),
									((Product) entry.getValue()).getWd(),
									((Product) entry.getValue()).getIv(),
									((Product) entry.getValue()).getQty());
					}
					dataTable.updateUI();
				}
			}
		} while (!serialNo.equals(""));
	}

	// 一般出貨
	private void packageOutputActionPerformed(ActionEvent evt) {
		String packageNo = null;
		FindProductDao dao = new FindProductDao();
		List<Product> products = new ArrayList<Product>();
		Map<String, Product> map = new HashMap<String, Product>();
		serialOutputButton.setEnabled(false);

		do {
			packageNo = JOptionPane.showInputDialog(locale
					.getProperty("Please input the package no"));
			if (!packageNo.equals("")) {
				// 若是沒輸入過的packageNo才會處理
				if (packageList.indexOf(packageNo) == -1) {
					packageList.add(packageNo);
					products = dao.getProductByPackage(packageNo);
					// 若查無資料則警告
					if (products.size() < 1) {
						JOptionPane.showMessageDialog(dataTable,
								locale.getProperty("No Records"));
						return;
					}

					for (Product product : products) {
						list.add(product);

						if (!map.containsKey(product.getDescription()))
							map.put(product.getDescription(), product);
						else {
							Product p = map.get(product.getDescription());
							int value = p.getQty();
							p.setQty(value + product.getQty());
							map.put(product.getDescription(), p);
						}
					}
					Iterator iterator = map.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry entry = (Entry) iterator.next();
						model.addRow(entry.getKey().toString(),
								((Product) entry.getValue()).getWd(),
								((Product) entry.getValue()).getIv(),
								((Product) entry.getValue()).getQty());
					}
					dataTable.updateUI();
				}
			}
		} while (!packageNo.equals(""));
	}

	public void checkIsValid() {
		if (outputNoField.getText().equals("")) {
			isValid = false;
		}
		if (customerOrderNoField.getText().equals("")) {
			isValid = false;
		} else {
			isValid = true;
		}
	}

	private void isDateValid() {
		String text = outputDateField.getText();
		String expression = "(19|20)[0-9]{2}[- /.](0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		if (matcher.matches()) {
			this.isValid = true;
		} else {
			this.isValid = false;
			JOptionPane.showMessageDialog(this,
					locale.getProperty("The date you inputed is illegal"));
		}
	}

	public void okActionPerformed(ActionEvent evt) {
		isDateValid();
		checkIsValid();
		if (!isValid) {
			JOptionPane.showMessageDialog(this,
					locale.getProperty("You have to fill all fields !"));
			return;
		}
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(
				now.getTime());
		InsertProductDao dao = new InsertProductDao();
		boolean isSuccess = true;
		Date date = null;
		try {
			date = sdf.parse(outputDateField.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (Product product : list) {
			product.setShipped(true);
			product.setUpdatedBy(user.getId());
			product.setUpdated_at(currentTimestamp);
			product.setOutputNo(outputNoField.getText());
			product.setOutputDate((new java.sql.Date((date).getTime())));
			product.setCustomerId(((Customer) customerComboBox
					.getSelectedItem()).getId());
			product.setCustomerOrderNo(customerOrderNoField.getText());
			try {
				dao.setProductIsShipped(product);
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = false;
				JOptionPane.showMessageDialog(this,
						locale.getProperty("update faild"));
			}
		}
		if (isSuccess) {
			JOptionPane.showMessageDialog(this,
					locale.getProperty("update success"));
			reset();
		}
	}

	public void reset() {
		products = new Vector<Product>();
		model = new ProductTableModel(products);
		Date today = new Date();
		dataTable.setModel(model);
		dataTable.updateUI();
		outputNoField.setText("");
		outputDateField.setText(sdf.format(today));
		customerOrderNoField.setText("");
		serialList = new Vector<String>();
		packageList = new Vector<String>();
		packageOutputButton.setEnabled(true);
		serialOutputButton.setEnabled(true);
	}

	class ProductTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		public String[] colNames = { "Description", "WD", "IV", "Q'ty" };

		public Class[] colTypes = { String.class, String.class, String.class,
				Integer.class };

		Vector dataVector;
		private int qtySum = 0;

		public ProductTableModel(Vector dataVector) {
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

		public void addRow(String description, String wd, String iv, int qty) {
			Vector v = new Vector(4);
			v.add(0, description);
			v.add(1, wd);
			v.add(2, iv);
			v.add(3, new Integer(qty));
			dataVector.add(v);
			qtySum += qty;
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

		public int getQtySum() {
			return qtySum;
		}

		public Object getValueAt(int row, int col) {
			return ((Vector) dataVector.get(row)).get(col);
		}

		public Class getColumnClass(int col) {
			return getValueAt(0, col).getClass();
		}
	}
}
