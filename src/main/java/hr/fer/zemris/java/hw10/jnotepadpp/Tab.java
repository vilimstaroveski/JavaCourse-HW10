package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Class represents a tab in {@link JNotepadPP}. Tab is defined by
 * editor {@link JTextArea}, tab button {@link TabComponent} and status bar {@link StatusBar}.
 * 
 * @author Vilim Staroveški
 *
 */
public class Tab extends JPanel {

	/**
	 * Dafault serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Image for unmodified tab content.
	 */
	private static final ImageIcon greenDisc = new ImageIcon("icons/green.png");
	
	/**
	 * Image for modified tab content
	 */
	private static final ImageIcon redDisc = new ImageIcon("icons/red.png");
	
	/**
	 * Image for x button for closing current tab.
	 */
	private static final ImageIcon cross = new ImageIcon("icons/cross.png");

	/**
	 * Parent pane in which this tab is.
	 */
	private JTabbedPane parent;
	/**
	 * Editor for text.
	 */
	private JTextArea editor;
	/**
	 * Location of document in this tab. If document is new, fileLocation is null.
	 */
	private Path fileLocation;
	/**
	 * Tab component, below actual editor.
	 */
	private TabComponent tabButton;
	/**
	 * Flag for modified.
	 */
	private boolean modified;
	/**
	 * Parent notepad containing this tab.
	 */
	private JNotepadPP notepad;
	/**
	 * Private status bar for text statistics.
	 */
	private StatusBar statusBar;
	
	/**
	 * Creates new {@link Tab}.
	 * @param parent Parent pane in which this tab is.
	 * @param initialText initial text, if tab is opened from existing file. Null otherwise.
	 * @param initialFileLocation initial file location if tab is opened from existing file. Null otherwise.
	 * @param notepad Parent notepad containing this tab.
	 */
	public Tab(JTabbedPane parent, String initialText, Path initialFileLocation, JNotepadPP notepad) {

		this.parent = parent;
		this.notepad = notepad;
		this.statusBar = notepad.getStatusBar();
		this.setLayout(new BorderLayout());
		this.editor = initialText == null ? new JTextArea() : new JTextArea(
				initialText);
		this.fileLocation = initialFileLocation;

		this.editor.getDocument().addDocumentListener(new DocumentListener() {

			private Document doc = editor.getDocument();
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified();
				statusBar.setLengthLabel(doc.getLength());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified();
				statusBar.setLengthLabel(doc.getLength());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified();
				statusBar.setLengthLabel(doc.getLength());
			}
		});
		this.editor.addCaretListener(new CaretListener() {
			
			private Document doc = editor.getDocument();
			
			@Override
			public void caretUpdate(CaretEvent e) {
				String text = "";
				try {
					text = doc.getText(0, editor.getCaret().getDot());
				} catch (BadLocationException ignorable) {
				}
				int column = 0;
				int line = 0;
				
				for(int i = 0; i < text.length(); i++) {
					char c = text.charAt(i);
					if( c == '\n') {
						line++;
					}
				}
				String lastLine = text;
				if(text.contains("\n")) {
					lastLine = text.substring(text.lastIndexOf('\n'));
				}
				column = lastLine.length();
				int selected = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				
				if(selected != 0) {
					notepad.enableTextMenus();
				}
				else {
					notepad.disableTextMenus();
				}
				
				statusBar.setLineInfoLabel(line, column, selected);
			}
		});
		this.add(new JScrollPane(editor), BorderLayout.CENTER);

		this.tabButton = new TabComponent(this);
		if (this.fileLocation != null) {
			this.tabButton.setToolTip(fileLocation.toString());
			this.tabButton.setTitle(fileLocation.getFileName().toString());
		}
		parent.add(this);
		parent.setTabComponentAt(parent.indexOfComponent(this), this.tabButton);
	}

	/**
	 * Sets file location of this tab. 
	 * @param filePath new file location for this tab.
	 */
	public void setFileLocation(Path filePath) {
		this.fileLocation = filePath;
		this.tabButton.setToolTip(filePath.toString());
		this.tabButton.setTitle(fileLocation.getFileName().toString());
	}
	/**
	 * Sets this tab in unmodified state.
	 */
	public void setUnmodified() {
		this.modified = false;
		this.tabButton.setGreenIcon();
	}
	/**
	 * Sets this tab in modified state.
	 */
	public void setModified() {
		this.modified = true;
		this.tabButton.setRedIcon();
	}
	/**
	 * Returns editor of this tab.
	 * @return editor of this tab.
	 */
	public JTextArea getEditor() {
		return editor;
	}
	/**
	 * If this tab has a file location.
	 * @return If this tab has a file location.
	 */
	public boolean locationLess() {
		return fileLocation == null;
	}
	/**
	 * Returns this file location.
	 * @return this file location.
	 */
	public Path getFileLocation() {
		return fileLocation;
	}
	/**
	 * Saves current document.
	 */
	public void saveDoc() {
		this.notepad.save();
	}
	/**
	 * If this tab is modified.
	 * @return If this tab is modified.
	 */
	public boolean isModified() {
		return modified;
	}
	/**
	 * Returns file name of document in this tab.
	 * @return file name of document in this tab.
	 */
	public String fileName() {
		return fileLocation == null ? "unnamed.txt" : this.fileLocation.getFileName().toString();
	}
	/**
	 * Tab component, below actual editor. Presents icon for modified and unmodified state,
	 * name of current document and close button.
	 * @author Vilim Staroveški
	 *
	 */
	private class TabComponent extends JPanel {

		/**
		 * Default serial verision UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Icon for modified and unmodified state.
		 */
		private Icon icon;
		/**
		 * Name of document.
		 */
		private JLabel fileName;
		/**
		 * Close button, closes this document.
		 */
		private JButton closeButton;

		/**
		 * Creates new {@link TabComponent}.
		 * @param tabParent tab that contains this {@link TabComponent}
		 */
		public TabComponent(Tab tabParent) {
			super(new FlowLayout());
			setOpaque(false);
			
			this.icon = greenDisc;

			this.fileName = new JLabel("unnamed.txt");
			this.fileName.setIcon(icon);
			this.fileName.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					Tab.this.parent.setSelectedComponent(Tab.this);
				}
			});
				

			this.closeButton = new JButton(cross);
			this.closeButton.setContentAreaFilled(false);
			this.closeButton.setFocusable(false);
			this.closeButton.setBorder(BorderFactory.createEtchedBorder());
			this.closeButton.setBorderPainted(false);
			this.closeButton.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					closeButton.setBorderPainted(false);
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					closeButton.setBorderPainted(true);
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					int i = parent.indexOfTabComponent(TabComponent.this);
					if(tabParent.modified) {
						int r = JOptionPane.showConfirmDialog(parent, 
								"'"+TabComponent.this.fileName.getText()+
								"' has unsaved changes. Do you want to save it?", 
								"Warning", 
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						if(r == JOptionPane.YES_OPTION) {
							Tab.this.saveDoc();
						}
					}
					if (i != -1) {
						parent.remove(i);
					}
				}
			});
			this.closeButton.setRolloverEnabled(true);

			add(fileName);
			add(closeButton);
		}

		/**
		 * Sets title of document on this tab components label.
		 * @param newTitle title of document opened in this tab.
		 */
		public void setTitle(String newTitle) {
			this.fileName.setText(newTitle);
		}
		/**
		 * Sets file location as tool tip for this tab component.
		 * @param tip file location of document.
		 */
		public void setToolTip(String tip) {
			this.fileName.setToolTipText(tip);
		}
		/**
		 * Sets modified icon on this componenet.
		 */
		public void setRedIcon() {
			this.fileName.setIcon(redDisc);
		}
		/**
		 * Sets unmodified icon on this componenet.
		 */
		public void setGreenIcon() {
			this.fileName.setIcon(greenDisc);
		}
	}
}
