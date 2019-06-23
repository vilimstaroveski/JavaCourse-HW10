package hr.fer.zemris.java.hw10.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.Tab;
/**
 * Saves current document as
 * @author Vilim Staroveški
 *
 */
public class SaveAsAction extends LocalizableAction {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Parent of this action
	 */
	private JNotepadPP parent;
	/**
	 * Pane containing all tabs.
	 */
	private JTabbedPane tabbedPane;
	/**
	 * Creates new {@link SaveAsAction}
	 * @param key action name
	 * @param keyDes2 action description
	 * @param lp {@link LocalizationProvider} 
	 * @param tabbedPane pane containing all tabs.
	 * @param jNotepadPP parent of this action
	 */
	public SaveAsAction(String key, String keyDes2, ILocalizationProvider lp, JNotepadPP jNotepadPP, JTabbedPane tabbedPane) {
		super(key, keyDes2, lp);
		parent = jNotepadPP;
		this.tabbedPane = tabbedPane;
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		Tab tab = (Tab) tabbedPane.getSelectedComponent();
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save document"); 
		if(fc.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(parent, 
					"Nothing has been saved!", 
					"System messege", 
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Path file = fc.getSelectedFile().toPath();
		if(Files.exists(file)) {
			int r = JOptionPane.showConfirmDialog(parent, 
					"File "+file+" already exist. Do you want to overwrite it?", 
					"Warning", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if(r != JOptionPane.YES_OPTION) {
				return;
			}
		}
		tab.setFileLocation(file); 
		try {
			Files.write(file, tab.getEditor().getText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(parent, 
					e1.getMessage(),
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		tab.setUnmodified();
	}

}
