package com.pos.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.pos.bean.Product;
import com.pos.bean.User;

public class Xls {
	private Workbook workbook;
	private Sheet sheet;
	private final int PACKAGE = 0;
	private final int DESCRIPTION = 1;
	private final int LOTNO = 2;
	private final int SERIALNO = 3;
	private final int QTY = 4;
	private final int IVMIN = 5;
	private final int IVAVG = 6;
	private final int IVMAX = 7;
	private final int DMIN = 8;
	private final int DAVG = 9;
	private final int DMAX = 10;
	private final int VFMIN = 11;
	private final int VFAVG = 12;
	private final int VFMAX = 13;
	private final int WD = 14;
	private final int IV = 15;

	public static boolean isValid(File xls) {
		boolean isValid = false;
		Properties locale = new Properties();
		Workbook workbook = null;
		Sheet sheet = null;
		try {
			locale.load(new FileInputStream("Locale/zh_TW_zh_TW.locale"));
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					locale.getProperty("Can't load Language file"),
					locale.getProperty("Can't load Language file"),
					JOptionPane.WARNING_MESSAGE);
		}

		try {
			workbook = Workbook.getWorkbook(xls);
			sheet = workbook.getSheet(0);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					locale.getProperty("Excel error"),
					locale.getProperty("Excel error"),
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}

		if (sheet.getColumns() == 19) {
			isValid = true;
		}
		return isValid;
	}

	public Xls(File xls) {
		try {
			this.workbook = Workbook.getWorkbook(xls);
			this.sheet = workbook.getSheet(0);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[] getPackages() {
		// int rows = sheet.getRows() - 1;
		int rows = sheet.getColumn(PACKAGE).length - 1;
		String[] packages = new String[rows];
		Cell cell;
		for (int i = 0; i <= rows; i++) {
			cell = sheet.getCell(PACKAGE, i);
			if (i != 0)
				packages[i - 1] = cell.getContents();
		}
		return packages;
	}

	public String[] getColumn(int number) {
		String[] column = new String[16];
		Cell cell;
		for (int i = 0; i < 16; i++) {
			cell = sheet.getCell(number, i);
			column[i] = cell.getContents();
		}
		return column;
	}

	public Product toProduct(int column, User user) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(
				now.getTime());
		Date unknown = new Date(1000,01,01);
		Product product = new Product();
		Cell cell;
		cell = sheet.getCell(0, column);
		product.setPackage(cell.getContents());
		cell = sheet.getCell(1, column);
		product.setDescription(cell.getContents());
		cell = sheet.getCell(2, column);
		product.setLotNo(cell.getContents());
		cell = sheet.getCell(3, column);
		product.setSerialNo(cell.getContents());
		cell = sheet.getCell(4, column);

		try {
			product.setQty(Integer.parseInt(cell.getContents()));
		} catch (Exception e) {
			product.setQty(0);
		}

		cell = sheet.getCell(5, column);

		try {
			product.setIvMin(Float.parseFloat(cell.getContents()));
		} catch (Exception e) {
			product.setIvMin(0);
		}

		cell = sheet.getCell(6, column);

		try {
			product.setIvAvg(Float.parseFloat(cell.getContents()));
		} catch (Exception e) {
			product.setIvAvg(0);
		}

		cell = sheet.getCell(7, column);

		try {
			product.setIvMax(Float.parseFloat(cell.getContents()));
		} catch (Exception e) {
			product.setIvMax(0);
		}

		cell = sheet.getCell(8, column);

		try {
			product.setdMin(Float.parseFloat(cell.getContents()));
		} catch (Exception e) {
			product.setdMin(0);
		}

		cell = sheet.getCell(9, column);

		try {
			product.setdAvg(Float.parseFloat(cell.getContents()));
		} catch (Exception e) {
			product.setdAvg(0);
		}

		cell = sheet.getCell(10, column);

		try {
			product.setdMax(Float.parseFloat(cell.getContents()));
		} catch (Exception e) {
			product.setdMax(0);
		}

		cell = sheet.getCell(11, column);

		try {
			product.setVfMin(Float.parseFloat(cell.getContents()));
		} catch (Exception e) {
			product.setVfMin(0);
		}

		cell = sheet.getCell(12, column);
		try {
			product.setVfAvg(Float.parseFloat(cell.getContents()));
		} catch (Exception e) {
			product.setVfAvg(0);
		}

		cell = sheet.getCell(13, column);

		try {
			product.setVfMax(Float.parseFloat(cell.getContents()));
		} catch (Exception e) {
			product.setVfMax(0);
		}
		cell = sheet.getCell(14, column);
		product.setWd(cell.getContents());
		cell = sheet.getCell(15, column);
		product.setIv(cell.getContents());
		product.setCreatedBy(user.getId());
		product.setUpdatedBy(user.getId());
		product.setCreated_at(currentTimestamp);
		product.setUpdated_at(currentTimestamp);
		product.setShipped(false);
		product.setOutputNo("0");
		product.setOutputDate(unknown);
		product.setCustomerOrderNo("0");

		return product;
	}

	public static void main(String[] args) {
		File file = new File("123.xls");
		Xls xls = new Xls(file);
		String[] data = xls.getPackages();

		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i]);
		}
		System.out.println(data.length);
	}
}
