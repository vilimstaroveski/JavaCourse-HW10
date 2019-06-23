package hr.fer.zemris.java.hw10.i18n;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
/**
 * Class extends {@link LocalizationProviderBridge} and implements {@link WindowListener}
 * and when parent window is closing, it disconnects parent frame from localization provider.
 * @author Vilim Staroveški
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge implements WindowListener {

	/**
	 * Creates new {@link FormLocalizationProvider}
	 * @param parent {@link LocalizationProvider} that gives translations.
	 * @param frame frame that is regitered to {@link LocalizationProvider}
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		frame.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		disconnect();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}
