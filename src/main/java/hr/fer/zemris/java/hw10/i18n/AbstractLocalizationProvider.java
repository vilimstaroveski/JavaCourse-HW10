package hr.fer.zemris.java.hw10.i18n;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementation of {@link ILocalizationProvider}. It has 
 * method getString() as abstract.
 * @author Vilim Staroveški
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Listeners of this localization provider.
	 */
	private List<ILocalizationListener> listeners;
	/**
	 * Creates new {@link AbstractLocalizationProvider}
	 */
	public AbstractLocalizationProvider() {

		this.listeners = new ArrayList<ILocalizationListener>();
	}
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		if(!(listeners.contains(listener))) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		if(listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}
	/**
	 * Informs listeners that change has happened.
	 */
	public void fire() {
		for(ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}
	
	@Override
	public abstract String getString(String tag);

}
