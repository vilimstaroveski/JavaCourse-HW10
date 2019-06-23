package hr.fer.zemris.java.hw10.actions;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.Tab;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
/**
 * Opens existing document and presents it in new tab.
 * @author Vilim Staroveški
 *
 */
public class OpenAction extends LocalizableAction {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Parent of this action.
	 */
	private JNotepadPP parent;
	
	/**
	 * Pane containing all tabs
	 */
	private JTabbedPane tabbedPane;

	/**
	 * Creates new {@link OpenAction}
	 * @param key action name
	 * @param keyDes2 action description
	 * @param lp {@link LocalizationProvider} 
	 * @param tabbedPane pane containing all tabs.
	 * @param jNotepadPP parent of this action
	 */
	public OpenAction(String key, String keyDes2, ILocalizationProvider lp,
			JNotepadPP jNotepadPP, JTabbedPane tabbedPane) {
		
		super(key, keyDes2, lp);
		parent = jNotepadPP;
		this.tabbedPane = tabbedPane;
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path filePath = fc.getSelectedFile().toPath();
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			Component tabComp = tabbedPane.getComponent(i);
			if (!(tabComp instanceof Tab)) {
				continue;
			}
			Tab tab = (Tab) tabComp;
			if (tab.getFileLocation() == null) {
				continue;
			}
			try {
				if (Files.isSameFile(tab.getFileLocation(), filePath)) {
					tabbedPane.setSelectedComponent(tab);
					return;
				}
			} catch (IOException ignorable) {
			}
		}
		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(
					parent, 
					"File " + filePath + " does not exist!", 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(filePath);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(parent, e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		tabbedPane.setSelectedComponent(new Tab(tabbedPane, new String(bytes,
				StandardCharsets.UTF_8), filePath, parent));
	}
}
