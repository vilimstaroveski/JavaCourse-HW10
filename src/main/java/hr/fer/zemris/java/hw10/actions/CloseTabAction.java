package hr.fer.zemris.java.hw10.actions;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.StatusBar;
import hr.fer.zemris.java.hw10.jnotepadpp.Tab;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
/**
 * Closes current tab.
 * @author Vilim Staroveški
 *
 */
public class CloseTabAction extends LocalizableAction {

	/**
	 * Serial verision UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * {@link JNotepadPP} parent of this action.
	 */
	private JNotepadPP parent;
	/**
	 * Status bar of parent notepad.
	 */
	private StatusBar statusBar;
	/**
	 * Pane containing all tabs.
	 */
	private JTabbedPane tabbedPane;
	/**
	 * Creates new {@link CloseTabAction}.
	 * @param key action name
	 * @param keyDes2 action description
	 * @param lp {@link LocalizationProvider}
	 * @param tabbedPane Pane containing all tabs.
	 * @param jNotepadPP {@link JNotepadPP} parent of this action.
	 * @param statusBar Status bar of parent notepad.
	 */
	public CloseTabAction(String key, String keyDes2, ILocalizationProvider lp, JTabbedPane tabbedPane, JNotepadPP jNotepadPP, StatusBar statusBar) {
		super(key, keyDes2, lp);
		parent = jNotepadPP;
		this.tabbedPane = tabbedPane;
		this.statusBar = statusBar;
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		Tab selectedTab = (Tab) tabbedPane.getSelectedComponent();
		if(selectedTab.isModified()) {
			int r = JOptionPane.showConfirmDialog(selectedTab, 
					"'"+selectedTab.fileName()+
					"' has unsaved changes. Do you want to save it?", 
					"Warning", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if(r == JOptionPane.YES_OPTION) {
				parent.save();
			}
		}
		tabbedPane.remove(selectedTab);
		statusBar.initialize();
	}

}
