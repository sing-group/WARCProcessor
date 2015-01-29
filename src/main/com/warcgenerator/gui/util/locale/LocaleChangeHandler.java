package com.warcgenerator.gui.util.locale;

import java.util.Vector;

public class LocaleChangeHandler {
	Vector localeChangeListeners;
	
	public synchronized void addLocaleChangeListener(LocaleChangeListener l) {
		Vector v = localeChangeListeners == null ? new Vector(2)
				: (Vector) localeChangeListeners.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			localeChangeListeners = v;
		}
	}

	public void fireLocaleChanged(LocaleChangeEvent e) {
		if (localeChangeListeners != null) {
			Vector listeners = localeChangeListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++)
				((LocaleChangeListener) listeners.elementAt(i))
						.localeChanged(e);
		}
	}
}
