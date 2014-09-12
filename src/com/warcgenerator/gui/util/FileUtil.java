package com.warcgenerator.gui.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.warcgenerator.gui.exception.util.DestopNotFoundException;
import com.warcgenerator.gui.exception.util.UtilException;

public class FileUtil {
	public static void openInDefaultExplorer(String path) throws UtilException {
		File file = new File (path);
		Desktop desktop = Desktop.getDesktop();
		if (desktop != null) {
			try {
				desktop.open(file);
			} catch (IOException e) {
				throw new UtilException(e);
			}
		} else {
			throw new DestopNotFoundException();
		}
	}
}
