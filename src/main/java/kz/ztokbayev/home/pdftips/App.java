package kz.ztokbayev.home.pdftips;

import kz.ugs.callisto.system.propertyfilemanager.PropsManager;

/**Стартовая точка
 * @author Zhass
 *
 */
public class App	{
	

    public static void main( String[] args )	{
    	String pdfFilePath = "C:\\Temp\\file4\\Abdulgapparov036.pdf";
    	String outDir = "C:\\Temp\\file4\\";
        
    	ParserFileService pfs = new ParserFileService();
    	
    	//MyConverter.generateHTMLFromPDF(pdfFilePath, outDir + "Abdulgapparov036.html");
        //MyConverter.generateImageFromPDF(pdfFilePath, outDir);
        //MyConverter.extractImage(pdfFilePath, outDir);
    }
}
