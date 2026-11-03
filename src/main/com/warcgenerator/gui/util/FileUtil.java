package com.warcgenerator.gui.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.warcgenerator.gui.exception.util.DestopNotFoundException;
import com.warcgenerator.gui.exception.util.UtilException;

public class FileUtil {
	public static void openInDefaultExplorer(String path) throws UtilException {
		if (!Desktop.isDesktopSupported()) {
			throw new DestopNotFoundException();
		}
		try {
			Desktop.getDesktop().open(new File(path));
		} catch (IOException | IllegalArgumentException | UnsupportedOperationException e) {
			throw new UtilException(e);
		}
	}
}
