package hr.fer.zemris.java.hw10.i18n;
/**
 * Interface for localization listeners. Listeners are 
 * components that needs to be translated into different language
 * when change occures
 * @author Vilim Staroveški
 *
 */
public interface ILocalizationListener {
	/**
	 * Method called when change of localization occures.
	 */
	public void localizationChanged();
}
