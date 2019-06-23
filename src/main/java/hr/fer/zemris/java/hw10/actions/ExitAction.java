package hr.fer.zemris.java.hw10.actions;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.Tab;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

/**
 * Exits from program.
 * @author Vilim Staroveški
 *
 */
public class ExitAction extends LocalizableAction {

	/**
	 * Serial verision UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Pane containing all tabs
	 */
	private JTabbedPane tabbedPane;
	/**
	 * {@link JNotepadPP} parent of this action.
	 */
	private JNotepadPP parent;
	/**
	 * Creates new {@link ExitAction}
	 * @param key action name 
	 * @param keyDes2 action description
	 * @param lp {@link LocalizationProvider}
	 * @param tabbedPane Pane containing all tabs
	 * @param parent parent of this action
	 */
	public ExitAction(String key, String keyDes2, ILocalizationProvider lp, JTabbedPane tabbedPane, JNotepadPP parent) {

		super(key,  keyDes2,  lp);
		
		this.tabbedPane = tabbedPane;
		this.parent = parent;
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (tabbedPane.getTabCount() == 0) {
			parent.dispose();
			return;
		}
		List<Tab> unsavedTabs = new ArrayList<Tab>();
		for (int i = tabbedPane.getTabCount() - 1; i >= 0; i--) {
			Tab tab = (Tab) tabbedPane.getComponentAt(i);
			if (tab.isModified()) {
				unsavedTabs.add(tab);
			}
		}
		if (unsavedTabs.size() != 0) {
			int r = JOptionPane
					.showConfirmDialog(
							parent,
							"There are unsaved changes in some tags, do you want to save changes?",
							"Warning", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			if (r == JOptionPane.YES_OPTION) {
				for (Tab tab : unsavedTabs) {
					tabbedPane.setSelectedComponent(tab);
					parent.save();
				}
			}
			if (r == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		parent.dispose();
	}
}
