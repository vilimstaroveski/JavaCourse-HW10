package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableMenu;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;

/**
 * Class representing a localized menu in menu bar.
 * @author Vilim Staroveški
 *
 */
public class Menu extends LocalizableMenu {

	/**
	 * Default serial UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new {@link LocalizableMenu}
	 * @param key tag for menu name
	 * @param lp {@link LocalizationProvider}
	 */
	public Menu(String key, ILocalizationProvider lp) {
		super(key, lp);
	}

}
