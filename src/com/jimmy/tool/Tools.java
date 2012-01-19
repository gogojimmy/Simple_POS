package com.jimmy.tool;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class Tools {

	public static void printTwoArray(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j] + "\t");
			}
			System.out.println();
		}
	}


	public static int getColumnCount(PreparedStatement ps) throws Exception {
		ResultSetMetaData meta = ps.getMetaData();
		return meta.getColumnCount();
	}

	public static int getColumnCount(CallableStatement ps) throws Exception {
		ResultSetMetaData meta = ps.getMetaData();
		return meta.getColumnCount();
	}


	public static String[][] listToArray(LinkedList<String[]> grid) {
		if (grid.size() == 0)
			return null;

		String data[][] = new String[grid.size()][grid.getFirst().length];
		for (int i = 0; i < data.length; i++) {
			String[] temp = grid.remove();
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = temp[j];
			}
		}
		return data;
	}

	public static String[] listToArray(LinkedList<String> data) {
		if (data.size() == 0)
			return null;

		String[] array = new String[data.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = data.get(i);
		}
		return array;
	}

	public static void copyFile(String source, String save) {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			fin = new FileInputStream(new File(source));
			fout = new FileOutputStream(new File(save));
			byte b[] = new byte[10240];
			int n = fin.read(b);
			while (n != -1) {
				fout.write(b, 0, n);
				n = fin.read(b);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fin.close();
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String[] listToArray(ArrayList data) {
		if (data.size() == 0)
			return null;

		String[] array = (String[]) data.toArray(new String[data.size()]);
		return array;
	}

	public static String[] vectorToArray(Vector vector) {
		String[] data = new String[vector.size()];
		for (int i = 0; i < data.length; i++) {
			data[i] = (String) vector.get(i);
		}
		return data;
	}

	public static void backUpDB() {
	}

	public static void recoveryDB() {
	}
	
	public static JTable autoResizeColWidth(JTable table, DefaultTableModel model) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setModel(model);

        int margin = 5;

        for (int i = 0; i < table.getColumnCount(); i++) {
            int                     vColIndex = i;
            DefaultTableColumnModel colModel  = (DefaultTableColumnModel) table.getColumnModel();
            TableColumn             col       = colModel.getColumn(vColIndex);
            int                     width     = 0;

            // Get width of column header
            TableCellRenderer renderer = col.getHeaderRenderer();

            if (renderer == null) {
                renderer = table.getTableHeader().getDefaultRenderer();
            }

            Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);

            width = comp.getPreferredSize().width;

            // Get maximum width of column data
            for (int r = 0; r < table.getRowCount(); r++) {
                renderer = table.getCellRenderer(r, vColIndex);
                comp     = renderer.getTableCellRendererComponent(table, table.getValueAt(r, vColIndex), false, false,
                        r, vColIndex);
                width = Math.max(width, comp.getPreferredSize().width);
            }

            // Add margin
            width += 2 * margin;

            // Set the width
            col.setPreferredWidth(width);
        }

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
            SwingConstants.LEFT);

        // table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);

        return table;
    }
}
