package hr.fer.zemris.java.hw10.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.Tab;
/**
 * Creates new tab in {@link JNotepadPP} program
 * @author Vilim Staroveški
 *
 */
public class NewTabAction extends LocalizableAction {
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
	 * Creates new {@link NewTabAction}
	 * @param key action name
	 * @param keyDes2 action description
	 * @param lp {@link LocalizationProvider} 
	 * @param tabbedPane pane containing all tabs.
	 * @param jNotepadPP parent of this action
	 */
	public NewTabAction(String key, String keyDes2, ILocalizationProvider lp, JNotepadPP jNotepadPP, JTabbedPane tabbedPane) {
		super(key, keyDes2, lp);
		
		parent = jNotepadPP;
		this.tabbedPane = tabbedPane;
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		tabbedPane.setSelectedComponent(new Tab(tabbedPane, null, null, parent));
	}

}
