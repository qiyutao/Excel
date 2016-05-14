import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

public class Menu extends MouseAdapter {
	private ExcelFrame frame = null;
	private JMenuBar menuBar = null;
	private JMenu[] menus = null;
	private JMenuItem[] mItemFiles = null;
	private JMenuItem[] mItemEdits = null;
	private JMenuItem[] mItemInserts = null;
	private JMenuItem[] mItemDatas = null;
	private Event event = null;
	private Edit edit = null;
	private Insert insert = null;
	private MenuData data = null;
	private JPopupMenu popupMenu = null;
	private JMenuItem[] items = null;
	private JLabel[] labels = null;
	private JMenuItem about = null;

	public Menu(ExcelFrame f) {
		// TODO Auto-generated constructor stub
		frame = f;
		event = new Event();
		menuBar = new JMenuBar();
		menus = new JMenu[5];

		mItemFiles = new JMenuItem[5];
		mItemEdits = new JMenuItem[5];
		mItemInserts = new JMenuItem[3];
		mItemDatas = new JMenuItem[4];

		menus[0] = new JMenu("�ļ�");
		menus[1] = new JMenu("�༭");
		menus[2] = new JMenu("����");
		menus[3] = new JMenu("����");
		menus[4] = new JMenu("����");

		edit = new Edit(f);
		insert = new Insert(frame.getTable());
		data = new MenuData(frame);
		popupMenu = new JPopupMenu();
		items = new JMenuItem[7];
	}

	public void addToFrame() {
		addMenuFile();
		menuBar.add(menus[0]);

		addMenuEdit();
		menuBar.add(menus[1]);

		addMenuInsert();
		menuBar.add(menus[2]);

		addMenuData();
		menuBar.add(menus[3]);

		addHelp();
		menuBar.add(menus[4]);

		frame.setJMenuBar(menuBar);

		addPopMenu();
		frame.getTable().addMouseListener(this);
	}

	class Event implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == mItemFiles[1])
				open();
			else if (e.getSource() == mItemFiles[2])
				save();
			else if (e.getSource() == mItemFiles[3])
				saveOther();
			else if (e.getSource() == mItemFiles[4])
				System.exit(0);

			else if (e.getSource() == mItemEdits[0] || e.getSource() == items[0])
				edit.copy();
			else if (e.getSource() == mItemEdits[1] || e.getSource() == items[1])
				edit.cut();
			else if (e.getSource() == mItemEdits[2] || e.getSource() == items[2])
				edit.paste();
			else if (e.getSource() == mItemEdits[3] || e.getSource() == mItemEdits[4])
				edit.showFind();

			else if (e.getSource() == mItemInserts[0] || e.getSource() == items[3])
				insert.addRow();
			else if (e.getSource() == mItemInserts[1] || e.getSource() == items[4])
				insert.addCol();
			else if (e.getSource() == mItemInserts[2])
				insert.addImage();

			else if (e.getSource() == mItemDatas[0])
				data.sort();
			else if (e.getSource() == mItemDatas[1])
				data.filter();
			else if (e.getSource() == mItemDatas[2] || e.getSource() == items[5])
				data.sum(true);
			else if (e.getSource() == mItemDatas[3])
				data.sum(false);

			else if (e.getSource() == about)
				about();
		}
	}

	public void addMenuFile() {
		mItemFiles[0] = new JMenuItem("�½�");
		mItemFiles[1] = new JMenuItem("��");
		mItemFiles[2] = new JMenuItem("����");
		mItemFiles[3] = new JMenuItem("���Ϊ");
		mItemFiles[4] = new JMenuItem("�˳�");

		for (int i = 0; i < mItemFiles.length; i++) {
			menus[0].add(mItemFiles[i]);
			mItemFiles[i].addActionListener(event);
			if (i == 0 || i == 1 || i == 3)
				menus[0].addSeparator();
		}

	}

	public void addMenuEdit() {
		mItemEdits[0] = new JMenuItem("����");
		mItemEdits[1] = new JMenuItem("����");
		mItemEdits[2] = new JMenuItem("ճ��");
		mItemEdits[3] = new JMenuItem("����");
		mItemEdits[4] = new JMenuItem("�滻");

		for (int i = 0; i < mItemEdits.length; i++) {
			menus[1].add(mItemEdits[i]);
			mItemEdits[i].addActionListener(event);
			if (i == 1 || i == 2)
				menus[1].addSeparator();
		}

	}

	public void addMenuInsert() {
		mItemInserts[0] = new JMenuItem("������");
		mItemInserts[1] = new JMenuItem("������");
		mItemInserts[2] = new JMenuItem("����ͼƬ");

		for (int i = 0; i < mItemInserts.length; i++) {
			menus[2].add(mItemInserts[i]);
			mItemInserts[i].addActionListener(event);
			if (i == 1)
				menus[2].addSeparator();
		}

	}

	public void addMenuData() {
		mItemDatas[0] = new JMenuItem("����");
		mItemDatas[1] = new JMenuItem("ɸѡ");
		mItemDatas[2] = new JMenuItem("���");
		mItemDatas[3] = new JMenuItem("ƽ��ֵ");

		for (int i = 0; i < mItemDatas.length; i++) {
			menus[3].add(mItemDatas[i]);

			mItemDatas[i].addActionListener(event);
			if (i == 1)
				menus[3].addSeparator();
		}

	}

	public void addHelp() {
		about = new JMenuItem("����");

		menus[4].add(about);
		about.addActionListener(event);
	}

	public void about() {
		String message = "Excel++\nVersion: V0.32\nAuthor: Seven\n" + "Copyright: Seven.All rights reserved.";
		JOptionPane.showMessageDialog(frame, message);
	}

	public void open() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(frame);
		File fileChosed = fileChooser.getSelectedFile();
		if (fileChosed != null)
			frame.read(fileChosed);
	}

	public void save() {
		frame.write();
	}

	public void saveOther() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showSaveDialog(frame);
		File fileChosed = fileChooser.getSelectedFile();
		if (fileChosed != null) {
			frame.setFile(fileChosed);
			save();
		}
	}

	public void addPopMenu() {

		items[0] = new JMenuItem("����");
		items[1] = new JMenuItem("����");
		items[2] = new JMenuItem("ճ��");
		items[3] = new JMenuItem("������");
		items[4] = new JMenuItem("������");
		items[5] = new JMenuItem("���");
		items[6] = new JMenuItem("ƽ����");

		for (int i = 0; i < 7; i++) {
			popupMenu.add(items[i]);
			items[i].addActionListener(event);
			if (i == 2 || i == 4)
				popupMenu.addSeparator();
		}
		addToolBar();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON3)
			popupMenu.show(frame.getTable(), e.getX(), e.getY());
		else if (e.getSource() == labels[0])
			saveOther();
		else if (e.getSource() == labels[1])
			open();
		else if (e.getSource() == labels[2])
			edit.showFind();
		else if (e.getSource() == labels[3])
			data.sum(true);
		else if (e.getSource() == labels[4])
			data.sort();
		else if (e.getSource() == labels[5])
			data.filter();
	}

	public void addToolBar() {
		JToolBar tBar = new JToolBar();

		labels = new JLabel[6];
		labels[0] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "\\src\\image\\��.png"));
		labels[1] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "\\src\\image\\����.png"));
		labels[2] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "\\src\\image\\����.png"));
		labels[3] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "\\src\\image\\���.png"));
		labels[4] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "\\src\\image\\����.png"));
		labels[5] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "\\src\\image\\ɸѡ.png"));

		for (int i = 0; i < labels.length; i++) {
			tBar.add(labels[i]);
			tBar.addSeparator(new Dimension(30, 30));
			labels[i].addMouseListener(this);
		}

		ExcelFrame.PANEl.add(tBar, BorderLayout.NORTH);
	}
}
