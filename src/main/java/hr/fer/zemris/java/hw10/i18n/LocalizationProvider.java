package hr.fer.zemris.java.hw10.i18n;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Class that supports localization changes and localizes its listeners.
 * It implements pattern Singleton.
 * @author Vilim Staroveški
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Current language.
	 */
	private String language;
	/**
	 * Resorce bundle for current language
	 */
	private ResourceBundle bundle;
	/**
	 * Instance of this class.
	 */
	private static LocalizationProvider instance;
	/**
	 * Creates new {@link LocalizationProvider}.
	 */
	private LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.hw10.i18n.prijevodi", locale);
		instance = this;
	}
	/**
	 * Returns instance of this provider.
	 * @return instance of this provider.
	 */
	public static LocalizationProvider getInstance() {
		if(instance == null) {
			return new LocalizationProvider();
		}
		return instance;
	}
	/**
	 * Sets language to diffrent one.
	 * @param language new language.
	 */
	public void setLanguage(String language) {
		if(this.language.equals(language)) {
			return;
		}
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.hw10.i18n.prijevodi", locale);
		fire();
	}
	
	@Override
	public String getString(String tag) {
		return bundle.getString(tag);
	}

}
