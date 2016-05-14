import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WriteXls {
	private WritableWorkbook workbook = null;
	private WritableSheet sheet = null;
	private jxl.write.Label label = null;
	private ExcelTable table = null;
	private ShowProgress progress = null;

	public WriteXls(File name, ExcelTable t) throws IOException {
		// TODO Auto-generated constructor stub
		workbook = Workbook.createWorkbook(name);
		sheet = workbook.createSheet("sheet1", 0);
		table = t;
	}

	public void write() throws RowsExceededException, WriteException, IOException {
		int col = table.getColumnCount();
		int row = table.getRowCount();

		progress = new ShowProgress(row, table);
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				Object value = table.getValueAt(i, j);
				if (value != null) {
					if (!(value instanceof ImageIcon)) {
						label = new jxl.write.Label(j, i, (String) value);
						sheet.addCell(label);
					}
				}
				progress.setCurrent(i);
			}
		progress.setCurrent(100);
		workbook.write();
	}

	public void close() throws WriteException, IOException {
		workbook.close();
	}
}
