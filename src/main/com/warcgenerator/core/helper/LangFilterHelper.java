package com.warcgenerator.core.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarInputStream;

import org.apache.log4j.Logger;
import org.apache.lucene.misc.TrigramLanguageGuesser;
import org.jsoup.Jsoup;

import com.warcgenerator.core.datasource.common.bean.Country;
import com.warcgenerator.core.plugin.webcrawler.WebCrawlerHandler;

public class LangFilterHelper {
	private static Logger logger = Logger.getLogger
            (WebCrawlerHandler.class);
	public static boolean checkLanguageAllowed(String html,
			List<Country> languagesFilter) throws Exception {
		boolean allowed = true;

		if (languagesFilter.size() > 0) {
			String text = Jsoup.parse(html).text();
			String lang = TrigramLanguageGuesser.detectLanguage(text);
			logger.info("Lang detected: " + lang);
			Country c = new Country();
			c.setCode(lang);
			Locale l = new Locale(lang);
			c.setName(l.getDisplayLanguage());
			allowed = languagesFilter.contains(c);
		} else {
			logger.info("Language filter didn't specified");
		}
		
		return allowed;
	}

	public static List<Country> listNotSelectedLanguages(
			List<Country> countriesSelectedList) throws IOException {
		List<Country> notSelCountryList = new ArrayList<Country>();
		List<Country> listAvailableLang = listAvailableLanguagesFilter();
		for (Country countryAvailable : listAvailableLang) {
			if (!countriesSelectedList.contains(countryAvailable)) {
				notSelCountryList.add(countryAvailable);
			}
		}
		return notSelCountryList;
	}

	public static List<Country> listAvailableLanguagesFilter()
			throws IOException {
		List<Country> countryList = new ArrayList<Country>();
		JarInputStream jis = new JarInputStream(
				TrigramLanguageGuesser.class
						.getResourceAsStream("Trigrams.jar"));
		TrigramLanguageGuesser g = new TrigramLanguageGuesser(jis);

		for (String code : g.recognizedLanguages()) {
			Locale l = new Locale(code);
			countryList.add(new Country(code, l.getDisplayName()));
		}

		return countryList;
	}
}
