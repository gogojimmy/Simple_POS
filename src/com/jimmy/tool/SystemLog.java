package com.jimmy.tool;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

public class SystemLog {

	private static Logger wrire() {
		Logger logger = Logger.getLogger("ErrorLog");
		FileHandler fh;

		try {
			fh = new FileHandler("Log/Error.log", true);
			logger.addHandler(fh);
			logger.setLevel(Level.ALL);
			SimpleFormatter formmater = new SimpleFormatter();
			fh.setFormatter(formmater);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error", "Error to write to log",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return logger;
	}

	public static void error(String log) {
		Logger logger = SystemLog.wrire();
		logger.log(Level.SEVERE, log);
	}

	public static void warning(String log) {
		Logger logger = SystemLog.wrire();
		logger.log(Level.WARNING, log);
	}

	public static void fine(String log) {
		Logger logger = SystemLog.wrire();
		logger.log(Level.FINEST, log);
	}
}
