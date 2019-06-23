package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.actions.ChangeLanguageAction;
import hr.fer.zemris.java.hw10.actions.CloseTabAction;
import hr.fer.zemris.java.hw10.actions.CopyAction;
import hr.fer.zemris.java.hw10.actions.CutAction;
import hr.fer.zemris.java.hw10.actions.ExitAction;
import hr.fer.zemris.java.hw10.actions.InvertAction;
import hr.fer.zemris.java.hw10.actions.NewTabAction;
import hr.fer.zemris.java.hw10.actions.OpenAction;
import hr.fer.zemris.java.hw10.actions.PasteAction;
import hr.fer.zemris.java.hw10.actions.SaveAction;
import hr.fer.zemris.java.hw10.actions.SaveAsAction;
import hr.fer.zemris.java.hw10.actions.SortAscendingAction;
import hr.fer.zemris.java.hw10.actions.SortDescendingAction;
import hr.fer.zemris.java.hw10.actions.StatsAction;
import hr.fer.zemris.java.hw10.actions.ToLowerAction;
import hr.fer.zemris.java.hw10.actions.ToUpperAction;
import hr.fer.zemris.java.hw10.actions.UniqueAction;
import hr.fer.zemris.java.hw10.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Program for multiple document editing. It can open existing documents, save
 * current, edit more than one document in the same time. It gives user function
 * to change language between croatian, english and german.
 * 
 * @author Vilim Staroveški
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -2251112875444054056L;
	
	/**
	 * System clipboard for copy, cut and paste actions.
	 */
	private final ClipboardHandler clipboard;
	
	/**
	 * Status bar for text statistics.
	 */
	private StatusBar statusBar;

	/**
	 * Pane that contains all opened tabs in this program.
	 */
	private JTabbedPane tabbedPane;
	
	/**
	 * {@link LocalizationProvider} for giving translations to this program.
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * Creates new {@link JNotepadPP}
	 */
	public JNotepadPP() {

		setTitle("JNotepad++");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(200, 200);
		setSize(800, 400);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void	windowClosing(WindowEvent e){
				exitAction.actionPerformed(null);
			}
		});
		clipboard = new ClipboardHandler();
		initGUI();
		
	}
	
	/**
	 * Initializes GUI for this program.
	 */
	private void initGUI() {
		
		tabbedPane = new JTabbedPane();
		statusBar = new StatusBar(this);
		statusBar.initialize();
		tabbedPane.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					if(tabbedPane.getTabCount() == 0) {
						JNotepadPP.this.setTitle( "Notepad++" );
						statusBar.initialize();
						return;
					}
					Tab selectedTab = (Tab)tabbedPane.getSelectedComponent();
					if(selectedTab == null || selectedTab.getFileLocation() == null) {
						JNotepadPP.this.setTitle( "Unnamed.txt - Notepad++" );
						selectedTab.getEditor().getCaret().setDot(0);
						statusBar.initialize();
						return;
					}
					JNotepadPP.this.setTitle( selectedTab.getFileLocation().toString() + " - Notepad++");
					selectedTab.getEditor().getCaret().setDot(0);
					statusBar.initialize();
					return;
				}
			});
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		createActions();
		createMenus();
		createToolbar();
	}

	/**
	 * Creates all supported actions of this program.
	 */
	private void createActions() {
		
		openDocumentAction = new OpenAction("open", "openDes", this.flp, this, tabbedPane);
		saveDocumentAction = new SaveAction("save", "saveDes", this.flp, this, tabbedPane);
		saveDocumentAsAction = new SaveAsAction("saveAs", "saveAsDes", this.flp, this, tabbedPane);
		makeNewBlankDocAction = new NewTabAction("newtab", "newtabDes", this.flp, this, tabbedPane);
		exitAction = new ExitAction("exit", "exitDes", this.flp, tabbedPane, this);
		closeCurrentTabAction = new CloseTabAction("closetab", "closetabDes", this.flp, tabbedPane, this, statusBar);
		copySelectedPartAction = new CopyAction("copy", "copyDes", this.flp, tabbedPane, clipboard);
		cutSelectedPartAction = new CutAction("cut", "cutDes", this.flp, tabbedPane, clipboard);
		pasteAction = new PasteAction("paste", "pasteDes", this.flp, tabbedPane, clipboard);
		statsAction = new StatsAction("stats", "statsDes", this.flp, tabbedPane, this);
		enLang = new ChangeLanguageAction("en", "languageEn", "languageDes", this.flp);
		hrLang = new ChangeLanguageAction("hr", "languageHr", "languageDes", this.flp);
		deLang = new ChangeLanguageAction("de", "languageDe", "languageDes", this.flp);
		
		invertAction = new InvertAction("invert", "invertDes", this.flp, tabbedPane);
		toLowerAction = new ToLowerAction("lower", "lowerDes", this.flp, tabbedPane);
		toUpperAction = new ToUpperAction("upper", "upperDes", this.flp, tabbedPane);
		
		sortAscendingAction = new SortAscendingAction("sortA", "sortADes", this.flp, tabbedPane);
		sortDescendingAction = new SortDescendingAction("sortD", "sortDDes", this.flp, tabbedPane);
		uniqueAction = new UniqueAction("unique", "uniqueDes", this.flp, tabbedPane);
	}

	/**
	 *  Creates menus of this program. Menus are File, Edit, Languages, Tools and Sort.
	 */
	private void createMenus() {
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu langMenu = new Menu("languages", this.flp);
		
		JMenu fileMenu = new Menu("file", this.flp);
		
		fileMenu.add(new JMenuItem(makeNewBlankDocAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeCurrentTabAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(statsAction));
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new Menu("edit", this.flp);
		
		editMenu.add(cutSelectedPartAction);
		editMenu.add(copySelectedPartAction);
		editMenu.add(pasteAction);
		
		langMenu.add(new JMenuItem(enLang));
		langMenu.add(new JMenuItem(deLang));
		langMenu.add(new JMenuItem(hrLang));
		
		JMenu toolsMenu = new Menu("tools", this.flp);
		JMenu caseMenu = new Menu("case", this.flp);
		JMenu sortMenu = new Menu("sort", this.flp);
		
		toLower = new JMenuItem(toLowerAction);
		toUpper = new JMenuItem(toUpperAction);
		invert = new JMenuItem(invertAction);
		sortAscending = new JMenuItem(sortAscendingAction);
		sortDescending = new JMenuItem(sortDescendingAction);
		unique = new JMenuItem(uniqueAction);
		
		toLower.setEnabled(false);
		toUpper.setEnabled(false);
		invert.setEnabled(false);
		sortAscending.setEnabled(false);
		sortDescending.setEnabled(false);
		unique.setEnabled(false);
		
		caseMenu.add(invert);
		caseMenu.add(toUpper);
		caseMenu.add(toLower);
		sortMenu.add(sortAscending);
		sortMenu.add(sortDescending);
		sortMenu.add(unique);
		
		toolsMenu.add(caseMenu);
		toolsMenu.add(sortMenu);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(langMenu);
		menuBar.add(toolsMenu);
		
		setJMenuBar(menuBar);
	}

	/**
	 * Creates dockable toolbar.
	 */
	private void createToolbar() {
		
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(makeNewBlankDocAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveDocumentAsAction));
		toolBar.add(cutSelectedPartAction);
		toolBar.add(copySelectedPartAction);
		toolBar.add(pasteAction);
		toolBar.add(statsAction);
		
		getContentPane().add(toolBar, BorderLayout.NORTH);
	}
	
	/**
	 * Triggers save action.
	 */
	public void save() {
		saveDocumentAction.actionPerformed(null);
	}
	
	/**
	 * Disables menus used for text editing.
	 */
	public void disableTextMenus() {
		toLower.setEnabled(false);
		toUpper.setEnabled(false);
		invert.setEnabled(false);
		sortAscending.setEnabled(false);
		sortDescending.setEnabled(false);
		unique.setEnabled(false);
	}
	/**
	 * Enables menus used for text editing.
	 */
	public void enableTextMenus() {
		toLower.setEnabled(true);
		toUpper.setEnabled(true);
		invert.setEnabled(true);
		sortAscending.setEnabled(true);
		sortDescending.setEnabled(true);
		unique.setEnabled(true);
	}
	
	/**
	 * Returns this program's status bar.
	 * @return this program's status bar.
	 */
	public StatusBar getStatusBar() {
		return this.statusBar;
	}
	
	/**
	 * Menu item for changing selected letters case to lower case.
	 */
	private JMenuItem toLower;
	/**
	 * Menu item for changing selected letters case to upper case.
	 */
	private JMenuItem toUpper;
	/**
	 * Menu item for changing selected letters case to oposite case.
	 */
	private JMenuItem invert;
	/**
	 * Menu item for sorting selected lines ascending.
	 */
	private JMenuItem sortAscending;
	/**
	 * Menu item for sorting selected lines descending.
	 */
	private JMenuItem sortDescending;
	/**
	 * Menu item for removing duplicate lines from selected lines.
	 */
	private JMenuItem unique;
	/**
	 * Changes language to english.
	 */
	private Action enLang;
	/**
	 * Changes language to croatian.
	 */
	private Action hrLang;
	/**
	 * Changes language to german.
	 */
	private Action deLang;
	/**
	 * Inverts letter case.
	 */
	private Action invertAction;
	/**
	 * Changes letter case to lower case.
	 */
	private Action toLowerAction;
	/**
	 * Changes letter case to upper case.
	 */
	private Action toUpperAction;
	/**
	 * Sorts selected lines ascending.
	 */
	private Action sortAscendingAction;
	/**
	 * Sorts selected lines descending.
	 */
	private Action sortDescendingAction;
	/**
	 * Removes duplicate lines from selected lines.
	 */
	private Action uniqueAction;
	/**
	 * Closes current tab.
	 */
	private Action closeCurrentTabAction;
	/**
	 * Opens existing document
	 */
	private Action openDocumentAction;
	/**
	 * Saves curent document.
	 */
	private Action saveDocumentAsAction;
	/**
	 * Saves current document as.
	 */
	private Action saveDocumentAction;
	/**
	 * Creates new document and opens it in new tab.
	 */
	private Action makeNewBlankDocAction;
	/**
	 * Copies selected text to clipboard.
	 */
	private Action copySelectedPartAction;
	/**
	 * Cuts selected text to clipboard.
	 */
	private Action cutSelectedPartAction;
	/**
	 * Pastes text from clipboard to text.
	 */
	private Action pasteAction;
	/**
	 * Exits the program.
	 */
	private Action exitAction;
	/**
	 * Gives text statistics.
	 */
	private Action statsAction;
	/**
	 * Method called at program start.
	 * @param args command line arguments, ignorable because they are not used in this program.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater( () -> {
			JFrame notepad = new JNotepadPP();
			notepad.setVisible(true);
		});
	}
}
