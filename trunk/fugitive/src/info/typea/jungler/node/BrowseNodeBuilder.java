package info.typea.jungler.node;

import info.typea.fugitive.logging.LogUtil;
import info.typea.jungler.operation.BrowseNodeLookupOperation;
import info.typea.jungler.xml.XmlUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BrowseNodeBuilder {
	private static final Logger log = LogUtil.getLogger(BrowseNodeBuilder.class);
	
	protected static XPathExpression expr;
	protected int maxDepth;
	static {
		try {
			XPath xpath = XmlUtil.getInstance().getXPath();
			expr = xpath.compile(
					"//*[local-name()='BrowseNodes']" +
					"/*[local-name()='BrowseNode']" +
					"/*[local-name()='Children']" +
					"/*[local-name()='BrowseNode']"
				);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}			
	}
	public static void main(String[] args) {
		try {
			BrowseNodeBuilder me = new BrowseNodeBuilder();
			me.buildBrowseNode("492128", "Œ¾ŒêŠw", 1);
			
			log.info("finish");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Node buildBrowseNode(Node rootNode, int maxDepth) throws Exception {
		
		this.maxDepth = maxDepth;
		
		buildBrowseNodeTree(rootNode, 0);
		
		return rootNode;

	}
	public Node buildBrowseNode(String rootBrowseNodeId, String rootBrowseNodeName, int maxDepth) throws Exception {
		
		Node rootNode = new Node(rootBrowseNodeId, rootBrowseNodeName);
		return buildBrowseNode(rootNode, maxDepth);

	}
	public Node buildBrowseNode(String rootBrowseNodeId, String rootBrowseNodeName) throws Exception {
		
		return buildBrowseNode(rootBrowseNodeId, rootBrowseNodeName, -1);
	}

	
	private void buildBrowseNodeTree(Node node, int depth) throws IOException, MalformedURLException, ParserConfigurationException, SAXException, XPathExpressionException {
		
		if (maxDepth >=0 && depth > maxDepth) return;
		node.setComplete(true);
		
		BrowseNodeLookupOperation op = new BrowseNodeLookupOperation();
		op.setBrowseNodeId(node.getId());
		String url = op.toRestRequest();
		//TODO Proxy Setting
		URLConnection conn = new URL(url).openConnection();
		Document doc = XmlUtil.getInstance().getDocument(conn.getInputStream(), 1000);
	
		NodeList nodes = (NodeList)expr.evaluate(doc,XPathConstants.NODESET);
		for (int i=0; i<nodes.getLength(); i++) {
			NodeList childs = nodes.item(i).getChildNodes();
			String id = childs.item(0).getTextContent();
			String name = childs.item(1).getTextContent();
			Node child = new Node(id, name);
			child.setParent(node);
			node.addChild(child);
			buildBrowseNodeTree(child, depth + 1);
			
			log.info(indent(depth + 1, "--") + child.toString());
		}
	}
	
	public void printBrowseNodeTree(Node node, int level, PrintWriter writer) {
		
		writer.println(indent(level,"--") + node.toString());
		writer.flush();
		List<Node> nodes = node.getChilds();
		for (Node child : nodes) {
			printBrowseNodeTree(child, level + 1, writer);
		}
	}
	
	private String indent(int len, String s) {
		StringBuilder buf = new StringBuilder();
		for (int i=0; i<len ;i++) {
			buf.append(s);
		}
		return buf.toString();
	}

	public int getMaxDepth() {
		return maxDepth;
	}
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
}
