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
import javax.swing.table.AbstractTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.jimmy.tool.ColumnsAutoSizer;
import com.pos.bean.Product;
import com.pos.bean.RepoStorShip;
import com.pos.bean.User;
import com.pos.dao.FindProductDao;
import com.pos.dao.FindRepoStorShipDao;

// 存貨週轉率
public class TurnoverPanel extends JPanel {
	private User user;
	private Properties locale;
	private ProductTableModel model;
	private Vector<Product> products;
	private List<Product> list;
	private JTable dataTable;

	/**
	 * Create the panel.
	 */
	public TurnoverPanel(User user, Properties locale) {
		this.user = user;
		this.locale = locale;
		setLayout(null);

		JButton updateButton = new JButton(locale.getProperty("Update"));
		updateButton.setBounds(6, 6, 138, 45);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updateActionPerformed(evt);
			}
		});
		add(updateButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 63, 638, 361);
		add(scrollPane);
		products = new Vector<Product>();
		model = new ProductTableModel(products);
		dataTable = new JTable(model);
		scrollPane.setViewportView(dataTable);

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
			Label label;
			Number number;
			FindRepoStorShipDao rssDao = new FindRepoStorShipDao();
			RepoStorShip rss = new RepoStorShip();
			String repo = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD");
			int i = 1;
			for (Product product : list) {
				// Description
				label = new Label(0, i, product.getDescription());
				sheet.addCell(label);
				// WD
				label = new Label(1, i, product.getWd());
				sheet.addCell(label);
				// IV
				label = new Label(2, i, product.getIv());
				sheet.addCell(label);
				// Qty
				number = new Number(3, i, product.getQty());
				sheet.addCell(number);
				// 倉
				rss.setId(product.getRepoId());
				repo = rssDao.getRssById(rss).getName();
				label = new Label(4, i, repo);
				sheet.addCell(label);
				// 出庫日期
				label = new Label(5, i, sdf.format(product.getOutputDate()));
				sheet.addCell(label);
				// 入庫日期
				label = new Label(6, i, sdf.format(product.getInputDate()));
				sheet.addCell(label);
				i++;
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

	private void updateActionPerformed(ActionEvent evt) {
		FindRepoStorShipDao rssDao = new FindRepoStorShipDao();
		FindProductDao productDao = new FindProductDao();
		RepoStorShip rss = new RepoStorShip();
		String repo = null;
		Map<String, Product> map = new HashMap<String, Product>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD");

		// 清空目前結果
		this.products = new Vector<Product>();
		this.model = new ProductTableModel(products);
		dataTable.setModel(model);

		list = productDao.getProductTurnoverRate();

		// 搜尋結果為空則警告
		if (list.size() < 1)
			JOptionPane.showMessageDialog(dataTable,
					locale.getProperty("No Records"));

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

		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			rss.setId(((Product) entry.getValue()).getRepoId());
			repo = rssDao.getRssById(rss).getName();
			model.addRow(((Product) entry.getValue()).getDescription(),
					((Product) entry.getValue()).getWd(),
					((Product) entry.getValue()).getIv(),
					((Product) entry.getValue()).getQty(), repo,
					((Product) entry.getValue()).getOutputDate().toString(),
					((Product) entry.getValue()).getInputDate().toString());
		}

		dataTable.updateUI();
		ColumnsAutoSizer.sizeColumnsToFit(dataTable);
	}

	class ProductTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		public String[] colNames = { "Description", "WD", "IV", "Q'ty",
				locale.getProperty("Reposotory"),
				locale.getProperty("outputDate"),
				locale.getProperty("Storage_date") };

		public Class[] colTypes = { String.class, String.class, String.class,
				Integer.class, String.class, String.class, String.class };

		Vector dataVector;

		public void addRow(String description, String wd, String iv, int qty,
				String repository, String outputDate, String inputDate) {
			Vector v = new Vector(13);
			v.add(0, description);
			v.add(1, wd);
			v.add(2, iv);
			v.add(3, new Integer(qty));
			v.add(4, repository);
			v.add(5, outputDate);
			v.add(6, inputDate);
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
