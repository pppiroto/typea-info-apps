package info.typea.jungler.operation;
import info.typea.jungler.generate.Operation;
import info.typea.jungler.type.*;

/**
 * AWSECommerceService Version : 2006-11-14
 * このクラスは、{@link info.typea.jungler.generate.AmazonEcsOperationClassGenerator}によって、自動生成されました。
 * created on Sat Jun 30 00:37:08 JST 2007
 * @see info.typea.jungler.generate.AmazonEcsOperationClassGenerator
 * @see info.typea.jungler.generate.OperationClassTemplete
 */
public class HelpOperation extends Operation {
	/** required fields */
	protected final String[] REQUIRED_FIELDS = {"About", "HelpType"};
	/** available ResponseGroups */
	public enum HELP_RESPONSEGROUP {Request, Help};
	/** Constructor */
	public HelpOperation() {
		setParameter("Operation","Help");
	}
	/** required parameter list */
	public String[] getRequiredFields() {
		return REQUIRED_FIELDS;
	}
	/** set ResponseGroup */
	public void setResponseGroup(HELP_RESPONSEGROUP responseGroup) {
		super.setResponseGroup(responseGroup.toString());
	}
	/* modifires */
	public void setAbout(String[] about) {
		setParameter("About", about);
	}
	public void setAbout(String about) {
		setParameter("About", about);
	}
	public String[] getAbout() {
		return getParameter("About");
	}
	public void setAssociateTag(String[] associateTag) {
		setParameter("AssociateTag", associateTag);
	}
	public void setAssociateTag(String associateTag) {
		setParameter("AssociateTag", associateTag);
	}
	public String[] getAssociateTag() {
		return getParameter("AssociateTag");
	}
	public void setContentType(String[] contentType) {
		setParameter("ContentType", contentType);
	}
	public void setContentType(String contentType) {
		setParameter("ContentType", contentType);
	}
	public String[] getContentType() {
		return getParameter("ContentType");
	}
	public void setHelpType(String[] helpType) {
		setParameter("HelpType", helpType);
	}
	public void setHelpType(String helpType) {
		setParameter("HelpType", helpType);
	}
	public String[] getHelpType() {
		return getParameter("HelpType");
	}
	public void setMarketplace(String[] marketplace) {
		setParameter("Marketplace", marketplace);
	}
	public void setMarketplace(String marketplace) {
		setParameter("Marketplace", marketplace);
	}
	public String[] getMarketplace() {
		return getParameter("Marketplace");
	}
	public void setMarketplaceDomain(String[] marketplaceDomain) {
		setParameter("MarketplaceDomain", marketplaceDomain);
	}
	public void setMarketplaceDomain(String marketplaceDomain) {
		setParameter("MarketplaceDomain", marketplaceDomain);
	}
	public String[] getMarketplaceDomain() {
		return getParameter("MarketplaceDomain");
	}
	public void setStyle(String[] style) {
		setParameter("Style", style);
	}
	public void setStyle(String style) {
		setParameter("Style", style);
	}
	public String[] getStyle() {
		return getParameter("Style");
	}
	public void setValidate(String[] validate) {
		setParameter("Validate", validate);
	}
	public void setValidate(String validate) {
		setParameter("Validate", validate);
	}
	public String[] getValidate() {
		return getParameter("Validate");
	}
	public void setVersion(String[] version) {
		setParameter("Version", version);
	}
	public void setVersion(String version) {
		setParameter("Version", version);
	}
	public String[] getVersion() {
		return getParameter("Version");
	}
	public void setXMLEscaping(String[] xMLEscaping) {
		setParameter("XMLEscaping", xMLEscaping);
	}
	public void setXMLEscaping(String xMLEscaping) {
		setParameter("XMLEscaping", xMLEscaping);
	}
	public String[] getXMLEscaping() {
		return getParameter("XMLEscaping");
	}
}
