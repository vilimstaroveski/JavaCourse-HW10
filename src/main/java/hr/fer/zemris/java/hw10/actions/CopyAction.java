package hr.fer.zemris.java.hw10.actions;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.ClipboardHandler;
import hr.fer.zemris.java.hw10.jnotepadpp.Tab;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Copies selected text to clipboard.
 * @author Vilim Staroveški
 *
 */
public class CopyAction extends LocalizableAction {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * System clipboard handler.
	 */
	private ClipboardHandler clipboard;
	/**
	 * Tabbed pane containing all tabs.
	 */
	private JTabbedPane tabbedPane;
	/**
	 * Creates new {@link CopyAction}
	 * @param key action name
	 * @param keyDes2 action description
	 * @param lp {@link LocalizationProvider}
	 * @param tabbedPane pane containing all tabs.
	 * @param clipboard system clipboard handler.
	 */
	public CopyAction(String key, String keyDes2, ILocalizationProvider lp, JTabbedPane tabbedPane, ClipboardHandler clipboard) {
		super(key, keyDes2, lp);
		this.tabbedPane = tabbedPane;
		this.clipboard = clipboard;
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		Tab tab = (Tab) tabbedPane.getSelectedComponent();
		JTextArea editor = tab.getEditor();
		Document doc = editor.getDocument();
		int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int length = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		try {
			clipboard.putOnClipboard(doc.getText(start, length));
		} catch (BadLocationException ignorable) {
		}
	}

}
