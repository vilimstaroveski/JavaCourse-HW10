package hr.fer.zemris.java.hw10.i18n;

/**
 * Interface for localization providers. Providers
 * gives localization support.
 * @author Vilim Staroveški
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds {@link ILocalizationListener} to list of listeners.
	 * @param listener new {@link ILocalizationListener}
	 */
	public void addLocalizationListener(ILocalizationListener listener);
	/**
	 * Removes {@link ILocalizationListener} from list of listeners.
	 * @param listener {@link ILocalizationListener} that will be removed
	 */
	public void removeLocalizationListener(ILocalizationListener listener);
	/**
	 * Translates given String in current language.
	 * @param tag tag for this String.
	 * @return translation.
	 */
	public String getString(String tag);
}
