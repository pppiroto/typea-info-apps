package info.typea.jungler.operation;
import info.typea.jungler.generate.Operation;
import info.typea.jungler.type.*;

/**
 * AWSECommerceService Version : 2006-11-14
 * このクラスは、{@link info.typea.jungler.generate.AmazonEcsOperationClassGenerator}によって、自動生成されました。
 * created on Sat Jun 30 00:37:09 JST 2007
 * @see info.typea.jungler.generate.AmazonEcsOperationClassGenerator
 * @see info.typea.jungler.generate.OperationClassTemplete
 */
public class ListSearchOperation extends Operation {
	/** required fields */
	protected final String[] REQUIRED_FIELDS = {"ListType"};
	/** available ResponseGroups */
	public enum LISTSEARCH_RESPONSEGROUP {Request, ListInfo, ListMinimum};
	/** Constructor */
	public ListSearchOperation() {
		setParameter("Operation","ListSearch");
	}
	/** required parameter list */
	public String[] getRequiredFields() {
		return REQUIRED_FIELDS;
	}
	/** set ResponseGroup */
	public void setResponseGroup(LISTSEARCH_RESPONSEGROUP responseGroup) {
		super.setResponseGroup(responseGroup.toString());
	}
	/* modifires */
	public void setAWSAccessKeyId(String[] aWSAccessKeyId) {
		setParameter("AWSAccessKeyId", aWSAccessKeyId);
	}
	public void setAWSAccessKeyId(String aWSAccessKeyId) {
		setParameter("AWSAccessKeyId", aWSAccessKeyId);
	}
	public String[] getAWSAccessKeyId() {
		return getParameter("AWSAccessKeyId");
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
	public void setCity(String[] city) {
		setParameter("City", city);
	}
	public void setCity(String city) {
		setParameter("City", city);
	}
	public String[] getCity() {
		return getParameter("City");
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
	public void setEmail(String[] email) {
		setParameter("Email", email);
	}
	public void setEmail(String email) {
		setParameter("Email", email);
	}
	public String[] getEmail() {
		return getParameter("Email");
	}
	public void setFirstName(String[] firstName) {
		setParameter("FirstName", firstName);
	}
	public void setFirstName(String firstName) {
		setParameter("FirstName", firstName);
	}
	public String[] getFirstName() {
		return getParameter("FirstName");
	}
	public void setLastName(String[] lastName) {
		setParameter("LastName", lastName);
	}
	public void setLastName(String lastName) {
		setParameter("LastName", lastName);
	}
	public String[] getLastName() {
		return getParameter("LastName");
	}
	public void setListPage(String[] listPage) {
		setParameter("ListPage", listPage);
	}
	public void setListPage(String listPage) {
		setParameter("ListPage", listPage);
	}
	public String[] getListPage() {
		return getParameter("ListPage");
	}
	public void setListType(String[] listType) {
		setParameter("ListType", listType);
	}
	public void setListType(String listType) {
		setParameter("ListType", listType);
	}
	public String[] getListType() {
		return getParameter("ListType");
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
	public void setName(String[] name) {
		setParameter("Name", name);
	}
	public void setName(String name) {
		setParameter("Name", name);
	}
	public String[] getName() {
		return getParameter("Name");
	}
	public void setState(String[] state) {
		setParameter("State", state);
	}
	public void setState(String state) {
		setParameter("State", state);
	}
	public String[] getState() {
		return getParameter("State");
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
