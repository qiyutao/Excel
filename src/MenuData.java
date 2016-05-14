import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MenuData implements ItemListener {
	private ExcelTable table = null;
	private ExcelFrame frame = null;
	private Set<String> set = null;
	private List<JComboBox<String>> list = null;
	private String select = null;
	private TableRowSorter<TableModel> sorter = null;

	public MenuData(ExcelFrame frame) {
		// TODO Auto-generated constructor stub
		this.frame = frame;
		this.table = frame.getTable();
		list = new ArrayList<JComboBox<String>>();
		set = new HashSet<String>();
		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
	}

	public void sort() {
		JOptionPane.showMessageDialog(frame, "通过单击要排序列的列名进行升序和降序的改变");
		RowSorter sorter = new TableRowSorter(table.getModel());
		table.setRowSorter(sorter);
	}

	public void filter() {
		if (table.getCol() == 0 && table.getRow() == 0)
			JOptionPane.showMessageDialog(frame, "表格中没有数据");
		else {
			for (int i = 0; i < table.getCol(); i++) {
				for (int j = 0; j < table.getRow(); j++) {
					String str = (String) table.getValueAt(j, i);
					if (str == null)
						break;
					else {
						set.add(str);
					}
				}
				addItem(i);
				set.clear();
			}
			addComboBox();
		}

	}

	public void sum(boolean avg) {
		int[] rows = table.getSelectedRows();
		int[] cols = table.getSelectedColumns();
		if (rows.length == 0 || cols.length == 0) {
			JOptionPane.showMessageDialog(frame, "请选择求和区域");
			return;
		}
		double total = 0.0;
		double tmp;
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < cols.length; j++) {
				try {
					tmp = Double.parseDouble((String) table.getValueAt(rows[i], cols[j]));
					total += tmp;
				} catch (NumberFormatException e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(frame, "选择区域存在非数字字符，请检查");
				}
			}
		}
		if (avg)
			JOptionPane.showMessageDialog(frame, "总和：" + total);
		else
			JOptionPane.showMessageDialog(frame, "平均值：" + total / rows.length * cols.length);
	}

	public void addItem(int index) {
		Iterator<String> it = set.iterator();
		JComboBox<String> box = new JComboBox<String>();
		String colName = setColName(index);

		while (it.hasNext()) {
			box.addItem(colName + ". " + it.next());
		}
		box.setSelectedIndex(0);
		box.addItemListener(this);
		list.add(box);
	}

	public void addComboBox() {
		JPanel p = new JPanel();
		Iterator<JComboBox<String>> it = list.iterator();
		while (it.hasNext())
			p.add(it.next());
		
		ExcelFrame.PANEl.add(p, BorderLayout.SOUTH);
		p.validate();
		frame.validate();
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED) {
			select = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
			select = select.substring(3, select.length());
			sorter.setRowFilter(RowFilter.regexFilter(select));
			System.out.println(select);
		}
	}
}
