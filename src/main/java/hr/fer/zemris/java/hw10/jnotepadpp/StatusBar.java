package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

/**
 * Class represents bar that gives information about text length, current position of caret
 * and actual time.
 * @author Vilim Staroveški
 *
 */
public class StatusBar extends JPanel {

	/**
	 * Default serial verision UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Label that shows time.
	 */
	private JLabel dateLabel;
	/**
	 * Label that shows text length.
	 */
	private JLabel lengthLabel;
	/**
	 * Label that shows caret position.
	 */
	private JLabel lineInfoLabel;

	/**
	 * Creates new {@link StatusBar}
	 * @param parent notepadpp parent.
	 */
	public StatusBar(JNotepadPP parent) {

		dateLabel = new JLabel();
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lengthLabel = new JLabel();
		lineInfoLabel = new JLabel();

		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setLayout(new GridLayout(1, 3));
		add(lengthLabel);
		add(lineInfoLabel);
		add(dateLabel);

		Thread t = new Thread(() -> {
			while (true) {
				SwingUtilities.invokeLater(() -> {

					dateLabel.setText(new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss").format(new Date()));
					repaint();
				});
				try {
					Thread.sleep(200);
				} catch (Exception e) {
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Updates text length label.
	 * @param textLength length of text
	 */
	public void setLengthLabel(int textLength) {
		this.lengthLabel.setText("length:" + textLength);
	}

	/**
	 * Updates label for caret position
	 * @param line line where caret is
	 * @param column column where caret is.
	 * @param selected how many characters are selected.
	 */
	public void setLineInfoLabel(int line, int column, int selected) {
		this.lineInfoLabel.setText("Ln:" + line + " Col:" + column + " Sel:"
				+ selected);
	}

	/**
	 * Initializes labels to initial values 0.
	 */
	public void initialize() {
		setLengthLabel(0);
		setLineInfoLabel(0, 0, 0);
	}

}
