import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Edit {
	private ExcelTable table = null;
	private ExcelFrame frame = null;
	private int[] rows;
	private int[] cols;
	private int[] rows1;
	private int[] cols1;
	private int x, y;
	List<String> list;

	public Edit(ExcelFrame f) {
		// TODO Auto-generated constructor stub
		frame = f;
		table = frame.getTable();
		x = 0;
		y = 0;
	}

	public void getCell(boolean flag) {
		rows = table.getSelectedRows();
		cols = table.getSelectedColumns();
		if (rows.length != 0 || cols.length != 0) {
			String tmp = null;
			list = new ArrayList<String>();
			for (int i = 0; i < rows.length; i++)
				for (int j = 0; j < cols.length; j++) {
					tmp = (String) table.getValueAt(rows[i], cols[j]);
					list.add(tmp);
					if (flag)
						table.setValueAt("", rows[i], cols[j]);
				}
		} else {
			JOptionPane.showMessageDialog(null, "请选择要操作的数据", "alert", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void copy() {
		getCell(false);
	}

	public void cut() {
		getCell(true);
	}

	public void paste() {
		rows1 = table.getSelectedRows();
		cols1 = table.getSelectedColumns();
		if (rows.length != rows1.length && cols.length != cols1.length) {
			JOptionPane.showMessageDialog(frame, "请选择正确的操作区域");
		} else {
			for (int i = 0; i < rows.length; i++)
				for (int j = 0; j < cols.length; j++) {
					table.setValueAt(list.get(j + i * cols.length), rows1[i], cols1[j]);
				}
		}
		list = null;
	}

	public void showFind() {
		FindDiag diag = new FindDiag(frame, this);
	}

	public void find(String message) {
		for (int i = x; i < table.getRow(); i++) {
			for (int j = y; j < table.getCol(); j++) {
				y = 0;
				if (((String) table.getValueAt(i, j)).indexOf(message) >= 0) {
					table.getSelectionModel().setSelectionInterval(i, i);
					Rectangle cellRect = table.getCellRect(i, j, true);
					table.scrollRectToVisible(cellRect);
					table.editCellAt(i, j);
					y = j + 1;
					x = i;
					if (y == table.getCol()) {
						y = 0;
						x++;
					}
					return;
				}
			}
		}
		JOptionPane.showMessageDialog(frame, "未找到”" + message + "”");
	}

	public void replace(String message, String replace, boolean flag) {
		String tmp;
		for (int i = x; i < table.getRow(); i++) {
			for (int j = y; j < table.getCol(); j++) {
				y = 0;
				tmp = (String) table.getValueAt(i, j);
				if (tmp.indexOf(message) >= 0) {
					
					Rectangle cellRect = table.getCellRect(i, j, true);
					table.scrollRectToVisible(cellRect);

					tmp = tmp.replace(message, replace);
					table.setValueAt(tmp, i, j);
					if (!flag) {
						y = j + 1;
						x = i;
						if (y == table.getCol()) {
							y = 0;
							x++;
						}
						return;
					}
				}
			}
		}
		JOptionPane.showMessageDialog(frame, "替换完成");
	}
}
