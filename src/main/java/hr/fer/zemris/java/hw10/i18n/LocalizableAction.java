package hr.fer.zemris.java.hw10.i18n;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/**
 * Action that is localizable.
 * @author Vilim Staroveški
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * Default serial verision UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Listener for localization changes.
	 */
	private ILocalizationListener listener;
	/**
	 * Provider of localization changes.
	 */
	private ILocalizationProvider provider;
	/**
	 * Creates new {@link LocalizableAction}
	 * @param key name of action
	 * @param keyDes2 action descrpition
	 * @param lp localization provider.
	 */
	public LocalizableAction(String key, String keyDes2, ILocalizationProvider lp) {
		
		String translation = lp.getString(key);
		putValue(NAME, translation);
		translation = lp.getString(keyDes2);
		putValue(SHORT_DESCRIPTION, translation);
		provider = lp;
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = provider.getString(key);
				putValue(NAME, translation);
				translation = lp.getString(keyDes2);
				putValue(SHORT_DESCRIPTION, translation);
			}
		};
		
		provider.addLocalizationListener(listener);
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);
}
