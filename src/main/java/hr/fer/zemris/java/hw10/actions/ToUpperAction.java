package hr.fer.zemris.java.hw10.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.Tab;
/**
 * Changes case of selected letters to upper case.
 * @author Vilim Staroveški
 *
 */
public class ToUpperAction extends LocalizableAction {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Pane containing all tabs.
	 */
	private JTabbedPane tabbedPane;
	/**
	 * Creates new {@link ToUpperAction}
	 * @param key action name
	 * @param keyDes action description
	 * @param lp {@link LocalizationProvider} 
	 * @param tabbedPane pane containing all tabs.
	 */
	public ToUpperAction(String key, String keyDes, ILocalizationProvider lp, JTabbedPane tabbedPane) {
		super(key, keyDes, lp);
		this.tabbedPane = tabbedPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Tab tab = (Tab) tabbedPane.getSelectedComponent();
		JTextArea editor = tab.getEditor();
		Document doc = editor.getDocument();
		int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int length = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		try {
			String text = doc.getText(start, length);
			doc.remove(start, length);
			char[] chars = text.toCharArray();
			for(int i = 0; i < chars.length; i++) {
				if(Character.isLowerCase(chars[i])) {
					chars[i] = Character.toUpperCase(chars[i]);
				}
			}
			String result = new String(chars);
			doc.insertString(start, result, null);
		} catch (BadLocationException ignorable) {
		}
	}

}
