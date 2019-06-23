package hr.fer.zemris.java.hw10.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.ClipboardHandler;
import hr.fer.zemris.java.hw10.jnotepadpp.Tab;
/**
 * Pastes text from clipboard to text.
 * @author Vilim Staroveški
 *
 */
public class PasteAction extends LocalizableAction {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Clipboard handler.
	 */
	private ClipboardHandler clipboard;
	
	/**
	 * Pane containing all tabs
	 */
	private JTabbedPane tabbedPane;

	/**
	 * Creates new {@link PasteAction}
	 * @param key action name
	 * @param keyDes2 action description
	 * @param lp {@link LocalizationProvider} 
	 * @param tabbedPane pane containing all tabs.
	 * @param clipboard Clipboard handler.
	 */
	public PasteAction(String key, String keyDes2, ILocalizationProvider lp, JTabbedPane tabbedPane, ClipboardHandler clipboard) {
		super(key, keyDes2, lp);

		this.clipboard = clipboard;
		this.tabbedPane = tabbedPane;
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		Tab tab = (Tab) tabbedPane.getSelectedComponent();
		JTextArea editor = tab.getEditor();
		Document doc = editor.getDocument();
		int start = editor.getCaret().getDot();
		try {
			doc.insertString(start, clipboard.getFromClipboard(), null);
		} catch (BadLocationException ignorable) {
		}
	}

}
