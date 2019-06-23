package hr.fer.zemris.java.hw10.i18n;

/**
 * Class that connects localization provider with new localization
 * listener.
 * @author Vilim Staroveški
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Flag connected.
	 */
	private boolean connected;
	/**
	 * {@link ILocalizationListener}.
	 */
	private ILocalizationListener listener;
	/**
	 * {@link ILocalizationProvider} parent
	 */
	private ILocalizationProvider parent;
	/**
	 * Creates new {@link LocalizationProviderBridge}
	 * @param parent provider for localization
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		this.listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
		connect();
	}
	/**
	 * Connects parent localization provider with listener
	 */
	public void connect() {
		if(!connected) {
			connected = true;
			parent.addLocalizationListener(listener);
		}
	}
	/**
	 * Disconnects parent localization provider with listener
	 */
	public void disconnect() {
		if(connected) {
			connected = false;
			parent.removeLocalizationListener(listener);
		}
	}
	@Override
	public String getString(String tag) {
		return parent.getString(tag);
	}
}
