package hr.fer.zemris.java.hw10.i18n;

import javax.swing.JMenu;
/**
 * Menu that is localizable. It changes its name depending on current localization
 * @author Vilim Staroveški
 *
 */
public abstract class LocalizableMenu extends JMenu {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Listenr of localization changes.
	 */
	private ILocalizationListener listener;
	/**
	 * Provider of localization changes.
	 */
	private ILocalizationProvider provider;
	/**
	 * Creates new {@link LocalizableMenu}
	 * @param key name of the menu
	 * @param lp localization provider.
	 */
	public LocalizableMenu(String key, ILocalizationProvider lp) {
		
		String translation = lp.getString(key);
		setText(translation);
		
		provider = lp;
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = provider.getString(key);
				setText(translation);
			}
		};
		provider.addLocalizationListener(listener);
	}
}
