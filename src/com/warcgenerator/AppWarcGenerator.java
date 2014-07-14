package com.warcgenerator;

import com.warcgenerator.core.exception.WarcException;
import com.warcgenerator.core.exception.config.ConfigException;
import com.warcgenerator.core.exception.datasource.OpenException;
import com.warcgenerator.core.exception.datasource.ReadException;
import com.warcgenerator.core.exception.datasource.WriteException;

/**
 * AppWarcGenerator
 * 
 * @author Miguel Callon
 */
public class AppWarcGenerator {
	/**
	 * Main method
	 * @param args
	 */
	public static void main (String args[]) {
		String confFilePath = "";
		if (args.length > 1) {
			System.out.println("Wrong number of parameters");
		} else if (args.length == 1) {
			confFilePath = args[0];
			System.out.println("Load configuration from:" + confFilePath);
		} else {
			System.out.println("Load default configuration");
		}
		
		AppWarc app = AppWarc.getInstance();
		
		try {
			app.execute(confFilePath);
		} catch (OpenException e) {
			System.out.println("Is not posible open data source. Check config.xml");
		} catch (ReadException e) {
			System.out.println("Is not posible read from data source. Check permission.");
		} catch (WriteException e) {
			System.out.println("Is not posible write in data source. Check permission.");
		} catch (ConfigException e) {
			System.out.println("Is not posible read configuration. Check config.xml"+e);
		} catch (WarcException e) {
			System.out.println(e);
		}
	}
}
