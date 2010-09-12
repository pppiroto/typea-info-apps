package info.typea.jungler.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Administrator
 */
public class XmlUtil {
	protected static XmlUtil me;
	protected XmlUtil() {
	}
	/**
	 * @return
	 */
	public static XmlUtil getInstance() {
		if (me == null) {
			me = new XmlUtil();
		}
		return me;
	}
	/**
	 * @param in
	 * @param wait
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document getDocument(InputStream in, long wait) 
		throws ParserConfigurationException, SAXException, IOException {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getDocument(in);
	}
	
	/**
	 * @param in
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document getDocument(InputStream in) 
		throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(in));
	}
	
	/**
	 * @param url
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document getDocument(URL url) 
		throws ParserConfigurationException, SAXException, IOException {
		
		URLConnection conn = url.openConnection();
		conn.connect();
		
		return this.getDocument(conn.getInputStream());
	}
	
	/**
	 * @return
	 */
	public XPath getXPath() {
		XPathFactory xfactory = XPathFactory.newInstance();
		return xfactory.newXPath();
	}
	
	public static String toXPathExper(String simplePath) {
		simplePath = simplePath.replaceAll("/([^/]+)", "/\\*[local-name()='$1']");
		simplePath = simplePath.replaceAll("^/", "//");
		return simplePath;
	}
	
}
