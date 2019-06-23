package hr.fer.zemris.java.hw10.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw10.i18n.ILocalizationProvider;
import hr.fer.zemris.java.hw10.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.i18n.LocalizationProvider;

/**
 * Changes language of localization.
 * @author Vilim Staroveški
 *
 */
public  class ChangeLanguageAction extends LocalizableAction{

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Current language.
	 */
	private String lang;
	/**
	 * Creates new {@link ChangeLanguageAction}
	 * @param lang language of localization
	 * @param key text name for this action
	 * @param keyDes2 description for this action
	 * @param lp {@link LocalizationProvider}
	 */
	public ChangeLanguageAction(String lang, String key, String keyDes2,
			ILocalizationProvider lp) {
		
		super(key, keyDes2, lp);
		this.lang = lang;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(lang);
	}
}
