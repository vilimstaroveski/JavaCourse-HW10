package hr.fer.zemris.java.hw10.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.Tab;
/**
 * Sorts selected lines in descending order.
 * @author Vilim Staroveški
 *
 */
public class SortDescendingAction extends LocalizableAction {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Pane containing all tabs.
	 */
	private JTabbedPane tabbedPane; 
	/**
	 * Creates new {@link SortDescendingAction}
	 * @param key action name
	 * @param keyDes2 action description
	 * @param lp {@link LocalizationProvider} 
	 * @param tabbedPane pane containing all tabs.
	 */
	public SortDescendingAction(String key, String keyDes2, ILocalizationProvider lp, JTabbedPane tabbedPane) {
		super(key, keyDes2, lp);
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
			int offsetOfFirstLine = editor.getLineStartOffset(editor.getLineOfOffset(start));
			int offsetEndOfLastLine = editor.getLineEndOffset(editor.getLineOfOffset(start+length));
			
			int offset = offsetOfFirstLine;
			String selectedPart = doc.getText(offset, offsetEndOfLastLine - offset);
			doc.remove(offset, offsetEndOfLastLine - offset);
			String[] lines = selectedPart.split("\n");
			List<String> listOfLines = new ArrayList<String>();
			for(String line : lines) {
				listOfLines.add(line+"\n");
			}
			Locale hrLocale = new Locale("hr");
			Collator hrCollator = Collator.getInstance(hrLocale);
			
			Collections.sort(listOfLines, hrCollator);
			Collections.reverse(listOfLines);
			for(String line : listOfLines) {
				doc.insertString(offsetOfFirstLine, line, null);
				offsetOfFirstLine = offsetOfFirstLine + line.length();
			}
			
		} catch (BadLocationException ignorable) {
		}
	}

}
