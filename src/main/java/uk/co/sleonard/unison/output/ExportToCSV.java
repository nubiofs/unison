package uk.co.sleonard.unison.output;

import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import uk.co.sleonard.unison.gui.UNISoNException;

/**
 * The Class ExportToCSV.
 *
 * @author Stephen
 */
public class ExportToCSV {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		ExportToCSV test = new ExportToCSV();
		String data = "M'I-5'Persecut ion , Bern ard Le vin expre sses h is v iews";
		test.extractCommas(data);
	}

	/**
	 * Export table to csv.
	 *
	 * @param table
	 *            the table
	 * @param fieldNames
	 *            the field names
	 * @throws UNISoNException
	 *             the UNI so n exception
	 */
	@SuppressWarnings("deprecation")
	public void exportTableToCSV(JTable table, Vector<String> fieldNames) throws UNISoNException {
		FileDialog file = new FileDialog(new JFrame(), "Save CSV Network File", FileDialog.SAVE);
		final String CSV_FILE_SUFFIX = ".csv";
		String initialValue = "*" + CSV_FILE_SUFFIX;
		file.setFile(initialValue); // set initial filename filter
		file.setFilenameFilter(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(CSV_FILE_SUFFIX)) {
					return true;
				}
				return false;
			}

		});
		file.show(); // Blocks
		String curFile = null;
		if ((curFile = file.getFile()) != null && !curFile.equals(initialValue)) {

			if (!curFile.endsWith(CSV_FILE_SUFFIX)) {
				curFile += CSV_FILE_SUFFIX;
			}
			String filename = file.getDirectory() + curFile;

			exportTable(filename, table, fieldNames);
		}

	}

	/**
	 * Export table.
	 *
	 * @param fileName
	 *            the file name
	 * @param table
	 *            the table
	 * @param fieldNames
	 *            the field names
	 * @throws UNISoNException
	 *             the UNI so n exception
	 */
	public void exportTable(String fileName, JTable table, Vector<String> fieldNames) throws UNISoNException {
		try {
			File file = new File(fileName);
			if (file != null) {

				// clear old file if it exists
				if (file.exists()) {
					file.delete();
				}
				try {
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
					PrintWriter fileWriter = new PrintWriter(bufferedWriter);
					String data;
					for (int j = 0; j < table.getColumnCount(); ++j) {

						// replace any commas in data!
						data = extractCommas(fieldNames.get(j));

						fileWriter.print(data + ",");
					}
					fileWriter.println("");
					for (int i = 0; i < table.getRowCount(); ++i) {
						for (int j = 0; j < table.getColumnCount(); ++j) {
							data = extractCommas(table.getValueAt(i, j).toString());
							fileWriter.print(data + ",");
						}
						fileWriter.println("");
					}
					fileWriter.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error " + e);
				}
			}

		} catch (Exception e) {
			throw new UNISoNException("Failed to export to CSV", e);
		}
	}// export Table

	/**
	 * Extract commas.
	 *
	 * @param data
	 *            the data
	 * @return the string
	 */
	private String extractCommas(String data) {
		if (data.startsWith("M'I-5'Persecut ion")) {
			int i = 0;
			i++;
		}
		if (data.indexOf(',') > -1) {
			data = data.replaceAll(",", ";");
		}
		return data;
	}

}
