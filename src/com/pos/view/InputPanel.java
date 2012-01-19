package com.pos.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.pos.bean.Product;
import com.pos.bean.RepoStorShip;
import com.pos.bean.User;
import com.pos.controller.Xls;
import com.pos.dao.FindRepoStorShipDao;
import com.pos.dao.InsertProductDao;

// 入庫表單
public class InputPanel extends JPanel {
	private JTextField inputNoField;
	private JTextField inputDateField;
	private JTextField inPoField;
	private JTextField inLotField;
	private User user;
	private Properties locale;
	private Xls xls;
	private JFileChooser chooser;
	private JLabel okLabel, packageListLabel, readListLabel, unknownListLabel,
			packageListCountLabel;
	private JButton readButton, okButton, resetButton;
	private ArrayList<String> packageList;
	private JTextArea unknownListArea, readListArea, packageListArea;
	private boolean isValid;
	private JComboBox repoComboBox, storComboBox, shipComboBox;
	private SimpleDateFormat sdf;
	private Date today;

	/**
	 * Create the panel.
	 */
	public InputPanel(User user, Properties locale) {
		this.user = user;
		this.locale = locale;
		this.xls = null;
		this.isValid = false;
		setLayout(null);

		JLabel inputNoLabel = new JLabel(locale.getProperty("Storage_no"));
		inputNoLabel.setBounds(23, 26, 80, 16);
		add(inputNoLabel);

		inputNoField = new JTextField();
		inputNoField.setBounds(115, 20, 134, 28);
		add(inputNoField);
		inputNoField.setColumns(10);

		JLabel inputDateLabel = new JLabel(locale.getProperty("Storage_date"));
		inputDateLabel.setBounds(23, 54, 80, 16);
		add(inputDateLabel);

		sdf = new SimpleDateFormat("yyyy/MM/DD");
		today = new Date();
		inputDateField = new JTextField(sdf.format(today));
		inputDateField.setBounds(115, 48, 134, 28);

		add(inputDateField);
		inputDateField.setColumns(10);

		JLabel inPoLabel = new JLabel(locale.getProperty("Order_no"));
		inPoLabel.setBounds(23, 82, 80, 16);
		add(inPoLabel);

		inPoField = new JTextField();
		inPoField.setBounds(115, 76, 134, 28);
		add(inPoField);
		inPoField.setColumns(10);

		JLabel inLotLabel = new JLabel("Lot");
		inLotLabel.setBounds(23, 110, 61, 16);
		add(inLotLabel);

		inLotField = new JTextField();
		inLotField.setBounds(115, 104, 134, 28);
		add(inLotField);
		inLotField.setColumns(10);

		JLabel repoLabel = new JLabel(locale.getProperty("Reposotory"));
		repoLabel.setBounds(306, 26, 80, 16);
		add(repoLabel);

		JLabel storageLabel = new JLabel(locale.getProperty("Storage"));
		storageLabel.setBounds(306, 54, 80, 16);
		add(storageLabel);

		JLabel shipLabel = new JLabel(locale.getProperty("Ship"));
		shipLabel.setBounds(306, 82, 80, 16);
		add(shipLabel);

		FindRepoStorShipDao findDao = new FindRepoStorShipDao();

		repoComboBox = new JComboBox();
		for (RepoStorShip rss : findDao.findAll(1)) {
			repoComboBox.addItem(rss);
		}
		repoComboBox.setBounds(398, 22, 93, 27);
		add(repoComboBox);

		storComboBox = new JComboBox();
		for (RepoStorShip rss : findDao.findAll(2)) {
			storComboBox.addItem(rss);
		}
		storComboBox.setBounds(398, 50, 93, 27);
		add(storComboBox);

		shipComboBox = new JComboBox();
		for (RepoStorShip rss : findDao.findAll(3)) {
			shipComboBox.addItem(rss);
		}
		shipComboBox.setBounds(398, 78, 93, 27);
		add(shipComboBox);

		JLabel excelLabel = new JLabel(locale.getProperty("File"));
		excelLabel.setBounds(306, 110, 61, 16);
		add(excelLabel);

		JButton fileButton = new JButton(locale.getProperty("fileButton"));
		fileButton.setBounds(398, 105, 117, 29);
		fileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileButtonActionPerformed(evt);
			}
		});
		add(fileButton);

		okLabel = new JLabel();
		okLabel.setBounds(527, 110, 61, 16);
		add(okLabel);

		// 以下為Excel檔確認ok後顯示的元件

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 174, 134, 224);
		add(scrollPane);

		packageListArea = new JTextArea();
		packageListArea.setEnabled(false);
		packageListArea.setEditable(false);
		scrollPane.setViewportView(packageListArea);

		packageListLabel = new JLabel(locale.getProperty("packagelist"));
		packageListLabel.setBounds(23, 146, 117, 16);
		packageListLabel.setEnabled(false);
		add(packageListLabel);

		readListLabel = new JLabel(locale.getProperty("readList"));
		readListLabel.setBounds(168, 146, 157, 16);
		readListLabel.setEnabled(false);
		add(readListLabel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(169, 174, 134, 224);
		add(scrollPane_1);

		readListArea = new JTextArea();
		readListArea.setEnabled(false);
		readListArea.setEditable(false);
		scrollPane_1.setViewportView(readListArea);

		resetButton = new JButton(locale.getProperty("reset"));
		resetButton.setBounds(483, 347, 117, 51);
		resetButton.setEnabled(false);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				resetActionPerformed(evt);
			}
		});
		add(resetButton);

		okButton = new JButton(locale.getProperty("Input"));
		okButton.setBounds(483, 262, 117, 51);
		okButton.setEnabled(false);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				okActionPerformed(evt);
			}
		});
		add(okButton);

		readButton = new JButton(locale.getProperty("read"));
		readButton.setBounds(483, 174, 117, 51);
		readButton.setEnabled(false);
		readButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				readActionPerformed(evt);
			}
		});
		add(readButton);

		unknownListLabel = new JLabel(locale.getProperty("unknownList"));
		unknownListLabel.setEnabled(false);
		unknownListLabel.setBounds(316, 146, 133, 16);
		add(unknownListLabel);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(315, 174, 134, 224);
		add(scrollPane_2);

		unknownListArea = new JTextArea();
		unknownListArea.setEnabled(false);
		unknownListArea.setEditable(false);
		scrollPane_2.setViewportView(unknownListArea);

		packageListCountLabel = new JLabel();
		packageListCountLabel.setBounds(23, 403, 134, 16);
		packageListCountLabel.setEnabled(false);
		add(packageListCountLabel);

	}

	private void isDateValid() {
		String text = inputDateField.getText();
		String expression = "(19|20)[0-9]{2}[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])";
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

	private void checkPackageNo(String packageNo) {
		Boolean isTheSame = false;

		for (Iterator<String> iter = packageList.iterator(); iter.hasNext();) {
			String string = iter.next();
			if (string.equals(packageNo)) {
				iter.remove();
				isTheSame = true;
			}
		}

		if (isTheSame) {
			readListArea.append(packageNo + "\n");
			packageListArea.setText("");
			for (int i = 0; i < packageList.size(); i++) {
				packageListArea.append(packageList.get(i) + "\n");
			}
			packageListCountLabel.setText(locale.getProperty("All: ")
					+ packageList.size());
		} else {
			unknownListArea.append(packageNo + "\n");
			JOptionPane.showMessageDialog(null, locale.getProperty("unknown package no"), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void checkIsValid() {
		if (inputNoField.getText().equals("") || inPoField.getText().equals("")
				|| inLotField.getText().equals("")) {
			isValid = false;
		} else {
			isValid = true;
		}

	}

	private void okActionPerformed(ActionEvent evt) {
		checkIsValid();
		isDateValid();
		if (!packageListArea.getText().equals("") || isValid == false) {
			JOptionPane
					.showMessageDialog(
							this,
							locale.getProperty("You have to find all package and fill all fields !"));
			return;
		}
		InsertProductDao dao = new InsertProductDao();
		Product product;
		Date date = null;
		boolean isSuccess = false;
		try {
			date = sdf.parse(inputDateField.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 1; i < xls.getPackages().length + 1; i++) {
			product = xls.toProduct(i, user);
			product.setInputNo(inputNoField.getText());
			product.setInputDate(new java.sql.Date((date).getTime()));
			product.setInPo(inPoField.getText());
			product.setInLot(inLotField.getText());
			product.setRepoId(((RepoStorShip) repoComboBox.getSelectedItem())
					.getId());
			product.setStorId(((RepoStorShip) storComboBox.getSelectedItem())
					.getId());
			product.setShipId(((RepoStorShip) shipComboBox.getSelectedItem())
					.getId());
			try {
				dao.setProductInfoToDBbean(product);
				isSuccess = true;
			} catch (Exception e) {
				isSuccess = false;
				JOptionPane.showMessageDialog(this,
						locale.getProperty("Insert faild"));
			}
		}
		if (isSuccess) {
			JOptionPane.showMessageDialog(this,
					locale.getProperty("Insert Success"));
			resetActionPerformed(evt);
		}
	}

	private void resetActionPerformed(ActionEvent evt) {
		inputNoField.setText("");
		inputDateField.setText(sdf.format(today));
		inPoField.setText("");
		inLotField.setText("");
		this.xls = null;
		okLabel.setText("");
		packageListArea.setText("");
		packageListArea.setEnabled(false);
		readListArea.setText("");
		readListArea.setEnabled(false);
		unknownListArea.setText("");
		unknownListArea.setEnabled(false);
		okButton.setEnabled(false);
		readButton.setEnabled(false);
		resetButton.setEnabled(false);
		packageListCountLabel.setEnabled(false);
		packageListCountLabel.setText("");
		this.packageList = null;
	}

	private void readActionPerformed(ActionEvent evt) {
		String packageNo = null;
		Vector<String> packages = new Vector<String>();
		do {
			packageNo = JOptionPane.showInputDialog(locale
					.getProperty("Please input the package no"));
			if (!packageNo.equals("")) {
				if (packages.indexOf(packageNo) == -1)
					checkPackageNo(packageNo);
			}
		} while (!packageNo.equals(""));
	}

	private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.xls = null;
		this.packageList = null;
		packageListArea.setText("");
		packageListArea.setEnabled(false);
		readListArea.setText("");
		readListArea.setEnabled(false);
		unknownListArea.setText("");
		unknownListArea.setEnabled(false);
		okButton.setEnabled(false);
		readButton.setEnabled(false);
		resetButton.setEnabled(false);
		packageListCountLabel.setEnabled(false);
		packageListCountLabel.setText("");
		
		Boolean fileIsOk = false;
		chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Excel file only", "xls");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle(locale.getProperty("Choose Excel file"));
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			fileIsOk = Xls.isValid(file);
			if (true) {
				// okLabel.setText("OK");
				packageListLabel.setEnabled(true);
				readListLabel.setEnabled(true);
				packageListArea.setEnabled(true);
				unknownListLabel.setEnabled(true);
				unknownListArea.setEnabled(true);
				readListArea.setEnabled(true);
				readButton.setEnabled(true);
				okButton.setEnabled(true);
				resetButton.setEnabled(true);
				packageListCountLabel.setEnabled(true);
				this.xls = new Xls(file);
				packageList = new ArrayList<String>();

				for (String string : xls.getPackages()) {
					packageList.add(string);
					packageListArea.append(string + "\n");
				}
				packageListCountLabel.setText(locale.getProperty("All:")
						+ packageList.size());
			} else {
				// okLabel.setText("Faild");
			}
		}
	}
}
