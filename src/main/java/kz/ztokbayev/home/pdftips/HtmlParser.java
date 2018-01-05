package kz.ztokbayev.home.pdftips;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {

	private static Logger logger = LogManager.getLogger(HtmlParser.class);

	public void parseHtml(String htmlFilePath) {
		logger.info("Start file " + htmlFilePath + " ----------------------------------");
		if (htmlFilePath != null)	{
			File input = new File(htmlFilePath);
			if (input.canRead())	{
				Document doc = null;
				try {
					doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
				if (doc != null) {
					Element content = doc.getElementById("page_0");
					Elements pText = null;
					try {
						pText = content.getElementsByClass("p");
						for (Element txt : pText) {
							logger.info(txt.text());
						}					
					}	catch (Exception e) {
						logger.error(e.getMessage(), e);
					}	
				}
			}
		}
		logger.info("End file " + htmlFilePath + " ----------------------------------");
	}

}
