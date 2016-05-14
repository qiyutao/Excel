import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class ReadXls {
	private File fileXls = null;
	private ExcelTable table = null;
	private Workbook workbook = null;
	private ShowProgress progress = null;
	private int col;
	private int row;

	public ReadXls(File f, ExcelTable t) {
		// TODO Auto-generated constructor stub
		fileXls = f;
		table = t;
	}

	public void setTableValue() throws BiffException, IOException {
		workbook = Workbook.getWorkbook(fileXls);
		Sheet sheet = workbook.getSheet(0);
		row = sheet.getRows();
		col = sheet.getColumns();

		if (row > table.getRowCount() || col > table.getColumnCount())
			addCell();

		Cell cell = null;
		progress = new ShowProgress(row, table);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				cell = sheet.getCell(j, i);
				table.setValueAt(cell.getContents(), i, j);
			}
			progress.setCurrent(i);
		}
		progress.setCurrent(100);
	}

	public void addCell() {

		table.setRow(row);
		table.setCol(col);
		Insert insert = new Insert(table);

		if (row > table.getRowCount()) {
			int tmp = row - table.getRowCount();
			for (int i = 0; i < tmp; i++) {
				insert.addRow();
			}
		}
		if (col > table.getColumnCount()) {
			int tmp = col - table.getColumnCount();
			for (int i = 0; i < tmp; i++) {
				insert.addCol();
			}
		}
	}

	public void close() {
		workbook.close();
	}
}
