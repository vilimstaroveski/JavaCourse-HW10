package hr.fer.zemris.java.hw10.actions;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.Tab;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
/**
 * Presents text statistics, lenght of text, number of 
 * non blank characters and number of lines
 * @author Vilim Staroveški
 *
 */
public class StatsAction extends LocalizableAction {

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
	 * Creates new {@link StatsAction}
	 * @param key action name
	 * @param keyDes2 action description
	 * @param lp {@link LocalizationProvider} 
	 * @param tabbedPane pane containing all tabs.
	 * @param jNotepadPP parent of this action
	 */
	public StatsAction(String key, String keyDes2, ILocalizationProvider lp, JTabbedPane tabbedPane, JNotepadPP jNotepadPP) {
		super(key, keyDes2, lp);

		this.parent = jNotepadPP;
		this.tabbedPane = tabbedPane;
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		Tab selectedTab = (Tab) tabbedPane.getSelectedComponent();
		Document doc = selectedTab.getEditor().getDocument();
		String text = "";
		try {
			text = doc.getText(0, doc.getLength());
		} catch (BadLocationException ignorable) {
		}
		int numberOfCharacters = text.length();
		int numberOfNonBlankCharacters = 0;
		int numberOfLines = 1;
		
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if(Character.isWhitespace(c)) {
				if( c == '\n') {
					numberOfLines++;
				}
				continue;
			}
			numberOfNonBlankCharacters++;
		}
		JOptionPane.showMessageDialog(parent, 
							"Your document has "+numberOfCharacters+" characters, "
									+ ""+numberOfNonBlankCharacters+" non-blank "
									+ "characters and "+numberOfLines+" lines.", 
							"Statistical info", 
							JOptionPane.INFORMATION_MESSAGE);
	}

}
