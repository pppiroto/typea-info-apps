package info.typea.jungler.generate;

import info.typea.fugitive.logging.LogUtil;
import info.typea.jungler.xml.XmlUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;


import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class AmazonEcsOperationClassGenerator {
	private static final Logger log = LogUtil.getLogger(AmazonEcsOperationClassGenerator.class);
	
	private static String BASE_DIR = "C:\\Program Files\\eclipse3.2.2\\workspace\\fugitive\\src\\info\\typea\\jungler\\operation\\";
//	private static String BASE_DIR = "C:\\work\\AmazonEcsClassese\\info\\typea\\jungler\\operation\\";
	
	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				BASE_DIR = args[0];
			}
			if (BASE_DIR == null) {
				System.out.println("usage AmazonEscOperationClassGenerator [base path]");
				System.exit(1);
			}
			File baseDir = new File(BASE_DIR);
			if (!baseDir.exists()){
				baseDir.mkdirs();
			}
			
			AmazonEcsOperationClassGenerator me = new AmazonEcsOperationClassGenerator();
			
			for (String operation : OPERATIONS) {
				
				log.info("generating operation java file [" + operation + "]");
				String url = me.getOperationHelpRest(operation);
				
				log.info("url:[" + url + "]");
				File out = new File(BASE_DIR + operation + "Operation.java");
				me.generate(new URL(url).openConnection(), new FileOutputStream(out));
			}
			
			log.info("finish. Amazon Ecs Operation java file genarate.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getOperationHelpRest(String operationName) {
		Operation ope = new Operation();
		
		ope.setParameter("Operation", "Help");
		ope.setParameter("About", operationName);
		ope.setParameter("HelpType", "Operation");

		return ope.toRestRequest();
	}
	
	public void generate(URLConnection amazonUrlConn, OutputStream out) throws Exception {
		
		Document doc = XmlUtil.getInstance().getDocument(amazonUrlConn.getInputStream());
		XPath xpath = XmlUtil.getInstance().getXPath();
		
		XPathExpression exprName 
			= xpath.compile(
				"//*[local-name()='Information']" +
				"/*[local-name()='OperationInformation']" +
				"/*[local-name()='Name']/text()"
			);
		OperationClassTemplete templete 
			= new OperationClassTemplete(exprName.evaluate(doc));

		XPathExpression exprVer 
			= xpath.compile(
				"//*[local-name()='OperationRequest']" +
				"/*[local-name()='Arguments']" +
				"/*[local-name()='Argument'][@Name='Version']/@Value"
			);
		templete.setAmazonEcsVersion(exprVer.evaluate(doc));
		
		templete.setPackageName("info.typea.jungler.operation");

		XPathExpression reqParms 
			= xpath.compile(
				"//*[local-name()='Information']/" +
				"/*[local-name()='OperationInformation']/" +
				"/*[local-name()='RequiredParameters']/" +
				"/*[local-name()='Parameter']/text()"
			);
		
		NodeList reqNodes = (NodeList)reqParms.evaluate(doc,XPathConstants.NODESET);
		for (int i=0; i<reqNodes.getLength(); i++) {
			String key = reqNodes.item(i).getNodeValue();
			templete.addParameter(key);
			templete.addRequiredParameter(key);
		}
		
		XPathExpression exprParms 
			= xpath.compile(
				"//*[local-name()='Information']/" +
				"/*[local-name()='OperationInformation']/" +
				"/*[local-name()='AvailableParameters']/" +
				"/*[local-name()='Parameter']/text()"
			);
		
		NodeList paramNodes = (NodeList)exprParms.evaluate(doc,XPathConstants.NODESET);
		for (int i=0; i<paramNodes.getLength(); i++) {
			templete.addParameter(paramNodes.item(i).getNodeValue());
		}
		
		
		XPathExpression exprResponseGroups 
			= xpath.compile(
				"//*[local-name()='Information']/" +
				"/*[local-name()='OperationInformation']/" +
				"/*[local-name()='AvailableResponseGroups']/" +
				"/*[local-name()='ResponseGroup']/text()"
			);
		
		NodeList responseGroups = (NodeList)exprResponseGroups.evaluate(doc,XPathConstants.NODESET);
		for (int i=0; i<responseGroups.getLength(); i++) {
			templete.addAvailableResponseGroup(responseGroups.item(i).getNodeValue());
		}
		
		templete.output(out);
	}
	
	public void test(String url) throws Exception {
		log.info(url);
		
		URLConnection conn = (new URL(url)).openConnection();
		conn.connect();
		
		XPathFactory xfactory = XPathFactory.newInstance();
		XPath xpath = xfactory.newXPath();
		//XPathExpression expr = xpath.compile("//*[local-name()='Parameter']/text()");
		
		XPathExpression exprVer 
			= xpath.compile(
				"//*[local-name()='OperationRequest']" +
				"/*[local-name()='Arguments']" +
				"/*[local-name()='Argument'][@Name='Version']/@Value"
			);
		log.info(exprVer.evaluate(new InputSource(conn.getInputStream())));
		
		log.info("finish.");
	}

	public static final String[] OPERATIONS = {
		  "BrowseNodeLookup"
		, "CartAdd"
		, "CartClear"
		, "CartCreate"
		, "CartGet"
		, "CartModify"
		, "CustomerContentLookup"
		, "CustomerContentSearch"
		, "Help"
		, "ItemLookup"
		, "ItemSearch"
		, "ListLookup"
		, "ListSearch"
		, "MultiOperation"
		, "SellerListingLookup"
		, "SellerListingSearch"
		, "SellerLookup"
		, "SimilarityLookup"
		, "TransactionLookup"	
	};
}
