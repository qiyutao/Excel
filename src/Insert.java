import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class Insert {
	private ExcelTable table = null;
	
	public Insert(ExcelTable table) {
		// TODO Auto-generated constructor stub
		this.table = table;
	}
	
	public void addRow() {
		int count=table.getColumnCount();
		Object[] data=new  Object[count];
		((DefaultTableModel)table.getModel()).addRow(data);
	}
	
	public void addCol() {
		int count=table.getRowCount();
		Object[] data=new  Object[count];
		((DefaultTableModel)table.getModel()).addColumn(data);
	}
	
	public void addImage() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(table);
		File file = fileChooser.getSelectedFile();
		ImageIcon icon = new ImageIcon(file.getPath());
		table.setValueAt((ImageIcon)icon, 1, 1);
	}
}

