package metier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class CourM {
	private String stringHtml;
	
	public CourM(int numCours) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	      try {
	         DocumentBuilder builder = factory.newDocumentBuilder();
	         File fileXML = new File("Cours1.xml");
	         Document xml = builder.parse(fileXML);
	         Element root = xml.getDocumentElement();
	         XPathFactory xpf = XPathFactory.newInstance();
	         XPath path = xpf.newXPath();
	         
	         String expression  = "//cours[@num='"+numCours+"']";
	         this.stringHtml =  (String)path.evaluate(expression, root);
      
	      } catch (ParserConfigurationException e) {
	         e.printStackTrace();
	      } catch (SAXException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (XPathExpressionException e) {
	 		e.printStackTrace();
          }
	}
	
	public String getStringHtml() {
		return stringHtml;
	}
		
}
