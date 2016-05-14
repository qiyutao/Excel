import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class ExcelTable extends JTable {
	private int row;
	private int col;

	public ExcelTable() {
		// TODO Auto-generated constructor stub
		super(40, 26);
	}

	public void setTable() {
		setColumnSelectionAllowed(true);
		setRowSelectionAllowed(true);
		setRowHeight(25);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setDefaultRenderer(Object.class, new TableStyle());
	}

	public void setRow(int r) {
		row = r;
	}

	public void setCol(int c) {
		col = c;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}

class TableStyle extends DefaultTableCellRenderer {
	public TableStyle() {
		setHorizontalAlignment(RIGHT);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value instanceof ImageIcon) {
			System.out.println(12);
			return new JLabel((ImageIcon) value);
		}

		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (row % 2 == 0 && column % 2 == 0 || row % 2 != 0 && column % 2 != 0)
			cell.setBackground(new Color(240, 245, 250));
		else
			cell.setBackground(new Color(249, 249, 249));

		int[] x = table.getSelectedRows();
		int[] y = table.getSelectedColumns();
		if (x.length != 0 || y.length != 0) {
			for (int i = 0; i < x.length; i++)
				for (int j = 0; j < y.length; j++) {
					if (row == x[i] && column == y[j])
						cell.setBackground(new Color(225, 180, 85));// (128,
																	// 255, 255)
				}
		}
		return cell;
	}
}
