package com.pos.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.jimmy.tool.ColumnsAutoSizer;
import com.pos.bean.Customer;
import com.pos.bean.Product;
import com.pos.bean.RepoStorShip;
import com.pos.bean.User;
import com.pos.dao.FindCustomerDao;
import com.pos.dao.FindProductDao;
import com.pos.dao.FindRepoStorShipDao;
import com.pos.view.StockPanel.ProductTableModel;

// Serial No查詢表單
public class SerialNoQueryPanel extends JPanel {
	private JTable dataTable;
	private User user;
	private Properties locale;
	private ProductTableModel model;
	private Vector<Product> products;
	private List<Product> list;
	private SimpleDateFormat sdf;

	/**
	 * Create the panel.
	 */
	public SerialNoQueryPanel(User user, Properties locale) {
		this.user = user;
		this.locale = locale;
		setLayout(null);

		JButton readButton = new JButton(locale.getProperty("read"));
		readButton.setBounds(6, 6, 138, 45);
		readButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				readActionPerformed(evt);
			}
		});
		readButton.getInputMap().put(KeyStroke.getKeyStroke("Enter"), "none");
		add(readButton);

		products = new Vector<Product>();
		model = new ProductTableModel(products);
		dataTable = new JTable(model);
		dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		dataTable.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				ColumnsAutoSizer.sizeColumnsToFit(dataTable);
			}
		});

		JScrollPane scrollPane = new JScrollPane(dataTable);
		scrollPane.setBounds(6, 63, 638, 361);
		add(scrollPane);

		JButton excelButton = new JButton(locale.getProperty("excel"));
		excelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excelOutputActionPerformed(e);
			}
		});
		excelButton.setBounds(493, 6, 138, 45);
		add(excelButton);
	}

	// 匯出Excel
	private void excelOutputActionPerformed(ActionEvent e) {
		if (list == null || list.size() < 1) {
			JOptionPane.showMessageDialog(dataTable,
					locale.getProperty("no data"), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(locale.getProperty("Choose path"));
		chooser.setMultiSelectionEnabled(false);
		String path = null;
		FindRepoStorShipDao rssDao = new FindRepoStorShipDao();
		FindCustomerDao customerDao = new FindCustomerDao();
		RepoStorShip rss = new RepoStorShip();
		String repo = "";
		String stor = "";
		String ship = "";
		Customer customer = new Customer();
		String customerName = "";
		String isShipped = "";
		
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().toString();
		} else {
			return;
		}
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File(path
					+ ".xls"));
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);

			// 寫標題
			for (int i = 0; i < dataTable.getModel().getColumnCount(); i++) {
				Label label = new Label(i, 0, dataTable.getModel()
						.getColumnName(i));
				sheet.addCell(label);
			}

			// 寫內容
			int i = 0;
			Label label;
			for (Product product : list) {
				
				rss.setId(product.getRepoId());
				repo = rssDao.getRssById(rss).getName();

				rss.setId(product.getStorId());
				stor = rssDao.getRssById(rss).getName();

				rss.setId(product.getShipId());
				ship = rssDao.getRssById(rss).getName();

				if (product.getCustomerId()!=0)  {
					customer.setId(product
							.getCustomerId());
				customerName = customerDao.getCustomerById(customer)
						.getName();
				} else {
					customerName = "";
				}
				String outputDate = ""; 
				String customerOrderNo = "";
				
				if (product.isShipped()) {
					isShipped = locale.getProperty("shipped");
					outputDate = (product.getOutputDate()
							.toString());
					customerOrderNo = (product.getCustomerOrderNo());
				} else {
					isShipped = locale.getProperty("not shipped");
				}
				
				label = new Label(i, 1, product.getDescription());
				sheet.addCell(label);
				label = new Label(i, 2, product.getWd());
				sheet.addCell(label);
				label = new Label(i, 3, product.getIv());
				sheet.addCell(label);
				label = new Label(i, 4, String.valueOf(product.getQty()));
				sheet.addCell(label);
				label = new Label(i, 5, repo);
				sheet.addCell(label);
				label = new Label(i, 6, stor);
				sheet.addCell(label);
				label = new Label(i, 7, ship);
				sheet.addCell(label);
				label = new Label(i, 8, customerName);
				sheet.addCell(label);
				label = new Label(i, 9, customerOrderNo);
				sheet.addCell(label);
				label = new Label(i, 10, outputDate);
				sheet.addCell(label);
				label = new Label(i, 11, product.getInputNo());
				sheet.addCell(label);
				label = new Label(i, 12, product.getInLot());
				sheet.addCell(label);
				label = new Label(i, 13, isShipped);
				sheet.addCell(label);
			}

			// 寫入Excel
			workbook.write();
			workbook.close();
			JOptionPane.showMessageDialog(dataTable, "output success");
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

	// 讀取動作
	private void readActionPerformed(ActionEvent evt) {
		String serialNo = null;
		FindProductDao productDao = new FindProductDao();
		FindRepoStorShipDao rssDao = new FindRepoStorShipDao();
		FindCustomerDao customerDao = new FindCustomerDao();
		RepoStorShip rss = new RepoStorShip();
		Map<String, Product> map = new HashMap<String, Product>();

		String repo = null;
		String stor = null;
		String ship = null;
		String isShipped = "";
		Customer customer = new Customer();
		String customerName = null;
		sdf = new SimpleDateFormat("yyyy/MM/DD");
		// 儲存搜尋記錄
		Vector<String> serialNos = new Vector<String>();

		// 清空已有的內容
		this.products = new Vector<Product>();
		this.model = new ProductTableModel(products);
		dataTable.setModel(model);

		serialNo = JOptionPane.showInputDialog(locale
				.getProperty("Please input the serial no"));

		if (!serialNo.equals("")) {
			if (serialNos.indexOf(serialNo) == -1) {
				list = productDao.getProductBySerialNo(serialNo);
				serialNos.add(serialNo);

				if (list.size() < 1)
					JOptionPane.showMessageDialog(dataTable,
							locale.getProperty("No Records"));

				try {
					for (Product product : list) {

						if (!map.containsKey(product.getDescription()))
							map.put(product.getDescription(), product);
						else {
							Product p = map.get(product.getDescription());
							int value = p.getQty();
							p.setQty(value + product.getQty());
							map.put(product.getDescription(), p);
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(dataTable, "for loop" + e);
				}

				Iterator iterator = map.entrySet().iterator();

				try {
					while (iterator.hasNext()) {
						Entry entry = (Entry) iterator.next();
						rss.setId(((Product) entry.getValue()).getRepoId());
						repo = rssDao.getRssById(rss).getName();

						rss.setId(((Product) entry.getValue()).getStorId());
						stor = rssDao.getRssById(rss).getName();

						rss.setId(((Product) entry.getValue()).getShipId());
						ship = rssDao.getRssById(rss).getName();

						if (((Product) entry.getValue()).getCustomerId()!=0)  {
							customer.setId(((Product) entry.getValue())
									.getCustomerId());
						customerName = customerDao.getCustomerById(customer)
								.getName();
						} else {
							customerName = "";
						}
						String outputDate = ""; 
						String customerOrderNo = "";
						
						if (((Product) entry.getValue()).isShipped()) {
							isShipped = locale.getProperty("shipped");
							outputDate = ((Product) entry.getValue()).getOutputDate()
									.toString();
							customerOrderNo = ((Product) entry
									.getValue()).getCustomerOrderNo();
						} else {
							isShipped = locale.getProperty("not shipped");
						}

						model.addRow(((Product) entry.getValue())
								.getDescription(), ((Product) entry.getValue())
								.getWd(), ((Product) entry.getValue()).getIv(),
								((Product) entry.getValue()).getQty(), repo,
								stor, ship, customerName, customerOrderNo, outputDate, ((Product) entry
										.getValue()).getInputDate().toString(),
								((Product) entry.getValue()).getInLot(),
								isShipped);
					}
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(dataTable, "while Loop" + e);
					e.printStackTrace();
				}
			}

			dataTable.updateUI();
			ColumnsAutoSizer.sizeColumnsToFit(dataTable);
		}
	}

	class ProductTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		public String[] colNames = { "Description", "WD", "IV", "Q'ty",
				locale.getProperty("Reposotory"),
				locale.getProperty("Storage"), locale.getProperty("Ship"),
				locale.getProperty("customer"),
				locale.getProperty("customerOrderNo"),
				locale.getProperty("outputDate"),
				locale.getProperty("Storage_no"), locale.getProperty("lot"),
				locale.getProperty("status") };

		public Class[] colTypes = { String.class, String.class, String.class,
				Integer.class, String.class, String.class, String.class,
				String.class, String.class, String.class, String.class,
				String.class, String.class };

		Vector dataVector;

		public void addRow(String description, String wd, String iv, int qty,
				String repository, String storage, String ship,
				String customer, String customerOrderNo, String outputDate,
				String inputOrderNo, String inLot, String status) {
			Vector v = new Vector(13);
			v.add(0, description);
			v.add(1, wd);
			v.add(2, iv);
			v.add(3, new Integer(qty));
			v.add(4, repository);
			v.add(5, storage);
			v.add(6, ship);
			v.add(7, customer);
			v.add(8, customerOrderNo);
			v.add(9, outputDate);
			v.add(10, inputOrderNo);
			v.add(11, inLot);
			v.add(12, status);
			dataVector.add(v);
		}

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
