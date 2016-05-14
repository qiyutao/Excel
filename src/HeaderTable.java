import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

class HeaderTable extends JTable {
	private ExcelTable refTable;

	public HeaderTable(ExcelTable refTable, int columnWidth) {
		super(new HeaderTableModel(refTable.getRowCount()));

		this.refTable = refTable;
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getColumnModel().getColumn(0).setPreferredWidth(columnWidth);
		setDefaultRenderer(Object.class, new RowHeaderRenderer(refTable, this));
		setPreferredScrollableViewportSize(new Dimension(columnWidth, 0));
		setRowHeight(25);

		refTable.getTableHeader().setDefaultRenderer(new ColHeaderRenderer(refTable));
	}

	public HeaderTable(Component[] components) {
		// TODO Auto-generated constructor stub
		((JTable) components[1]).setDefaultRenderer(Object.class, new RowHeaderRenderer(refTable, this));
	}
}

class HeaderTableModel extends AbstractTableModel {
	private int rowCount;

	public HeaderTableModel(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return 1;
	}

	public Object getValueAt(int row, int column) {
		return row;
	}
}

class RowHeaderRenderer extends JLabel implements TableCellRenderer, ListSelectionListener {
	private JTable reftable;
	private JTable tableShow;

	public RowHeaderRenderer(JTable reftable, JTable tableShow) {
		this.reftable = reftable;
		this.tableShow = tableShow;

		ListSelectionModel listModel = reftable.getSelectionModel();
		listModel.addListSelectionListener(this);
	}

	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
			int row, int col) {
		((HeaderTableModel) table.getModel()).setRowCount(reftable.getRowCount());
		JTableHeader header = reftable.getTableHeader();

		this.setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(CENTER);
		setBackground(header.getBackground());

		if (isSelect(row)) {
			setForeground(Color.white);
			setBackground(Color.lightGray);
		} else {
			setForeground(header.getForeground());
		}

		setFont(header.getFont());
		setText(String.valueOf(row + 1));

		return this;
	}

	public void valueChanged(ListSelectionEvent e) {
		tableShow.repaint();
	}

	private boolean isSelect(int row) {
		int[] sel = reftable.getSelectedRows();
		for (int i = 0; i < sel.length; i++)
			if (sel[i] == row)
				return true;
		return false;
	}
}

class ColHeaderRenderer extends DefaultTableCellRenderer implements ListSelectionListener {
	private ExcelTable reftable = null;

	public ColHeaderRenderer(ExcelTable reTable) {
		// TODO Auto-generated constructor stub
		this.reftable = reTable;

		ListSelectionModel listModel = reftable.getSelectionModel();
		listModel.addListSelectionListener(this);
	}

	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
			int row, int col) {
		Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, col);
		setText(setColName(col));

		Color bg = new Color(225, 180, 85);
		Color fg = new Color(51, 51, 51);

		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(CENTER);

		cell.setBackground(bg);
		cell.setForeground(fg);
		if (isSelect(col)) {
			cell.setForeground(Color.white);
			cell.setBackground(Color.lightGray);
			reftable.getTableHeader().repaint();
		}

		return cell;
	}

	private boolean isSelect(int col) {
		int[] sel = reftable.getSelectedColumns();
		for (int i = 0; i < sel.length; i++)
			if (sel[i] == col)
				return true;
		return false;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		reftable.getTableHeader().repaint();
	}

	public String setColName(int index) {
		String colCode = "";
		char key = 'A';
		int loop = index / 26;
		if (loop > 0) {
			colCode += setColName(loop - 1);
		}
		key = (char) (key + index % 26);
		colCode += key;
		return colCode;
	}

}
