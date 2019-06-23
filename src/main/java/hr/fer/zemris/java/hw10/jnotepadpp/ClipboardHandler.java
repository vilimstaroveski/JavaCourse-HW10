package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
/**
 * Class that handles system clipboard. Posible actions are
 * put on clipboard and get from clipboard.
 * @author Vilim Staroveški
 *
 */
public class ClipboardHandler implements ClipboardOwner {
	/**
	 * System clipboard that is used in this class.
	 */
	private final Clipboard clipboard;
	/**
	 * Creates new {@link ClipboardHandler}
	 */
	public ClipboardHandler() {
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// unused
	}
	/**
	 * Puts text on clipboard.
	 * @param text selected text
	 */
	public void putOnClipboard(String text) {
		
		StringSelection stringSelection = new StringSelection(text);
		clipboard.setContents(stringSelection, this);
	}
	/**
	 * Returns text from clipboard.
	 * @return text from clipboard.
	 */
	public String getFromClipboard() {
		
		String result = "";
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null)
				&& contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents
						.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException e) {
				System.out.println(e.getMessage());
				return "";
			}
		}
		return result;
	}
}
