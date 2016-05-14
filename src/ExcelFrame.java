import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class ExcelFrame extends JFrame {
	private ExcelTable table = null;
	private int row;
	private int col;
	private Menu menu = null;
	private File file = null;
	public static JPanel PANEl = null;

	public ExcelFrame() {
		// TODO Auto-generated constructor stub
		table = new ExcelTable();
		PANEl = new JPanel(new BorderLayout());
	}

	public void createTable() {
		setTitle("Excel++");
		setSize(1000, 700);
		setLocation(162, 22);
		add(PANEl, BorderLayout.NORTH);
		table.setTable();
		setScroll();
		setMenu();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		setVisible(true);
	}

	public void setScroll() {
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setRowHeaderView(new HeaderTable(table, 70));
		add(scrollPane);
	}

	public ExcelTable getTable() {
		return table;
	}

	public void setMenu() {
		menu = new Menu(this);
		menu.addToFrame();
	}

	public void setFile(File f) {
		file = f;
	}

	public void read(File fileChosed) {
		file = fileChosed;
		ReadXls readXls = new ReadXls(fileChosed, table);
		try {
			readXls.setTableValue();
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			readXls.close();
		}
	}

	public void write() {
		WriteXls writeXls;
		try {
			writeXls = new WriteXls(file, table);
			writeXls.write();
			writeXls.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void constructGUI() {
		try {
			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			SubstanceLookAndFeel.setCurrentTheme(new SubstanceTerracottaTheme());
			SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
			SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBubblesWatermark());
			SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
			SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());

		} catch (Exception e) {
			System.err.println("Something went wrong!");
		}
		ExcelFrame frame = new ExcelFrame();
		frame.createTable();
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				constructGUI();
			}
		});
	}
}
