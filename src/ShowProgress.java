import javax.swing.JComponent;
import javax.swing.ProgressMonitor;

public class ShowProgress {
	ProgressMonitor pbar;
	int max;
	int current;
	int old = -1;

	public ShowProgress(int m, JComponent com) {
		// TODO Auto-generated constructor stub
		max = m;
		pbar = new ProgressMonitor(com, "文件进度", "Initializing . . .", 0, 100);
	}

	public void setCurrent(int c) {
		current = c;
		if(c==100)
			pbar.setProgress(100);
		else
			show();
	}

	public void show() {
		int s = (int) ((current / (max * 1.0)) * 100);
		if (old != s) {
			pbar.setProgress(s);
			pbar.setNote("Operation is " + s + "% complete");
			old = s;
		}
	}
}
