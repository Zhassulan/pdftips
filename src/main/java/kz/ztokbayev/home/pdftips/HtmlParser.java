package kz.ztokbayev.home.pdftips;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {

	private static Logger logger = LogManager.getLogger(HtmlParser.class);

	public void parseHtml(String htmlFilePath) {
		//logger.info("Start file " + htmlFilePath + " ----------------------------------");
		if (htmlFilePath != null) {
			File input = new File(htmlFilePath);
			if (input.canRead()) {
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
						String outPath = FilenameUtils.getFullPath(htmlFilePath);
						String csvFilePath = outPath + FilenameUtils.getBaseName(htmlFilePath) + ".csv";
						FileWriter writer = new FileWriter(csvFilePath);
						pText = content.getElementsByClass("p");
						List <String> strList = new ArrayList <String> ();
						for (Element txt : pText) {
							//logger.info(txt.text());
							strList.add(txt.text());
							//CSVUtils.writeLine(writer, Arrays.asList(txt.text()));
						}
						CSVUtils.writeLine(writer, strList);
						writer.flush();
				        writer.close();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		//logger.info("End file " + htmlFilePath + " ----------------------------------");
	}

}
