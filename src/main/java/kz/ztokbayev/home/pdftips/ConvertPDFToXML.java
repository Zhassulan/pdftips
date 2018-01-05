package kz.ztokbayev.home.pdftips;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PRIndirectReference;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

public class ConvertPDFToXML {

	private static StreamResult streamResult;
	private static TransformerHandler handler;
	private static AttributesImpl atts;
	private static Logger logger = LogManager.getLogger(ConvertPDFToXML.class);

	public static void convertPdfToXML(String pdfFilePath) {
		try {
			Document document = new Document();
			document.open();
			PdfReader reader = new PdfReader(pdfFilePath);
			PdfDictionary page = reader.getPageN(1);
			PRIndirectReference objectReference = (PRIndirectReference) page.get(PdfName.CONTENTS);
			PRStream stream = (PRStream) PdfReader.getPdfObject(objectReference);
			byte[] streamBytes = PdfReader.getStreamBytes(stream);
			@SuppressWarnings("deprecation")
			RandomAccessFileOrArray randomFile = new RandomAccessFileOrArray(streamBytes);
			PRTokeniser tokenizer = new PRTokeniser(randomFile);
			
			StringBuffer strbufe = new StringBuffer();
			
			String splitter = ";";
			String str = "";
			while (tokenizer.nextToken()) {
				if (tokenizer.getTokenType() == PRTokeniser.TokenType.START_ARRAY) {
				}
				if (tokenizer.getTokenType() == PRTokeniser.TokenType.END_ARRAY) {
					str += splitter;
					strbufe.append(str);
					str = "";
				}	
				if (tokenizer.getTokenType() == PRTokeniser.TokenType.STRING) {
					String s = tokenizer.getStringValue();
					//strbufe.append(s);
					//logger.info(s);
					str += s;
				}
			}
			String test = strbufe.toString();
			
			String outPath = FilenameUtils.getFullPath(pdfFilePath);
			String xmlFile = outPath + FilenameUtils.getBaseName(pdfFilePath) + ".xml"; 
			logger.info(xmlFile);
			streamResult = new StreamResult(xmlFile);
			initXML();
			process(test);
			closeXML();
			document.add(new Paragraph(".."));
			document.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void initXML() throws ParserConfigurationException, TransformerConfigurationException, SAXException {
		SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

		handler = tf.newTransformerHandler();
		Transformer serializer = handler.getTransformer();
		//serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		handler.setResult(streamResult);
		handler.startDocument();
		atts = new AttributesImpl();
		handler.startElement("", "", "data", atts);
	}

	private static void process(String s) throws SAXException {
		String[] elements = s.split("\\|");
		atts.clear();
		handler.startElement("", "", "Message", atts);
		handler.characters(elements[0].toCharArray(), 0, elements[0].length());
		handler.endElement("", "", "Message");
	}

	private static void closeXML() throws SAXException {
		handler.endElement("", "", "data");
		handler.endDocument();
	}
}
