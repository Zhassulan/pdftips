package kz.ztokbayev.home.pdftips;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import kz.ugs.callisto.system.propertyfilemanager.PropsManager;

public class ParserFileService {
	
	private static Logger logger = LogManager.getLogger(ParserFileService.class);
	private MyConverter converter;
	private String storageFolder = PropsManager.getInstance().getProperty("storageFolder"); 
	
	public ParserFileService() {
		super();
		converter = new MyConverter();
		processFiles(storageFolder);
	}

	private void processFiles(String path) {
		File curDir = new File(path);
		File[] filesList = curDir.listFiles();
		for (File f : filesList) {
			if (f.isDirectory())
				processFiles(f.getPath());
			if (f.isFile() && FilenameUtils.getExtension(f.getAbsolutePath()).equals("pdf")) {
				String outPath = FilenameUtils.getFullPath(f.getAbsolutePath());
				String htmlFile = outPath + FilenameUtils.getBaseName(f.getName()) + ".html";
				
				ConvertPDFToXML.convertPdfToXML(f.getAbsolutePath());
				//converter.generateHTMLFromPDF(f.getAbsolutePath(), htmlFile);
				
				//HtmlParser hp = new HtmlParser();
				//hp.parseHtml(htmlFile);
				
				//converter.extractImage(f.getAbsolutePath(), outPath);
			}
		}
	}

}
