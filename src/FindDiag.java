import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindDiag extends JDialog implements ActionListener {
	private JTextField text1, text2;
	private ExcelFrame frame = null;
	private Edit edit = null;
	private JButton[] Buttons;

	public FindDiag(ExcelFrame frame, Edit edit) {
		// TODO Auto-generated constructor stub
		super(frame, "Find/Replace");
		this.frame = frame;
		this.edit = edit;
		Buttons = new JButton[4];
		launch();
	}

	public void launch() {
		setSize(350, 150);
		setLocation(frame.getLocation().x + 200, frame.getLocation().y + 200);
		setText();
		setButtons();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void setText() {
		JLabel label1 = new JLabel("Find:");
		text1 = new JTextField(20);

		JLabel label2 = new JLabel("Replace with:");
		text2 = new JTextField(20);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));

		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);

		add(panel, BorderLayout.NORTH);
	}

	public void setButtons() {
		Buttons[0] = new JButton("Find");
		Buttons[1] = new JButton("Next");
		Buttons[2] = new JButton("Replace");
		Buttons[3] = new JButton("Replace All");

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		for (int i = 0; i < 4; i++) {
			Buttons[i].addActionListener(this);
			panel.add(Buttons[i]);
		}
		add(panel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == Buttons[0] || e.getSource() == Buttons[1])
			edit.find(text1.getText());
		else if (e.getSource() == Buttons[2])
			edit.replace(text1.getText(), text2.getText(), false);
		else if (e.getSource() == Buttons[3])
			edit.replace(text1.getText(), text2.getText(), true);
	}
}
